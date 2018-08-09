package bd.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import bd.connection.getcon;

public class GetCurrentYear 
{
	private getcon conn =new getcon();
	private ResultSet rs0;
	private String currentyear="";
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
}
