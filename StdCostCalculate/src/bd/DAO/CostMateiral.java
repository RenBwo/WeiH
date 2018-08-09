package bd.DAO;


import java.sql.SQLException;
import bd.connection.getcon;

public class CostMateiral {
	 private getcon conn =new getcon();
/*
* 直接材料成本
* 价格：1 过去一年的采购发票蓝字平均不含税单价格  原始数据是未含税,否则，取物料计划价格 未含税 
*/
	public void costMateiral(int firstitemid,int finterid,String currentyear,String currentperiod) throws SQLException
	{ 	/*清除数据*/
		conn.update("",";delete from t_CostMaterialBD where fproditemid ="+firstitemid+" and finterid <= "+finterid);
		
		String command4 = " ;insert into t_CostMaterialBD(fproditemid,finterid,FLevel,FParentID,FItemID,fQtyPer,fQty"
		+ ",fitemsize,Fnumber,FbomInterID,fprice)"
		+ " select a.firstitemid,"+finterid+ ",a.FLevel,a.FParentID,a.FItemID,a.fQtyPer,a.fQty,a.fitemsize,b.Fnumber"
		+ ",a.FbomInterID,isnull(w.avrprice,isnull(b.fplanprice,0)) as fprice "
		+ " from BDBomMulExpose a "
		+ " join		t_icitem b on a.fitemid  = b.fitemid and a.firstitemid = "+firstitemid+" and a.finterid = "+finterid
		+ " left join (select b.fitemid ,sum(b.fstdamount) as funtaxamount, sum(b.fqty) as fqty"
				+ ",case  when sum(b.fqty)>0 then round(sum(b.fstdamount)/sum(b.fqty),4) else 0 end as avrprice "
				+ " from icpurchase a join icpurchaseentry b on a.FInterID = b.FInterID"
				+ " and ( a.ftrantype = 75 or a.ftrantype = 76 ) "
				+ " where a.fcheckdate > dateadd(year,-1,dateadd(day,datediff(day,0,getdate()),0)) "
				+ " and isnull(b.fqty , 0)<> 0 and isnull(b.fstdamount ,0) <> 0 and a.fstatus = 1 "
				+ " and a.frob =1 and (isnull(fheadselfi0252,0) =0 and isnull(fheadselfi0349,0 ) = 0) "
				+ " group by b.fitemid "
		+ ") w 		on w.fitemid = a.fitemid "		
		+ " where  b.ferpclsid = 1 ";
	 	////System.out.println("材料成本： "+command4);
		conn.update("",command4);
		conn.update("",";update t_CostMaterialBD set fAmtMaterial = fprice*fqty where finterid ="+finterid
				+ " and fproditemid="+firstitemid);
		conn.close();
		}

}
