package bd.View;

import java.text.DecimalFormat;
import javax.swing.JTable;

public class SetReportValue 
{
	/* 
	 * set tableReport value
	 */
	public void setReprotValue(
			JTable tableReport
			,JTable tableMaterial
			,JTable tableCalculate
			,Double packagesize
			,Double gainrate
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
			* 2.直接人工=工资 + 保险
			*/
			tableReport.setValueAt(df4.format(Double.parseDouble(
					tableCalculate.getValueAt(tableCalculate.getRowCount() - 1,7
							).toString()) 
						+ Double.parseDouble(tableCalculate.getValueAt(
								tableCalculate.getRowCount() - 1,8).toString())
						+ Double.parseDouble(frame.textFK14.getText())  ),2,2);
				/*
			     * 3-1.间接人工
			     */
				tableReport.setValueAt(	df2.format(Double.parseDouble(
						frame.textFK2.getText())),4,3);
				tableReport.setValueAt(	df4.format(Double.parseDouble(
						tableCalculate.getValueAt(
						tableCalculate.getRowCount() - 1,9).toString()))
						,4,2);
				/*
			     * 3-2.制造费用--电费
			     */
				tableReport.setValueAt(df4.format(Double.parseDouble(
						tableCalculate.getValueAt(
						tableCalculate.getRowCount() - 1,10).toString()
						)),5,2);		   
				/*
			     * 3-3.制造费用--辅料费
			     */
				tableReport.setValueAt(df4.format(Double.parseDouble(
						tableCalculate.getValueAt(
						tableCalculate.getRowCount() - 1,12).toString()
						)),6,2);				/*
			     * 3-4.制造费用--设备折旧费
			     */
				tableReport.setValueAt(df4.format(Double.parseDouble(
						tableCalculate.getValueAt(
						tableCalculate.getRowCount() - 1,11).toString()
						)),7,2);				/*
			     * 3-5.制造费用--工装模具折旧费
			     */
				tableReport.setValueAt(df4.format(Double.parseDouble(
						tableCalculate.getValueAt(
						tableCalculate.getRowCount() - 1,13).toString()
						)),8,2);			   
				/*
			     * 3-6.制造费用--其他成本
			     */
				tableReport.setValueAt(df4.format(Double.parseDouble(
						frame.textFK18.getText())),9,3);
				tableReport.setValueAt(df4.format(Double.parseDouble(
						frame.textFK18.getText())),9,2);
				/*
				 * 3-7.厂房折旧 = K3	
				 */			
				tableReport.setValueAt(df4.format(Double.parseDouble(
						frame.textFK3.getText())),10,3);
				tableReport.setValueAt(df4.format(Double.parseDouble(
						frame.textFK3.getText())),10,2);
				/*
			     * 3.制造费用合计
			     */
				tableReport.setValueAt(df4.format(Double.parseDouble(
						tableReport.getValueAt(4, 2).toString())
						+ Double.parseDouble(tableReport.getValueAt(5, 2).toString())
						+ Double.parseDouble(tableReport.getValueAt(6, 2).toString())
						+ Double.parseDouble(tableReport.getValueAt(7, 2).toString())
						+ Double.parseDouble(tableReport.getValueAt(8, 2).toString())
						+ Double.parseDouble(tableReport.getValueAt(9, 2).toString())
						+ Double.parseDouble(tableReport.getValueAt(10, 2).toString()
								)),3,2);
				/*
				 * 一、生产成本合计=1.直接材料成本+2.直接人工+3.制造费用合计
				 */
				tableReport.setValueAt(df4.format(Double.parseDouble(
						tableReport.getValueAt(1, 2).toString())
				    	+Double.parseDouble(tableReport.getValueAt(2, 2).toString())
	                    +Double.parseDouble(tableReport.getValueAt(3, 2).toString())
	                    ),0,2);
				/*
				 * 成品不良成本
				 */
				tableReport.setValueAt(df2.format(Double.parseDouble(
						frame.textFK4.getText())),12,3);/*成品不良成本k4*/
				tableReport.setValueAt(df4.format(Double.parseDouble(
						tableReport.getValueAt(0, 2).toString())
						*Double.parseDouble(frame.textFK4.getText())),12,2);
				/*
				 * 财务费用
				 */
				tableReport.setValueAt(df2.format(Double.parseDouble(
						frame.textFK5.getText())),13,3);
				tableReport.setValueAt(df4.format(Double.parseDouble(
						tableReport.getValueAt(0, 2).toString())
						*Double.parseDouble(frame.textFK5.getText())),13,2);
				/*
				 * 销售费用
				 */
				tableReport.setValueAt(df2.format(Double.parseDouble(
						frame.textFK7.getText())),14,3);
				tableReport.setValueAt(df4.format(Double.parseDouble(
						tableReport.getValueAt(0, 2).toString())
						*Double.parseDouble(frame.textFK7.getText())),14,2);
				/*
				 * 管理费用
				 */
				tableReport.setValueAt(df2.format(Double.parseDouble(
						frame.textFK6.getText())),15,3);
				tableReport.setValueAt(df4.format(Double.parseDouble(
						tableReport.getValueAt(0, 2).toString())
						*Double.parseDouble(frame.textFK6.getText())),15,2);
				/*
				 * 管理费用--土地摊销
				 */
				tableReport.setValueAt(df4.format(Double.parseDouble(
						frame.textFK15.getText())),16,3);
				tableReport.setValueAt(df4.format(Double.parseDouble(
						frame.textFK15.getText())),16,2);
				/*
				 * 新开模具工装费用 
				 */
				tableReport.setValueAt(df4.format(Double.parseDouble(
						frame.textFK16.getText())
						/Double.parseDouble(frame.textFK17.getText())),17,2);
				/*
				 * 外贸项目
				 */
				int l;
				for (l=0;l<18;l++) {
					if (l==11) {}
					else {tableReport.setValueAt(tableReport.getValueAt(
							l, 2), l, 5);}
				}
				
				/*
				 * 运输费用
				 */
				tableReport.setValueAt(df4.format(Double.parseDouble(
						frame.textFK8.getText())),18,2);
				/*
				 * FOB青岛费用
				 */
				tableReport.setValueAt(df4.format(Double.parseDouble(
						frame.textFK20.getText())
						/Double.parseDouble(frame.textFK21.getText())
						*packagesize),18,5);
				tableReport.setValueAt("FOB青岛费用="+Double.parseDouble(
						frame.textFK20.getText())
				+"（整柜 BY FCL,40HQ/GP,45HQ/GP）/"+Double.parseDouble(
						frame.textFK21.getText())
				+"立方米*单只产品体积 "+packagesize,18,6);
				/*
				 * 二、期间费用合计
				 */
				tableReport.setValueAt(df4.format(Double.parseDouble(
						tableReport.getValueAt(12, 2).toString())
						    	+Double.parseDouble(tableReport.getValueAt(13, 
						    			2).toString())
								+Double.parseDouble(tableReport.getValueAt(14, 
										2).toString())
								+Double.parseDouble(tableReport.getValueAt(15, 
										2).toString())
								+Double.parseDouble(tableReport.getValueAt(16, 
										2).toString())
								+Double.parseDouble(tableReport.getValueAt(17, 
										2).toString())
								+Double.parseDouble(tableReport.getValueAt(18, 
										2).toString())),11,2);
				tableReport.setValueAt(df4.format(Double.parseDouble(
						tableReport.getValueAt(12, 5).toString())
				    	+Double.parseDouble(tableReport.getValueAt(13, 
				    			5).toString())
						+Double.parseDouble(tableReport.getValueAt(14, 
								5).toString())
						+Double.parseDouble(tableReport.getValueAt(15, 
								5).toString())
						+Double.parseDouble(tableReport.getValueAt(16, 
								5).toString())
						+Double.parseDouble(tableReport.getValueAt(17, 
								5).toString())
						+Double.parseDouble(tableReport.getValueAt(18, 
								5).toString())),11,5);
				/*
				 * 三、产品利润=（期间成本+生产成本）×利润率/（1-利润率）
				 */
				tableReport.setValueAt(df2.format(gainrate),19,3);
				tableReport.setValueAt(df4.format((Double.parseDouble(
						tableReport.getValueAt(0, 2).toString())
						+Double.parseDouble(tableReport.getValueAt(11, 
								2).toString()))
						*gainrate
						/(1-gainrate)),19,2);
				tableReport.setValueAt(df4.format((Double.parseDouble(
						tableReport.getValueAt(0, 5).toString())
						+Double.parseDouble(tableReport.getValueAt(11, 
								5).toString()))
						*gainrate
						/(1-gainrate)),19,5);
				/*
				 * 四、核价=（一、生产成本合计 + 二、期间费用合计 + 三、产品利润）
				 */
				tableReport.setValueAt(df4.format(Double.parseDouble(
						tableReport.getValueAt(0, 2).toString())
						+Double.parseDouble(tableReport.getValueAt(11, 
								2).toString())
						+Double.parseDouble(tableReport.getValueAt(19, 
								2).toString())),20,2); 
				tableReport.setValueAt(df4.format(Double.parseDouble(
						tableReport.getValueAt(0, 5).toString())
						+Double.parseDouble(tableReport.getValueAt(11, 
								5).toString())
						+Double.parseDouble(tableReport.getValueAt(19, 
								5).toString())),20,5); 
				/*
				 * 五、增值税额
				 */
				tableReport.setValueAt(df2.format(Double.parseDouble(
						frame.textFK10.getText())),21,3);
				tableReport.setValueAt(df4.format(Double.parseDouble(
						tableReport.getValueAt(20, 2).toString())
						*Double.parseDouble(frame.textFK10.getText())),21,2); 
				/*
				 * 六、出厂核价_含税RMB=(四、核价+五、增值税额)+(四、核价+五、增值税额
				 * )*货款年利率/365*内贸账期
				 * 					=(四、核价+五、增值税额)*（1+货款年利率*内贸账期/365）					
				 */
				tableReport.setValueAt(df4.format((Double.parseDouble(
						tableReport.getValueAt(20, 2).toString())
						+Double.parseDouble(tableReport.getValueAt(21, 
								2).toString()))
						*(1+Double.parseDouble(frame.textFK13.getText())
						*Double.parseDouble(frame.textFK11.getText())/365)),
						22,2);
				tableReport.setValueAt("货款年利率： "+df2.format(
						Double.parseDouble(frame.textFK13.getText()))
						+";账期： "+df0.format(Double.parseDouble(
								frame.textFK11.getText()))+"天",22,3);
				/*
				 * 六、FOB青岛核价_USD=(四、核价/预算汇率）+（四、核价/预算
				 * 汇率）*货款年利率/365*外贸账期
				 * 					=（四、核价/预算汇率）*(1+货款年利率*外贸账期/365)
				 */
			tableReport.setValueAt(df4.format(Double.parseDouble(
					tableReport.getValueAt(20, 5).toString())
						/Double.parseDouble(frame.textFK12.getText())*(
								1+Double.parseDouble(frame.textFK13.getText())
						*Double.parseDouble(frame.textFK22.getText())/365
						)), 22, 5);
				
				/*
				 * tableReport.setValueAt("货款年利率： "+df2.format(
				 * Double.parseDouble(textFK13.getText())),19,5);
				 */
				tableReport.setValueAt("货款年利率： "+df2.format(
						Double.parseDouble(frame.textFK13.getText()))
						+";账期： "+df0.format(Double.parseDouble(
								frame.textFK11.getText()))+"天;"
						+"预算汇率： "+frame.textFK12.getText(),22,6);
		}
	private DecimalFormat df0,df2,df4;
	private MainFrame frame =new MainFrame();;
}
