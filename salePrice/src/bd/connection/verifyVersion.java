package bd.connection;


import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;
/*
 * version information
 */
public class verifyVersion {
	public JFrame Verifyframe;
	private ResultSet 	rsver;
	public getcon conver = new getcon();
	public int exit=0;
    public static  int verifyVer=0;
    /*
     * 程序版本
     */
    private int v1=1 ,v2=0 ,v3=0 ,v4=0;
	
	
	public verifyVersion() throws SQLException {
		Verifyframe = new JFrame();
		Verifyframe.setBounds(0, 0,500,300);
		Verifyframe.setTitle("版本验证");
		verVersion();
		
	}
	
	/*
		* create table_version_verify-BDStdCostProgVersion
		*/

		
		public void crTab() throws SQLException {
	
		rsver = conver.query("",";select count(*) from sysobjects where type = 'u' and name like 'BDStdCostProgVersion'");
		rsver.next();
		exit = rsver.getInt(1);
		if (exit ==0)
		{ 
			String CrTVerVerify = ";CREATE TABLE BDStdCostProgVersion"
					+ "(version varchar(30),v1 int,v2 int,v3 int,v4 int,programfile varchar(101) default 'standCostCal')" ;
		   	conver.update("",CrTVerVerify );
		} }
	  /*
     * 版本验证
     */
    public int verVersion() throws SQLException {
     rsver=conver.query("",";select count(*) from BDStdCostProgVersion where programfile like 'standCostCal'");
     
     if (rsver.next()&&(rsver.getInt(1)==0)) {
    	 String cmdInVer=";insert into BDStdCostProgVersion(version,v1,v2,v3,v4,programfile) "
    	 		+ " values('"+String.valueOf(v1)+"'+'.'+'"+String.valueOf(v2)+"'+'.'+'"+String.valueOf(v3)+"'+'.'+'"
    			 +String.valueOf(v4)+"',"+v1+","+v2+","+v3+","+v4+",'standCostCal')";
    	 conver.update("",cmdInVer);
    	 verifyVer = 1;}
     else {
    	 if (rsver.getInt(1) > 1) { 
    			 conver.update("",";delete BDStdCostProgVersion where v1<(select max(v1) from BDStdCostProgVersion)");
    			 conver.update("",";delete BDStdCostProgVersion where v2<(select max(v2) from BDStdCostProgVersion)");
    			 conver.update("",";delete BDStdCostProgVersion where v3<(select max(v3) from BDStdCostProgVersion)");
    			 conver.update("",";delete BDStdCostProgVersion where v4<(select max(v4) from BDStdCostProgVersion)"); }
    	 else {
    	 rsver=conver.query("",";select v1,v2,v3,v4 from BDStdCostProgVersion "
     		+ " where programfile like 'standCostCal'");
     		if (rsver.next()) 
     		{	if
     		(
     			(rsver.getInt(1) > v1) 
     			||((rsver.getInt(1) == v1)&&(rsver.getInt(2) > v2))
     			||((rsver.getInt(1) == v1)&&(rsver.getInt(2) ==v2)&&(rsver.getInt(3)>v3))
     			||((rsver.getInt(1) == v1)&&(rsver.getInt(2) ==v2)&&(rsver.getInt(3)==v3)&&(rsver.getInt(4)>v4))
     		)  	{ verifyVer = 0;}
     		else {
     			conver.update("",";update BDStdCostProgVersion set v1="+v1+",v2="+v2+",v3="+v3+",v4="+v4);
     			verifyVer = 1;}
     	}
    	else {verifyVer = 0;}
     }}   
       return verifyVer;
    }



}