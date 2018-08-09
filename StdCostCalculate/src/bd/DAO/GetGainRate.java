package bd.DAO;
/*
 * gainRate depends on class
 */

import java.sql.ResultSet;
import java.sql.SQLException;
import bd.connection.getcon;

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
	private getcon conn=new getcon();
	private ResultSet rs0;
	private double gainrate ;

}
