package bd.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LabourAndMake {
	public void get() throws SQLException
	{
		createTable();
		set();
	}
	/*
	 * 制造费用与直接工资表 TABLE t_BDLabourAndMake
	 */
	private void createTable()  throws SQLException
	{
		rs0 = conn.query(";select count(*) from sysobjects where type = 'u' and name like 't_BDLabourAndMake'");
		if(rs0.next() && rs0.getInt(1) >0 ) 
   		{
			clean();
			//System.out.println("table_LabourAndMake exists ");
   		}
		else
		{
			String  command1 = ";CREATE TABLE t_BDLabourAndMake"
   						 + "("
   						 + "foperid 		int not null default 0"
   						 + ",fopersn		int"
   						 + ",Fnumber 		varchar(60)"
   						 + ",assyname		varchar(60)"
   						 + ",fQty 			decimal(20,4)"					 
   						 + ",fpiecerate 	decimal(20,4)"					
   						 + ",fopername 		varchar(60)"
   						 + ",frate 			decimal(20,4)"
   						 + ",fmakeqty 		decimal(20,4)"
   						 + ",amtpay 		decimal(20,4)"
   						 + ",amtassure 		decimal(20,4)"
   						 + ",costworker 	decimal(20,4)"
   						 + ",fmatpower 		decimal(20,4)"
   						 + ",fdepr 			decimal(20,4)"
   						 + ",famtadi 		decimal(20,4)"
   						 + ",famtmodel 		decimal(20,4)"
   						 + ",fproditemid  	int"
   						 + ",finterid    	int"
   						 + ",createdate 	datetime"
   						 + ")";
			conn.update(command1);
			conn.close();
			//System.out.println("create table_LabourAndMake success ");
   		}
	}
	/*
	 * cleanLabourAndMake
	 */
	public void clean() throws SQLException
	{
		String cleanLabourAndMake=";delete from t_BDLabourAndMake where fproditemid = "
	     	 +ProductInfo.firstitemid +
	     	 " and ( finterid = "+ProductInfo.finterid
	     	 +" or datediff(day,createdate,getdate()) >2)";
			//System.out.println("清除历史数据:"+cleanLabourAndMake);
	    conn.update(cleanLabourAndMake);
	    conn.close();
	}
	/* 
	 * 制造费用与直接工资
	 * 直接生产成本=直接人工+制造费用（人工）+设备电费与折旧+材料费用+工装模具费用
	 */
	private void set() 	throws SQLException	
		{
			/* 直接人工		 */
			String sql0 = ";select d.foperid,d.fopersn,b.fnumber ,b.fname as assyname,"
			+ " (case isnull((select 1 from t_icitem where fitemid = a.fitemid "
			+ " and fname like '扁管盘料'),0) "
			+ " when 1 then round(a.fqty*(100-a.fscrap)/100/a.fqtyper,0) else a.fqty end) as fqty,"
			+ " e.fname as opername,"
			+ " d.fpiecerate,d.fentryselfz0236 as frate,"
			+ " d.fentryselfz0237 as fmakeqty ,	"
			+ " (case isnull((select 1 from t_icitem where fitemid = a.fitemid and fname "
			+ " like '扁管盘料'),0) "
			+ " when 1 then round(a.fqty*(100-a.fscrap)/100/a.fqtyper,0) else a.fqty end)"
			+ "*d.fpiecerate*(case d.fentryselfz0236 when 0 then 1 else d.fentryselfz0236 "
			+ " end)*d.fentryselfz0237 as amtpay,"
			+Coefficient.k01 + "*(case isnull((select 1 from t_icitem where fitemid = a.fitemid "
					+ " and fname like '扁管盘料'),0) "
			+ " when 1 then round(a.fqty*(100-a.fscrap)/100/a.fqtyper,0) else a.fqty end)"
			+ "*d.fpiecerate*(case d.fentryselfz0236 when 0 then 1 else d.fentryselfz0236 "
			+ " end)*d.fentryselfz0237 as amtassure,"
			+ " (case isnull((select 1 from t_icitem where fitemid = a.fitemid "
			+ " and fname like '扁管盘料'),0) "
			+ " when 1 then round(a.fqty*(100-a.fscrap)/100/a.fqtyper,0) else a.fqty end)"
			+ "*d.fpiecerate*(case d.fentryselfz0236 when 0 then 1 else d.fentryselfz0236"
			+ " end)*d.fentryselfz0237*(1+ "+Coefficient.k01+ ")*"+Coefficient.k02+ " as cost_worker "
			+ " into #t_costworker "
			+ " from BDBomMulExpose 	a "
			+ " join t_icitem 			b 	on a.fitemid = b.fitemid and a.firstitemid = "
			+ProductInfo.firstitemid+" and a.finterid = "+ProductInfo.finterid
			+ " join t_Routing  		c 	on c.finterid = a.froutingid"
			+ " join t_routingoper 		d 	on c.finterid = d.finterid "
			+ " left join t_submessage 	e 	on e.finterid = d.foperid and e.fparentid = 61 "
			+ " where b.ferpclsid <> 1  "
			+ " and  a.sn not in (select a.sn  from BDBomMulExpose 	a  "
					+ "	join t_icitem b on a.fitemid = b.fitemid  and b.ferpclsid <> 1 "
					+ " and a.firstitemid = "+ProductInfo.firstitemid+" and a.finterid = "+ProductInfo.finterid
					+ " join (select a.sn from BDBomMulExpose  a join icbom b  "
					+ " on a.fitemid = b.fitemid  and a.firstitemid = "+ProductInfo.firstitemid
					+" and a.finterid = "+ProductInfo.finterid+" and b.fbomskip = 1058 "
					+ " and b.fusestatus = 1072 )   a0 	on a.sn  like a0.sn+'%')  " ;
			//System.out.println("工艺路线，直接人工 : "+sql0);
			
			/*能源费用（电费与燃气费，以下用电费代表能源费）与折旧
			 * 钎焊焊接工艺40336 之钎焊炉设备的电费：=单位入炉产品体积用电量×入炉产品体积×电价 201809变更点
			 * =（单位用电量FelectricPerV×电价+用气量FGasPerV×气价）×入炉产品体积
			 * 
			 * 其它设备的电费 = 零件数量×工件数量×(功率×0.4×电价/产能)
			 * =零件数量*工件数量×功率×0.4×电价/产能
			 * =t1.fqty×t4.fentryselfz0237×t6.fpower×0.4×工业电价/产能	
			 * 		 
			 * 钎焊焊接工艺40336产能： 	根据产品不同计算 CapacityIndividual()
			 * 扁管压出工艺40494产能： 	根据产品不同计算 60*扁管压出速度/扁管长度（坯料尺寸）;
			 * 扁管切断工艺 40495产能：	根据芯体长度不同计算 
			 * 						400mm < l 			then 7800 pc/h
			 * 						400mm <= l <600mm	then 6600 pc/h
			 * 						600mm <= l <800mm	then 5400 pc/h
			 * 						800mm <= l <1000mm	then 4200 pc/h
			 * 						1000mm <= l 		then 2400 pc/h
			 * 其余工艺产能：	 		根据工艺不同计算，标准产能/工资系数 fcapacity/t4.fentryselfz0236
			 * 
			 * 折旧：取最后一期折旧/同一固定资产编号下的设备数量
			 * 固定资产编号:同一固定资产编号同一设备,取最后日期的FALTERID,如纯净水设备
			 * */
			String sql1 =" ;select t4.foperid,t4.fopersn,t2.fnumber ,t2.fname as assyname,"
					+ " (case isnull((select 1 from t_icitem where fitemid = t1.fitemid "
					+ " and fname like '扁管盘料'),0) "
					+ " when 1 then round(t1.fqty*(100-t1.fscrap)/100/t1.fqtyper,0) "
					+ " else t1.fqty end) as fqty,"
			+ " t5.fname as opername,t4.fpiecerate,t4.fentryselfz0236 as frate, "
			+ " t4.fentryselfz0237 as fmakeqty, "
			+ "  t6.FmanName,"
			/*
			 * 电费
			 */
			+ " isnull("
			/*
			 * 钎焊炉设备电费与气费在产品信息里计算，此处为0 因为有双芯体，
			 * 13代码的芯体长宽高是双芯体合计数，所以不能在这里计算
			 */
			+ "(case when (isnull(t6.FelectricPerV,0) > 0 or isnull(t6.FGasPerV,0) >0) "
				+ "then 0 else  "
				+ " (case isnull((select 1 from t_icitem where fitemid = t1.fitemid "
					+ " and fname like '扁管盘料'),0) "
			/*
			 * 扁管盘料需要进行单位转换
			 */
			 	+ " when 1 then round(t1.fqty*(100-t1.fscrap)/100/t1.fqtyper,0)"
			/*
			 * 其他零部件数量
			 */
			 	+ " else t1.fqty end)" 
			 + "*t4.fentryselfz0237*t6.fpower*0.4*"+Coefficient.k00+ "/"
			/*
			 * 产能
			 */
			 	+ "(case t4.foperid when 40336 then " + MachineInfo.capacity			
			/*钎焊炉工艺 40336*/
			 		+ " when 40494 then round(t11.fpressrate*60/"+ProductInfo.length+ ",4)" /*扁管压出工艺40494*/
			 		+ " when 40495 then (case "									/*扁管切断工艺 40495*/
			 			+ " when " +ProductInfo.length*1000 +" < 400 then 7800 "
			 			+ " when " +ProductInfo.length*1000 +">=400 and "+ProductInfo.length*1000 +"< 600 then 6600"
			 			+ " when " +ProductInfo.length*1000 +">=600 and "+ProductInfo.length*1000 +"< 800 then 5400"
			 			+ " when " +ProductInfo.length*1000 +">=800 and "+ProductInfo.length*1000 +"< 1000 then 4200"
			 			+ " else  2400 end  )" 								
			/*其余工艺*/
					+ " else round( t6.fcapacity/(case t4.fentryselfz0236 when 0 then 1 "
						+ " else t4.fentryselfz0236 end),4) "						
				+ "end) end)"					
			+ " ,0 ) as famtpower,  " 									
			/*折旧*/
			+ " isnull("
			+ " (case isnull((select 1 from t_icitem where fitemid = t1.fitemid"
			+ " and fname like '扁管盘料'),0) "
			+ " when 1 then round(t1.fqty*(100-t1.fscrap)/100/t1.fqtyper,0)"
			+ " else t1.fqty end)" 										/*零部件数量*/
			+ "*t4.fentryselfz0237*t6.fdepreciation/(t101.fnum*30*8*"
			+ "(case t4.foperid when 40336 then " + MachineInfo.capacity        	/*钎焊炉工艺 40336*/
			+ " when 40494 then round(t11.fpressrate*60/"+ProductInfo.length+ ",4)" /*扁管压出工艺40494*/
			+ " when 40495 then (case when " +ProductInfo.length*1000 +" < 400 "	/*扁管切断工艺 40495*/
					+ " then 7800 "			
					+ " when " +ProductInfo.length*1000 +">=400 and "+ProductInfo.length*1000 +"< 600 then 6600"
					+ " when " +ProductInfo.length*1000 +">=600 and "+ProductInfo.length*1000 +"< 800 then 5400"
					+ " when " +ProductInfo.length*1000 +">=800 and "+ProductInfo.length*1000 +"< 1000 then 4200"
					+ " else  2400 end )" 								
			+ " else round( t6.fcapacity/(case t4.fentryselfz0236 when 0 then 1 "
			+ " else t4.fentryselfz0236 end),4) end) "/*其余工艺*/
			+ "),0) as fdepr " 
			+ " into #t_machine "
			+ " from BDBomMulExpose 				t1  "
			+ " join t_icitem 						t2 		on t1.fitemid = t2.fitemid "
			+ " and t1.firstitemid = "+ProductInfo.firstitemid+" and t1.finterid = "+ProductInfo.finterid		 
			+ " join t_Routing 						t3 		on t3.finterid = t1.froutingid"
			+ " join t_routingoper 					t4 		on t3.finterid = t4.finterid "		
			+ " left join t_submessage 				t5 		on t5.finterid = t4.foperid"
			+ "	and t5.fparentid = 61 "		
			+ " left join (select b.fassetinterid,a.foperno,b.fmanname,b.fpower,b.fcapacity"
				+ ",b.fdepreciation,b.funit,b.FelectricPerV,b.FGasPerV" 
				+ " from t_CostCalculateBD	a join	t_costcalculatebd_entry0 b on a.fid = b.fid	"
				+ ") 								t6		on t6.foperno = t4.foperid "
			+ " left join (select a.fassetid,max(a.falterid) as falterid,b.fnum,b.fassetnumber"
				+ " from t_FAAlter a join t_facard b on a.falterid = b.falterid and b.fnum > 0"
				+ " group by a.fassetid,b.fnum,b.fassetnumber    "
				+ ") 								t101	on  t6.fassetinterid = t101.fassetid"
			+ " left join t_costcalculate_flatpipe  t11		on t11.fpipenumber = t1.fitemid "
			+ " and t11.fcheckbox = 0"	
			+ " where t2.ferpclsid <> 1  "
			+ " and  t1.sn not in (select a.sn  from BDBomMulExpose 	a  "
			+ "	join t_icitem b on a.fitemid = b.fitemid  and b.ferpclsid <> 1 "
			+ " and a.firstitemid = "+ProductInfo.firstitemid+" and a.finterid = "+ProductInfo.finterid
			+ " join (select a.sn from BDBomMulExpose  a join icbom b  "
				+ " on a.fitemid = b.fitemid  and a.firstitemid = "+ProductInfo.firstitemid
				+" and a.finterid = "+ProductInfo.finterid+" and b.fbomskip = 1058 "
				+ " and b.fusestatus = 1072 )   a0 	on a.sn  like a0.sn+'%')  ";	
			//System.out.println("电费与折旧:"+sql1);
			/*辅料费用 用量×单价
			 * 用量：
			 * 钎焊工艺40336：	钎剂、液氮用量/小时产能； 					201809取消
			 * 钎焊工艺40336：	钎剂、液氮标准用量（m^3/m^3）×入炉产品体积 	201809增加
			 * 焊膏、纯净水：		零部件数量×工件数量×标准用量（为每个产品平摊，手工录入)
			 * 芯体烘干喷漆 40142 根据颜色不同，选择不同的用料,
			 * 集流管长<=200MM，	零部件数量×工件数量×标准用量(为普通产品的1/2);
			 * 扁管压出工艺40494 	零部件数量×工件数量×标准用量(为锌丝用量*扁管长度)；
			 * 其他为： 			零部件数量×工件数量×标准用量
			 */
			String sql2 =" ;select t4.foperid,t4.fopersn,t2.fnumber ,t2.fname as assyname"
			+ ",(case isnull((select 1 from t_icitem where fitemid = t1.fitemid "
				+ " and fname like '扁管盘料'),0) "
				+ "when 1 then round(t1.fqty*(100-t1.fscrap)/100/t1.fqtyper,0)"
				+ " else t1.fqty end) as fqty"
			+ ",t5.fname as opername,t4.fpiecerate,t4.fentryselfz0236 as frate"		
			+ ",t4.fentryselfz0237 as fmakeqty, "
			+ " t8.faidname,isnull(t8.fprice,0) as fprice,"
			+ " isnull("
			/*
			 * 用量
			 */
			+ "(case "
			/*
			 * 钎剂、液氮 在辅料信息计算，因为有双芯体而裸包产品的长宽高是双芯体合计数，
			 * 这里进行计算的话，就多算了。
			 */
			+ " when (t4.foperid = 40336 and (t8.faidname like '%钎剂%' "
			+ " or t8.faidname like'%液氮%'))"
			+ " then 0 else "			
			/*
			 * 零部件数量×工件数量
			 */
			+ "(case isnull((select 1 from t_icitem where fitemid = t1.fitemid  "
			+ "and fname like '扁管盘料'),0)  "
			+ "when 1 then round(t1.fqty*(100-t1.fscrap)/100/t1.fqtyper,0) "
			+ "else t1.fqty end)*t4.fentryselfz0237*"
			/*
			 * 锌丝用量
			 */
			+ "(case when (t4.foperid = 40494 and t8.faidname like '%锌丝%' )"
			+ " then t11.fqtyzn/1000*"+ProductInfo.length
			/*
			 * 集流管长<=200MM 用量
			 */
			+ " when (t4.foperid = 40142 and "+ProductInfo.length+"<=0.2 ) then t8.fqty/2 "
			/*
			 * 其它
			 */
			+ " else t8.fqty  end)"
			+ " end)"
			/*
			 * 单价
			 */
			+ "*t8.Fprice,0) as famtAdi "		
			+ " into #t_adi "		
			+ " from BDBomMulExpose 				t1  "
			+ " join t_icitem 						t2 	on t1.fitemid = t2.fitemid"
			+ "  and t1.firstitemid = "+ProductInfo.firstitemid+" and t1.finterid = "+ProductInfo.finterid
			+ " join t_Routing 						t3	on t3.finterid = t1.froutingid "		
			+ " join t_routingoper 					t4 on t3.finterid = t4.finterid "		
			+ " left join t_submessage 				t5 on t5.finterid = t4.foperid	"
			+ " and t5.fparentid = 61 "		
			+ " left join t_CostCalculateBD 		t6 on t6.foperno = t4.foperid "		
			+ " left join t_costcalculatebd_entry1 	t8 on t6.fid = t8.fid "
			+ " and (t8.fcolor = 0 or (t8.fcolor = t2.f_135 and t2.fitemid = ("
				+ " select t34.fitemid "
				+ " 		from t_icitemcore t33 join BDBomMulExpose t34 on t34.firstitemid = "
				+ProductInfo.firstitemid+" and t34.finterid = "+ProductInfo.finterid
				+ " 		and t34.fitemid = t33.fitemid and t33.fnumber like '13.%' ))) "		
			+ " left join t_costcalculate_flatpipe  t11		on t11.fpipenumber = t1.fitemid "
			+ " and t11.fcheckbox = 0"
			+ " where t2.ferpclsid <> 1  "
			+ " and  t1.sn not in (select a.sn  from BDBomMulExpose 	a  "
					+ "	join t_icitem b on a.fitemid = b.fitemid  and b.ferpclsid <> 1 "
					+ " and a.firstitemid = "+ProductInfo.firstitemid+" and a.finterid = "+ProductInfo.finterid
					+ " join (select a.sn from BDBomMulExpose  a join icbom b  "
						+ " on a.fitemid = b.fitemid  and a.firstitemid = "+ProductInfo.firstitemid
						+" and a.finterid = "+ProductInfo.finterid+" and b.fbomskip = 1058 "
						+ " and b.fusestatus = 1072 )   a0 	on a.sn  like a0.sn+'%')  ";	
			//System.out.println("辅料："+sql2);
			/*工装模具费用：零件数量*工件数量*模具费用/模具产能
			 * 扁管压出工艺40494模具产能：模具最大使用量/(扁管长度*米克重)
			 * 其它工艺
			 * */
			String sql3 =" ;select t4.foperid,t4.fopersn,t2.fnumber ,t2.fname as assyname,"
			+ " (case isnull((select 1 from t_icitem where fitemid = t1.fitemid "
				+ " and fname like '扁管盘料'),0) "
				+ " when 1 then round(t1.fqty*(100-t1.fscrap)/100/t1.fqtyper,0) "
				+ " else t1.fqty end) as fqty,"
			+ " t5.fname as opername,t4.fpiecerate,t4.fentryselfz0236 as frate,"
			+ " t4.fentryselfz0237 as fmakeqty, "	
			+ " (case t4.foperid when 40494 then t11.fmodelname else t6.fnamemodel end)"
				+ " as fnamemodel,"
			+ " (case isnull((select 1 from t_icitem where fitemid = t1.fitemid"
				+ " and fname like '扁管盘料'),0) "
				+ " when 1 then round(t1.fqty*(100-t1.fscrap)/100/t1.fqtyper,0) else t1.fqty end)"
				+ "*t4.fentryselfz0237*"
				+ "(case t4.foperid when 40494 then round(t11.famtmodel*"+ProductInfo.length
					+ "*1000*t13.f_140/t11.cap,10)"/* 扁管压出工艺40494模具产能：模具最大使用量/(扁管长度*米克重)*/
					+ " else round(t6.famtperoper,10) end ) as famtmodel "		
			+ " into #t_model "		
			+ " from BDBomMulExpose t1  "
			+ " join t_icitem t2 on t1.fitemid = t2.fitemid and t1.firstitemid = "
			+ProductInfo.firstitemid+" and t1.finterid = "+ProductInfo.finterid	
			+ " join t_Routing t3 on t3.finterid = t1.froutingid "		
			+ " join t_routingoper t4 on t3.finterid = t4.finterid "		
			+ " left join t_submessage t5 on t5.finterid = t4.foperid	and t5.fparentid = 61 "		
			+ " left join (select a.foperno,b.fnamemodel,b.famtperoper,b.famtmodel "
									+ " from t_CostCalculateBD	a join	t_costcalculatebd_entry2 b"
									+ " on a.fid = b.fid"
									+ ") t6 on t6.foperno = t4.foperid and t6.foperno <> 40494 "		
			+ " left join (select t001.fpipenumber,'挤压模具' as fmodelname,t001.fmaxqtymodel as cap"
			+ ",t003.famtmodel as famtmodel "
			+ " 			from t_costcalculate_flatpipe 		t001  "
			+ " 			left join t_CostCalculateBD 		t002 on t002.foperno = 40494 "
			+ " 			left join t_costcalculatebd_entry2 	t003 on t002.fid = t003.fid"
			+ " and t003.FNameModel like '%挤压模%'"
			+ " 			where fcheckbox = 0"
			+ " 			union "
			+ " 			select t001.fpipenumber,'挤压轮' as fmodelname,50000000 as cap "
			+ ",t003.famtmodel as famtmodel "
			+ " 			from t_costcalculate_flatpipe 		t001  "
			+ " 			left join t_CostCalculateBD 		t002 on t002.foperno = 40494  "
			+ " 			left  join t_costcalculatebd_entry2 	t003 on t002.fid = t003.fid"
			+ " and t003.FNameModel like '%挤压轮%' "
			+ " 			where fcheckbox = 0)  t11 on t11.fpipenumber = t1.fitemid"
			+ " and t4.foperid = 40494 "
			+ " left join t_icitemcustom t13 on t13.fitemid = t1.fitemid and t4.foperid = 40494"		
			+ " where t2.ferpclsid <> 1 "
			+ " and  t1.sn not in (select a.sn  from BDBomMulExpose 	a  "
					+ "	join t_icitem b on a.fitemid = b.fitemid  and b.ferpclsid <> 1"
					+ " and a.firstitemid = "+ProductInfo.firstitemid+" and a.finterid = "+ProductInfo.finterid
					+ " join (select a.sn from BDBomMulExpose  a join icbom b  "
						+ " on a.fitemid = b.fitemid  and a.firstitemid = "+ProductInfo.firstitemid
						+" and a.finterid = "+ProductInfo.finterid+" and b.fbomskip = 1058 "
						+ " and b.fusestatus = 1072 )   a0 	on a.sn  like a0.sn+'%')  ";
			//System.out.println("JIG费用:"+sql3);
			
			String sql4 =";select foperid,fopersn,fnumber,assyname,sum(fqty) as fqty,opername"
					+ ",fpiecerate,frate,"
			+ " fmakeqty,sum(amtpay) as amtpay, sum(amtassure) as amtassure"
			+ ",sum(cost_worker) as costworker "		
			+ " into #t_20 "		
			+ " from #t_costworker "		
			+ " group by foperid,fopersn,fnumber,assyname,opername,fpiecerate,frate,fmakeqty"		
			
			+ "; select foperid,fopersn,fnumber,assyname,sum(fqty) as fqty,opername,fpiecerate"
			+ ",frate,fmakeqty,"
			+ " sum(famtpower) as fmatpower,sum(fdepr) as fdepr "
			+ " into #t_21 "		
			+ " from #t_machine  "		
			+ " group by foperid,fopersn,fnumber ,assyname,opername,fpiecerate,frate,fmakeqty"
			
			+ " ;select foperid,fopersn,fnumber,assyname,sum(fqty) as fqty,opername,fpiecerate"
			+ ",frate,"
			+ " fmakeqty,sum(famtadi) as famtadi "
			+ " into #t_22  "		
			+ " from #t_adi "		
			+ " group by foperid,fopersn,fnumber,assyname,opername,fpiecerate,frate,fmakeqty "
			
			+ " ;select foperid,fopersn,fnumber,assyname,sum(fqty) as fqty,opername,fpiecerate"
			+ ",frate,"
			+ " fmakeqty,sum(famtmodel) as famtmodel "
			+ " into #t_23"
			+ " from #t_model "		
			+ " group by foperid,fopersn,fnumber,assyname,opername,fpiecerate,frate,fmakeqty  "	;	
			
			String sql5 =";insert into t_BDLabourAndMake( fproditemid,finterid,foperid,fopersn"
					+ ",fnumber,assyname,fqty,fopername,fpiecerate,"
			+ " frate,fmakeqty,amtpay,amtassure,costworker,fmatpower,fdepr,famtadi,famtmodel) "		
			+ " select "+ProductInfo.firstitemid+ ","+ProductInfo.finterid+ ",t1.foperid,t1.fopersn,t1.fnumber"
					+ ",left(t1.assyname,30),t1.fqty,t1.opername,t1.fpiecerate,"
			+ " t1.frate,t1.fmakeqty,t1.amtpay,t1.amtassure,t1.costworker,t2.fmatpower"
			+ ",t2.fdepr,t3.famtadi,t4.famtmodel "
			+ " from #t_20 t1 "
			+ " left join #t_21 t2 on t1.foperid=t2.foperid and t1.fnumber = t2.fnumber"
			+ " and t1.fopersn = t2.fopersn "		
			+ " left join #t_22 t3 on t1.foperid=t3.foperid and t1.fnumber = t3.fnumber"
			+ " and t1.fopersn = t3.fopersn "		
			+ " left join #t_23 t4 on t1.foperid=t4.foperid and t1.fnumber = t4.fnumber"
			+ " and t1.fopersn = t4.fopersn "
			+ " ;drop table #t_model, #t_adi, #t_machine, #t_costworker,#t_20"
			+ ", #t_21,  #t_22, #t_23";	

			//System.out.println("直接人工与制造费用明细:"+sql0+sql1+sql2+sql3+sql4+sql5);
			conn.update(sql0+sql1+sql2+sql3+sql4+sql5);
			conn.close();
		}
		
	private DBConnect conn =new DBConnect();
	private ResultSet 	rs0;

}
