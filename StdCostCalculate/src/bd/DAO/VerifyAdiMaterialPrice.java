package bd.DAO;
import java.sql.ResultSet;
import java.sql.SQLException;

import bd.connection.getcon;
/*
 * verify adi price
 */
public class VerifyAdiMaterialPrice {
	public int verifyAdiPrice(int firstitemid,int finterid) throws SQLException 
	{
		String cmdverify=";select count(*) "
				+ " from 	BDBomMulExpose 			t1"
				+ " join 	t_Routing 					t2 	"
				+ " on 		t1.fitemid = t2.fitemid  "
				+ " and 	t1.firstitemid ="+firstitemid
				+ " and 	t1.finterid = "+finterid
				+ " and 	t2.finterid = t1.froutingid"
				+ " join 	t_routingoper 				t3 	"
				+ " on 		t2.finterid = t3.finterid "
				+ " join 	t_CostCalculateBD 			t4 "
				+ "	on 		t4.foperno = t3.foperid "
				+ " join 	t_costcalculatebd_entry1 	t5 "
				+ "	on 		t4.fid = t5.fid "
				+ " and 	isnull(t5.fprice,0) = 0 "
				+ " and 	isnull(t5.fqty,0) <> 0 ";
		rs0=conn.query("", cmdverify);
		if(rs0.next() && rs0.getInt(1) > 0 ) 
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
	private ResultSet rs0;
	private getcon conn=new getcon();
}
