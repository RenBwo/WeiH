package bd.DAO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import bd.connection.getcon;

public class InitParams {
	public getcon conn = new getcon();
    private ResultSet 	rs0,rs01;
	public int 	firstitemid = 0 ,finterid = 0,bomVerifyError = 1,tab_exist=0 ;
	public int 	materialVerifyError = 1 ,adiVerifyError=1;
	
    public String currentperiod="" ,currentyear="",itemname="",model="",oem=""  ;
    public double packagesize=0,unpackagesize=0,gainrate=0,itselfQtySaled=0,modelQtySaled=0;
    public double width=0,length=0,capacity = 0;

  
    /* 
     * 启动参数：产品代码，当前会计期间
     */
   	public void SomeArguments(String sfnumber) throws SQLException
   	{ 	rs0 = conn.query("",";select a.fitemid,a.fname,a.fmodel,a.f_131,a.fsize from t_icitem a "
   				+ " join icbom b  on a.fitemid = b.fitemid and b.fusestatus = 1072 "
   				+ " and b.fstatus = 1 and a.fnumber = '"+sfnumber+ "'");
		if (rs0.next()) {
			firstitemid = rs0.getInt("fitemid");	//System.out.println("firstitemid :"+firstitemid);
			itemname	= rs0.getString("fname");	//System.out.println("itemname :"+itemname);
			oem 		= rs0.getString("f_131");	//System.out.println("oem   :"+oem );
			model 		= rs0.getString("fmodel");	//System.out.println("model :"+model);
			packagesize = rs0.getDouble("fsize");	//System.out.println("packagesize: "+packagesize);
			
			createTable();
			/*
			 * INSERT INTO t_bdStandCostRPT
			 */
			String command5 = ";insert into t_bdStandCostRPT(fproditemid,fdate)"
					+ " values( "+firstitemid+ ",getdate() )";
			conn.update("",command5);
			//System.out.println("insert new data row into  t_bdStandCostRPT ok");
			rs0 = conn.query("",";select max(finterid) from t_bdStandCostRPT");
			rs0.next();	
			finterid = (rs0.wasNull() ? 1 : rs0.getInt(1)) ;//System.out.println("销售报价表内码: "+finterid);
			
			rs0 = conn.query("",";select  fvalue from t_systemprofile where fkey like 'currentyear' and fcategory = 'GL'");
	        rs0.next();
		    currentyear = rs0.getString(1);//System.out.println("currentyear: "+currentyear);
		
		    rs0 = conn.query("",";select fvalue from t_systemprofile where fkey like 'currentperiod' and fcategory = 'GL'");
			rs0.next();
			currentperiod = rs0.getString(1);//System.out.println("currentperiod : "+currentperiod );
			/*
			 * update price
			 */
			UpdatePrice();	//System.out.println("升级辅料最新单价，设备最新折旧和功率信息 " );
			/* 
			 * BOM EXPOSE AND VERIFY
			 */
			BomExpose();	//System.out.println("finish BOMexpose  " );
			verifyBOM();	//System.out.println("bomVerifyError : "+bomVerifyError );
			
			/* 
			 *THE LENGTH AND WIDTH OF UNPACKAGED PRODUCT
			 */
			LenWid();				//System.out.println("unpackagelength : ["+length+"]unpackageWidth:"+width);
			/* 
			 * CAPACITY 钎焊炉
			 */
			CapacityQHL(length*1000,width*1000);	//System.out.println("钎焊炉产能 : "+capacity);
			/*
			 * gainRate depends on class
			 */
			rs01 = conn.query("",";select round(f_101,6) from t_item_3015 "
				+ " where '01.'+fnumber =  left('"+sfnumber+"',5) ");		
			if (rs01.next())	{gainrate = rs01.getDouble(1);}		//System.out.println("gainrate: "+gainrate); 
			/*
			 * itselfitselfQtySaled,modelQtySaled
			 */
			rs01 = conn.query("",";select sum(b.fqty) from icsale a join icsaleentry b on a.finterid = b.finterid "
					+ " and a.fstatus > 0 and a.fcancellation = 0 and b.fitemid = "+firstitemid);		
			if (rs01.next())	{itselfQtySaled = rs01.getDouble(1);}
			else {itselfQtySaled =0; } 	//System.out.println("itselfQtySaled: "+itselfQtySaled);

			String cmdModelQtySaled = ";select sum(fqty) from icsale a join icsaleentry b "
					+ " on a.finterid = b.finterid and a.fstatus > 0 and a.fcancellation = 0  "
					+ " join t_icitem c on c.fitemid = b.fitemid and left(c.fmodel,5) like left('"+model+"',5)"
					+ " group by left(c.fmodel,5)";
			rs01 = conn.query("",cmdModelQtySaled );
			if (rs01.next())	{modelQtySaled = rs01.getDouble(1);}	 	
			else {modelQtySaled = 0 ;}    //System.out.println("modelQtySaled: "+cmdModelQtySaled +"[[[["+modelQtySaled);
			rs01.close();
			}
		else {firstitemid = 0;}
       rs0.close();
       } 		
    /* 
     * 检查报价表和BOM多级展开表，如果没有，创建。
     */
   	public void createTable() throws SQLException
	{  
   		/*  报价表*/
   		/*conn.update("","drop table t_bdStandCostRPT;");*/
   		rs0 = conn.query("",";select count(*) from sysobjects where type = 'u' and name like 't_bdStandCostRPT'");
   		rs0.next();
   		tab_exist = rs0.getInt(1);
   			if (tab_exist == 0)
   			{ 
   				String  command4 = ";CREATE TABLE t_bdStandCostRPT"
   						 + "("
   						 + " fid					int"  			//K3BOS单据id
  						 + ",fClasstypeId			int"  			//K3BOS单据	Classtypeid
  						 + ",fBillno				varchar(50)"  	//K3BOS BILLNO
   						 + ",finterid    			int Identity(00000001,1) not null"
   						 + ",fproditemid 			int"			//产品物料内码
   						 + ",fdate					datetime"		//核价日期
   						 
   						 + ",fCostProduce		 	decimal(20,4)"  //一.生产成本合计（1+2+3）
   						 
   						 + ",fCostMaterial 			decimal(20,4)"  //1.直接材料成本
   						   						 
   						 + ",fCostDirectLabor		decimal(20,4)"  //2.直接人工
   						 + ",fCostPay 				decimal(20,4)"  //2.1直接人工-工资
   						 + ",fCostAssure			decimal(20,4)"  //2.2直接人工-保险 
   						  						 
   						 + ",fCostMake			 	decimal(20,4)"  //3.制造费用（合计）
   						 + ",fCostMake_Pay 			decimal(20,4)"  //3.1制造费用-间接人工
   						 + ",fCostMake_Power	 	decimal(20,4)"  //3.2制造费用-电费
   						 + ",fCostMake_Adi	 		decimal(20,4)"  //3.3制造费用-辅料
   						 + ",fCostMake_deprication 	decimal(20,4)"  //3.4制造费用-设备折旧
   						 + ",fCostMake_Jig		 	decimal(20,4)"  //3.5制造费用-工装模具折旧
   						 + ",fCostMake_others	 	decimal(20,4)"  //3.6制造费用-其他成本 
   						 + ",fCostHouse			 	decimal(20,4)"  //3.7厂房折旧 
   						 
   						 + ",fCostProidInland		decimal(20,4)"  //二.期间费用合计（内贸）
   						 + ",fCostProidFobQD		decimal(20,4)"  //二.期间费用合计（FOB青岛）
   						 + ",fCostBadProd			decimal(20,4)"  //成品不良成本
   						 + ",fCostFinance		 	decimal(20,4)"  //财务费用
   						 + ",fCostSail				decimal(20,4)"  //销售费用
   						 + ",fCostManage		 	decimal(20,4)"  //管理费用
   						 + ",fCostLand				decimal(20,4)"  //管理费用--土地摊销
   						 + ",fCostNewModel			decimal(20,4)"  //新模具工装费用 
   						 + ",QtyShare				decimal(20,4)" 	//新开模具摊销数量
   						 + ",itselfQtySaled			decimal(20,4)" 	//历史销售数量，取已经审核的销售发票    						    						 
   						 + ",modelQtySaled			decimal(20,4)" 	//型号历史销售数量，取已经审核的销售发票   						 
   						 + ",fCostTransportInland	decimal(20,4)" 	//运输费用 （内贸）  						 
   						 + ",fCostTransportFobQD	decimal(20,4)"  //运输费用（FOB青岛）
   						 
   						 
   						 + ",fCostStandInland		decimal(20,4)"  //标准成本=生产成本合计+期间费用合计（内贸）
   						 + ",fCostStandFobQD		decimal(20,4)"  //标准成本=生产成本合计+期间费用合计（FOB青岛）
   						 + ",fGainInland			decimal(20,4)"  //产品利润（内贸）
   						 + ",fGainFobQD			 	decimal(20,4)"  //产品利润（FOB青岛）
   						 
   						 + ",fCostTotalInland		decimal(20,4)"  //核价（内贸）
   						 + ",fCostTotalFobQD		decimal(20,4)"  //核价（FOB青岛）RMB
   						 
   						 + ",fincrementTax			decimal(20,4)"  //增值税
   						 
   						 + ",fPrice_China_InTax_Delay	decimal(20,4)"  //出厂核价 含税  RMB Days 30
   						 + ",fPrice_FobQD_Delay_USD	 	decimal(20,4)" //出厂核价 含税  USD Days 60 FOB青岛	 
   						 
   						 + ",fResPriceK0		decimal(20,4)" //电费价格
   						 + ",fAssureK1			decimal(20,4)" //直接人工_保险系数
   						 + ",fMakePayK2			decimal(20,4)" //制造费用_间接人工系数
   						 + ",fBadProdK4			decimal(20,4)" //产品不良系数
   						 + ",fFinanceK5			decimal(20,4)" //财务费用系数
   						 + ",fManageK6			decimal(20,4)" //管理费用系数
   						 + ",fSailK7			decimal(20,4)" //销售费用系数
   						 + ",fGainRateK9		decimal(20,4)" //产品利润率
   						 + ",fTaxRateK10		decimal(20,4)" //国内增值税率
   						 + ",fDelayDaysK11		decimal(20,4)" //国内账期
   						 + ",fDelayDaysK22		decimal(20,4)" //外贸账期   						 
   						 + ",fExchangeRateK12	decimal(20,4)" //预算汇率
   						 + ",fInterestRateK13	decimal(20,4)" //贷款利率
   						 + ",fwashingK14		decimal(20,4)" //清洗费用  
   						 + ")";
   				 conn.update("",command4);
   			}
   		/*
   		* BOM多级展开表 BDBomMulExpose
   		*/
   		rs0 = conn.query("",";select count(*) from sysobjects where type = 'u' and name like 'BDBomMulExpose'");
   		rs0.next();
   		tab_exist = rs0.getInt(1);
   		if (tab_exist ==0)
   		{ 
   			String command1 = ";CREATE TABLE BDBomMulExpose"
   					+ "("
   					+ "firstitemid int"
   					+ ",flevel varchar(101)"
   					+ ",FParentID int"
   					+ ",FItemID int"
   					+ ",fQtyPer decimal(10,4)"
   					+ ",fQty decimal(10,4)"
   					+ ",funitid int"
   					+ ",fscrap decimal(10,4)"
   					+ ",fitemsize varchar(10)"
   					+ ",fbominterid int"
   					+ ",fbomnumber varchar(20)"
   					+ ",sn varchar(300)"
   					+ ",fstatus int"
   					+ ",fusestatus int"
   					+ ",flevel0 varchar(101)"
   					+ ",froutingid int"
   					+ ",finterid int"
   					+ ",enditem int"
   					+ ",maketype int"
   					+ ")" ;
   		
   		conn.update("",command1);
   		}   				
   		/* 
   		* 直接材料成本表   	TABLE t_CostMaterialBD
   		*/
   		rs0 = conn.query("",";select count(*) from sysobjects where type = 'u' and name like 't_CostMaterialBD'");
   		rs0.next();
   		tab_exist = rs0.getInt(1);
   		if (tab_exist == 0)
   		{	String  command1 = ";CREATE TABLE t_CostMaterialBD"
   						 + "("
   						 + "FLevel 		int not null default 0"
   						 + ",FParentID 	int"
   						 + ",FItemID 		int"
   						 + ",fQtyPer 		decimal(20,4)"
   						 + ",fQty 		decimal(20,4)"
   						 + ",fitemsize 	varchar(60)"
   						 + ",Fnumber 		varchar(60)"
   						 + ",FbomInterID 	int"
   						 + ",fprice 		decimal(20,4)"
   						 + ",fAmtMaterial decimal(20,4)"
   						 + ",fproditemid  int"
   						 + ",finterid     	int"
   						 + ")";
   				 conn.update("",command1);
   			}
   			/*
   			 * 制造费用与直接工资表 TABLE t_BDLabourAndMake
   			 */
   			rs0 = conn.query("",";select count(*) from sysobjects where type = 'u' and name like 't_BDLabourAndMake'");
   			rs0.next();
   			tab_exist = rs0.getInt(1);
   			if (tab_exist == 0)
   			{
   				String  command1 = ";CREATE TABLE t_BDLabourAndMake"
   						 + "("
   						 + "foperid 	int not null default 0,"
   						 + "fopersn	int,"
   						 + "Fnumber 	varchar(60),"
   						 + "assyname	varchar(60),"
   						 + "fQty decimal(20,4),"					 
   						 + "fpiecerate decimal(20,4),"					
   						 + "fopername varchar(60),"
   						 + "frate decimal(20,4),"
   						 + "fmakeqty decimal(20,4),"
   						 + "amtpay decimal(20,4),"
   						 + "amtassure decimal(20,4),"
   						 + "costworker decimal(20,4),"
   						 + "fmatpower decimal(20,4),"
   						 + "fdepr decimal(20,4),"
   						 + "famtadi decimal(20,4),"
   						 + "famtmodel decimal(20,4),"
   						 + "fproditemid  int,"
   						 + "finterid    	int"
   						 + ")";
   				 conn.update("",command1);
   				 tab_exist = 0;
   			}
   			}
   	/* 
   	 * BOM EXPOSE 
   	 */
	public void BomExpose() throws SQLException
	{//conn.update("","TRUNcate table  BDBomMulExpose;");/*清除数据*/	 
		 int level = 0;
		 String cmddel=";delete from BDBomMulExpose where  firstitemid = "+firstitemid 
					+" and finterid = "+finterid ;
		 conn.update("",cmddel);
		 String command20 = ";with recursive_cte as ("
		 		+ " select "+ firstitemid+ " as firstitemid"
		 		+ ",cast("+level+ " as varchar(101)) as flevel "
		 		+ ",0 as FParentID,a.FItemID as fitemid"
		 		+ ",cast(1 as decimal(12,4) ) as fQtyPerPro "
		 		+ ",cast(1  as decimal(12,4) )as fQty"
		 		+ ",a.funitid as funitid "
		 		+ ",cast(0 as decimal(10,4) ) as fscrap "
		 		+ ",cast('' as varchar(10)) as  fitemsize"
		 		+ ",a.FInterID as fbominterid"
		 		+ ",a.fbomnumber"
		 		+ ",cast('1' as varchar(300)) as sn"
		 		+ ",a.fstatus,a.fusestatus , cast ('.' as varchar(101)) as point"
		 		+ ","+finterid+" as finterid, 0 as enditem"
				+ " from icbom a  "
				+ " where a.fusestatus = 1072 and a.fstatus = 1 and a.fitemid = "+firstitemid
				+ " union all "
				+ " select "+ firstitemid+ " as firstitemid "
				+ " ,cast(cast(c.flevel as int)+1 as varchar(101)) as flevel"
				+ " ,a.fitemid as FParentID,b.FItemID as fitemid"
				+ " ,cast(b.fqty as decimal(12,4) ) as fQtyPerPro"
				+ " ,cast(round(b.fqty*c.fqty/(1-b.fscrap*0.01),4)  as decimal(12,4) ) as fQty "
				+ " ,b.funitid as funitid,cast(b.fscrap as decimal(10,4)) as fscrap "
				+ " ,cast(b.fitemsize as varchar(10)) as  fitemsize "
				+ " ,a.FInterID as fbominterid,a.fbomnumber "
				+ " ,cast(c.sn+right('000'+cast(b.fentryid as varchar(20)),3) as varchar(300)) as sn"
				+ " ,a.fstatus,a.fusestatus,cast(c.point+'......' as varchar(101)) as point "
		 		+ " ,"+finterid+" as finterid, 0 as enditem"
				+ " from icbom a join icbomchild b on a.finterid = b.finterid and a.fusestatus = 1072 "
				+ " and a.fstatus = 1 "
				+ " join recursive_cte c on a.fitemid = c.fitemid "
				+ " where a.fitemid = c.fitemid "
				+ " ) "
				+ " select * into #bom from recursive_cte  order by sn  ";
		 String upenditem=";update #bom set enditem = 1 where fitemid in ("
		 		+ " select  fitemid  from #bom  where fitemid not in (select fparentid from #bom))";
		 String command21 = ";insert into BDBomMulExpose(firstitemid,flevel,flevel0,FParentID"
		 		+ " ,FItemID,fQtyPer,fQty,funitid,fscrap,fitemsize,fbominterid,fbomnumber"
		 		+ ",sn,fstatus,fusestatus,froutingid,finterid,enditem,maketype)" 
			    + " select a.firstitemid,a.flevel,a.point+a.flevel,a.FParentID"
			    + ", a.FItemID,a.fQtyPerpro,a.fQty,a.funitid,a.fscrap,a.fitemsize, a.fbominterid,a.fbomnumber"
			    + ",a.sn,a.fstatus,a.fusestatus,d.froutingid,a.finterid,a.enditem,b.ferpclsid"
		 		+ " from #bom a join t_icitem b on a.fitemid = b.fitemid"
		 		+ " left join icbom d on a.fitemid = d.fitemid and d.fusestatus = 1072 and d.fstatus = 1";
		 String cmddroptab=";drop table #bom"; 
		 conn.update("",command20+upenditem+command21+cmddroptab);		 
		 //System.out.println("bomexpose:::: "+command20+upenditem+command21+cmddroptab);
	}
	/*
	 * BOM Verify	
	 */
	public void verifyBOM() throws SQLException{
		String cmdverify =";select count(*) from BDBomMulExpose where enditem = 1 and maketype = 2"
				+ " and firstitemid = "+firstitemid +" and finterid = "+finterid;
		rs0 = conn.query("",cmdverify);
		rs0.next();
		bomVerifyError = rs0.getInt(1);
	}
	/*
	 * verify direct_material price验证直接材料价格
	 */
	public void verifyMaterialPrice() throws SQLException{	
		String cmdverify =";select count(*) from t_CostMaterialBD where fproditemid= "+firstitemid 
				+" and finterid = "+finterid +" and isnull(fprice,0)=0 ";
		rs0=conn.query("", cmdverify);
		rs0.beforeFirst();
		rs0.next();
		materialVerifyError =rs0.getInt(1);
	}
	/*
	 * verify adi price
	 */
	public void verifyAdiPrice() throws SQLException {
		String cmdverify=";select count(*) "
	  + " from BDBomMulExpose 			t1"
	  + " join t_Routing 				t2 	on t1.fitemid = t2.fitemid "
	  + " and t1.firstitemid ="+firstitemid+ " and t1.finterid = "+finterid
      + " and  t2.finterid = t1.froutingid"
      + " join t_routingoper 			t3 	on t2.finterid = t3.finterid "
      + " join t_CostCalculateBD 		t4 	on t4.foperno = t3.foperid "
      + " join t_costcalculatebd_entry1	t5 	on t4.fid = t5.fid and isnull(t5.fprice,0) = 0 "
      + " and isnull(t5.fqty,0) <> 0 ";
		rs0=conn.query("", cmdverify);
		rs0.beforeFirst();
		rs0.next();
		adiVerifyError =rs0.getInt(1);//System.out.println("verifyad:"+cmdverify );
	}
	/* 
	 * 芯体长宽 单位 米	
	 * 长：喷锌扁管'30.%' 下级物料扁管盘料 --03.a18.%--的坯料尺寸
	 * 宽：集流管--'%集流管%'--下级物料圆铝--'03.a01.%' or '03.a19.%--用量/圆铝米重
	 */
	public void LenWid() throws SQLException{
		
			rs0 = conn.query("",";select max(a.fitemsize) "
					+ " from BDBomMulExpose a "
					+ " join t_icitem b on a.fparentid  = b.fitemid and a.firstitemid =  "+firstitemid+" "
					+ " and a.finterid = "+finterid
					+ " where b.fnumber like '30.%' and isnull(a.fitemsize,'0') > '0' and "
					+ " a.fitemid in  (select fitemid from t_icitem where fnumber like '03.a18.%')");
			rs0.next();	
			length = rs0.getDouble(1);
			//System.out.println("裸包产品长： "+length);
			rs0 = conn.query("","; select max(round(a.fqtyper/c.f_140,4)) as y "
					+ " from BDBomMulExpose a "
					+ " join t_icitem b on a.fparentid  = b.fitemid and a.firstitemid = "+firstitemid+" and a.finterid = "+finterid
					+ " join t_icitemcustom c on c.fitemid = a.fitemid "
					+ " where b.fname like '%集流管%' and "
					+ " isnull(c.f_140,0) >0 and isnull(a.fqtyper,'0') > '0' and "
					+ " a.fitemid in (select fitemid from t_icitem"
					+ " where fnumber like '03.a01.%' or fnumber like '03.a19.%')");
			rs0.next();
			width = rs0.getDouble(1);
			//System.out.println("裸包产品宽： "+ width);
		}
	/* 
	 * 钎焊炉产能 unit mm
	 */
	public double  CapacityQHL(double len,double wid) throws SQLException
	{   BigDecimal bd ;
		while(400<len && len<=900 && 900<wid )//1
		{bd = new BigDecimal(1000*60/(wid+240));
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		capacity = Double.parseDouble(bd.toString());
		return capacity;}
		while( len <=400 && 900<wid) //2
		{bd = new BigDecimal(2*1000*60/(wid+240));
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		capacity = Double.parseDouble(bd.toString());
		return capacity;}
		while(900<len && 400<wid && wid<=900) //3
		{bd = new BigDecimal(1000*60/(len+240));
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		capacity = Double.parseDouble(bd.toString());
		return capacity;}
		while(900<len && wid<=400 ) //4
		{bd = new BigDecimal(2*1000*60/(len+240));
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		capacity = Double.parseDouble(bd.toString());
		return capacity;}
		while(400<len && len<=900 && 400<wid  && wid<=900  ) //5
		{bd = new BigDecimal(1000*60/(wid+240) > 1000*60/(len+240)?1000*60/(wid+240):1000*60/(len+240));
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		capacity = Double.parseDouble(bd.toString());
		return capacity;}
		while(len<=400 && 400<wid  && wid<=900  ) //6
		{bd = new BigDecimal(2*1000*60/(wid+240) > 1000*60/(len+240)?2*1000*60/(wid+240):1000*60/(len+240));
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		capacity = Double.parseDouble(bd.toString());
		return capacity;}
		while(400<len  && len<=900 && wid<=400 ) //7
		{bd = new BigDecimal(2*1000*60/(len+240) > 1000*60/(wid+240)?2*1000*60/(len+240):1000*60/(wid+240));
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		capacity = Double.parseDouble(bd.toString());
		return capacity;}
		while( len<=400 && wid<=400 ) //8
		{bd = new BigDecimal(2*1000*60/(len+240) > 2*1000*60/(wid+240)?2*1000*60/(len+240):2*1000*60/(wid+240));
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		capacity = Double.parseDouble(bd.toString());
		return capacity;}
		
		while(900<len && 900< wid) //9
		{//System.out.println("超过900毫米的产品，超出了钎焊炉的工艺范围");
		return capacity;}
		return capacity;
		}
	/*
	 * 	升级辅料单价，设备最新折旧和功率信息
	 * 1 过去一年的采购发票蓝字平均不含税单价格  原始数据是未含税
	 * 2 计划价格								原始数据是未含税
	 */
	public void UpdatePrice() throws SQLException {
		String sql0 = "; update a set a.fprice = isnull(w.avrprice,isnull(b.fplanprice,0))"
				+ " from t_costcalculatebd_entry1 	a"
				+ " join t_icitem 					b	on a.faidnumber  = b.fitemid "
				+ " left join  (select b.fitemid ,sum(b.fstdamount) as funtaxamount, sum(b.fqty) as fqty"
						+ ",case  when sum(b.fqty)>0 then round(sum(b.fstdamount)/sum(b.fqty),4) else 0 end as avrprice "
						+ " from icpurchase a join icpurchaseentry b on a.FInterID = b.FInterID"
						+ " and ( a.ftrantype = 75 or a.ftrantype = 76 ) "
						+ " where a.fcheckdate > dateadd(year,-1,dateadd(day,datediff(day,0,getdate()),0)) "
						+ " and isnull(b.fqty , 0)<> 0 and isnull(b.fstdamount ,0) <> 0 and a.fstatus = 1 "
						+ " and a.frob =1 and (isnull(fheadselfi0252,0) =0 and isnull(fheadselfi0349,0 ) = 0) "
						+ " group by b.fitemid "
				+ ") 								w	on w.fitemid = a.faidnumber ";
		//System.out.println("升级辅料单价：" + sql0);
		conn.update("",sql0);
		String sql1 = "; update t_costcalculatebd_entry0 set fpower=b.fpowercap,fassetinterid=b.fassetnumber"
				+ ",fmanname=b.fdevicename,fmachinespection=b.fspecification"
				+ " from t_costcalculatebd_entry0 a join icdeviceaccount b on a.fmanum = b.fdevicenumber";
		//System.out.println("升级设备信息：" + sql1);
		conn.update("",sql1);
		sql1 = "; update t_costcalculatebd_entry0 set fdepreciation = c.fdeprshould"
				+ " from t_costcalculatebd_entry0 a "
				+ " join (select fassetid,max(fdeprperiods) as fdeprperiods"
				+ " from t_fabalance "
				+ "  where fdeprshould > 0 group by fassetid) b on b.fassetid = a.fassetinterid"
				+ " join t_fabalance c on c.fdeprperiods = b.fdeprperiods and c.fassetid = a.fassetinterid";
		conn.update("",sql1);//System.out.println("升级设备折旧信息："+sql1);
		
	}

}
