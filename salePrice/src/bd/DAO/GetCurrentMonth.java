package bd.DAO;

import bd.connection.getcon;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetCurrentMonth {
	private getcon conn =new getcon();
	private ResultSet rs0;
	private String currentperiod="";
	
	public String getCurrentMonth() throws SQLException
	{
		rs0 = conn.query("",";select fvalue from t_Systemprofile "
	    		+ " where fkey like 'currentperiod' and fcategory = 'GL'");
	    if(rs0.next())
	    {
		currentperiod = rs0.getString(1);
	    }
	    conn.close();
		return currentperiod;
	}
	

}
