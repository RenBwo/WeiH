package bd.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import bd.connection.GetDBConnect;

public class GetPackageSize {

	public double getPackageSize (String fnumber) throws SQLException
	{
		String cmdGetPackageSize =";select a.fsize from t_icitem a "
	  			+ " join icbom b  on a.fitemid = b.fitemid and b.fusestatus = 1072 "
	  			+ " and b.fstatus = 1 and a.fnumber like '"+fnumber+ "'";
   		rs0 = conn.query("",cmdGetPackageSize );
		if (rs0.next()) 
		{
			size = rs0.getDouble(1);
		}
		else
		{
			size = 0.0;
		}
		conn.close();
		return size;
	}
	
	private GetDBConnect conn=new GetDBConnect();
	private ResultSet rs0;
	public static double size ;
}
