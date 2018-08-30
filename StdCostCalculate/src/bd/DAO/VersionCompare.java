package bd.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import bd.View.StdCostCalculate;

public class VersionCompare {
	/*
	 * compare version
	 */
	public int versionCompare() 
	{
		
		if ((StdCostCalculate.localVer).compareTo(versionFromDB) < 0)
		{
			versionOK= 0;
			//System.out.println("ver_Local<ver_DB,Ver_local need to update");
		}
		if ((StdCostCalculate.localVer).compareTo(versionFromDB) ==0)
		{
			//System.out.println("ver_Local=ver_DB,it's ok");
			versionOK=  1;
		} 
		if ((StdCostCalculate.localVer).compareTo(versionFromDB) > 0)
		{
			//System.out.println("ver_Local>ver_DB ,Ver_DB need to update ");
			versionOK= 2;
		}
		return versionOK;
	}
	/*
	 *  create table_version_verify-BDStdCostProgVersion
	 */
	public void createTableVersionControl()  
	{
		try
		{
			rs0 = conn.query("",";select count(*) from sysobjects where type = 'u' "
			 		+ " and name like 'BDStdCostProgVersion'");
			 if (rs0.next() && rs0.getInt(1)<1)
			 {
				 String cmdCreateTableVerControl = ";CREATE TABLE BDStdCostProgVersion"
						+ "(version varchar(30)"
						+ ",programfile varchar(101)"
						+ ",description varchar(200)"
						+ ")" ;
			   	conn.update("",cmdCreateTableVerControl );
			   	conn.close();
			 } 
		}
		catch (SQLException e)
		{
			
		}
	}

 /*
  * get
  */
 public void get() 
 {
	try
	{
		cmdVerInfo = ";select distinct version from BDStdCostProgVersion  "
				+ " where programfile like '"+StdCostCalculate.programName
				+"' order by version desc";
		rs0=conn.query("", cmdVerInfo);
		if (rs0.next())
		{
			versionFromDB=rs0.getString(1);
			//System.out.println("versionfromDB"+versionFromDB);
		}
		else
		{
			versionFromDB="0.0.0.0";
			//System.out.println("versionfromDB no"+versionFromDB);
		}
	}
	catch (SQLException e)
	{
		
	}
	 		
 }

 

 private DBConnect conn=new DBConnect();
 private ResultSet rs0;
 private String cmdVerInfo;
 static public String versionFromDB;
 private int versionOK;
 

}
