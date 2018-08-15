package bd.DAO;
/*
 * modelQtySaled
 */
import java.sql.ResultSet;
import java.sql.SQLException;

import bd.connection.GetDBConnect;

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
			rs0.getDouble(1);
		}	 	
		else 
		{
			modelQtySaled =  -9999.9 ;
		} 
		conn.close();
		return modelQtySaled;
	}
	private GetDBConnect conn=new GetDBConnect();
	private ResultSet rs0;
	public static double modelQtySaled ;

}
