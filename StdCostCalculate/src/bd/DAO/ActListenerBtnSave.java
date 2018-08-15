package bd.DAO;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import bd.View.*;

public class ActListenerBtnSave implements ActionListener  
{
	public void actionPerformed(ActionEvent e) 
	{
		try 
		{
			udateStandardCostReport.udateStandardCostReport(
					GetCurrentYear.currentyear
					,GetFirstItemID.firstitemid
					,GetFinterID.finterid
					, Double.parseDouble(StdCostCalculate.mainFrame.tableReport.getValueAt(0,2).toString()) 			/*一.生产成本合计（1+2+3）*/
					, Double.parseDouble(StdCostCalculate.mainFrame.tableReport.getValueAt(1,2).toString()) 		/*1.直接材料成本*/
					, Double.parseDouble(StdCostCalculate.mainFrame.tableReport.getValueAt(2,2).toString())		/*2.直接人工*/
					, Double.parseDouble(StdCostCalculate.mainFrame.tableCalculate.getValueAt(StdCostCalculate.mainFrame.tableCalculate.getRowCount() 
							- 1,7).toString())											/*2.1直接人工-工资*/
					, Double.parseDouble(StdCostCalculate.mainFrame.tableCalculate.getValueAt(StdCostCalculate.mainFrame.tableCalculate.getRowCount() 
							- 1,8).toString())											/*2.2直接人工-保险*/
					, Double.parseDouble(StdCostCalculate.mainFrame.tableReport.getValueAt(3,2).toString())		/*3.制造费用（合计）*/
					, Double.parseDouble(StdCostCalculate.mainFrame.tableReport.getValueAt(4,2).toString())		/*3.1制造费用-间接人工 */	
					, Double.parseDouble(StdCostCalculate.mainFrame.tableReport.getValueAt(5,2).toString())		/*3.2制造费用-电费*/ 
					, Double.parseDouble(StdCostCalculate.mainFrame.tableReport.getValueAt(6,2).toString())		/*3.3制造费用-辅料*/
					, Double.parseDouble(StdCostCalculate.mainFrame.tableReport.getValueAt(7,2).toString())		/*3.4制造费用-设备折旧 */
					, Double.parseDouble(StdCostCalculate.mainFrame.tableReport.getValueAt(8,2).toString())		/*3.5制造费用-工装模具 */
					, Double.parseDouble(StdCostCalculate.mainFrame.tableReport.getValueAt(9,2).toString())		/*3.6制造费用-其他成本 */
					, Double.parseDouble(StdCostCalculate.mainFrame.tableReport.getValueAt(10,2).toString())		/*3.7厂房折旧  */
					, Double.parseDouble(StdCostCalculate.mainFrame.tableReport.getValueAt(11,2).toString())		/*二.期间费用合计（内贸）*/
					, Double.parseDouble(StdCostCalculate.mainFrame.tableReport.getValueAt(11,5).toString())		/*二.期间费用合计（FOB青岛）*/
					, Double.parseDouble(StdCostCalculate.mainFrame.tableReport.getValueAt(12,2).toString())		/*成品不良成本 */
					, Double.parseDouble(StdCostCalculate.mainFrame.tableReport.getValueAt(13,2).toString())		/*财务费用 */
					, Double.parseDouble(StdCostCalculate.mainFrame.tableReport.getValueAt(14,2).toString())		/*销售费用 */
					, Double.parseDouble(StdCostCalculate.mainFrame.tableReport.getValueAt(15,2).toString())		/*管理费用 */
					, Double.parseDouble(StdCostCalculate.mainFrame.tableReport.getValueAt(16,2).toString())		/*管理费用--土地摊销 */
					, Double.parseDouble(StdCostCalculate.mainFrame.tableReport.getValueAt(17,2).toString())		/*新开模具工装费用 */
					, GetItselfQtySaled.itselfQtySaled													/*产品自身历史销售数量 */	
					, GetModelQtySaled.modelQtySaled														/*产品型号历史销售数量 */
					, 0.0																/*新开模具摊销数量 */	
					, Double.parseDouble(StdCostCalculate.mainFrame.tableReport.getValueAt(18,2).toString())		/*运输费用 （内贸） */
					, Double.parseDouble(StdCostCalculate.mainFrame.tableReport.getValueAt(18,5).toString())		/*运输费用（FOB青岛） */
					, Double.parseDouble(StdCostCalculate.mainFrame.tableReport.getValueAt(0,2).toString())		/*标准成本=生产成本合计+期间费用合计（内贸)-运输费用 （内贸）*/
						+Double.parseDouble(StdCostCalculate.mainFrame.tableReport.getValueAt(11,2).toString())
						-Double.parseDouble(StdCostCalculate.mainFrame.tableReport.getValueAt(18,2).toString())
					, Double.parseDouble(StdCostCalculate.mainFrame.tableReport.getValueAt(0,5).toString())		/*标准成本=生产成本合计+期间费用合计（FOB青岛)-运输费用（FOB青岛）*/	
						+Double.parseDouble(StdCostCalculate.mainFrame.tableReport.getValueAt(11,5).toString())
						-Double.parseDouble(StdCostCalculate.mainFrame.tableReport.getValueAt(18,5).toString())
					, Double.parseDouble(StdCostCalculate.mainFrame.tableReport.getValueAt(19,2).toString())		/*产品利润（内贸） */
					, Double.parseDouble(StdCostCalculate.mainFrame.tableReport.getValueAt(19,5).toString())		/*产品利润（FOB青岛） */
					, Double.parseDouble(StdCostCalculate.mainFrame.tableReport.getValueAt(20,2).toString())		/*核价（内贸）*/	
					, Double.parseDouble(StdCostCalculate.mainFrame.tableReport.getValueAt(20,5).toString())		/*核价（FOB青岛）RMB*/		
					, Double.parseDouble(StdCostCalculate.mainFrame.tableReport.getValueAt(21,2).toString())		/*增值税*/
					, Double.parseDouble(StdCostCalculate.mainFrame.tableReport.getValueAt(22,2).toString())		/*出厂核价 含税  RMB Days 30*/
					, Double.parseDouble(StdCostCalculate.mainFrame.tableReport.getValueAt(22,5).toString())		/*出厂核价 含税  USD Days 60 FOB青岛*/	
					, Double.parseDouble(StdCostCalculate.mainFrame.textFK0.getText().toString())					/*电费价格 */
					, Double.parseDouble(StdCostCalculate.mainFrame.textFK1.getText().toString())					/*直接人工-保险公积金补助福利系数 */	
					, Double.parseDouble(StdCostCalculate.mainFrame.textFK2.getText().toString())					/*制造费用-间接人工系数 */	
					, Double.parseDouble(StdCostCalculate.mainFrame.textFK4.getText().toString())					/*产品不良系数 */
					, Double.parseDouble(StdCostCalculate.mainFrame.textFK5.getText().toString())					/*产品不良系数 */	
					, Double.parseDouble(StdCostCalculate.mainFrame.textFK6.getText().toString())					/*财务费用系数 */	
					, Double.parseDouble(StdCostCalculate.mainFrame.textFK7.getText().toString())					/*管理费用系数 */
					, GetGainRate.gainrate															/*产品利润率 */
					, Double.parseDouble(StdCostCalculate.mainFrame.textFK10.getText().toString())					/*销售费用系数 */	
					, Double.parseDouble(StdCostCalculate.mainFrame.textFK11.getText().toString())					/*国内增值税率 */
					, Double.parseDouble(StdCostCalculate.mainFrame.textFK22.getText().toString())					/*国内账期 */
					, Double.parseDouble(StdCostCalculate.mainFrame.textFK12.getText().toString())					/*国外账期 */
					, Double.parseDouble(StdCostCalculate.mainFrame.textFK13.getText().toString())					/*预算汇率 */
					, Double.parseDouble(StdCostCalculate.mainFrame.textFK14.getText().toString())					/*贷款利率 */
					+Double.parseDouble(StdCostCalculate.mainFrame.textFK141.getText().toString())
					);
			delStandardCostReport.delHistPrice(GetFirstItemID.firstitemid, GetFinterID.finterid);
			updateProductStdCost.updateProductStdCost(
					GetFirstItemID.firstitemid
					, Double.parseDouble(StdCostCalculate.mainFrame.tableReport.getValueAt(0,5).toString())
					+Double.parseDouble(StdCostCalculate.mainFrame.tableReport.getValueAt(11,5).toString())
					-Double.parseDouble(StdCostCalculate.mainFrame.tableReport.getValueAt(18,5).toString())
					);
			cleanBom.cleanBom(GetFirstItemID.firstitemid, GetFinterID.finterid);;
			updateCompanyPricePolicy.updateCompanyPricePolicy(GetFirstItemID.firstitemid
					,Double.parseDouble(StdCostCalculate.mainFrame.tableReport.getValueAt(22,2).toString())	
					,Double.parseDouble(StdCostCalculate.mainFrame.tableReport.getValueAt(22,5).toString())	
					);
			StdCostCalculate.mainFrame.lblstatus.setText(" 标准成本保存成功！客户价格体系更新完成。");
		} 
		catch (SQLException e1) 
		{
			e1.printStackTrace();
		}
	}
	
	private CleanBom cleanBom=new CleanBom();
	private DelStandardCostReport delStandardCostReport=new DelStandardCostReport(); 
	private UpdateProductStdCost updateProductStdCost=new UpdateProductStdCost();
    private UpdateStandardCostReport udateStandardCostReport=new UpdateStandardCostReport();
    private UpdateCompanyPricePolicy updateCompanyPricePolicy = new UpdateCompanyPricePolicy();
    
}
