package bd.DAO;

import java.sql.SQLException;
import bd.connection.getcon;

public class UpdateMachineInfo {
	/*
	 * 	升级设备最新折旧和功率信息
	 */
	public void updateMachineInfo() throws SQLException
	{
		String sql1 = "; update t_costcalculatebd_entry0 set fpower=b.fpowercap,fassetinterid=b.fassetnumber"
				+ ",fmanname=b.fdevicename,fmachinespection=b.fspecification"
				+ " from t_costcalculatebd_entry0 a join icdeviceaccount b on a.fmanum = b.fdevicenumber";
		//System.out.println("升级设备信息：" + sql1);
		conn.update("",sql1);
		sql1 = "; update t_costcalculatebd_entry0 set fdepreciation = c.fdeprshould"
				+ " from t_costcalculatebd_entry0 a "
				+ " join (select fassetid,max(fdeprperiods) as fdeprperiods"
				+ " from t_fabalance "
				+ "  where fdeprshould > 0 group by fassetid) b on b.fassetid = a.fassetinterid"
				+ " join t_fabalance c on c.fdeprperiods = b.fdeprperiods and c.fassetid = a.fassetinterid";
		conn.update("",sql1);
		//System.out.println("升级设备折旧信息："+sql1);
		conn.close();
	}
	private getcon conn=new getcon();

}
