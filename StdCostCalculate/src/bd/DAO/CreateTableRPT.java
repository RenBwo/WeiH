package bd.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import bd.connection.GetDBConnect;

public class CreateTableRPT {

	private GetDBConnect conn =new GetDBConnect();
	 private ResultSet 	rs0;

   	public void createTableRPT() throws SQLException
	{  
   		/*  报价表*/
   		/*conn.update("","drop table t_bdStandCostRPT;");*/
   		rs0 = conn.query("",";select count(*) from sysobjects where type = 'u' and name like 't_bdStandCostRPT'");
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
   						 + ",fproditemid 			int"			//产品物料内码
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
   						 + ",itselfQtySaled			decimal(20,4)" 	//历史销售数量，取已经审核的销售发票    						    						 
   						 + ",modelQtySaled			decimal(20,4)" 	//型号历史销售数量，取已经审核的销售发票   						 
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
   				 conn.update("",command4);
   				 conn.close();
   			     //System.out.println("createtable rpt success ");   				
   			}
	}
}
