package bd.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import bd.connection.GetDBConnect;

public class GetItemName {

	public String getItemName(String fnumber) throws SQLException
	{
		String cmdGetItemName=";select a.fname from t_icitem a "
	  			+ " join icbom b  on a.fitemid = b.fitemid and b.fusestatus = 1072 "
	  			+ " and b.fstatus = 1 and a.fnumber like '"+fnumber+ "'";
   		rs0 = conn.query("",cmdGetItemName);
		if (rs0.next()) 
		{
			itemname = rs0.getString(1);
			conn.close();
			return itemname;
		}
		else
		{
			return "";
		}
	}
	
	private GetDBConnect conn=new GetDBConnect();
	private ResultSet rs0;
	static public String itemname ;

}
