package bd.View;
/*
 * 
 * 20171028 
 * 取消过冷式储液器
 * 纠正扁管切断产能公式
 * 同一固定资产编号的设备卡片取最近日期
 */
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.sql.*;
import java.text.DecimalFormat;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

import bd.DAO.*;
import bd.connection.*;

public class salePrice20180109 {

	JFrame frame;
	private JInternalFrame internalFrame;
	
	private JPanel top,center,bottom;
	private JTabbedPane tabbedPane; 
	private JScrollPane scrollPane_report,scrollPane_coffi ;
	private JScrollPane scrollPane_material,scrollPaneCostMake;
	
	
	private JTable tableCalculate,tableMaterial,tableReport;
	private JTable tableEnergy,tableAdi,tableModel,tableBOM;;

	private JLabel labFnumber;
	private JLabel lblK0,lblK1 ,lblK2 ,lblK3 ,lblK4 ,lblK5,lblK6,lblK7,lblK8,lblK81,lblK9,lblK10 ;
	private JLabel lblK11,lblK12,lblK13,lblK14,lblK15,lblK16,lblK17,lblK18,lblK19 ;
	private JTextField txFnumber;
	private JTextField textFK0,textFK1,textFK2,textFK3,textFK4,textFK5,textFK6,textFK7,textFK8,textFK81;
	private JTextField textFK9,textFK10,textFK11,textFK12,textFK13,textFK14,textFK15,textFK16,textFK17;
	private JTextField textFK141,textFK151/*,textFK161,textFK171*/;
	private JButton btnGenerate;  
	private JPanel panel_reporHead;
	private JPanel panel;
	private JPanel panel_bottom;
	private JLabel lblCompany;
	private JLabel lblTitle;
	private JScrollPane scrollPane_tabledata;
	private JLabel lbl_billno,lbl_ProdName,lbl_ProdName0,lbl_Model0,lbl_Model,lbl_OEMNo0,lbl_OEMNo;
    private DecimalFormat df0,df2,df4;
    private JScrollPane scrollPane_energe;
    private JScrollPane scrollPane_adi;
    private JScrollPane scrollPane_model;
    private JScrollPane scrollPane_bom;
    
    private JLabel lblTopciTitle;
    private JLabel lblINFOR;
    private JButton btnR2Save;
    private JLabel lblstatus;
    private InitParams initparam = new InitParams();
    private JScrollPane scrollPane_DevDesc;
   
    
	/**
	 * Launch the application.
	 */
/*	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					salePrice window = new salePrice();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
*/
	/**
	 * Create the application.
	 * @throws PropertyVetoException 
	 */
	public salePrice20180109()  {/*
		initialize();
	}*/

	/**
	 * Initialize the contents of the frame.
	 * @throws PropertyVetoException 
	 */
	/*private void initialize()  {*/
		frame = new JFrame();
		frame.setTitle("销售报价系统");
		frame.setBounds(0, 0,872,698);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());
		
		top = new JPanel();
		top.setBackground(Color.LIGHT_GRAY);
		top.setForeground(Color.WHITE);
		top.setPreferredSize(new Dimension(800,50));
		bottom = new JPanel();
		bottom.setForeground(Color.LIGHT_GRAY);
		bottom.setPreferredSize(new Dimension(800,50));
	
		
		center = new JPanel();
		center.setPreferredSize(new Dimension(800,550));

		frame.getContentPane().add(top,BorderLayout.NORTH);		
		
		lblTopciTitle = new JLabel("销  售  报  价  系  统");
		lblTopciTitle.setForeground(Color.BLUE);
		lblTopciTitle.setFont(new Font("宋体", Font.BOLD, 27));
		top.add(lblTopciTitle);
		frame.getContentPane().add(bottom,BorderLayout.SOUTH);
		bottom.setLayout(new BorderLayout(0, 0));
		
		lblINFOR = new JLabel(" ^_^ 欢迎使用销售报价系统！ 简要说明：在系数设置界面，确认系数->生成报价;在产品报价单界面，检查报价->保存到系统");
		bottom.add(lblINFOR);
		
		lblstatus = new JLabel(" ^_^ ……");
		bottom.add(lblstatus, BorderLayout.SOUTH);
		frame.getContentPane().add(center,BorderLayout.CENTER);
		center.setLayout(new BorderLayout(0, 0));
	
	
		
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabbedPane.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		tabbedPane.setBounds(10,10, 713, 427);
		tabbedPane.setPreferredSize(new Dimension(600, 200));
		center.add(tabbedPane);

		scrollPane_coffi = new JScrollPane();
		tabbedPane.addTab("系数设置", null, scrollPane_coffi, null);
		
		internalFrame = new JInternalFrame("系数设置");
		internalFrame.setResizable(true);
		scrollPane_coffi.setViewportView(internalFrame);
		internalFrame.getContentPane().setLayout(new MigLayout("", "[60px:100px:220px,grow 220,shrink 60][50px:100px:100px,grow,shrink 20,left][30px][90px][100px][30px][][60px:100px:100px,grow][]", "[][][][][][][][][][grow]"));
		
		labFnumber = new JLabel("物料长代码");
		internalFrame.getContentPane().add(labFnumber, "cell 0 7");
		
		txFnumber = new JTextField();
		internalFrame.getContentPane().add(txFnumber, "cell 1 7,alignx left");
		txFnumber.setForeground(Color.BLUE);
		txFnumber.setText("01.02.00.0217-hg");
		txFnumber.setColumns(15);
		
		lblK0 = new JLabel("工业电价k0");
		internalFrame.getContentPane().add(lblK0 , "cell 0 0,alignx left");
		
		textFK0 = new JTextField();
		CoefficientCalculate k0 = new CoefficientCalculate();
		try{textFK0.setText(k0.CoeValue("k0"));}
		catch (SQLException ex) {}
		internalFrame.getContentPane().add(textFK0, "cell 1 0,alignx left");
		textFK0 .setColumns(15);
		
		lblK1 = new JLabel("保险系数K1");
		internalFrame.getContentPane().add(lblK1, "cell 0 1,alignx left");
				textFK1 = new JTextField("0");
		internalFrame.getContentPane().add(textFK1, "cell 1 1,alignx left");
		textFK1.setColumns(15);
	 	CoefficientCalculate k1 = new CoefficientCalculate();
	 	try{textFK1.setText(k1.CoeValue("k1"));}
	 	catch (SQLException ex) {}
				
		lblK2 = new JLabel("制造费用-车间管理者工资系数k2");
		lblK2.setForeground(Color.BLACK);
		internalFrame.getContentPane().add(lblK2, "cell 0 2,alignx left");
		
		textFK2 = new JTextField();
		CoefficientCalculate k2 = new CoefficientCalculate();
		try{textFK2.setText(k2.CoeValue("k2"));}
		catch (SQLException ex) {}
		internalFrame.getContentPane().add(textFK2, "cell 1 2,alignx left");
		textFK2.setColumns(15);
		
		lblK3 = new JLabel("厂房摊销系数K3");
		internalFrame.getContentPane().add(lblK3, "cell 0 3,alignx left");
		
		textFK3 = new JTextField();
		CoefficientCalculate k3 = new CoefficientCalculate();
		try{textFK3.setText(k3.CoeValue("k3"));}
		catch (SQLException ex) {}
		internalFrame.getContentPane().add(textFK3, "cell 1 3,alignx left");
		textFK3.setColumns(15);
		
		lblK4 = new JLabel("成品不良系数K4");
		lblK4.setForeground(Color.BLACK);
		internalFrame.getContentPane().add(lblK4, "cell 0 4,alignx left");
		
		textFK4 = new JTextField();
		internalFrame.getContentPane().add(textFK4, "cell 1 4,alignx left");
		textFK4.setColumns(15);
		CoefficientCalculate k4 = new CoefficientCalculate();
		try{textFK4.setText(k4.CoeValue("k4"));}
		catch (SQLException ex) {}
		
		lblK5 = new JLabel("财务费用系数K5");
		internalFrame.getContentPane().add(lblK5, "cell 3 0,alignx left");
		
