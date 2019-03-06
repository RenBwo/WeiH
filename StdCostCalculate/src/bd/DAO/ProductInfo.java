package bd.DAO;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductInfo {
	public void productInfoBeforeBOM(String fnumber)
	{
		try
		{
			getFirstItemID(fnumber);
			getFinterID();
			getItemName();
			getModel();
			getOEM();
			getGainRate(fnumber);
			getPackageSize() ;
			conn.close();
		}
		catch (SQLException e)
		{
			
		}
	}
	public void productInfoAfterBOM()
	{
		try
		{
			getQtySaled();
			getAmtNewJig();
			getQtyAmortizeNewJig();
			getAmtAmortizeNewJig();
			getLen();
			getWid();
			getHgt();
			getVolumn();
			verifyLWH();
			conn.close();
		}
		catch (SQLException e)
		{
			
		}
	}
	/*
	 * fitemid
	 */
	private void getFirstItemID(String fnumber) throws SQLException
	{
		String cmdGetFirstItemID=";select a.fitemid from t_icitem a "
	  			+ " join icbom b  on a.fitemid = b.fitemid and b.fusestatus = 1072 "
	  			+ " and b.fstatus = 1 and a.fnumber like '"+fnumber+ "'";
   		rs0 = conn.query(cmdGetFirstItemID);
		if (rs0.next()) 
		{
			firstitemid = rs0.getInt(1);
		}
		else
		{
			firstitemid =0;
		}
		rs0.close();
	}
	/*
	 * finterid
	 */
	private void getFinterID() throws SQLException
	{
		String command5 = ";insert into t_bdStandCostRPT(fproditemid,fdate)"
				+ " values( "+firstitemid+ ",getdate() )";
		conn.update(command5);
		
		rs0 = conn.query(";select max(finterid) from t_bdStandCostRPT where fproditemid ="
						+firstitemid);
		if(rs0.next()) 
		{
			finterid = (rs0.wasNull() ? 1 : rs0.getInt(1)) ;
		}
		else
		{
			finterid = 0;
		}
		rs0.close();		
	}
	/*
	 * fitmename
	 */
	private void getItemName() throws SQLException
	{
		String cmdGetItemName=";select left(a.fname,30)  from t_icitem a "
	  			+ " join icbom b  on a.fitemid = b.fitemid and b.fusestatus = 1072 "
	  			+ " and b.fstatus = 1 and a.fitemid = "+firstitemid;
   		rs0 = conn.query(cmdGetItemName);
		if (rs0.next()) 
		{
			itemname = rs0.getString(1);
		}
		else
		{
			itemname =  "";
		}
		rs0.close();
	}
	/*
	 * fmodel
	 */
	private void getModel() throws SQLException
	{
		String cmdGetModel=";select a.fmodel from t_icitem a "
	  			+ " join icbom b  on a.fitemid = b.fitemid and b.fusestatus = 1072 "
	  			+ " and b.fstatus = 1 and a.fitemid="+firstitemid;
   		rs0 = conn.query(cmdGetModel);
		if (rs0.next()) 
		{
			model = rs0.getString(1);
		}
		else
		{
			model = "";
		}
		rs0.close();
	}
	/*
	 * OEM
	 */
	private void getOEM() throws SQLException
	{
		String cmdGetOEM=";select left(a.f_131,30) from t_icitem a "
	  			+ " join icbom b  on a.fitemid = b.fitemid and b.fusestatus = 1072 "
	  			+ " and b.fstatus = 1 and a.fitemid ="+firstitemid;
   		rs0 = conn.query(cmdGetOEM);
		if (rs0.next()) 
		{
			OEM = rs0.getString(1);
		}
		else
		{
			OEM = "";
		}
		rs0.close();
	}
	/*
	 * GainRate
	 */
	private void getGainRate(String fnumber) throws SQLException
	{
		rs0 = conn.query(";select isnull(round(f_101,4)/100,0) from t_item_3015 "
				+ " where '01.'+fnumber =  left('"+fnumber+"',5) ");		
		if (rs0.next())	
		{
			gainrate = rs0.getDouble(1);
		}
		else
		{
			gainrate =  -900.0;
		}
		rs0.close();
	}
	/*
	 * unpackage product size
	 */
	private void getPackageSize() throws SQLException
	{
		String cmdGetPackageSize =";select a.fsize from t_icitem a "
	  			+ " join icbom b  on a.fitemid = b.fitemid and b.fusestatus = 1072 "
	  			+ " and b.fstatus = 1 and a.fitemid ="+firstitemid;
   		rs0 = conn.query(cmdGetPackageSize );
		if (rs0.next()) 
		{
			packagesize = rs0.getDouble(1);
		}
		else
		{
			packagesize = 0.0;
		}
		rs0.close();
	}
	/* 
	 * 芯体长宽 单位 米	
	 * 长：喷锌扁管'30.%' 下级物料扁管盘料 --03.a18.%--的坯料尺寸
	 * 宽：集流管--'%集流管%'--下级物料圆铝--'03.a01.%' or '03.a19.%--用量/圆铝米重
	 * 
	 * 1.0.0.5 
	 * 取13裸包产品的长宽高
	 */
	private void getLen() throws SQLException
	{
		rs0= conn.query("; select isnull(a.f_173,0) as len"
					+ " from t_icitem a "
					+ " join BDBomMulExpose b on a.fitemid = b.fitemid"
					+ " and a.fnumber like '13.%' "
					+ " and b.firstitemid = "+firstitemid
					+ " and b.finterid = "+finterid
					);
		if(rs0.next()) 
		{
			length=rs0.getDouble(1);
		}
		else
		{
			length=0.0;
		}
		rs0.close();
	}

	private void getWid() throws SQLException
	{
		rs0= conn.query("; select isnull(a.f_177,0) as wid"
						+ " from t_icitem a"
						+ " join BDBomMulExpose b on a.fitemid = b.fitemid"
						+ " and a.fnumber like '13.%'"
						+ " and b.firstitemid = "+firstitemid
						+ " and b.finterid = "+finterid
						);
		if(rs0.next())
		{
			width=rs0.getDouble(1);					
		}
		else
		{
			width=0.0;
		}
		rs0.close();
	}
	private void getHgt() throws SQLException
	{
		rs0= conn.query("; select isnull(a.f_174 ,0) as height "
						+ " from t_icitem a "
						+ " join BDBomMulExpose b on a.fitemid = b.fitemid"
						+ " and a.fnumber like '13.%'"
						+ " and b.firstitemid = "+firstitemid
						+ " and b.finterid = "+finterid
						);
		if(rs0.next()) 
		{
			height= rs0.getDouble(1);
		}
		else
		{
			height= 0.0;
		}
		rs0.close();
	}
	/*
	 * volumn m^3
	 */
	private void getVolumn() throws SQLException
	{
		rs0= conn.query("; select 1 from t_icitem a "
						+ " join BDBomMulExpose b on a.fitemid = b.fitemid"
						+ " and a.fnumber in( '2.01.650','2.01.6501','2.01.6502')"
						+ " and b.firstitemid = "+firstitemid
						+ " and b.finterid = "+finterid
						);
		if(rs0.next()) 
		{
			volumn= length*width*height+0.000097356;
		}
		else
		{
			volumn= length*width*height;
		}
		rs0.close();
	}
	/*
	 * 长宽高验证
	 * 钎焊焊接工艺40336产能： 	根据产品不同计算 
	 * 扁管压出工艺40494产能： 	根据产品不同计算 60*扁管压出速度/扁管长度（坯料尺寸）
	 * 扁管切断工艺 40495产能：	根据芯体长度不同计算
	 * 400mm < l 			then 7800 pc/h
	 * 400mm <= l <600mm	then 6600 pc/h
	 * 600mm <= l <800mm	then 5400 pc/h
	 * 800mm <= l <1000mm	then 4200 pc/h
	 * 1000mm <= l 		then 2400 pc/h
	 * 芯体烘干喷漆 40142 辅料用量：集流管长<=200MM，用量为普通产品的1/2
	 * 这三个工艺要用芯体的长或宽或体积，不能为0
	 */
	private void verifyLWH() throws SQLException
	{
		if((Route.hasQHL==true||Route.hasPipe==true||Route.hasDry==true)
			&& ( length <=0.0000 ||length > 2.00
			||width <=0.0000 ||width > 0.10
			|| height <=0.0000 || height > 1.50)) 
		{
			verifyLWH= 1;		
		}
		else 
		{
			verifyLWH= 0;		
		}
	
	}

	/*
	 * HistorySaledQTY about 13.*
	 */
	private void getQtySaled() throws SQLException
	{
		String cmdQtySaled = ";select isnull(sum(b.fqty),0) "
				+ " from icsale a "
				+ " join icsaleentry b "
				+ " on a.finterid = b.finterid and a.fstatus > 0 "
				+ " and a.fcancellation = 0  "
				+ " and b.fitemid in ("
				+ " select t1.fitemid from "
				+ " icbom t1 join icbomchild t2 on t1.finterid = t2.finterid and t1.fstatus >0"
				+ " and t1.fcancellation =0  and t1.fusestatus = 1072"
				+ " join BDBomMulExpose t3 on t3.fitemid = t2.fitemid"
				+ " and t3.firstitemid="+firstitemid
				+ " join t_icitem t4 on t4.fitemid = t3.fitemid "
				+ " and  t4.fnumber like '13.%' )";
		rs0 = conn.query(cmdQtySaled );
		if (rs0.next())	
		{
			saledQty =rs0.getDouble(1);
		}	 	
		else 
		{
			saledQty = 0.0 ;
		}
		rs0.close(); 
		//System.out.println("history saled qty: "+saledQty);
	}

	/*
	 * Get NewJig Amortize QTY t_icitemcustom f_179
	 */
	private void getQtyAmortizeNewJig() throws SQLException
	{	
		String cmdNewJigAmortizeQty = ";select isnull(b.f_179,0) "
				+ " from BDBomMulExpose a "
				+ " join t_icitem b "
				+ " on a.fitemid = b.fitemid "
				+ " and b.fnumber like '13.%'"
				+ " and a.firstitemid = " +firstitemid;
		rs0 = conn.query(cmdNewJigAmortizeQty );
		if (rs0.next())	
		{
			newJigAmortizeQty =rs0.getDouble(1);
		}	 	
		else 
		{
			newJigAmortizeQty = 0.0 ;
		}
		rs0.close();
		//System.out.println(cmdNewJigAmortizeQty+"新开模具计划摊销数量："+newJigAmortizeQty);
	}
	/*
	 * Get NewJig Amt 新开模具费用f_178 新开专用工装夹具费用 f_181
	 */
	private void getAmtNewJig() throws SQLException
	{
		String cmdNewJigAmt = ";select isnull((b.f_178+b.f_181),0)"
				+ " from BDBomMulExpose a "
				+ " join t_icitem b "
				+ " on a.fitemid = b.fitemid "
				+ " and b.fnumber like '13.%'"
				+ " and a.firstitemid = " +firstitemid;;
		rs0 = conn.query(cmdNewJigAmt  );
		if (rs0.next())	
		{
			newJigAmt =rs0.getDouble(1);
		}	 	
		else 
		{
			newJigAmt = 0.0 ;
		}
		rs0.close(); 
		//System.out.println("新开模具费用："+cmdNewJigAmt+" value:"+newJigAmt);
	}
	
	/*
	 * Get NewJig Amortize AMT
	 */
	private void getAmtAmortizeNewJig() 
	{
		if (saledQty < newJigAmortizeQty && newJigAmortizeQty >0 )	
		{
			newJigAmortizeAmt =
					Double.parseDouble(new BigDecimal(
							newJigAmt/newJigAmortizeQty).toString());
		}	 	
		else 
		{
			newJigAmortizeAmt = 0.0 ;
		} 
		//System.out.println("新开模具费用摊销"+newJigAmortizeAmt);
	}
	
	static public int firstitemid,finterid ,verifyLWH;
	static public String itemname,model,OEM ;
	static public double length,width,height,volumn,gainrate,packagesize
	,saledQty,newJigAmortizeQty,newJigAmt,newJigAmortizeAmt;
	private DBConnect conn=new DBConnect();
	private ResultSet rs0;

}
