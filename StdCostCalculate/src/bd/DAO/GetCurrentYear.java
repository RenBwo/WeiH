package bd.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import bd.connection.GetDBConnect;

public class GetCurrentYear 
{
	public String getCurrentYear() throws SQLException
	{
		rs0 = conn.query("",";select  fvalue from t_Systemprofile "
				+ " where fkey like 'currentyear' and fcategory = 'GL'");
        if(rs0.next())
        {
	    currentyear = rs0.getString(1);
        }
        conn.close();
	    return currentyear;
	}
	private GetDBConnect conn =new GetDBConnect();
	private ResultSet rs0;
	public static String currentyear="";
	
}
