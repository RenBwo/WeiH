package bd.DAO;

import java.sql.SQLException;

import bd.connection.getcon;

public class UpdateAdiMaterialPrice {
	/*
	 * 	升级辅料单价
	 * 1 过去一年的采购发票蓝字平均不含税单价格  原始数据是未含税
	 * 2 计划价格								原始数据是未含税
	 */
	public void updateAdiMatrialPrice() throws SQLException {
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
		conn.close();
		
	}
	private getcon conn=new getcon();
	

}
