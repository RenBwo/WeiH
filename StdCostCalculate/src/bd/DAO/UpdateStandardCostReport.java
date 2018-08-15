package bd.DAO;

import java.sql.SQLException;
import bd.connection.GetDBConnect;

public class UpdateStandardCostReport {
	/*
	 * update StdCostRPT
	 */
	public void udateStandardCostReport(
		  String currentyear			, int firstitemid				, int finterid
		, double fCostProduce			, double fCostMaterial			, double fCostDirectLabor	
		, double fCostPay				, double  fCostAssure 			, double fCostMake			
		, double fCostMake_Pay 			, double fCostMake_Power 		, double fCostMake_Adi		
		, double fCostMake_deprication	, double fCostMake_Jig			, double fCostMake_others
		, double fCostHouse 			, double fCostProidInland		, double fCostProidFobQD	
		, double fCostBadProd 			, double fCostFinance			, double fCostSail			
		, double fCostManage 			, double fCostLand				, double fCostNewModel		
		, double itselfQtySaled			, double modelQtySaled
		, double QtyShare				, double fCostTransportInland	, double fCostTransportFobQD	
		, double fCostStandInland		, double fCostStandFobQD		, double fGainInland			
		, double fGainFobQD				, double fCostTotalInland		, double fCostTotalFobQD		
		, double fincrementTax		, double fPrice_China_InTax_Delay	, double fPrice_FobQD_Delay_USD		
		, double fResPriceK0 			, double fAssureK1 				, double fMakePayK2	
		, double fBadProdK4				, double fFinanceK5				, double fManageK6				
		, double fSailK7				, double gainrate				, double fTaxRateK10	
		, double fDelayDaysK11 			, double fDelayDaysK22			, double fExchangeRateK12 
		, double fInterestRateK13 		, double fwashingK14
			) throws SQLException
	{
		String   upStdCostRPT=" ; declare @fid int"
				+ " ; EXEC	[dbo].[GetICMaxNum] 't_bdStandCostRPT',@fid OUTPUT,1 "
				+ " ; update t_bdStandCostRPT set  fid = @fid, fclasstypeid = 200000013"
				+ ", fBillno = '"+currentyear+"'+convert(varchar(20),@fid)"			
				+ ", fCostProduce="  			+	fCostProduce				/*一.生产成本合计（1+2+3）*/
		        + ", fCostMaterial = "			+	fCostMaterial 				/*1.直接材料成本*/
				+ ", fCostDirectLabor = " 		+	fCostDirectLabor			/*2.直接人工*/
				+ ", fCostPay = "				+	fCostPay 					/*2.1直接人工-工资*/
				+ ", fCostAssure = " 			+	fCostAssure					/*2.2直接人工-保险*/
				+ ", fCostMake = "				+	fCostMake					/*3.制造费用（合计）*/
				+ ", fCostMake_Pay = " 			+	fCostMake_Pay				/*3.1制造费用-间接人工 */
				+ ", fCostMake_Power = " 		+	fCostMake_Power				/*3.2制造费用-电费*/ 
				+ ", fCostMake_Adi = " 			+	fCostMake_Adi				/*3.3制造费用-辅料*/
				+ ", fCostMake_deprication=" 	+	fCostMake_deprication		/*3.4制造费用-设备折旧 */
				+ ", fCostMake_Jig = " 			+	fCostMake_Jig				/*3.5制造费用-工装模具 */
				+ ", fCostMake_others="			+	fCostMake_others			/*3.6制造费用-其他成本 */
				+ ", fCostHouse = " 			+	fCostHouse					/*3.7厂房折旧  */
				+ ", fCostProidInland="  		+	fCostProidInland			/*二.期间费用合计（内贸）*/
				+ ", fCostProidFobQD="  		+	fCostProidFobQD				/*二.期间费用合计（FOB青岛）*/
				+ ", fCostBadProd = " 			+	fCostBadProd				/*成品不良成本 */
				+ ", fCostFinance = " 			+	fCostFinance 				/*财务费用 */
				+ ", fCostSail = " 				+	fCostSail					/*销售费用 */
				+ ", fCostManage = " 			+	fCostManage					/*管理费用 */
				+ ", fCostLand = "  			+	fCostLand 					/*管理费用--土地摊销 */
				+ ", fCostNewModel = "  		+	fCostNewModel				/*新开模具工装费用 */
				//+ " ,QtyShare = "  				+？？						/*新开模具摊销数量 */	
				+ ", itselfQtySaled = " 		+	itselfQtySaled				/*产品自身历史销售数量 */	
				+ ", modelQtySaled = " 			+	modelQtySaled				/*产品型号历史销售数量 */
				+ ", fCostTransportInland = "  	+ 	fCostTransportInland 		/*运输费用 （内贸） */
				+ ", fCostTransportFobQD = " 	+ 	fCostTransportFobQD			/*运输费用（FOB青岛） */
				+ ", fCostStandInland="  		+	fCostStandInland			/*标准成本=生产成本合计+期间费用合计（内贸）*/
				+ ", fCostStandFobQD="  		+	fCostStandFobQD				/*标准成本=生产成本合计+期间费用合计（FOB青岛)*/
				+ ", fGainInland = " 			+	fGainInland					/*产品利润（内贸） */
				+ ", fGainFobQD = " 			+	fGainFobQD					/*产品利润（FOB青岛） */
				+ ", fCostTotalInland="			+	fCostTotalInland			/*核价（内贸）*/
				+ ", fCostTotalFobQD="			+	fCostTotalFobQD				/*核价（FOB青岛）RMB*/
				+ ", fincrementTax="			+	fincrementTax				/*增值税*/
				+ ", fPrice_China_InTax_Delay="	+	fPrice_China_InTax_Delay	/*出厂核价 含税  RMB Days 30*/
				+ ", fPrice_FobQD_Delay_USD=" 	+	fPrice_FobQD_Delay_USD		/*出厂核价 含税  USD Days 60 FOB青岛*/
			  	+ ", fResPriceK0 = " 			+	fResPriceK0					/*电费价格 */
				+ ", fAssureK1 = " 				+	fAssureK1					/*直接人工-保险公积金补助福利系数 */
				+ ", fMakePayK2 = "				+	fMakePayK2					/*制造费用-间接人工系数 */
				+ ", fBadProdK4 = " 			+	fBadProdK4					/*产品不良系数 */
				+ ", fFinanceK5 = " 			+	fFinanceK5					/*财务费用系数 */
				+ ", fManageK6 = " 				+	fManageK6					/*管理费用系数 */
				+ ", fSailK7 = " 				+ 	fSailK7						/*销售费用系数 */
				+ ", fGainRateK9 = " 			+	gainrate					/*产品利润率 */
				+ ", fTaxRateK10	 = " 		+	fTaxRateK10					/*国内增值税率 */
				+ ", fDelayDaysK11 = " 			+	fDelayDaysK11				/*国内账期 */
				+ ", fDelayDaysK22 = " 			+	fDelayDaysK22				/*国外账期 */
				+ ", fExchangeRateK12 = " 		+	fExchangeRateK12			/*预算汇率 */
				+ ", fInterestRateK13 = " 		+	fInterestRateK13			/*贷款利率 */
				+ ", fwashingK14 = " 			+	fwashingK14					/*清洗费用 */	
				+ " where fproditemid = "		+	firstitemid
				+ " and finterid = "			+	finterid ; 

		//System.out.println("标准成本保存语句: "+upStdCostRPT);
		conn.update("",upStdCostRPT);
		conn.close();
	}
	
	private GetDBConnect conn=new GetDBConnect();
}
