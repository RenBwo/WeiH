package bd.connection;

import java.sql.SQLException;

public class SetVersionInfo {
	public int setVersionInfo(String programName,int v1,int v2,int v3,int v4) throws SQLException
	{
		cmdDelVersion=";delete BDStdCostProgVersion where programfile like '"+programName+"'";
		conn.update("",cmdDelVersion);
		
		cmdInsertVersion=";insert into BDStdCostProgVersion(version,v1,v2,v3,v4,programfile) "
		 		+ "values('"+String.valueOf(v1)+"'+'.'+'"+String.valueOf(v2)+"'+'.'+'"
				+String.valueOf(v3)+"'+'.'+'"+String.valueOf(v4)+"',"
		 		+v1+","+v2+","+v3+","+v4+",'"+programName+"')";
		 conn.update("",cmdInsertVersion);
		 conn.close();
		 return 1;
	}
	private GetDBConnect conn = new GetDBConnect();
	private String cmdDelVersion,cmdInsertVersion;

}
