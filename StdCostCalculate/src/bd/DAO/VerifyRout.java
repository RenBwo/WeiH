package bd.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import bd.connection.GetDBConnect;

public class VerifyRout 
{
	private GetDBConnect conn =new GetDBConnect();
	private ResultSet 	rs0;	
	/*
	 * Routing integrity Verify	自制品并且不跳层的物料必须有工艺路线
	 * 1058 跳层,1059 不跳层
	 */
	public int verifyRout(int firstitemid,int finterid) throws SQLException
	{
		String cmdverify =";select count(*) from BDBomMulExpose where isnull(bomskip,0) = 1059 "
				+ " and  maketype = 2 and isnull(froutingid,0) = 0 "
				+ " and firstitemid = "+firstitemid +" and finterid = "+finterid;
		rs0 = conn.query("",cmdverify);
		if(rs0.next() && rs0.getInt(1) >0) 
		{
			conn.close();
			return 1;			
		}
		else 
		{
			conn.close();
			return 0;
		}
	
	}

}
