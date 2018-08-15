package bd.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import bd.connection.GetDBConnect;

public class VerifyMaterialPrice {
	/*
	 * verify direct_material price验证直接材料价格
	 */
	public int verifyMaterialPrice(int firstitemid,int finterid) 
			throws SQLException
	{	
		String cmdverify =";select count(*) from t_CostMaterialBD where fproditemid= "+firstitemid 
				+" and finterid = "+finterid +" and isnull(fprice,0)=0 ";
		rs0=conn.query("", cmdverify);
		if(rs0.next() && rs0.getInt(1) > 0  ) 
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

	private GetDBConnect conn =new GetDBConnect();
	private ResultSet 	rs0;
	
}