		textFK5 = new JTextField();
		internalFrame.getContentPane().add(textFK5, "cell 4 0,alignx left");
		textFK5.setColumns(15);
		CoefficientCalculate k5 = new CoefficientCalculate();
		try{textFK5.setText(k5.CoeValue("k5"));}
		catch (SQLException ex) {}
		
		lblK6 = new JLabel("管理费用系数K6");
		lblK6.setForeground(Color.BLACK);
		internalFrame.getContentPane().add(lblK6, "cell 3 1,alignx left");
		
		textFK6 = new JTextField();
		internalFrame.getContentPane().add(textFK6 , "cell 4 1,alignx left");
		textFK6 .setColumns(15);
		CoefficientCalculate k6 = new CoefficientCalculate();
		try{textFK6.setText(k6.CoeValue("k6"));}
		catch (SQLException ex) {}
		
		lblK7 = new JLabel("销售费用系数K7");
		internalFrame.getContentPane().add(lblK7, "cell 3 2,alignx left");
		
		textFK7 = new JTextField();
		internalFrame.getContentPane().add(textFK7, "cell 4 2,alignx left");
		textFK7.setColumns(15);
		CoefficientCalculate k7 = new CoefficientCalculate();
		try{textFK7.setText(k7.CoeValue("k7"));}
		catch (SQLException ex) {}
		
		
		lblK8 = new JLabel("内贸运输费用K8");
		lblK8.setForeground(Color.BLACK);
		internalFrame.getContentPane().add(lblK8, "cell 3 3,alignx left");
		
		textFK8 = new JTextField();
		internalFrame.getContentPane().add(textFK8, "cell 4 3,alignx left");
		textFK8.setColumns(15);
		textFK8.setText(String.valueOf(0));
		
		lblK81 = new JLabel("外贸运输费用 K81");
		internalFrame.getContentPane().add(lblK81, "cell 3 4,alignx left");
		
		textFK81 = new JTextField();
		internalFrame.getContentPane().add(textFK81, "cell 4 4,alignx left");
		textFK81.setColumns(15);
		textFK81.setText(String.valueOf(0));
		
		lblK9 = new JLabel("计提利润率K9");
		internalFrame.getContentPane().add(lblK9, "cell 6 0,alignx left");
		
		textFK9 = new JTextField();
		internalFrame.getContentPane().add(textFK9, "cell 7 0,alignx left");
		textFK9.setColumns(15);
		CoefficientCalculate k9 = new CoefficientCalculate();
		try{textFK9.setText(k9.CoeValue("k9"));}
		catch (SQLException ex) {}
		
		lblK10 = new JLabel("国内增值税率K10");
		internalFrame.getContentPane().add(lblK10, "cell 6 1,alignx left");
		
		textFK10 = new JTextField();
		internalFrame.getContentPane().add(textFK10, "cell 7 1,alignx left");
		textFK10.setColumns(15);
		CoefficientCalculate k10 = new CoefficientCalculate();
		try{textFK10.setText(k10.CoeValue("k10"));}
		catch (SQLException ex) {}
		
		lblK11 = new JLabel("账期(天数)K11");
		internalFrame.getContentPane().add(lblK11, "cell 6 2,alignx left");
		
		textFK11 = new JTextField();
		internalFrame.getContentPane().add(textFK11, "cell 7 2,alignx left");
		textFK11.setColumns(15);
		CoefficientCalculate k11 = new CoefficientCalculate();
		try{textFK11.setText(k11.CoeValue("k11"));}
		catch (SQLException ex) {}
		
		lblK12 = new JLabel("美元对人民币汇率K12");
		internalFrame.getContentPane().add(lblK12, "cell 6 3,alignx left");
		
		textFK12 = new JTextField();
		internalFrame.getContentPane().add(textFK12, "cell 7 3,alignx left");
		textFK12.setColumns(15);
		CoefficientCalculate k12 = new CoefficientCalculate();
		try{textFK12.setText(k12.CoeValue("k12"));}
		catch (SQLException ex) {}
		
		
		lblK13 = new JLabel("货款年利率K13");
		internalFrame.getContentPane().add(lblK13, "cell 6 4,alignx left");
		
		textFK13 = new JTextField();
		internalFrame.getContentPane().add(textFK13, "cell 7 4,alignx left");
		textFK13.setColumns(15);
		CoefficientCalculate k13 = new CoefficientCalculate();
		try{textFK13.setText(k13.CoeValue("k13"));}
		catch (SQLException ex) {}	
		
		lblK14 = new JLabel("直接人工（工资+保险)-清洗K14");
		internalFrame.getContentPane().add(lblK14, "cell 0 5,alignx left");
		
		textFK14 = new JTextField();
		internalFrame.getContentPane().add(textFK14, "cell 1 5,alignx left");
		textFK14.setColumns(20);
		textFK14.setText("0");	
		
		lblK18 = new JLabel("制造费用-清洗K141");
		internalFrame.getContentPane().add(lblK18, "cell 3 5,alignx left");		

		textFK141 = new JTextField();
		internalFrame.getContentPane().add(textFK141, "cell 4 5,alignx left");
		textFK141.setColumns(20);
		textFK141.setText("0");		
		
		lblK15 = new JLabel("直接人工（工资+保险)-福利费用K15");
		internalFrame.getContentPane().add(lblK15, "cell 0 6,alignx left");		
		textFK15 = new JTextField();
		internalFrame.getContentPane().add(textFK15, "cell 1 6,alignx left");
		textFK15.setColumns(20);
		textFK15.setText("0");
		
		lblK19 = new JLabel("制造费用-福利费用K151");
		internalFrame.getContentPane().add(lblK19, "cell 3 6,alignx left");
		textFK151 = new JTextField();
		internalFrame.getContentPane().add(textFK151, "cell 4 6,alignx left");
		textFK151.setColumns(20);
		textFK151.setText("0");
		
		lblK16 = new JLabel("新开模具费用K16");
		internalFrame.getContentPane().add(lblK16, "cell 6 5,alignx left");
		
		textFK16 = new JTextField();
		internalFrame.getContentPane().add(textFK16, "cell 7 5,alignx left");
		textFK16.setColumns(20);
		textFK16.setText("0");	
		/* 
		 * textFK161 = new JTextField();
		internalFrame.getContentPane().add(textFK161, "cell 5 12,alignx left");
		textFK161.setColumns(20);
		textFK161.setText("0");	
		*/
		
		lblK17 = new JLabel("新模产品预测销量 K17");
		internalFrame.getContentPane().add(lblK17, "cell 6 6,alignx left");
		
		textFK17 = new JTextField();
		internalFrame.getContentPane().add(textFK17, "cell 7 6,alignx left");
		textFK17.setColumns(20);
		textFK17.setText("0");
		/*
		textFK171 = new JTextField();
		internalFrame.getContentPane().add(textFK171, "cell 5 13,alignx left");
		textFK171.setColumns(20);
		textFK171.setText("0");
		*/


		
	/*
		 * button generate
		 * */
		btnGenerate   = new JButton("生成报价");
		generate EventAction = new generate();
		btnGenerate.addActionListener(EventAction);		
		
			btnGenerate .setForeground(Color.BLUE);
			btnGenerate .setBackground(new Color(0, 255, 0));
			internalFrame.getContentPane().add(btnGenerate , "cell 0 8,alignx right");
		
			
	
		internalFrame.setVisible(true);
		scrollPane_report = new JScrollPane();
		tabbedPane.addTab("产品报价单", null, scrollPane_report, null);
						
		panel = new JPanel();
		scrollPane_report.setViewportView(panel);
		panel.setLayout(new BorderLayout(0, 0));
						
		panel_reporHead = new JPanel();
		panel_reporHead.setBackground(Color.WHITE);
		panel_reporHead.setPreferredSize(new Dimension(800,100));
		panel.add(panel_reporHead,BorderLayout.NORTH);
		panel_reporHead.setLayout(new MigLayout("", "[100px][200px,grow][100px][300px,grow][100px][200px,grow]", "[][100px][]"));
		
		lblCompany = new JLabel("威海邦德散热系统股份有限公司");
		panel_reporHead.add(lblCompany, "cell 0 0 3 1,alignx left,aligny center");
		lblTitle = new JLabel("产 品 报 价 单");
		lblTitle.setFont(new Font("宋体", Font.BOLD, 15));
		panel_reporHead.add(lblTitle, "cell 3 1 2 1,alignx center");
		
