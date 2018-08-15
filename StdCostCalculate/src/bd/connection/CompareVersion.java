package bd.connection;

import java.sql.SQLException;

public class CompareVersion {
	 /*
     * 版本验证
     */
	 public int compareVersion(String programName,int v1,int v2,int v3,int v4) throws SQLException 
	 {
		 v=getVersionInfo.getVersionInfo(programName);
		 System.out.println(v[0]+"."+v[1]+"."+v[2]+"."+v[3]);
		 if (v[0]==v1 && v[1]==v2 && v[2]==v3  && v[3]==v4 )
		 {
			 return 1;
		 }
		 else
		 {
			 //if version_prog is lower than version_db, then update program
			 //if (true) 
			 //{return 0;}
			 //if version_prog is bigger than version_db,then update database
			 //if (v[0]>=v1)
			//	 {
				// setVersionInfo.setVersionInfo(programName, v1, v2, v3, v4);
				 return 0;
			//	 }
		 }
 
	 }
	 private SetVersionInfo setVersionInfo = new SetVersionInfo();
	 private GetVersionInfo getVersionInfo= new GetVersionInfo();
	 private int[] v=new int[4];



}
