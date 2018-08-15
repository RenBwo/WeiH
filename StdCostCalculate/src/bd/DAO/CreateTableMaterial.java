package bd.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import bd.connection.GetDBConnect;

public class CreateTableMaterial {
	private GetDBConnect conn =new GetDBConnect();
	private ResultSet 	rs0;
	
/* 
	* 直接材料成本表   	TABLE t_CostMaterialBD
	*/
	public void createTableMaterial() throws SQLException	
	{ 
		rs0 = conn.query("",";select count(*) from sysobjects where type = 'u' and name like 't_CostMaterialBD'");
		if(rs0.next() && rs0.getInt(1) >0 ) 
		{
			//System.out.println("table_Material exists ");
		}
		else
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
				 conn.close();
				//System.out.println("create table_Material success ");
			}
	}
}