		lbl_billno = new JLabel("表单编号：BD/XS-011-A0");	panel_reporHead.add(lbl_billno,"cell 5 0");
		/*
		 * button save
		 */
		btnR2Save = new JButton("保存到系统");
		btnR2Save .setForeground(Color.BLUE);
		btnR2Save .setBackground(new Color(0, 255, 0));
		btnR2Save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {					
					getcon SaveCon = new getcon();
					String	  savesql = "update t_BDSailPriceReport" 
					        + " set fCostMaterial = "+tableReport.getValueAt(5,1) 					/*直接材料费用*/
							+ ", fCostPay = "+tableCalculate.getValueAt(tableCalculate.getRowCount() - 1,7) 							/*直接工资-工资 */
							+ ", fCostAssure = " 	+tableCalculate.getValueAt(tableCalculate.getRowCount() - 1,8)					/*直接工资-保险 */
							+ ", fCostMake_Pay = " 	+tableCalculate.getValueAt(tableCalculate.getRowCount() - 1,9)					/*制造费用-人工 */
							+ ", fCostMake_Power = " +tableCalculate.getValueAt(tableCalculate.getRowCount() - 1,10)					/*制造费用-设备电费*/ 
							+ ", fCostMake_deprication = " +tableCalculate.getValueAt(tableCalculate.getRowCount() - 1,11)			/*制造费用-设备折旧 */
							+ ", fCostMake_Adi = " 			+tableCalculate.getValueAt(tableCalculate.getRowCount() - 1,12)			/*制造费用-辅料 */
							+ ", fCostMake_Jig = " 			+tableCalculate.getValueAt(tableCalculate.getRowCount() - 1,13)			/*制造费用-工装模具 */
							+ ", fCostMake = " 		+tableReport.getValueAt(8,1)					/*制造费用（合计） */
							+ ", fCostHouse = " 	+tableReport.getValueAt(9,1)					/*厂房摊销 */
							+ ", fCostBadProd = " 	+tableReport.getValueAt(12,1)					/*成品不良成本 */
							+ ", fCostFinance = " 	+tableReport.getValueAt(13,1)					/*财务费用 */
							+ ", fCostSail = " 		+tableReport.getValueAt(14,1)					/*销售费用 */
							+ ", fCostManage = " 	+tableReport.getValueAt(15,1)					/*管理费用 */
							+ ", fCostTransport = " +tableReport.getValueAt(16,1)					/*运输费用 */
							+ ", fGain = " 			+tableReport.getValueAt(17,1)					/*计提利润 */
							+ ", fSailPrice_China_InTax = " +tableReport.getValueAt(20,1)			/*国内			含税		价格 */
							+ ", fSailPrice_China_UninTax = " +tableReport.getValueAt(21,1)			/*国内			不含税	价格 */
							+ ", fSailPrice_China_InTax_Delay = " +tableReport.getValueAt(22,1)		/*国内	延期付款	含税		价格 */
							+ ", fSailPrice_China_UninTax_Delay = "+tableReport.getValueAt(23,1) 	/*国内	延期付款	不含税	价格 */
							+ ", fSailPrice_Foreign_USA = " 		+tableReport.getValueAt(21,4)	/*国外			美元		价格 */
							+ ", fSailPrice_Foreign_Delay_CNY = " +tableReport.getValueAt(23,4)		/*国外  	延期付款  	人民币	价格 */
							+ ", fSailPrice_Foreign_Delay_USA = " +tableReport.getValueAt(22,4)		/*国外  	延期付款  	美元		价格*/	  
							+ ", fResPriceK0 = " 					+textFK0.getText()	/*电费价格 */
							+ ", fAssureK1 = " 						+textFK1.getText()	/*保险系数 */
							+ ", fMakePayK2 = " 					+textFK2.getText()	/*制造费用-人工系数 */
							+ ", fHousek3 = " 						+textFK3.getText()	/*厂房摊销系数 */
							+ ", fBadProdK4 = " 					+textFK4.getText()	/*产品不良系数 */
							+ ", fFinanceK5 = " 					+textFK5.getText()	/*财务费用系数 */
							+ ", fManageK6 = " 						+textFK6.getText()	/*管理费用系数 */
							+ ", fSailK7 = " 						+textFK7.getText()	/*销售费用系数 */
							+ ", fTransportK8 = " 					+textFK8.getText()	/*运输费用 */
							+ ", fGainRateK9 = " 					+textFK9.getText()	/*计提利润率 */
							+ ", fTaxRateK10	 = " 				+textFK10.getText()	/*国内增值税率 */
							+ ", fDelayDAYSK11 = " 					+textFK11.getText()	/*延期天数 */
							+ ", fExchangeRateK12 = " 				+textFK12.getText()	/*汇率 */
							+ ", fInterestRateK13 = " 				+textFK13.getText()	/*贷款利率 */
							+ ", fwashingK14 = " 					+textFK14.getText()+"+"+textFK141.getText()	/*清洗费用 */
							//+ ", fpackageK15 = "  				+textFK15.getText()+"+"+textFK151.getText()	/*包装费用 */
							//+ ", fchuyeqiK16 = " 					+textFK16.getText()+"+"+textFK161.getText()	/*储液器费用 */
							//+ ", fxinziK17 = "  					+textFK17.getText()+"+"+textFK171.getText()	/*布包芯子费用 */
							+ " where fproditemid = "+initparam.firstitemid
							+ " and finterid = "+initparam.finterid; 
					System.out.println("销售报价保存语句: "+savesql);
					SaveCon.update(savesql);
					lblstatus.setText(" 销售报价保存成功！");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		panel_reporHead.add(btnR2Save, "cell 5 1");
		lbl_ProdName0 = new JLabel("产品名称");	panel_reporHead.add(lbl_ProdName0, "cell 0 2 1 1");
		lbl_ProdName = new JLabel("冷凝器");	panel_reporHead.add(lbl_ProdName, "cell 1 2 1 1");
		lbl_Model0 = new JLabel("公司产品型号");	panel_reporHead.add(lbl_Model0, "cell 2 2 1 1");
		lbl_Model = new JLabel("BD-FPB");	panel_reporHead.add(lbl_Model, "cell 3 2 1 1");
		lbl_OEMNo0 = new JLabel("OEM编号");	panel_reporHead.add(lbl_OEMNo0, "cell 4 2 1 1");
		lbl_OEMNo = new JLabel("无");	panel_reporHead.add(lbl_OEMNo, "cell 5 2 1 1");

		scrollPane_tabledata = new JScrollPane();
		panel.add(scrollPane_tabledata, BorderLayout.CENTER);
		panel_bottom = new JPanel();
		panel_bottom.setPreferredSize(new Dimension(800,50));
		panel.add(panel_bottom, BorderLayout.SOUTH);
		
		
		tableReport = new JTable();
		scrollPane_tabledata.setViewportView(tableReport);
		tableReport.setModel(new DefaultTableModel(
					new Object[][] {
						{null, null, null, null, null, null, null},
					},
					new String[] {
						"NO", "\u9879\u76EE", "\u8D39\u7528\uFF08\u56FD\u5185\uFF09", "\u5355\u4F4D\u6BD4\u4F8B", "\u9879\u76EE", "\u8D39\u7528\uFF08\u56FD\u5916\uFF09", "\u6210\u672C\u8D39\u7528\u6807\u51C6"
					}
				));	
		//tableReport.getTableHeader().setVisible(true);
		//tableReport.getTableHeader().isVisible();
				
		scrollPane_material = new JScrollPane();
		tabbedPane.addTab("材料成本明细", null, scrollPane_material, null);
		
		tableMaterial = new JTable();
		tableMaterial.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null, null, null, null, null,null},
				{null, null, null, null, null, null, null,null, null, null, null},
			},
			new String[] {
				"物料长代码", "物料名称", "规格型号", "计量单位", "单位数量","数量", "材料单价", 
				"材料金额","当月最新入库单价", "价格体系单价","计划单价"
	}
		));
		scrollPane_material.setViewportView(tableMaterial);
		
		scrollPaneCostMake = new JScrollPane();
		tabbedPane.addTab("直接工资与制造费用明细", null, scrollPaneCostMake, null);
		tableCalculate = new JTable();
		tableCalculate.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null},
			},
			new String[] {"零部件代码", "零部件名", "数量", "工序名称", "工序价格", "工资系数", "工件数量", 
				"工资", "保险", "制造费用-人工", "电费", "设备折旧分摊", "辅料费用", "工装模具费用"}
		));
		scrollPaneCostMake.setViewportView(tableCalculate);
		tableCalculate.setBackground(SystemColor.controlHighlight);
		tableCalculate.setBorder(new LineBorder(Color.BLUE, 2, true));
		tableCalculate.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		scrollPane_energe = new JScrollPane();
		tabbedPane.addTab("电费与折旧明细", null, scrollPane_energe, null);
		
		tableEnergy = new JTable();
		tableEnergy.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null,null,null, null,null,
					null, null,null,null, null, null, null, null, null,null},
				},
			new String[] {"foperid","fopersn","物料代码","物料名称","数量","工序","工序价格",
					"工资系数","工件数量","设备名","功率","产能","产能单位","电费","月提折旧","折旧分摊",
					"固定资产内码","固定资产编号","芯体长度","芯体宽度"
			}
		));
		scrollPane_energe.setViewportView(tableEnergy);
		
		scrollPane_adi = new JScrollPane();
		tabbedPane.addTab("辅料明细", null, scrollPane_adi, null);
		tableAdi = new JTable();
		tableAdi.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null, null, null, null,null,
					null, null, null, null,null,null,null,  null},
				},
			new String[] {
					"foperid","fopersn","零部件代码","零部件名","零件数量","工序","工价","工资系数","工件数量","辅料名",
					"用量","价格","辅料费用","当月最新入库单价", "价格体系单价", "计划单价","芯体长度","芯体宽度"
			}
		));
		scrollPane_adi.setViewportView(tableAdi);
		scrollPane_model = new JScrollPane();
		tabbedPane.addTab("工装模具明细", null, scrollPane_model, null);
		tableModel = new JTable();
		tableModel.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
			},
			new String[] {"foperid","fopersn","物料代码","物料名称","数量","工序","工序价格",
					"工资系数","工件数量","工装模具名称","工装模具分摊费用","米重(kg/m)","最大使用量(kg)","模具费用","芯体长度","芯体宽度"
			}
		));
		scrollPane_model.setViewportView(tableModel);
		
		scrollPane_bom = new JScrollPane();
		tabbedPane.addTab("BOM", null, scrollPane_bom, null);
		
		tableBOM = new JTable();
		tableBOM.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null, null, null, null, null, null, null, 
					null, null, null, null, null},
				
			},
			new String[] {
					"层级","FParentID","FItemID","物料代码","物料名称","规格","加类型",
					"单位数量","数量","单位","损耗率","坯料尺寸","BOM状态",
					"BOM禁用","fbominterid","BOM编号","sn"
						
			}
		));
		scrollPane_bom.setViewportView(tableBOM);
		
		scrollPane_DevDesc = new JScrollPane();
		tabbedPane.addTab("开发需求说明书", null, scrollPane_DevDesc, null);

	}
	/*
	 * event listener
	 */
	class generate implements ActionListener  {
		
		public void actionPerformed(ActionEvent event) {
			
			UpdatePrice updateprice = new UpdatePrice();
			Calculate cost = new Calculate();			
			tableData data = new tableData();
			TableFormat formReport = new TableFormat();
			String sqlMaterial,sqlstr2,sqlstr3,sqlEnergy,sqlAdi,sqlModel,sqlBOM;
			
		    System.out.println("start calcute");
		    
		    try {
			initparam.SomeArguments(txFnumber.getText());}
		    catch(SQLException e) {}
		    
			sqlMaterial = " select a.fnumber,b.fname,b.fmodel,c.fname,a.fqtyper,a.fqty,a.fprice,"
					+ "a.famtmaterial,w.fauxprice,t_SupplyPrice.fprice,b.fplanprice"
					+ "  from t_CostMaterialBD a join t_icitem b on a.fitemid = b.fitemid"
					+ " left join t_measureunit c on c.fmeasureunitid = b.funitid"
					+ " left join  (select w9.fitemid,w9.fauxprice from "
					+ "      (select  b.fitemid ,max(b.fauxprice) as fauxprice "
					+"		from icstockbill a "
					+"		join icstockbillentry b on a.ftrantype = 1 and a.finterid = b.finterid "
					+"		join ( "
					+"				select max(w1.fdate) as fdate,w2.fitemid "
					+"				from icstockbill w1 join icstockbillentry w2 on w1.finterid = w2.finterid "
					+"				where w1.ftrantype = 1 and "
					+"				w1.fdate >= cast('"+initparam.currentyear+"'+'-'+right('00'+"+initparam.currentperiod+",2)+'-01' as datetime) and "
					+"				w1.fdate < dateadd(month,1,cast('"+initparam.currentyear+"'+'-'+right('00'+"+initparam.currentperiod+",2)+'-01' as datetime))  group by w2.fitemid ) w on w.fdate = a.fdate and w.fitemid = b.fitemid " 
					+"		where a.fdate >= cast('"+initparam.currentyear+"'+'-'+right('00'+"+initparam.currentperiod+",2)+'-01' as datetime) and "
					+"		a.fdate < dateadd(month,1,cast('"+initparam.currentyear+"'+'-'+right('00'+"+initparam.currentperiod+",2)+'-01' as datetime)) "
					+"		group by b.fitemid) w9 "
					+"		) w 		on w.fitemid = a.fitemid "
					+ " left join (  select t01.fitemid ,max(t01.fpriceuntax) as fprice  "
					+ "				from (select a.fitemid,a.fprice/(1 + b.fvalueaddrate/100) as fpriceuntax  "
					+ "							from t_supplyentry a " 
					+ "							join t_supplier b on a.fsupid = b.fitemid " 
					+ "							where a.fquotetime <=  dateadd(day,-datediff(day,getdate(),0),0) "
					+ "							and a.fdisabledate > getdate()  and a.fused = 1 ) t01"
					+ "							group by t01.fitemid  ) t_SupplyPrice on t_SupplyPrice.fitemid = a.fitemid"
					+ " where a.fproditemid = "+initparam.firstitemid+" and a.finterid = "+initparam.finterid
					+ " order by b.fnumber;";
			System.out.println("TableMaterial.statement:"+sqlMaterial);
			sqlstr2 = " select Fnumber,assyname,fQty,fopername,fpiecerate,frate,"
					+ " fmakeqty,isnull(amtpay,0),isnull(amtassure,0),isnull(costworker,0),isnull(fmatpower,0),"
					+ "isnull(fdepr,0),isnull(famtadi,0),isnull(famtmodel,0) "
					+ " from t_BDLabourAndMake "
					+ " where fproditemid = "+initparam.firstitemid+" and finterid = "+initparam.finterid
					+ " order by fnumber,fopersn;";
			System.out.println("人工和制造费用:"+sqlstr2);
			
			sqlstr3 ="select  0 as no,'名称及产品编号' as item1,'' as item2,'' as item3,'名称及产品编号'as item4,''as item5,''as item6 "
					+" union select  1 as no,'材料成本 (每Kg)' as item1,'' as item2,'' as item3,'材料类型'as item4,''as item5,''as item6 "
					+" union select  2 as no,'材料类型' as item1,'' as item2,'' as item3,'重量（Kg）'as item4,''as item5,''as item6 "
					+" union select  3 as no,'重量（Kg）' as item1,'' as item2,'' as item3,'材料成本 (每公斤)'as item4,''as item5,''as item6 "
					+" union select  4 as no,'材料成本 (每件)' as item1,'' as item2,'' as item3,'材料成本 (每件'as item4,''as item5,''as item6 "
					+" union select  5 as no, '1.直接材料成本' as item1,'' as item2,'' as item3,'1.直接材料成本' as item4,''as item5,''as item6 " 
					+" union select  6 as no, '2.直接生产成本' as item1,'' as item2,'' as item3,'2.生产成本' as item4,''as item5,'直接工资+制造费用+厂房摊销'as item6 " 
					+" union select  7 as no, '     直接工资' as item1,'' as item2,'' as item3,'     直接工资' as item4,''as item5,'工资+保险'as item6 " 
					+" union select  8 as no, '     制造费用' as item1,'' as item2,'' as item3,'     制造费用' as item4,''as item5,'车间管理者工资+电费+折旧+模具费用+辅料费用'as item6 " 
					+" union select  9 as no, '     厂房摊销' as item1,'' as item2,'' as item3,'     厂房摊销' as item4,''as item5,'近12个月厂房折旧占直接人工的比例'as item6 " 
					+" union select  10 as no, ' 1+2合  计' as item1,'' as item2,'' as item3,' 1+2合  计' as item4,''as item5,'直接成本=直接材料成本+直接生产成本'as item6 " 
					+" union select  11 as no, '3.期间成本' as item1,'' as item2,'' as item3,'3.期间成本' as item4,''as item5,''as item6 " 
					+" union select  12 as no, '    成品不良成本' as item1,'' as item2,'' as item3,'    成品不良成本' as item4,''as item5,''as item6 "
					+" union select  13 as no, '    财务费用' as item1,'' as item2,'' as item3,'    财务费用' as item4,''as item5,'近12个月财务费用占营业成本的比例'as item6 "  
					+" union select  14 as no, '    销售费用（不含运费）' as item1,'' as item2,'' as item3,'    销售费用（不含运费）' as item4,''as item5,'近12个月销售费用占营业成本的比例'as item6 " 
					+" union select  15 as no, '    管理费用' as item1,'' as item2,'' as item3,'    管理费用' as item4,''as item5,'近12个月管理费用占营业成本的比例'as item6 " 
					+" union select  16 as no, '    运输费用' as item1,'' as item2,'国内报价为出厂价' as item3,'    运输费用' as item4,''as item5,'单只产品运达交货地点运费'as item6 " 
					+" union select  17 as no, '4.产品利润' as item1,'' as item2,'' as item3,'4.产品利润' as item4,''as item5,'按比例（计提利润/公司产成品的成本）计提利润'as item6 " 
					+" union select  18 as no, '5.不含税销售价(=1+2+3)' as item1,'' as item2,'' as item3,'5.销售价(=1+2+3+4)' as item4,''as item5,''as item6 " 
					+" union select  19 as no, '   国内增值税（17%）' as item1,'' as item2,'' as item3,'' as item4,''as item5,''as item6 " 
					+" union select  20 as no, '6.含税销售价：' as item1,'' as item2,'' as item3,'6.单价总计(不含税)' as item4,''as item5,''as item6 " 
					+" union select  21 as no, '7.不含税销售价：' as item1,'' as item2,'' as item3,'美元价：' as item4,''as item5,'使用的汇率需根据市场实际行情作变动'as item6 " 
					+" union select  22 as no, '有账期国内售价(含税）：' as item1,'' as item2,'' as item3,'有账期国外售价（美元结算）FOB:' as item4,''as item5,'现贷款年利率：'as item6 " 
					+" union select  23 as no, '有账期国内售价(不含税）：' as item1,'' as item2,'' as item3,'有账期国外售价（人民币结算）FOB：' as item4,''as item5,''as item6 " 
					+" union select  24 as no, '国内报价（含税）:' as item1,'' as item2,'' as item3,'国外报价（美元结算）FOB:' as item4,''as item5,''as item6 " 
					+" union select  25 as no, '国内报价（不含税）' as item1,'' as item2,'' as item3,'国外报价（人民币结算）FOB：' as item4,''as item5,''as item6  "; 
			
			sqlBOM = " select  a.flevel0,a.FParentID,a.FItemID,b.fnumber,b.fname ,b.fmodel,"
					+ "d.fname as maketype,a.fQtyPer,a.fQty,c.fname as unitname,a.fscrap,"
					+ "a.fitemsize,a.fstatus,a.fusestatus,a.fbominterid,a.fbomnumber,a.sn"
					+ " from BDBomMulExpose a"
					+ " join t_icitem b on a.fitemid = b.fitemid and a.firstitemid ="+initparam.firstitemid
					+ " join t_measureunit c on a.funitid = c.fitemid"
					+ " join t_submessage d on d.finterid = b.ferpclsid"
					+ " order by a.sn";	

		    
			if (initparam.firstitemid == 0 )
					{
				System.out.println("itemid is equal zero");
				try{
					btnR2Save.setVisible(false);
					data.myTableModel(tableBOM,sqlBOM,new int[]{});
					data.myTableModel(tableMaterial,sqlMaterial,new int[]{5,7});
					data.myTableModel(tableCalculate,sqlstr2,new int[]{7,8,9,10,11,12,13});
				    data.myTableModel(tableReport,sqlstr3,new int[]{}); } catch(SQLException e) {}
				 int a = tableReport.getColumnCount();
				   if (a == 7)
				   {formReport.ColumnFilter(tableReport,new int[]{0});}
				lblstatus.setText(" 产品不存在！");				
					}
			else {
				
				try {
				System.out.println("1.取得参数 成功，下一步，升级辅料单价;"+initparam.finterid+" and firstitemid"+initparam.firstitemid);
				updateprice.Update(initparam.currentyear,initparam.currentperiod);
	            System.out.println("1.1.升级辅料单价 成功，下一步，进行BOM展开;"+initparam.firstitemid);
	            cost.BomExpose(initparam.firstitemid,initparam.finterid);
				System.out.println("2.多级BOM 成功，下一步，计算材料成本");
				cost.CostMateiral( initparam.firstitemid,initparam.finterid,initparam.currentyear,initparam.currentperiod);
				System.out.println("3.直接成本计算 成功，下一步，计算人工与制造费用");
				cost.CostLabourAndMake(Double.parseDouble(textFK1.getText()), Double.parseDouble(textFK2.getText()), Double.parseDouble(textFK3.getText()), Double.parseDouble(textFK0.getText()),initparam.firstitemid,initparam.finterid,initparam.currentyear,initparam.currentperiod);
				System.out.println("4.制造费用与直接人工 成功，下一步，合成报价");}
				catch(SQLException e) {}
				
				sqlEnergy =" select t4.foperid,t4.fopersn,t2.fnumber ,t2.fname as assyname,"
						+" (case isnull((select 1 from t_icitem where fitemid = t1.fitemid and fname like '扁管盘料'),0) "
						+" when 1 then round(t1.fqty*(100-t1.fscrap)/100/t1.fqtyper,0) else t1.fqty end) as fqty,"
						+" t5.fname as opername,t4.fpiecerate,t4.fentryselfz0236 as frate, "
						+" t4.fentryselfz0237 as fmakeqty, "
						+" t6.fmanName,t6.fpower,"
						
						/*产能*/					
						+"isnull((case t4.foperid when 40336 then " + cost.capacity0   
						/*钎焊炉工艺 40336*/
						+ " when 40494 then round(t11.fpressrate*60/"+cost.length+",4)" 
						/*扁管压出工艺40494*/
						+ " when 40495 then (case  when " + cost.length*1000 +" < 400 then 7800 " 
																		+ " when " + cost.length*1000 +">=400 and "+cost.length*1000 +"< 600 then 6600" 
																		+ " when " + cost.length*1000 +">=600 and "+cost.length*1000 +"< 800 then 5400"
																		+ " when " + cost.length*1000 +">=800 and "+cost.length*1000 +"< 1000 then 4200" 
																		+ " else  2400 end )" 
						/*扁管切断工艺 40495*/
						+ " else round(t6.fcapacity/(case  when isnull(t4.fentryselfz0236,0)>0 then t4.fentryselfz0236 else 1 end),4) end) "
						/*其余工艺*/
						+ " ,0 ) as fcap,  " 					
						+" t6.funit,"
						
						 /*电费*/
						+"isnull("
						+" (case isnull((select 1 from t_icitem where fitemid = t1.fitemid and fname like '扁管盘料'),0) "
						+" when 1 then round(t1.fqty*(100-t1.fscrap)/100/t1.fqtyper,0) else t1.fqty end)"
						+ "*t4.fentryselfz0237*t6.fpower*0.4*"+Double.parseDouble(textFK0.getText())+"/"
						/*钎焊炉工艺 40336*/
						+ "(case t4.foperid when 40336 then " + cost.capacity0   
						/*扁管压出工艺40494*/
						+ " when 40494 then round(t11.fpressrate*60/"+cost.length+",4)" 
						/*扁管切断工艺 40495*/
						+ " when 40495 then (case  when " + cost.length*1000 +" < 400 then 7800 "
												+ " when " + cost.length*1000 +">=400 and "+cost.length*1000 +"< 600 then 6600" 
												+ " when " + cost.length*1000 +">=600 and "+cost.length*1000 +"< 800 then 5400"
												+ " when " + cost.length*1000 +">=800 and "+cost.length*1000 +"< 1000 then 4200" 
												+ " else  2400 end )" 								
						/*其余工艺*/
						+ " else round(t6.fcapacity/(case  when isnull(t4.fentryselfz0236,0)>0 then t4.fentryselfz0236 else 1 end),4) end) "
						+ " ,0 ) as famtpower,  "
						
						/*月折旧额*/
						+" round(t10.fdeprshould/t101.fnum,4) ,"  
						
						/*分摊折旧*/
						+" isnull("
						+" (case isnull((select 1 from t_icitem where fitemid = t1.fitemid and fname like '扁管盘料'),0) "
						+" when 1 then round(t1.fqty*(100-t1.fscrap)/100/t1.fqtyper,0) else t1.fqty end)"
						+ "*t4.fentryselfz0237*t10.fdeprshould/( t101.fnum*30*8*"
						/*钎焊炉工艺 40336*/
						+ "(case t4.foperid when 40336 then " + cost.capacity0        
						/*扁管压出工艺40494*/
						+ " when 40494 then round(t11.fpressrate*60/"+cost.length+",4)" 
						/*扁管切断工艺 40495*/
						+ " when 40495 then (case "														 
						 		+ " when " + cost.length*1000 +" < 400 then 7800 "  
						 		+ " when " + cost.length*1000 +">=400 and "+cost.length*1000 +"< 600 then 6600" 
						 		+ " when " + cost.length*1000 +">=600 and "+cost.length*1000 +"< 800 then 5400"  
						 		+ " when " + cost.length*1000 +">=800 and "+cost.length*1000 +"< 1000 then 4200"  
						 		+ " else  2400 end )" 								
						/*其余工艺*/
						+ " else round(t6.fcapacity/(case  when isnull(t4.fentryselfz0236,0)>0 then t4.fentryselfz0236 else 1 end),4) end) "
						+ "),0) as fdepr, " 
						+" t6.fassetInterId,t101.fassetnumber,"+cost.length+","+cost.width
						+" from BDBomMulExpose t1  "
						+ " join t_icitem t2 on t1.fitemid = t2.fitemid and t1.firstitemid = "+initparam.firstitemid		 
						+" join t_Routing t3 on t3.fitemid = t1.fitemid  and t3.finterid = t1.froutingid"
						+" join t_routingoper t4 on t3.finterid = t4.finterid "		
						+" left join t_submessage t5 on t5.finterid = t4.foperid	and t5.fparentid = 61 "		
						+" left join (select b.fassetinterid,a.foperno,b.fmanname,b.fpower,b.fcapacity,b.funit"
						+ " from t_CostCalculateBD	a join	t_costcalculatebd_entry0 b on a.fid = b.fid	"
						+ ")		t6		on t6.foperno = t4.foperid "		
						+" left join (select fassetid,max(convert(varchar(4),fyear)+right(convert(varchar(2),'00')+convert(varchar(2),fperiod),2)) as fdate,"
						+ "max(fdeprperiods) as fdeprperiods,max(fdeprshould) as fdeprshould "
						+ " from t_fabalance  group by fassetid "
						+ " ) t10	 on t10.fassetid = t6.fassetInterId "		
						+" left  join (select a.fassetid,max(a.falterid) as falterid,b.fnum,b.fassetnumber"
						+ " from t_FAAlter a join t_facard b on a.falterid = b.falterid and b.fnum > 0"
						+ " group by a.fassetid,b.fnum,b.fassetnumber    ) t101 on t6.fassetinterid = t101.fassetid "
						+" left join t_costcalculate_flatpipe   t11		on t11.fpipenumber = t1.fitemid "
						+"and t11.fcheckbox = 0"	
						+" where t2.ferpclsid <> 1 "
						/*
						+ "  and t2.fitemid not in ( "		
						+" select fitemid from t_icitem where substring(fnumber,6,5) > '819' "
						+" and fnumber like '2.%') "
						*/
						+ " and t1.fqty > 0 "
						+" order by t2.fnumber ,t4.fopersn; "	;
				System.out.println("电费与折旧: "+sqlEnergy);
				sqlAdi =" select t4.foperid,t4.fopersn,t2.fnumber ,t2.fname as assyname,"
						+" (case isnull((select 1 from t_icitem where fitemid = t1.fitemid and fname like '扁管盘料'),0) "
						+" when 1 then round(t1.fqty*(100-t1.fscrap)/100/t1.fqtyper,0) else t1.fqty end) as fqty,"
						+" t5.fname as opername,t4.fpiecerate,t4.fentryselfz0236 as frate, "		
						+" t4.fentryselfz0237 as fmakeqty, "
						+" t6.faidname,t6.fqty,isnull(t6.fprice,0) as fprice,"
						+" isnull("
						+" (case isnull((select 1 from t_icitem where fitemid = t1.fitemid and fname like '扁管盘料'),0) "
						+" when 1 then round(t1.fqty*(100-t1.fscrap)/100/t1.fqtyper,0) else t1.fqty end)"
						+ "*t4.fentryselfz0237*t6.Fprice*(case "
						+" when (t4.foperid = 40336 and (t6.faidname like '%钎剂%' or  t6.faidname like'%液氮%')) then t6.fqty/"+cost.capacity0
						+" when (t4.foperid = 40494 and t6.faidname like '%锌丝%' ) then t11.fqtyzn/1000*"+cost.length
						+" when (t4.foperid = 40142 and "+cost.length +"<=0.2 ) then t6.fqty/2 else t6.fqty end),0) as famtAdi, "
						+ "w.fauxprice,t_SupplyPrice.fprice,t110.fplanprice,"		
						+cost.length+","+cost.width 
						+ " from BDBomMulExpose t1  "		
						+" join t_icitem t2 on t1.fitemid = t2.fitemid and t1.firstitemid =  "+initparam.firstitemid		
						+" join t_Routing t3	on t3.fitemid = t1.fitemid and t3.finterid = t1.froutingid"		
						+" join t_routingoper t4 on t3.finterid = t4.finterid "		
						+" left join t_submessage t5 on t5.finterid = t4.foperid	and t5.fparentid = 61 "		
						+" left join (select a.foperno,b.fcolor,b.faidname,b.faidnumber,b.fqty,b.fprice "
						+ " from t_CostCalculateBD	a join	t_costcalculatebd_entry1 b on a.fid = b.fid) t6 on t6.foperno = t4.foperid "		
						+ " and (t6.fcolor = 0 or (t6.fcolor = t2.f_135"
						+ " and t2.fitemid = (select t34.fitemid from t_icitemcore t33 "
						+ " 		join BDBomMulExpose t34 on t34.fitemid = t33.fitemid and t34.firstitemid = "+initparam.firstitemid+" and t33.fnumber like '13.%' ))) "		
						+" left join t_costcalculate_flatpipe   t11		on t11.fpipenumber = t1.fitemid "
						+ "and t11.fcheckbox = 0"
						+ " left join  (select w9.fitemid,w9.fauxprice from "
						+ "      (select  b.fitemid ,max(b.fauxprice) as fauxprice "
						+"		from icstockbill a "
						+"		join icstockbillentry b on a.ftrantype = 1 and a.finterid = b.finterid "
						+"		join ( "
						+"				select max(w1.fdate) as fdate,w2.fitemid "
						+"				from icstockbill w1 join icstockbillentry w2 on w1.finterid = w2.finterid "
						+"				where w1.ftrantype = 1 and "
						+"				w1.fdate >= cast('"+initparam.currentyear+"'+'-'+right('00'+"+initparam.currentperiod+",2)+'-01' as datetime) and "
						+"				w1.fdate < dateadd(month,1,cast('"+initparam.currentyear+"'+'-'+right('00'+"+initparam.currentperiod+",2)+'-01' as datetime))  group by w2.fitemid ) w on w.fdate = a.fdate and w.fitemid = b.fitemid " 
						+"		where a.fdate >= cast('"+initparam.currentyear+"'+'-'+right('00'+"+initparam.currentperiod+",2)+'-01' as datetime) and "
						+"		a.fdate < dateadd(month,1,cast('"+initparam.currentyear+"'+'-'+right('00'+"+initparam.currentperiod+",2)+'-01' as datetime)) "
						+"		group by b.fitemid) w9 "
						+"		) w 		on w.fitemid = t6.faidnumber "
						+ " left join ( select t01.fitemid ,max(t01.fpriceuntax) as fprice " 
						+ " 				from (select a.fitemid,a.fprice/(1 + b.fvalueaddrate/100) as fpriceuntax "
						+ "  							from t_supplyentry a "
						+ "  							join t_supplier b on a.fsupid = b.fitemid  "
						+ "  							where a.fquotetime <=  dateadd(day,-datediff(day,getdate(),0),0) "
						+ "  							and a.fdisabledate > getdate()  and a.fused = 1 ) t01 "
						+ "  							group by t01.fitemid)  t_SupplyPrice on t_SupplyPrice.fitemid = t6.faidnumber"
						+ " left join t_icitem t110 on t110.fitemid = t6.faidnumber "
		                +" where t2.ferpclsid <> 1 "
		                /* 五科
		                + " and t2.fitemid not in ( "		
						+" select fitemid from t_icitem where substring(fnumber,6,5) > '819' "
						+" and fnumber like '2.%') "
						*/
						+ " and t1.fqty > 0  "
						+" order by t2.fnumber ,t4.fopersn; "	;	
				//System.out.println("sqladi: "+sqlAdi);
				sqlModel =" select t4.foperid,t4.fopersn,t2.fnumber ,t2.fname as assyname,"
						+" (case isnull((select 1 from t_icitem where fitemid = t1.fitemid and fname like '扁管盘料'),0) "
						+" when 1 then round(t1.fqty*(100-t1.fscrap)/100/t1.fqtyper,0) else t1.fqty end) as fqty,"
						+" t5.fname as opername,t4.fpiecerate,t4.fentryselfz0236 as frate,"
						+" t4.fentryselfz0237 as fmakeqty, "	
						+" (case t4.foperid when 40494 then t11.fmodelname else t6.fnamemodel end) as fnamemodel,"
						+ "isnull("
						+" (case isnull((select 1 from t_icitem where fitemid = t1.fitemid and fname like '扁管盘料'),0) "
						+" when 1 then round(t1.fqty*(100-t1.fscrap)/100/t1.fqtyper,0) else t1.fqty end)"
						+ "*t4.fentryselfz0237*"
						+ "(case t4.foperid when 40494 then round(t11.famtmodel*"+cost.length+"*1000*t13.f_140/t11.cap,10)"/* 扁管压出工艺40494模具产能：模具最大使用量/(扁管长度*米克重)*/
						+ " else round(t6.famtperoper,10) end),0 ) as famtmodel "
						+ ",t13.f_140,t11.cap/1000 ,"
						+ "(case t4.foperid when 40494 then t11.famtmodel else t6.famtmodel end) as famtmodel0,"
						+cost.length+","+cost.width		
						+" from BDBomMulExpose t1  "		
						+" join t_icitem t2 on t1.fitemid = t2.fitemid and t1.firstitemid =  "+initparam.firstitemid		
						+" join t_Routing t3 on t3.fitemid = t1.fitemid and t3.finterid = t1.froutingid"		
						+" join t_routingoper t4 on t3.finterid = t4.finterid "		
						+" left join t_submessage t5 on t5.finterid = t4.foperid	and t5.fparentid = 61 "		
						+" left join (select a.foperno,b.fnamemodel,b.famtperoper,b.famtmodel "
						+ " from t_CostCalculateBD	a join	t_costcalculatebd_entry2 b on a.fid = b.fid"
						+ ") t6 on t6.foperno = t4.foperid and t6.foperno <> 40494  "		
						+" left join (select t001.fpipenumber,'挤压模具' as fmodelname,t001.fmaxqtymodel as cap,t003.famtmodel as famtmodel "
						+ " 	from t_costcalculate_flatpipe 		t001  "
						+ " 	left join t_CostCalculateBD 		t002 on t002.foperno = 40494 "
						+ " 	left join t_costcalculatebd_entry2 	t003 on t002.fid = t003.fid and t003.FNameModel like '%挤压模%'"
						+ " 	where fcheckbox = 0"
						+ " 	union   "
						+ "		select t001.fpipenumber,'挤压轮' as fmodelname,50000000 as cap ,t003.famtmodel as famtmodel "
						+ " 	from t_costcalculate_flatpipe 		t001  "
						+ " 	left join t_CostCalculateBD 		t002 on t002.foperno = 40494  "
						+ " 	left join t_costcalculatebd_entry2 	t003 on t002.fid = t003.fid and t003.FNameModel like '%挤压轮%' "
						+ " 	where fcheckbox = 0)  t11 on t11.fpipenumber = t1.fitemid and t4.foperid = 40494 "
						+" left join t_icitemcustom t13 on t13.fitemid = t1.fitemid and t4.foperid = 40494"		
						+" where t2.ferpclsid <> 1 "
						/* 五科
						+ " and t2.fitemid not in ( "		
						+" select fitemid from t_icitem where substring(fnumber,6,5) > '819' "
						+" and fnumber like '2.%') "
						*/
						+ " and t1.fqty > 0  "
						+" order by t2.fnumber ,t4.fopersn; "	;
				
				System.out.println("工装模具明细: "+sqlModel);
				try{
				data.myTableModel(tableBOM,sqlBOM,new int[]{});
				data.myTableModel(tableMaterial,sqlMaterial,new int[]{5,7});
				data.myTableModel(tableCalculate,sqlstr2,new int[]{7,8,9,10,11,12,13});
			    data.myTableModel(tableReport,sqlstr3,new int[]{});
			    data.myTableModel(tableEnergy,sqlEnergy,new int[]{13,15});
			    data.myTableModel(tableAdi,sqlAdi,new int[]{12});
			    data.myTableModel(tableModel,sqlModel,new int[]{10}); } catch(SQLException e) {}
			
			 int a = tableReport.getColumnCount();
			   if (a == 7)
			   {formReport.ColumnFilter(tableReport,new int[]{0});}
			    //formReport.RowFilter(tableReport,new int[]{0});
			   df0 = new DecimalFormat("######0");
			   df2 = new DecimalFormat("######0.00%");
			   df4 = new DecimalFormat("######0.0000");
				/*
				 * 材料费用 
				 */
			    tableReport.setValueAt(df4.format(Double.parseDouble(tableMaterial.getValueAt(tableMaterial.getRowCount() - 1,7).toString())),5,1);
				/*
				 * 直接工资=工资 + 保险
				 */
			    tableReport.setValueAt(df4.format(Double.parseDouble(tableCalculate.getValueAt(tableCalculate.getRowCount() - 1,7).toString()) 
						+ Double.parseDouble(tableCalculate.getValueAt(tableCalculate.getRowCount() - 1,8).toString())
						+ Double.parseDouble(textFK14.getText())  /*水洗*/
						//+ Double.parseDouble(textFK15.getText())  /*纸箱*/
						//+ Double.parseDouble(textFK16.getText())  /*储液器*/
						//+ Double.parseDouble(textFK17.getText())  /*布包芯子*/
						    ),7,1);
			    /*
			     * 制造费用
			     */
				tableReport.setValueAt(df4.format(Double.parseDouble(tableCalculate.getValueAt(tableCalculate.getRowCount() - 1,9).toString()) 
						+ Double.parseDouble(tableCalculate.getValueAt(tableCalculate.getRowCount() - 1,10).toString())
					    + Double.parseDouble(tableCalculate.getValueAt(tableCalculate.getRowCount() - 1,11).toString())
					    + Double.parseDouble(tableCalculate.getValueAt(tableCalculate.getRowCount() - 1,12).toString())
					    + Double.parseDouble(tableCalculate.getValueAt(tableCalculate.getRowCount() - 1,13).toString())
					    + Double.parseDouble(textFK141.getText())  /*水洗*/
					   // + Double.parseDouble(textFK151.getText())  /*纸箱*/
					   // + Double.parseDouble(textFK161.getText())  /*储液器*/
					   // + Double.parseDouble(textFK171.getText())  /*布包芯子*/
						),8,1);
				/*
				 * 厂房摊销 = 直接工资 *K3	
				 */
				tableReport.setValueAt(df2.format(Double.parseDouble(textFK3.getText())), 9, 2);
				tableReport.setValueAt(df4.format(Double.parseDouble(tableReport.getValueAt(7, 1).toString())
						*Double.parseDouble(textFK3.getText())),9,1);
				/*
				 *直接生产成本 
				 */
				tableReport.setValueAt(df4.format(Double.parseDouble(tableReport.getValueAt(7, 1).toString())
				    	+Double.parseDouble(tableReport.getValueAt(8, 1).toString())
						+Double.parseDouble(tableReport.getValueAt(9, 1).toString())),6,1);
				/*
				 * 直接成本
				 */
				tableReport.setValueAt(df4.format(Double.parseDouble(tableReport.getValueAt(5, 1).toString())
				    	+Double.parseDouble(tableReport.getValueAt(6, 1).toString())),10,1);
				/*
				 * 成品不良成本
				 */
				tableReport.setValueAt(df2.format(Double.parseDouble(textFK4.getText())),12,2);/*成品不良成本k4*/
				tableReport.setValueAt(df4.format(Double.parseDouble(tableReport.getValueAt(10, 1).toString())
						*Double.parseDouble(textFK4.getText())),12,1);
				/*
				 * 财务费用
				 */
				tableReport.setValueAt(df2.format(Double.parseDouble(textFK5.getText())),13,2);
				tableReport.setValueAt(df4.format(Double.parseDouble(tableReport.getValueAt(10, 1).toString())
						*Double.parseDouble(textFK5.getText())),13,1);
				/*
				 * 管理费用
				 */
				tableReport.setValueAt(df2.format(Double.parseDouble(textFK6.getText())),15,2);
				tableReport.setValueAt(df4.format(Double.parseDouble(tableReport.getValueAt(10, 1).toString())
						*Double.parseDouble(textFK6.getText())),15,1);
				/*
				 * 销售费用
				 */
				tableReport.setValueAt(df2.format(Double.parseDouble(textFK7.getText())),14,2);
				tableReport.setValueAt(df4.format(Double.parseDouble(tableReport.getValueAt(10, 1).toString())
						*Double.parseDouble(textFK7.getText())),14,1);
/*
				 * 运输费用
				 */
				tableReport.setValueAt(df4.format(Double.parseDouble(textFK8.getText())),16,1);
				/*
				 * 期间成本
				 */
				tableReport.setValueAt(df4.format(Double.parseDouble(tableReport.getValueAt(12, 1).toString())
						    	+Double.parseDouble(tableReport.getValueAt(13, 1).toString())
								+Double.parseDouble(tableReport.getValueAt(14, 1).toString())
								+Double.parseDouble(tableReport.getValueAt(15, 1).toString())
								+Double.parseDouble(tableReport.getValueAt(16, 1).toString())),11,1);
				/*
				 * 产品利润
				 */
				tableReport.setValueAt(df2.format(Double.parseDouble(textFK9.getText())),17,2);
				tableReport.setValueAt(df4.format((Double.parseDouble(tableReport.getValueAt(10, 1).toString())
						+Double.parseDouble(tableReport.getValueAt(11, 1).toString()))
						*Double.parseDouble(textFK9.getText())
						/(1-Double.parseDouble(textFK9.getText()))),17,1);
				/*
				 * 不含税售价
				 */
				tableReport.setValueAt(df4.format(Double.parseDouble(tableReport.getValueAt(10, 1).toString())
						+Double.parseDouble(tableReport.getValueAt(11, 1).toString())
						+Double.parseDouble(tableReport.getValueAt(17, 1).toString())),18,1); 
				/*
				 * 税额
				 */
				tableReport.setValueAt(df2.format(Double.parseDouble(textFK10.getText())),19,2);
				tableReport.setValueAt(df4.format(Double.parseDouble(tableReport.getValueAt(18, 1).toString())
						*Double.parseDouble(textFK10.getText())),19,1); 
				/*
				 * 含税售价
				 */
				tableReport.setValueAt(df4.format(Double.parseDouble(tableReport.getValueAt(18, 1).toString())
						+Double.parseDouble(tableReport.getValueAt(19, 1).toString())),20,1); 
				/*
				 * 不含税售价
				 */
				tableReport.setValueAt(df4.format(Double.parseDouble(tableReport.getValueAt(18, 1).toString())),21,1); 
				/*
				 * 有账期国内含税售价
				 */
				tableReport.setValueAt("账期: "+df0.format(Double.parseDouble(textFK11.getText()))+"天",22,2);
				tableReport.setValueAt(df4.format(Double.parseDouble(tableReport.getValueAt(20, 1).toString())
						+Double.parseDouble(tableReport.getValueAt(20, 1).toString())
						*Double.parseDouble(textFK11.getText())
						*0.05/365),22,1); 
				/*
				 * 有账期国内不含税售价
				 */
				tableReport.setValueAt(df4.format(Double.parseDouble(tableReport.getValueAt(21, 1).toString())
						+Double.parseDouble(tableReport.getValueAt(21, 1).toString())
						*Double.parseDouble(textFK11.getText())
						*0.05/365),23,1);
				
				tableReport.setValueAt(tableReport.getValueAt(5, 1), 5, 4);
				tableReport.setValueAt(tableReport.getValueAt(6, 1), 6, 4);
				tableReport.setValueAt(tableReport.getValueAt(7, 1), 7, 4);
				tableReport.setValueAt(tableReport.getValueAt(8, 1), 8, 4);
				tableReport.setValueAt(tableReport.getValueAt(9, 1), 9, 4);
				tableReport.setValueAt(tableReport.getValueAt(10, 1), 10, 4);
				tableReport.setValueAt(tableReport.getValueAt(11, 1), 11, 4);
				tableReport.setValueAt(tableReport.getValueAt(12, 1), 12, 4);
				tableReport.setValueAt(tableReport.getValueAt(13, 1), 13, 4);
				tableReport.setValueAt(tableReport.getValueAt(14, 1), 14, 4);
				tableReport.setValueAt(tableReport.getValueAt(15, 1), 15, 4);
				tableReport.setValueAt(df4.format(Double.parseDouble(textFK81.getText())),16, 4);
				tableReport.setValueAt(tableReport.getValueAt(17, 1), 17, 4);
				tableReport.setValueAt(tableReport.getValueAt(18, 1), 18, 4);
				
				tableReport.setValueAt(tableReport.getValueAt(18, 4), 20, 4);
				tableReport.setValueAt(df4.format(Double.parseDouble(tableReport.getValueAt(20, 4).toString())
						/Double.parseDouble(textFK12.getText())), 21, 4);
				 /*
				  * 有账期国外售价 美元
				  */
				tableReport.setValueAt(df4.format(Double.parseDouble(tableReport.getValueAt(21, 4).toString())
						+Double.parseDouble(tableReport.getValueAt(21, 4).toString())
						*Double.parseDouble(textFK11.getText())
						*0.05/365),22,4);
				/*
				 * 有账期国外售价 人民币;
				 */
				tableReport.setValueAt(df4.format(Double.parseDouble(tableReport.getValueAt(20, 4).toString())
						+Double.parseDouble(tableReport.getValueAt(20, 4).toString())
						*Double.parseDouble(textFK11.getText())
						*0.05/365),23,4);
				
				tableReport.setValueAt("按公司产成品成本的 "+df2.format(Double.parseDouble(textFK9.getText()))+" 计提利润",17,5);
				tableReport.setValueAt("货款年利率： "+df2.format(Double.parseDouble(textFK13.getText())),22,5);
				tableReport.setValueAt("汇率： "+textFK12.getText(),23,5);
				btnR2Save.setVisible(true);
				lblstatus.setText(" 销售报价已经生成，请到报价单界面查看！");
			}
		    	  
			}
			
	}
	/*
	 * generate listener end		
	 */
	
}