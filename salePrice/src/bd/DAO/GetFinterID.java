package bd.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import bd.connection.getcon;

public class GetFinterID {
	public int getFinterID(int firstitemid) throws SQLException
	{
		String command5 = ";insert into t_bdStandCostRPT(fproditemid,fdate)"
				+ " values( "+firstitemid+ ",getdate() )";
		conn.update("",command5);
		
		rs0 = conn.query("",";select max(finterid) from t_bdStandCostRPT where fproditemid ="
						+firstitemid);
		if(rs0.next()) 
		{	
		finterid = (rs0.wasNull() ? 1 : rs0.getInt(1)) ;
		conn.close();
		return finterid;
		}
		else
		{
			return 0;
		}
	}
	private getcon conn=new getcon();
	private ResultSet rs0;
	private int finterid;

}
