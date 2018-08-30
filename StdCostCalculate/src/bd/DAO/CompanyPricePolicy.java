package bd.DAO;

import java.sql.SQLException;

import bd.View.StdCostCalculate;

public class CompanyPricePolicy {
	/*
	 * update company price policy
	 */
	public void update(double RMBPri,double USDPri) 
			throws SQLException
	{
		String upComPrcPly = " ; update icprcplyentry set "
			+ " fprice = (case fcuryid "
			+ " when 1 		then round(" + RMBPri + ",0) "	/*出厂核价 含税  RMB Days 30*/
			+ " when 1000 	then round(" + USDPri+ ",2) "	/*出厂核价 含税  USD Days 60 FOB青岛*/
			+ " else 0 end)"				
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
		conn.update("",upComPrcPly+upComPrcSpec);
		conn.close();
		//System.out.println("更新公司价格体系！"+upComPrcPly+upComPrcSpec);
	}
	/*
	 * 只用于自动计算
	 * 没有计算的，在价格体系里把价格改为1000
	 */
	public void clean() throws SQLException
	{
		String cmdClean=";update icprcplyentry set "
					+ " fprice = 1000 "
				+ " from icprcplyentry "
				+ " where finterid = 3 "
				+ " and fitemid not in ("
				+ " select fproditemid from t_bdStandCostRPT "
				+ " where autoFlag = "+StdCostCalculate.autoFlag ;
		System.out.println(cmdClean);
		//conn.update("", cmdClean);
		//conn.close();
	}
	private DBConnect conn=new DBConnect();

}
