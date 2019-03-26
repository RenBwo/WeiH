package bd.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MaterialAdi {
	
	public void get() throws SQLException
	{
		updateAdiMaterialPrice();
		verifyAdiPrice();
		getAmtQianji();
		getAmtLN2();
	}
	
	/*
	 * 	升级辅料单价
	 * 价格：1 过去一年的采购发票蓝字平均不含税单价格  原始数据是未含税,
	 * 否则，2、取有效期内供应商价格体系的最高价格 源价格含税 20190326 源价格变更为未税
	 * 国内增值税：
	 * 否则，3、取外购物料计划单价维护单 价格 未含税 
	 * select * from icclasstype where fname_chs like '外购物料计划单价%'
	 */
	private void updateAdiMaterialPrice() throws SQLException {
		String sql0 = "; update a set a.fprice = isnull(w.avrprice,isnull("
				+ "c.fprice,isnull(d.fprice,0)))"
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
				+ ") 								w	on w.fitemid = a.faidnumber "
				+ " left join (select  fitemid,max(fprice) as fprice from t_supplyentry "
					+ " where fdisabledate >  getdate()  and fprice > 0 group by fitemid"
					+ ") c on c.fitemid=  a.faidnumber "
				+ " left join (select distinct b.fbase,b.fprice "
				+ " from t_bos200000025 a "
				+ " join t_bos200000025entry b on a.fid = b.fid and a.fmulticheckstatus = 16"
				+ " and b.fdate2> getdate() ) d on d.fbase= a.faidnumber "		
			;
		//System.out.println("升级辅料单价：" + sql0);
		conn.update(sql0);
		conn.close();		
	}

	/*
	 * verify adiMaterial's price
	 */
	private void verifyAdiPrice() throws SQLException 
	{
		String cmdverify=";select count(*) "
				+ " from 	BDBomMulExpose 			t1"
				+ " join 	t_Routing 					t2 	"
				+ " on 		t1.fitemid = t2.fitemid  "
				+ " and 	t1.firstitemid ="+ProductInfo.firstitemid
				+ " and 	t1.finterid = "+ProductInfo.finterid
				+ " and 	t2.finterid = t1.froutingid"
				+ " join 	t_routingoper 				t3 	"
				+ " on 		t2.finterid = t3.finterid "
				+ " join 	t_CostCalculateBD 			t4 "
				+ "	on 		t4.foperno = t3.foperid "
				+ " join 	t_costcalculatebd_entry1 	t5 "
				+ "	on 		t4.fid = t5.fid "
				+ " and 	isnull(t5.fprice,0) = 0 "
				+ " and 	isnull(t5.fqty,0) <> 0 ";
		rs0=conn.query( cmdverify);
		if(rs0.next() && rs0.getInt(1) > 0 ) 
		{
			verifyAdiPrice= 1;
		}
		else
		{
			verifyAdiPrice=0;
		}
	}
	/*
	 * 钎剂费用,按单位产品体积用量进行计算
	 * 钎剂、液氮标准用量（m^3/m^3）×入炉产品体积×单价 
	 */
	private void getAmtQianji() throws SQLException
	{
		if (Route.hasQHL==true) 
		{
			rs0= conn.query(";select round(isnull(sum(fqty*fprice),0)* "+ProductInfo.volumn
					+ ",6) from t_costcalculatebd_entry1 "
					+ " where faidname like '%钎剂%' "
					);
				if(rs0.next()) 
				{
					amtQianji=rs0.getDouble(1);	
				}
				else
				{
					amtQianji=0.0;
				}
				rs0.close();
		}
		else
		{
			amtQianji=0.0;
		}
		//System.out.println("钎剂费用: "+amtQianji);
		
	}
	/*
	 * 液氮费用,按单位产品体积用量进行计算
	 * 液氮标准用量（m^3/m^3）×入炉产品体积 
	 */
	private void getAmtLN2() throws SQLException
	{
		if (Route.hasQHL==true) 
		{
			rs0= conn.query(";select round(isnull(sum(fqty*fprice),0)* "+ProductInfo.volumn 
					+",6) from t_costcalculatebd_entry1 "
					+ " where faidname like '%液氮%' "
					);
				if(rs0.next()) 
				{
					amtLN2=rs0.getDouble(1);
				}
				else
				{
					amtLN2=0.0;
				}
				rs0.close();
		}
		else
		{
			amtLN2=0.0;
		}
		//System.out.println("液氮费用: "+amtLN2);
		
	}
	private ResultSet rs0;
	private DBConnect conn=new DBConnect();
	public static double amtQianji,amtLN2;
	public static int verifyAdiPrice;

}
