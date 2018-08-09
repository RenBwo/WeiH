package bd.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import bd.connection.getcon;

public class CreateTableLaborAndMake {

	private getcon conn =new getcon();
	 private ResultSet 	rs0;

   	public void crteTabLabourAndMake() throws SQLException
	{   	/*
   			 * 制造费用与直接工资表 TABLE t_BDLabourAndMake
   			 */
   			rs0 = conn.query("",";select count(*) from sysobjects where type = 'u' and name like 't_BDLabourAndMake'");
   			if(rs0.next() && rs0.getInt(1) >0 ) 
   			{
   				//System.out.println("table_LabourAndMake exists ");
   			}
   			else
   			{
   				String  command1 = ";CREATE TABLE t_BDLabourAndMake"
   						 + "("
   						 + "foperid 	int not null default 0,"
   						 + "fopersn	int,"
   						 + "Fnumber 	varchar(60),"
   						 + "assyname	varchar(60),"
   						 + "fQty decimal(20,4),"					 
   						 + "fpiecerate decimal(20,4),"					
   						 + "fopername varchar(60),"
   						 + "frate decimal(20,4),"
   						 + "fmakeqty decimal(20,4),"
   						 + "amtpay decimal(20,4),"
   						 + "amtassure decimal(20,4),"
   						 + "costworker decimal(20,4),"
   						 + "fmatpower decimal(20,4),"
   						 + "fdepr decimal(20,4),"
   						 + "famtadi decimal(20,4),"
   						 + "famtmodel decimal(20,4),"
   						 + "fproditemid  int,"
   						 + "finterid    	int"
   						 + ")";
   				 conn.update("",command1);
   				 conn.close();
   				//System.out.println("create table_LabourAndMake success ");
   			}
   		}
}
