package bd.DAO;

import java.sql.SQLException;

/*
 * set autocalcualte environment
 */

public class EnvForAuto 
{
	public void set() throws SQLException
	{
		cmdSetEnv = ";truncate table BDBomMulExpose"
				+ ";truncate table t_costmaterialbd"
				+ ";truncate table t_BDLabourAndMake"
				+ ";truncate table t_bdStandCostRPT"
				+ ";update icmaxnum set fmaxnum = 0 "
				+ " where FTableName like 't_bdStandCostRPT'";
		conn.update("",cmdSetEnv);
		conn.close();
	}

	
	private String cmdSetEnv;
	private DBConnect conn=new DBConnect();

}
