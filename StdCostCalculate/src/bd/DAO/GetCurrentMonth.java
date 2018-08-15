package bd.DAO;

import bd.connection.GetDBConnect;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetCurrentMonth {
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
	private GetDBConnect conn =new GetDBConnect();
	private ResultSet rs0;
	static public String currentperiod="";
	
	
	

}
