package bd.View;


import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;

import bd.connection.GetDBConnect;

/*
 * version information
 */
public class VerifyFrame
{
	public VerifyFrame() throws SQLException
	{
		Verifyframe = new JFrame();
		Verifyframe.setBounds(0, 0,500,300);
		Verifyframe.setTitle("版本验证");
		createTable();
	}
	
	/*
	 *  create table_version_verify-BDStdCostProgVersion
	 *  */
	 public void createTable() throws SQLException
	 {rsver = conver.query("",";select count(*) from sysobjects where type = 'u' "
		 		+ " and name like 'BDStdCostProgVersion'");
		 if (rsver.next() && rsver.getInt(1)<1)
		 {
			 String CrTVerVerify = ";CREATE TABLE BDStdCostProgVersion"
					+ "(version varchar(30),v1 int,v2 int,v3 int,v4 int"
					+ ",programfile varchar(101) default 'standCostCal')" ;
		   	conver.update("",CrTVerVerify );
		 }  
	 } 

	public JFrame Verifyframe;
	private ResultSet 	rsver;
	public GetDBConnect conver = new GetDBConnect();
}