package bd.DAO;
/*
 * modelQtySaled
 */
import java.sql.ResultSet;
import java.sql.SQLException;

import bd.connection.getcon;

public class GetModelQtySaled {
	public double getModelQtySaled(String model) throws SQLException
	{	
		String cmdModelQtySaled = ";select isnull(sum(fqty),0) from icsale a "
				+ " join icsaleentry b "
				+ " on a.finterid = b.finterid and a.fstatus > 0 and a.fcancellation = 0  "
				+ " join t_icitem c on c.fitemid = b.fitemid and left(c.fmodel,5) like left('"
				+model+"',5)"
				+ " group by left(c.fmodel,5)";
		rs0 = conn.query("",cmdModelQtySaled );
		if (rs0.next())	
		{
			modelQtySaled = rs0.getDouble(1);
			conn.close();
			return modelQtySaled;
		}	 	
		else 
		{
			return -9999.9 ;
		} 	
	}
	private getcon conn=new getcon();
	private ResultSet rs0;
	private double modelQtySaled ;

}
