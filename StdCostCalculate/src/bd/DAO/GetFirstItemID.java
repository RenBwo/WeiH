package bd.DAO;

import java.sql.ResultSet;
import bd.connection.GetDBConnect;
import java.sql.SQLException;

public class GetFirstItemID {
	public int getFirstItemID(String fnumber) throws SQLException
	{
		String cmdGetFirstItemID=";select a.fitemid from t_icitem a "
	  			+ " join icbom b  on a.fitemid = b.fitemid and b.fusestatus = 1072 "
	  			+ " and b.fstatus = 1 and a.fnumber like '"+fnumber+ "'";
   		rs0 = conn.query("",cmdGetFirstItemID);
		if (rs0.next()) 
		{
			firstitemid = rs0.getInt(1);
			conn.close();
			return firstitemid;
		}
		else
		{
			return 0;
		}
	}
	
	private GetDBConnect conn=new GetDBConnect();
	private ResultSet rs0;
	public static int firstitemid ;

}
