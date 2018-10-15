package bd.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Route 
{
	public void get() throws SQLException
	{
		verifyRout();
		hasRouteQHL();
		hasRoutePipe();
		hasRouteDry();
	}
	/*
	 * Routing integrity Verify	自制品并且不跳层的物料必须有工艺路线
	 * 1058 跳层,1059 不跳层
	 */
	private void verifyRout() throws SQLException
	{
		String cmdverify =";select count(*) "
				+ " from BDBomMulExpose"
				+ " where isnull(bomskip,0) = 1059 "
				+ " and  maketype = 2 and isnull(froutingid,0) = 0 "
				+ " and firstitemid = "+ProductInfo.firstitemid 
				+" and finterid = "+ProductInfo.finterid;
		rs0 = conn.query(cmdverify);
		if(rs0.next() && rs0.getInt(1) >0) 
		{
			conn.close();
			verifyRoute= 1;			
		}
		else 
		{
			conn.close();
			verifyRoute=  0;
		}
	
	}
	/*
	 * 有钎焊炉工艺
	 * 钎焊焊接工艺40336
	 */
	private void hasRouteQHL() throws SQLException 
	{
		String cmdQHL=";select count(*) from BDBomMulExpose  a "
				+ " join t_routing b on a.froutingid = b.finterid "
				+ " and a.firstitemid = "+ProductInfo.firstitemid 
				+ " and a.finterid = "+ProductInfo.finterid
				+ " join t_routingoper c on c.finterid = b.finterid"
				+ " and isnull(c.foperid,0) = 40336";
		rs0 = conn.query(cmdQHL);
		if(rs0.next() && (rs0.getInt(1) >0))
		{
			hasQHL=true;
		}
		else 
		{
			hasQHL=false;
		}
		
	}
	/*
	 * 有扁管工艺
	 * 扁管压出工艺40494
	 * 扁管切断工艺40495
	 */
	private void hasRoutePipe() throws SQLException 
	{
		String cmdPipe=";select count(*) from BDBomMulExpose  a "
				+ " join t_routing b on a.froutingid = b.finterid "
				+ " and a.firstitemid = "+ProductInfo.firstitemid 
				+ " and a.finterid = "+ProductInfo.finterid
				+ " join t_routingoper c on c.finterid = b.finterid"
				+ " and (isnull(c.foperid,0) = 40494 or isnull(c.foperid,0) = 40495)";
		rs0 = conn.query(cmdPipe);
		if(rs0.next() && (rs0.getInt(1) >0))
		{
			hasPipe=true;
		}
		else 
		{
			hasPipe=false;
		}
		
	}/*
	 * 有芯体烘干喷漆工艺
	 * 芯体烘干喷漆 40142 
	 */
	private void hasRouteDry() throws SQLException 
	{
		String cmdDry=";select count(*) from BDBomMulExpose  a "
				+ " join t_routing b on a.froutingid = b.finterid "
				+ " and a.firstitemid = "+ProductInfo.firstitemid 
				+ " and a.finterid = "+ProductInfo.finterid
				+ " join t_routingoper c on c.finterid = b.finterid"
				+ " and isnull(c.foperid,0) = 40142";
		rs0 = conn.query(cmdDry);
		if(rs0.next() && (rs0.getInt(1) >0))
		{
			hasDry=true;
		}
		else 
		{
			hasDry=false;
		}
		
	}

	
	private DBConnect conn =new DBConnect();
	private ResultSet 	rs0;	
	public static Boolean hasQHL,hasPipe,hasDry;
	public static int verifyRoute;
	
}
