package bd.DAO;

import java.sql.SQLException;
import bd.connection.GetDBConnect;

public class UpdateProductStdCost {
	/*
	 * 升级物料.标准单位成本
	 */
	public void updateProductStdCost(int firstitemid,double value) throws SQLException{
		String cmdUpdateProductStdCost = "; update t_icitemstandard "
				+ " set fstandardcost= "+value
				+ " where fitemid = "+firstitemid;
		conn.update("",cmdUpdateProductStdCost);
		conn.close();
	}
	private GetDBConnect conn=new GetDBConnect();


}
