package bd.DAO;

import java.sql.SQLException;

public class CompanyPricePolicy {
	/*
	 * update company price policy
	 */
	public void set(double RMBPri,double USDPri) 
			throws SQLException
	{
		String upComPrcPly = " ; update icprcplyentry set "
			+ " fprice = (case fcuryid "
			+ " when 1 		then round(" + RMBPri + ",0) "	/*出厂核价 含税  RMB Days 30*/
			+ " when 1000 	then round(" + USDPri+ ",2) "	/*出厂核价 含税  USD Days 60 FOB青岛*/
			+ " else 0 end)"
			+ " ,fbegdate = getdate()"
			+ " ,fenddate = dateadd(day,-1,dateadd(year,datediff(year,0,getdate())+1,0))"				
			+ " where finterid = 3 "
			+ " and fitemid ="+ ProductInfo.firstitemid;
		String upComPrcSpec = " ; update icprcplyentryspec set"
			+ " flowprice = (case fcuryid "
			+ " when 1  	then round(" + RMBPri + ",0) "	/*出厂核价 含税  RMB Days 30*/
			+ " when 1000 	then round(" + USDPri+ ",2) "	/*出厂核价 含税  USD Days 60 FOB青岛*/
			+ " else 0 end)"	
			+ " from icprcplyentryspec a "
			+ " join icprcplyentry b on a.fitemid = b.fitemid"
			+ " and a.finterid = b.finterid "
			+ " and a.frelatedid = b.frelatedid "
			+ " and a.flpricecuryid = b.fcuryid"
			+ " and a.flpricectrl = 1 "
			+ " where a.fitemid ="+ ProductInfo.firstitemid
			+ " and a.finterid = 3";
		conn.update(upComPrcPly+upComPrcSpec);
		conn.close();
		//System.out.println("更新公司价格体系！"+upComPrcPly+upComPrcSpec);
	}
	
	/*
	 * 计算不成功的，在价格体系里把价格改为9000
	 */
	public void set9k() throws SQLException
	{
		String set9k=";update icprcplyentry set "
					+ " fprice = 9000 "
				+ " from icprcplyentry "
				+ " where finterid = 3 "
				+ " and fitemid ="+ ProductInfo.firstitemid;
		//System.out.println(set9k);
		conn.update(set9k);
		conn.close();
	}
	/*
	 *自动全量计算时，计算范围之外的产品，在价格体系里把价格改为0
	 */
	public void set0OutScope() throws SQLException
	{
		String set0=";update icprcplyentry set "
				+ " fprice = 9000 "
				+ " from icprcplyentry "
				+ " where finterid = 3 "
				+ " and fprice <> 0"
				+ " and fitemid not in ("
				+ "	select b.fitemid from t_icitemcore b"
				+ " join icbom c on c.fitemid= b.fitemid"
				+ " 	and c.fstatus = 1 and c.fusestatus = 1072 "
				+ "		and b.fnumber like '01.%')";
		//System.out.println(set9k);
		conn.update(set0);
		String setProductStdPric=" ;update t_icitemstandard "
				+ " set fstandardcost= 0 "
				+ " from t_icitemstandard a"
				+ " join t_icitemcore b on a.fitemid = b.fitemid and b.fnumber like '01.%' "
				+ " where a.fstandardcost <> 0 "
				+ " and a.fitemid not in ("
				+ " select b.fitemid from t_icitemcore b "
				+ " join icbom c on c.fitemid= b.fitemid and c.fstatus = 1 "
				+ " and c.fusestatus = 1072 and b.fnumber like '01.%')";
		conn.update(setProductStdPric);
		conn.close();
	}
	private DBConnect conn=new DBConnect();

}
