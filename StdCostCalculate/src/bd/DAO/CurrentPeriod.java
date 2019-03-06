package bd.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CurrentPeriod 
{
	public void get() throws SQLException
	{
		getYear();
		getMonth();
	}
	private void getYear() throws SQLException
	{
		rs0 = conn.query(";select  fvalue from t_Systemprofile "
				+ " where fkey like 'currentyear' and fcategory = 'GL'");
        if(rs0.next())
        {
        	currentyear = rs0.getString(1);
        }
        else 
        {
        	currentyear="";
        }
        conn.close();
	}

	private void getMonth() throws SQLException
	{
		rs0 = conn.query(";select fvalue from t_Systemprofile "
	    		+ " where fkey like 'currentperiod' and fcategory = 'GL'");
	    if(rs0.next())
	    {
	    	currentmonth = rs0.getString(1);
	    }
	    else 
	    {
	    	currentmonth="";
	    }
	    conn.close();
	}
	
	static public String currentyear,currentmonth;
	private DBConnect conn =new DBConnect();
	private ResultSet rs0;
	
}
