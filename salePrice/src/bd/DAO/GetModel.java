package bd.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import bd.connection.getcon;

public class GetModel {

	public String getModel(String fnumber) throws SQLException
	{
		String cmdGetModel=";select a.fmodel from t_icitem a "
	  			+ " join icbom b  on a.fitemid = b.fitemid and b.fusestatus = 1072 "
	  			+ " and b.fstatus = 1 and a.fnumber like '"+fnumber+ "'";
   		rs0 = conn.query("",cmdGetModel);
		if (rs0.next()) 
		{
			model = rs0.getString(1);
			conn.close();
			return model;
		}
		else
		{
			return "";
		}
	}
	
	private getcon conn=new getcon();
	private ResultSet rs0;
	private String model ;
}
