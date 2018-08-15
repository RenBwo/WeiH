package bd.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import bd.connection.GetDBConnect;

public class CreateTableBOM {
	private GetDBConnect conn =new GetDBConnect();
	private ResultSet 	rs0;
	
	public void createTableBOM() throws SQLException
{     		
		/*
		* BOM多级展开表 BDBomMulExpose
		*/
		rs0 = conn.query("",";select count(*) from sysobjects where type = 'u' and name like 'BDBomMulExpose'");
		if(rs0.next() && rs0.getInt(1) >0 ) 
		{
			//System.out.println("table_bom exists ");
		}
		else
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
					+ ",bomskip int"
					+ ")" ;
		
		conn.update("",command1);
		conn.close();
		//System.out.println("create table_bom success ");
		}
}

}
