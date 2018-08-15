package bd.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import bd.connection.GetDBConnect;

public class GetOEM {

	public String getOEM(String fnumber) throws SQLException
	{
		String cmdGetOEM=";select left(a.f_131,30) from t_icitem a "
	  			+ " join icbom b  on a.fitemid = b.fitemid and b.fusestatus = 1072 "
	  			+ " and b.fstatus = 1 and a.fnumber like '"+fnumber+ "'";
   		rs0 = conn.query("",cmdGetOEM);
		if (rs0.next()) 
		{
			 rs0.getString(1);
		}
		else
		{
			OEM = "";
		}
		conn.close();
		return OEM;
	}
	
	private GetDBConnect conn=new GetDBConnect();
	private ResultSet rs0;
	public static String OEM ;

}
