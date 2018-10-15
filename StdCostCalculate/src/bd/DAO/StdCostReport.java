package bd.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTable;

import bd.View.StdCostCalculate;

public class StdCostReport {
	
	public void createTableRPT() throws SQLException
	{  
  		/*  报价表*/
  		/*conn.update(StdCostCalculate.test,"drop table t_bdStandCostRPT;");*/
  		rs0 = conn.query(";select count(*) from sysobjects where type = 'u' and name like 't_bdStandCostRPT'");
  		if(rs0.next() && rs0.getInt(1)>0 ) 
  		{
  			//System.out.println("table_rpt exists ");
  		}
  		else
  		{ 
  				String  command4 = ";CREATE TABLE t_bdStandCostRPT"
  						 + "("
  						 + " fid					int"  			//K3BOS单据id
 						 + ",fClasstypeId			int"  			//K3BOS单据	Classtypeid
 						 + ",fBillno				varchar(50)"  	//K3BOS BILLNO
  						 + ",finterid    			int Identity(00000001,1) not null"
  						 + ",fproditemid 			int "			//产品物料内码
  						 + ",autoFlag				varchar(30) "	//自动升级标记，取批量计算开始时间 yyyymmddmmss		
  						 + ",fdate					datetime"		//核价日期
  						 
  						 + ",fCostProduce		 	decimal(20,4)"  //一.生产成本合计（1+2+3）
  						 
  						 + ",fCostMaterial 			decimal(20,4)"  //1.直接材料成本
  						   						 
  						 + ",fCostDirectLabor		decimal(20,4)"  //2.直接人工
  						 + ",fCostPay 				decimal(20,4)"  //2.1直接人工-工资
  						 + ",fCostAssure			decimal(20,4)"  //2.2直接人工-保险 
  						  						 
  						 + ",fCostMake			 	decimal(20,4)"  //3.制造费用（合计）
  						 + ",fCostMake_Pay 			decimal(20,4)"  //3.1制造费用-间接人工
  						 + ",fCostMake_Power	 	decimal(20,4)"  //3.2制造费用-电费
  						 + ",fCostMake_Adi	 		decimal(20,4)"  //3.3制造费用-辅料
  						 + ",fCostMake_deprication 	decimal(20,4)"  //3.4制造费用-设备折旧
  						 + ",fCostMake_Jig		 	decimal(20,4)"  //3.5制造费用-工装模具折旧
  						 + ",fCostMake_others	 	decimal(20,4)"  //3.6制造费用-其他成本 
  						 + ",fCostHouse			 	decimal(20,4)"  //3.7厂房折旧 
  						 
  						 + ",fCostProidInland		decimal(20,4)"  //二.期间费用合计（内贸）
  						 + ",fCostProidFobQD		decimal(20,4)"  //二.期间费用合计（FOB青岛）
  						 + ",fCostBadProd			decimal(20,4)"  //成品不良成本
  						 + ",fCostFinance		 	decimal(20,4)"  //财务费用
  						 + ",fCostSail				decimal(20,4)"  //销售费用
  						 + ",fCostManage		 	decimal(20,4)"  //管理费用
  						 + ",fCostLand				decimal(20,4)"  //管理费用--土地摊销
  						 + ",fCostNewModel			decimal(20,4)"  //新模具工装费用 
  						 + ",QtyShare				decimal(20,4)" 	//新开模具摊销数量
  						 + ",modelQtySaled			decimal(20,4)" 	//历史销售数量，取已经审核的销售发票   						 
  						 + ",fCostTransportInland	decimal(20,4)" 	//运输费用 （内贸）  						 
  						 + ",fCostTransportFobQD	decimal(20,4)"  //运输费用（FOB青岛）
  						 
  						 
  						 + ",fCostStandInland		decimal(20,4)"  //标准成本=生产成本合计+期间费用合计（内贸）
  						 + ",fCostStandFobQD		decimal(20,4)"  //标准成本=生产成本合计+期间费用合计（FOB青岛）
  						 + ",fGainInland			decimal(20,4)"  //产品利润（内贸）
  						 + ",fGainFobQD			 	decimal(20,4)"  //产品利润（FOB青岛）
  						 
  						 + ",fCostTotalInland		decimal(20,4)"  //核价（内贸）
  						 + ",fCostTotalFobQD		decimal(20,4)"  //核价（FOB青岛）RMB
  						 
  						 + ",fincrementTax			decimal(20,4)"  //增值税
  						 
  						 + ",fPrice_China_InTax_Delay	decimal(20,4)"  //出厂核价 含税  RMB Days 30
  						 + ",fPrice_FobQD_Delay_USD	 	decimal(20,4)" //出厂核价 含税  USD Days 60 FOB青岛	 
  						 
  						 + ",fResPriceK0		decimal(20,4)" //电费价格
  						 + ",fAssureK1			decimal(20,4)" //直接人工_保险系数
  						 + ",fMakePayK2			decimal(20,4)" //制造费用_间接人工系数
  						 + ",fBadProdK4			decimal(20,4)" //产品不良系数
  						 + ",fFinanceK5			decimal(20,4)" //财务费用系数
  						 + ",fManageK6			decimal(20,4)" //管理费用系数
  						 + ",fSailK7			decimal(20,4)" //销售费用系数
  						 + ",fGainRateK9		decimal(20,4)" //产品利润率
  						 + ",fTaxRateK10		decimal(20,4)" //国内增值税率
  						 + ",fDelayDaysK11		decimal(20,4)" //国内账期
  						 + ",fDelayDaysK22		decimal(20,4)" //外贸账期   						 
  						 + ",fExchangeRateK12	decimal(20,4)" //预算汇率
  						 + ",fInterestRateK13	decimal(20,4)" //贷款利率
  						 + ",fwashingK14		decimal(20,4)" //清洗费用  
  						 + ")";
  				 conn.update(command4);
  				 conn.close();
  			     //System.out.println("createtable rpt success ");   				
  			}
  		}
	/* 
	 * set tableReport value
	 */
	public void set(
			JTable tableReport
			,JTable tableMaterial
			,JTable tableCalculate
			)
	{
		df0 = new DecimalFormat("######0");
		df2 = new DecimalFormat("######0.00%");
		df4 = new DecimalFormat("######0.00");
		/*
		*  1.直接材料成本 
		*/
		tableReport.setValueAt(df4.format(Double.parseDouble(
				tableMaterial.getValueAt(tableMaterial.getRowCount() - 1,7
						).toString())),1,2);
		/*
		 * 2.直接人工=工资 + 保险 + 清洗直接人工
		 */
		tableReport.setValueAt(df4.format(Double.parseDouble(
				tableCalculate.getValueAt(tableCalculate.getRowCount() - 1,7
						).toString()) 
				+ Double.parseDouble(tableCalculate.getValueAt(
						tableCalculate.getRowCount() - 1,8).toString())
				+ Double.parseDouble(StdCostCalculate.mainFrame.textFK14.getText()
						)  ),2,2);
		/*
		 *  3-1.间接人工
		 */
		tableReport.setValueAt(	df2.format(Double.parseDouble(
				StdCostCalculate.mainFrame.textFK2.getText())),4,3);
		tableReport.setValueAt(	df4.format(Double.parseDouble(
				tableCalculate.getValueAt(
						tableCalculate.getRowCount() - 1,9).toString()))
						,4,2);
		/*
		 *  3-2.制造费用--电费
		 */
		tableReport.setValueAt(df4.format(Double.parseDouble(
				tableCalculate.getValueAt(
						tableCalculate.getRowCount() - 1,10).toString()
				)
				+MachineInfo.amtPowerQHL
				),5,2);
		/*
		 *  3-3.制造费用--辅料费
		 */
		tableReport.setValueAt(df4.format(Double.parseDouble(
				tableCalculate.getValueAt(
						tableCalculate.getRowCount() - 1,12).toString()
				)
				+MaterialAdi.amtQianji+MaterialAdi.amtLN2
				),6,2);
		/*
		 * 3-4.制造费用--设备折旧费
		 */ 
		tableReport.setValueAt(df4.format(Double.parseDouble(
				tableCalculate.getValueAt(
						tableCalculate.getRowCount() - 1,11).toString()
				)),7,2);
		/*
		 *  3-5.制造费用--工装模具折旧费
		 */
		tableReport.setValueAt(df4.format(Double.parseDouble(
				tableCalculate.getValueAt(
						tableCalculate.getRowCount() - 1,13).toString()
				)),8,2);
		/*
		 * 3-6.制造费用--其他成本
		 * 直接人工×制造费用其他百分比（K18） 20180919
		 */
		tableReport.setValueAt(df2.format(Double.parseDouble(
				StdCostCalculate.mainFrame.textFK18.getText())),9,3);
		tableReport.setValueAt(df4.format(Double.parseDouble(
				tableReport.getValueAt(2,2).toString())
				*Double.parseDouble(
				StdCostCalculate.mainFrame.textFK18.getText())),9,2);
		/*
		 * 3-7.厂房折旧 
		 * 直接人工×厂房折旧百分比（K3） 20180919
		 */
		tableReport.setValueAt(df2.format(Double.parseDouble(
				StdCostCalculate.mainFrame.textFK3.getText())),10,3);
		tableReport.setValueAt(df4.format(Double.parseDouble(
				tableReport.getValueAt(2, 2).toString())
				*Double.parseDouble(
				StdCostCalculate.mainFrame.textFK3.getText())),10,2);
		
		/*
		 * 3.制造费用合计
		 * 上述3-× ，再加上制造费用-清洗
		 */
		tableReport.setValueAt(df4.format(Double.parseDouble(
				tableReport.getValueAt(4, 2).toString())
				+ Double.parseDouble(tableReport.getValueAt(5, 2).toString())
				+ Double.parseDouble(tableReport.getValueAt(6, 2).toString())
				+ Double.parseDouble(tableReport.getValueAt(7, 2).toString())
				+ Double.parseDouble(tableReport.getValueAt(8, 2).toString())
				+ Double.parseDouble(tableReport.getValueAt(9, 2).toString())
				+ Double.parseDouble(tableReport.getValueAt(10,2).toString())
				+ Double.parseDouble(StdCostCalculate.mainFrame.textFK141.getText())
						),3,2);
		/*
		 * 一、生产成本合计=1.直接材料成本+2.直接人工+3.制造费用合计
		 */
		tableReport.setValueAt(df4.format(Double.parseDouble(
				tableReport.getValueAt(1, 2).toString())
				+Double.parseDouble(tableReport.getValueAt(2, 2).toString())
				+Double.parseDouble(tableReport.getValueAt(3, 2).toString())
				),0,2);
		/*
		 * 成品不良成本k4
		 */
		tableReport.setValueAt(df2.format(Double.parseDouble(
				StdCostCalculate.mainFrame.textFK4.getText())),12,3);
		tableReport.setValueAt(df4.format(Double.parseDouble(
				tableReport.getValueAt(0, 2).toString())
				*Double.parseDouble(StdCostCalculate.mainFrame.textFK4.getText())
				),12,2);
		/*
		 * 财务费用
		 */
		tableReport.setValueAt(df2.format(Double.parseDouble(
				StdCostCalculate.mainFrame.textFK5.getText())),13,3);
		tableReport.setValueAt(df4.format(Double.parseDouble(
				tableReport.getValueAt(0, 2).toString())
				*Double.parseDouble(StdCostCalculate.mainFrame.textFK5.getText())
				),13,2);
		/*
		 * 销售费用
		 */
		tableReport.setValueAt(df2.format(Double.parseDouble(
				StdCostCalculate.mainFrame.textFK7.getText())),14,3);
		tableReport.setValueAt(df4.format(Double.parseDouble(
				tableReport.getValueAt(0, 2).toString())
				*Double.parseDouble(StdCostCalculate.mainFrame.textFK7.getText())
				),14,2);
		/*
		 * 管理费用
		 */
		tableReport.setValueAt(df2.format(Double.parseDouble(
				StdCostCalculate.mainFrame.textFK6.getText())),15,3);
		tableReport.setValueAt(df4.format(Double.parseDouble(
				tableReport.getValueAt(0, 2).toString())
				*Double.parseDouble(StdCostCalculate.mainFrame.textFK6.getText())
				),15,2);
		/*
		 * 管理费用--土地摊销
		 * 直接人工×土地摊销百分比（K15） 20180919
		 */
		tableReport.setValueAt(df2.format(Double.parseDouble(
				StdCostCalculate.mainFrame.textFK15.getText())),16,3);
		tableReport.setValueAt(df4.format(Double.parseDouble(
				tableReport.getValueAt(2, 2).toString())
				*Double.parseDouble(
				StdCostCalculate.mainFrame.textFK15.getText())),16,2);
		/*
		 * 新开模具工装费用 
		 */
		tableReport.setValueAt(df4.format(ProductInfo.newJigAmortizeAmt),17,2);
		/*
		 * 外贸项目
		 */
		int l;
		for (l=0;l<18;l++) 
		{
			if (l==11) 
			{
				
			}
			else 
			{
				tableReport.setValueAt(tableReport.getValueAt(
							l, 2), l, 5);
			}
		}
		/*
		 * 运输费用
		 */
		tableReport.setValueAt(df4.format(Double.parseDouble(
						StdCostCalculate.mainFrame.textFK8.getText())),18,2);
		/*
		 * FOB青岛费用
		 */
		tableReport.setValueAt(df4.format(Double.parseDouble(
				StdCostCalculate.mainFrame.textFK20.getText())
				/Double.parseDouble(StdCostCalculate.mainFrame.textFK21.getText()
						)*ProductInfo.packagesize),18,5);
		tableReport.setValueAt("FOB青岛费用="+Double.parseDouble(
				StdCostCalculate.mainFrame.textFK20.getText())
		+"（整柜 BY FCL,40HQ/GP,45HQ/GP）/"+Double.parseDouble(
				StdCostCalculate.mainFrame.textFK21.getText())
		+"立方米*单只产品体积 "+ProductInfo.packagesize,18,6);
		/*
		 * 二、期间费用合计
		 */
		tableReport.setValueAt(df4.format(Double.parseDouble(
				tableReport.getValueAt(12, 2).toString())
				+Double.parseDouble(tableReport.getValueAt(13,2).toString())
				+Double.parseDouble(tableReport.getValueAt(14,2).toString())
				+Double.parseDouble(tableReport.getValueAt(15,2).toString())
				+Double.parseDouble(tableReport.getValueAt(16,2).toString())
				+Double.parseDouble(tableReport.getValueAt(17,2).toString())
				+Double.parseDouble(tableReport.getValueAt(18,2).toString())
				),11,2);
		tableReport.setValueAt(df4.format(Double.parseDouble(
						tableReport.getValueAt(12, 5).toString())
				+Double.parseDouble(tableReport.getValueAt(13,5).toString())
				+Double.parseDouble(tableReport.getValueAt(14,5).toString())
				+Double.parseDouble(tableReport.getValueAt(15,5).toString())
				+Double.parseDouble(tableReport.getValueAt(16,5).toString())
				+Double.parseDouble(tableReport.getValueAt(17,5).toString())
				+Double.parseDouble(tableReport.getValueAt(18,5).toString())
				),11,5);
		/*
		 * 三、产品利润=（期间成本+生产成本）×利润率/（1-利润率）
		 */
		tableReport.setValueAt(df2.format(ProductInfo.gainrate),19,3);
		tableReport.setValueAt(df4.format((Double.parseDouble(
				tableReport.getValueAt(0, 2).toString())
				+Double.parseDouble(tableReport.getValueAt(11,2).toString()))
				*ProductInfo.gainrate/(1-ProductInfo.gainrate)),19,2);
		tableReport.setValueAt(df4.format((Double.parseDouble(
				tableReport.getValueAt(0, 5).toString())
				+Double.parseDouble(tableReport.getValueAt(11,5).toString()))
				*ProductInfo.gainrate/(1-ProductInfo.gainrate)),19,5);
		/*
		 * 四、核价=（一、生产成本合计 + 二、期间费用合计 + 三、产品利润）
		 */
		tableReport.setValueAt(df4.format(Double.parseDouble(
				tableReport.getValueAt(0, 2).toString())
				+Double.parseDouble(tableReport.getValueAt(11,2).toString())
				+Double.parseDouble(tableReport.getValueAt(19,2).toString())
				),20,2); 
		tableReport.setValueAt(df4.format(Double.parseDouble(
				tableReport.getValueAt(0, 5).toString())
				+Double.parseDouble(tableReport.getValueAt(11, 5).toString())
				+Double.parseDouble(tableReport.getValueAt(19, 5).toString())
				),20,5);
		/*
		 * 五、增值税额
		 */
		tableReport.setValueAt(df2.format(Double.parseDouble(
				StdCostCalculate.mainFrame.textFK10.getText())),21,3);
		tableReport.setValueAt(df4.format(Double.parseDouble(
				tableReport.getValueAt(20, 2).toString())
				*Double.parseDouble(StdCostCalculate.mainFrame.textFK10.getText())
				),21,2); 
		/*
		 * 六、出厂核价_含税RMB=(四、核价+五、增值税额)+(四、核价+五、增值税额)*货款年利率
		 * /365*内贸账期
		 * =(四、核价+五、增值税额)*（1+货款年利率*内贸账期/365）					
		 */
		tableReport.setValueAt(df4.format((Double.parseDouble(
				tableReport.getValueAt(20, 2).toString())
				+Double.parseDouble(tableReport.getValueAt(21,2).toString()))
				*(1+Double.parseDouble(
						StdCostCalculate.mainFrame.textFK13.getText())
				*Double.parseDouble(
						StdCostCalculate.mainFrame.textFK11.getText())/365)),22,2);
		tableReport.setValueAt("货款年利率： "+df2.format(Double.parseDouble(
				StdCostCalculate.mainFrame.textFK13.getText()))
		+";账期： "+df0.format(Double.parseDouble(
				StdCostCalculate.mainFrame.textFK11.getText()))+"天",22,3);
		/*
		 * 六、FOB青岛核价_USD=(四、核价/预算汇率）+（四、核价/预算汇率）*货款年利率/365*外贸账期=（四、核价/预算汇率）*(1+货款年利率*外贸账期/365)
		 */
		tableReport.setValueAt(df4.format(Double.parseDouble(
				tableReport.getValueAt(20, 5).toString())
				/Double.parseDouble(StdCostCalculate.mainFrame.textExRate.getText()
						)*(1+Double.parseDouble(
								StdCostCalculate.mainFrame.textFK13.getText())
						*Double.parseDouble(
								StdCostCalculate.mainFrame.textFK22.getText())
						/365)
				), 22, 5);
		/*
		 * tableReport.setValueAt("货款年利率： "
		 * +df2.format(Double.parseDouble(textFK13.getText())),19,5);
		 */
		tableReport.setValueAt("货款年利率： "+df2.format(
				Double.parseDouble(StdCostCalculate.mainFrame.textFK13.getText()))
		+";账期： "+df0.format(Double.parseDouble(
				StdCostCalculate.mainFrame.textFK11.getText()))+"天;"
		+"预算汇率： "+StdCostCalculate.mainFrame.textExRate.getText(),22,6);
	}
	/*
	 * del history rpt
	 */
	public void clean() 
			throws SQLException
	{
		String cleanHistPrice=" ; delete from t_bdStandCostRPT "
				+ " where fproditemid = "+ProductInfo.firstitemid
				+ " and finterid < "+ProductInfo.finterid;
		conn.update(cleanHistPrice);
		conn.close();
	}
	public void clear() 
			throws SQLException
	{
		String clearPrice=" ; delete from t_bdStandCostRPT "
				+ " where fproditemid = "+ProductInfo.firstitemid;
		conn.update(clearPrice);
		conn.close();
	}
	/*
	 * update StdCostRPT
	 */
	public void udateStdCostReport(
		 double fCostProduce			, double fCostMaterial			, double fCostDirectLabor	
		, double fCostPay				, double  fCostAssure 			, double fCostMake			
		, double fCostMake_Pay 			, double fCostMake_Power 		, double fCostMake_Adi		
		, double fCostMake_deprication	, double fCostMake_Jig			, double fCostMake_others
		, double fCostHouse 			, double fCostProidInland		, double fCostProidFobQD	
		, double fCostBadProd 			, double fCostFinance			, double fCostSail			
		, double fCostManage 			, double fCostLand				, double fCostNewModel		
		, double fCostTransportInland	, double fCostTransportFobQD	
		, double fCostStandInland		, double fCostStandFobQD		, double fGainInland			
		, double fGainFobQD				, double fCostTotalInland		, double fCostTotalFobQD		
		, double fincrementTax		, double fPrice_China_InTax_Delay	, double fPrice_FobQD_Delay_USD		
		, double fResPriceK0 			, double fAssureK1 				, double fMakePayK2	
		, double fBadProdK4				, double fFinanceK5				, double fManageK6				
		, double fSailK7				, double fTaxRateK10	
		, double fDelayDaysK11 			, double fDelayDaysK22			, double fExchangeRateK12 
		, double fInterestRateK13 		, double fwashingK14
			) throws SQLException
	{
		df1 = new SimpleDateFormat("yyyyMMdd");
		yyyymm=df1.format(new Date());
		String   upStdCostRPT=" ; declare @fid int"
				+ " ; EXEC	[dbo].[GetICMaxNum] 't_bdStandCostRPT',@fid OUTPUT,1 "
				+ " ; update t_bdStandCostRPT set  "
				+ " fid = @fid"
				+ ", fclasstypeid = 200000013"
				+ ", fBillno = '"+yyyymm+"'+right('00000'+convert(varchar(20),@fid),5)"			
				+ ", autoFlag= '"+StdCostCalculate.autoFlag+"'"
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
				+ " ,QtyShare = "  				+ 	ProductInfo.newJigAmortizeQty	/*新开模具计划摊销数量 */	
				+ ", modelQtySaled = " 			+	ProductInfo.saledQty		/*13裸包产品 的历史销售数量 */
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
				+ ", fGainRateK9 = " 			+	ProductInfo.gainrate		/*产品利润率 */
				+ ", fTaxRateK10	 = " 		+	fTaxRateK10					/*国内增值税率 */
				+ ", fDelayDaysK11 = " 			+	fDelayDaysK11				/*国内账期 */
				+ ", fDelayDaysK22 = " 			+	fDelayDaysK22				/*国外账期 */
				+ ", fExchangeRateK12 = " 		+	fExchangeRateK12			/*预算汇率 */
				+ ", fInterestRateK13 = " 		+	fInterestRateK13			/*贷款利率 */
				+ ", fwashingK14 = " 			+	fwashingK14					/*清洗费用 */	
				+ " where fproditemid = "		+	ProductInfo.firstitemid
				+ " and finterid = "			+	ProductInfo.finterid ; 

		//System.out.println("标准成本保存语句: "+upStdCostRPT);
		conn.update(upStdCostRPT);
		conn.close();
	}
	/*
	 * 升级物料.标准单位成本
	 */
	public void setProductStdCost(double value) throws SQLException
	{
		String cmdUpdateProductStdCost = "; update t_icitemstandard "
				+ " set fstandardcost= "+value
				+ " where fitemid = "+ProductInfo.firstitemid;
		conn.update(cmdUpdateProductStdCost);
	}
	
	private DBConnect conn=new DBConnect();

	private DecimalFormat df0,df2,df4;
	private SimpleDateFormat df1;
	private ResultSet 	rs0;
	private String yyyymm;
}
