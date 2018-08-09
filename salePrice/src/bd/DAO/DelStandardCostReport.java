package bd.DAO;

import java.sql.SQLException;

import bd.connection.getcon;

public class DelStandardCostReport 
{
	/*
	 * del history rpt
	 */
	public void delHistPrice(int firstitemid,int finterid) 
			throws SQLException
	{
		String delHistPrice=" ; delete from t_bdStandCostRPT "
				+ " where fproditemid = "+firstitemid
				+ " and finterid < "+finterid;
		conn.update("",delHistPrice);
		conn.close();
	}
	private getcon conn=new getcon();
}
