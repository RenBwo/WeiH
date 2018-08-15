package bd.DAO;
/*
 * gainRate depends on class
 */

import java.sql.ResultSet;
import java.sql.SQLException;
import bd.connection.GetDBConnect;

public class GetGainRate 
{
	public double getGainRate(String fnumber) throws SQLException
	{
		rs0 = conn.query("",";select isnull(round(f_101,6),0) from t_item_3015 "
				+ " where '01.'+fnumber =  left('"+fnumber+"',5) ");		
		if (rs0.next())	
		{
			gainrate = rs0.getDouble(1);
			conn.close();
			return gainrate;
		}
		else
		{
			return -900.0;
		}
	}
	private GetDBConnect conn=new GetDBConnect();
	private ResultSet rs0;
	static public double gainrate ;

}
