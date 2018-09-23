package bd.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MaterialAdi {
	
	/*
	 * 	升级辅料单价
	 * 价格：1 过去一年的采购发票蓝字平均不含税单价格  原始数据是未含税,
	 * 否则，2、取有效期内供应商价格体系的最高价格 源价格含税
	 * 国内增值税：select round(f_101,6) from t_item_3015  where fnumber like 'k10'
	 * 否则，3、取外购物料计划单价维护单 价格 未含税 
	 * select * from icclasstype where fname_chs like '外购物料计划单价%'
	 */
	public void updateAdiMatrialPrice() throws SQLException {
		String sql0 = "; update a set a.fprice = isnull(w.avrprice,isnull("
				+ "round(c.fprice/(select 1+round(f_101,6) from t_item_3015 where fnumber like 'k10'),4)"
				+ ",isnull(d.fprice,0)))"
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
				+ " left join t_bos200000025entry d on d.fbase= a.faidnumber and d.fdate2 > getdate()"		
			;
		//System.out.println("升级辅料单价：" + sql0);
		conn.update(sql0);
		conn.close();		
	}

	/*
	 * verify adiMaterial's price
	 */
	public int verifyAdiPrice() throws SQLException 
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
			return 1;
		}
		else
		{
			return 0;
		}
	}
	private ResultSet rs0;
	private DBConnect conn=new DBConnect();

}
