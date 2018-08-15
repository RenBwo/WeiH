package bd.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import bd.connection.GetDBConnect;

public class VerifyBOM {
	/*
	 * BOM integrity Verify	 最终物料并且加工类型为自制的，说明BOM不完整
	 */
	public int verifyBOM(int firstitemid,int finterid) throws SQLException
	{
		String cmdverify =";select count(*) from BDBomMulExpose where enditem = 1 and maketype = 2"
				+ " and firstitemid = "+firstitemid +" and finterid = "+finterid;
		rs0 = conn.query("",cmdverify);
		if(rs0.next() && rs0.getInt(1) >0) 
		{conn.close();
		return 1;
		}
		else
		{ conn.close();
		return 0;
		}
		
	}

	private GetDBConnect conn =new GetDBConnect();
	private ResultSet 	rs0;
}
