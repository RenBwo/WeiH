package bd.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MaterialDirect 
{

	/* 
	* 直接材料成本表   	TABLE t_CostMaterialBD
	*/
	public void createTable() throws SQLException
	{ 
		rs0 = conn.query("",";select count(*) from sysobjects where type = 'u' and name like 't_CostMaterialBD'");
		if(rs0.next() && rs0.getInt(1) >0 ) 
		{
			//System.out.println("table_Material exists ");
		}
		else
		{	
			String  command1 = ";CREATE TABLE t_CostMaterialBD"
							 + "("
							 + "FLevel 			int not null default 0"
							 + ",FParentID 		int"
							 + ",FItemID 		int"
							 + ",fQtyPer 		decimal(20,4)"
							 + ",fQty 			decimal(20,4)"
							 + ",fitemsize 		varchar(60)"
							 + ",Fnumber 		varchar(60)"
							 + ",FbomInterID 	int"
							 + ",fprice 		decimal(20,4)"
							 + ",fAmtMaterial 	decimal(20,4)"
							 + ",fproditemid  	int"
							 + ",finterid     	int"
							 + ",createdate 	datetime"
							 + ")";
			conn.update("",command1);
			conn.close();
			//System.out.println("create table_Material success ");
		}
		
	}
	 /*
	  *	 直接材料成本
	  * 价格：1 过去一年的采购发票蓝字平均不含税单价格  原始数据是未含税,否则，取物料计划价格 未含税 
	  */
	public void set() throws SQLException
	{ 	
		/*清除数据*/
		String command4 = " ;insert into t_CostMaterialBD(fproditemid,finterid,FLevel,FParentID,FItemID,fQtyPer,fQty"
			+ ",fitemsize,Fnumber,FbomInterID,fprice,createdate)"
			+ " select a.firstitemid,"+ProductInfo.finterid+ ",a.FLevel,a.FParentID,a.FItemID,a.fQtyPer,a.fQty,a.fitemsize,b.Fnumber"
			+ ",a.FbomInterID,isnull(w.avrprice,isnull(b.fplanprice,0)) as fprice "
			+ ",getdate()"
			+ " from BDBomMulExpose a "
			+ " join		t_icitem b on a.fitemid  = b.fitemid and a.firstitemid = "
			+ProductInfo.firstitemid+" and a.finterid = "
			+ProductInfo.finterid
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
		conn.update("",";update t_CostMaterialBD set fAmtMaterial = fprice*fqty where finterid ="+ProductInfo.finterid
					+ " and fproditemid="+ProductInfo.firstitemid);
		conn.close();
		}
	/*
	 * clean CostMaterial
	 */
	public void clean() throws SQLException
	{
		String cmdDel=";delete from t_CostMaterialBD "
					+ "where fproditemid ="
					+ProductInfo.firstitemid
					+" and ( finterid = "+ProductInfo.finterid
					+" or datediff(day,createdate,getdate()) >2) ";
		conn.update("",cmdDel);	
		conn.close();
	}

	/*
	 * verify direct_material price验证直接材料价格
	 */
	public int verify() throws SQLException
	{	
		String cmdverify =";select count(*) from t_CostMaterialBD where fproditemid= "
				+ProductInfo.firstitemid 
				+" and finterid = "+ProductInfo.finterid 
				+" and isnull(fprice,0)=0 ";
		rs0=conn.query("", cmdverify);
		if(rs0.next() && rs0.getInt(1) > 0  ) 
		{
		conn.close();
		return 1;
		}
		else 
		{
			conn.close();
			return 0;
		}
	}
	private DBConnect conn =new DBConnect();
	private ResultSet 	rs0;
	

}
