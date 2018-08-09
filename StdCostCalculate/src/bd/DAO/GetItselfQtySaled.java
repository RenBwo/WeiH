package bd.DAO;
/*
 * itselfitselfQtySaled
 */
import bd.connection.getcon;
import java.sql.SQLException;
import java.sql.ResultSet;

public class GetItselfQtySaled {
	public double getItselfQtySaled(int firstitemid) throws SQLException
	{	
		rs0 = conn.query("",";select isnull(sum(b.fqty),0) from icsale a "
				+ " join icsaleentry b on a.finterid = b.finterid "
				+ " and a.fstatus > 0 and a.fcancellation = 0 and b.fitemid = "+firstitemid);		
		if (rs0.next())	
		{
			itselfQtySaled = rs0.getDouble(1); 	
			conn.close();
			return itselfQtySaled;
		}
		else 
		{
			return -9999.9; 
		}
	}
	private getcon conn=new getcon();
	private ResultSet rs0;
	private double itselfQtySaled ;
	
}
