package bd.connection;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GetVersionInfo {
	public int[] getVersionInfo(String programName) throws SQLException
	{
		cmdVerInfo = ";select distinct v1,v2,v3,v4 from BDStdCostProgVersion  "
		 		+ " where programfile like '"+programName+"' order by v1,v2,v3,v4";
		rs0=conn.query("", cmdVerInfo);
		if (rs0.next())
		{
			a[0]=rs0.getInt(1);
			a[1]=rs0.getInt(2);
			a[2]=rs0.getInt(3);
			a[3]=rs0.getInt(4);
			//System.out.println("a:"+a[3]);
			return a;
		}
		else
		{
			a[0]=0;
			a[1]=0;
			a[2]=0;
			a[3]=0;
			return a;
		}
	}
	private String cmdVerInfo;
	private GetDBConnect conn=new GetDBConnect();
	private ResultSet rs0;
	private int[] a=new int[4];

}
