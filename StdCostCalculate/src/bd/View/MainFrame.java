package bd.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import bd.DAO.Coefficient;
import net.miginfocom.swing.MigLayout;
import java.awt.FlowLayout;

public class MainFrame  {

	public JFrame frame;
	public JInternalFrame internalFrame; 
	public JPanel top,center,bottom,panel_coffi,panel_code,panel_code_fnum
	,panel_reporHead, panel,panel_bottom,panel_DevReq;
	
	public JTabbedPane tabbedPane; 
	
	public JScrollPane scrollPane_report,scrollPane_coffi ,scrollpane_code_result
	,scrollPane_material,scrollPaneCostMake,scrollPane_energe
	, scrollPane_adi,scrollPane_model,scrollPane_bom
	, scrollPane_DevReq,scrollPane_tabledata;
	
	public JTable tableCalculate,tableMaterial,tableReport
				,tableEnergy,tableAdi,tableModel,tableBOM,tableQueResult;
	
	public JLabel lblCompany,lblTitle,labModel, lblFnumber;
	public JLabel lblK0,lblK1 ,lblK2 ,lblK3 ,lblK4 ,lblK5,lblK6
	,lblK7,lblK8,lblK10 ,lblK11,lblK12,lblK13,lblK14,lblK15
	,lblK16,lblK17,lblK18,lblK20,lblK21,lblK22,lblK141;
	public JLabel lbl_billno,lbl_ProdName,lbl_ProdName0,lbl_Model0,lbl_Model,labItemname0,labItemname;
	public JLabel lblTopciTitle,lblINFOR,lblstatus;
    public JLabel lblDev0,lblDev1,lblDev2,lblDev3,lblDev4,lblDev5
    ,lblDev6,lblDev7,lblDev8,lblDev9,lblDev10,lblDev11,lblDev12
    ,lblDev13,lblDev14,lblDev15,lblDev16,lblDev17,lblDev18,lblDev19
    ,lblDev20,lblDev21,lblDev22,lblDev23,lblDev24,lblDev25,lblDev26
    ,lblDev27,lblDev28,lblDev29,lblDev30,lblDev31,lblDev32,lblDev33
    ,lblDev34,lblDev35,lblDev36,lblDev37,lblDev38;
    public JLabel lblComments,lblNewJigAmt,lblNewJigPlanAmortizeQty
    ,lblHistorySaledQty;
      
	public JTextField texFnum,texFmodel;
	public JTextField textFK0,textFK1,textFK2,textFK3,textFK4,textFK5
	,textFK6,textFK7,textFK8,/*textFK9,*/textFK10,textFK11,textFK12
	,textFK13,textFK14,textFK15,textFK16,textFK17,textFK18,textFK20
	,textFK21,textFK22,textFK141/*,textFK151,textFK161,textFK171*/;
	public JTextField txtDev0,txtDev1,txtDev2,txtDev3,txtDev4,txtDev5
	,txtDev6,txtDev7,txtDev8,txtDev9,txtDev10,txtDev11,txtDev12
	,txtDev13,txtDev14,txtDev15,txtDev16,txtDev17,txtDev18,txtDev19
	,txtDev20,txtDev21,txtDev22,txtDev23,txtDev24,txtDev25,txtDev26
	,txtDev27,txtDev28,txtDev29,txtDev30,txtDev31,txtDev32;
    public JTextField txtNewJigAmt,txtNewJigPlanAmortizeQty,txtHistorySaledQty;
	    
    public JButton btnGenerate,btnR2Save,btnQuery;
    public JCheckBox chckbxNewCheckBox;
    public JTextArea textArea;
    public JLabel lblVersionInfo;
    public Coefficient k=new Coefficient();

    
    public  MainFrame() {
	frame = new JFrame();
	frame.setTitle("标准成本计算");
	frame.setBounds(0, 0,872,698);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
	frame.getContentPane().setLayout(new BorderLayout());
	
	top = new JPanel();
	FlowLayout flowLayout = (FlowLayout) top.getLayout();
	flowLayout.setAlignOnBaseline(true);
	top.setBackground(Color.LIGHT_GRAY);
	top.setForeground(Color.WHITE);
	top.setPreferredSize(new Dimension(800,50));
	frame.getContentPane().add(top,BorderLayout.NORTH);	
	lblTopciTitle = new JLabel("标 准 成 本 计 算");
	lblTopciTitle.setForeground(Color.BLUE);
	lblTopciTitle.setFont(new Font("宋体", Font.BOLD, 27));
	top.add(lblTopciTitle);
	/**
	 * version
	 */
	lblVersionInfo = new JLabel("1.0.0.0");
	top.add(lblVersionInfo);
	
	bottom = new JPanel();
	bottom.setForeground(Color.LIGHT_GRAY);
	bottom.setPreferredSize(new Dimension(800,50));
	bottom.setLayout(new BorderLayout(0, 0));
	lblINFOR = new JLabel(" ^_^ 欢迎使用标准成本报价系统！ 简要说明：在系数设置界面，确认系数->生成报价;在产品报价单界面，检查报价->保存到系统");
	bottom.add(lblINFOR);
	lblstatus = new JLabel(" ^_^ ……");
	bottom.add(lblstatus, BorderLayout.SOUTH);
	frame.getContentPane().add(bottom,BorderLayout.SOUTH);
	
	center = new JPanel();
	center.setPreferredSize(new Dimension(800,550));
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
	
	internalFrame.getContentPane().setLayout(new BorderLayout());
	panel_coffi = new JPanel();
	panel_coffi.setPreferredSize(new Dimension(800,200));
	panel_coffi.setLayout(new MigLayout("", "[220px:200px:280px,grow 300,shrink 60][50px:100px:100px,grow,shrink 20,left][30px][90px][100px][30px][][60px:100px:100px,grow][][100px][90px]", "[][][][][][][][][][grow]"));
	internalFrame.getContentPane().add(panel_coffi,BorderLayout.SOUTH);	
	panel_code = new JPanel();
	panel_code.setBackground(Color.lightGray);
	panel_code.setLayout(new BorderLayout(0, 0));
	panel_code_fnum = new JPanel();
	panel_code_fnum.setPreferredSize(new Dimension(800,50));
	panel_code_fnum.setLayout(new MigLayout("", "[60px:600px:60px,shrink 60][100px:100px:100px,grow 180,shrink 20,left][20px][100px][100px,grow][20px][100px][100px][20px][100px][100px][100px]", "[][]"));
	scrollpane_code_result = new JScrollPane();
	tableQueResult = new JTable();
	tableQueResult.setEditingColumn(0);
	tableQueResult.setColumnSelectionAllowed(true);
	tableQueResult.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	tableQueResult.setPreferredScrollableViewportSize(new Dimension(450, 300));
	tableQueResult.setFillsViewportHeight(true);
	tableQueResult.setCellSelectionEnabled(true);
	tableQueResult.setModel( new DefaultTableModel(
					new Object[][] {{false, null, null, null, null, null},
									{false, null, null, null, null, null},},
					new String[]   {"选择", "物料代码", "规格型号", "物料名称", "适用车型", "体积"}) 
		{
		public static final long serialVersionUID = 1L;
		@SuppressWarnings("rawtypes")
		Class[] columnTypes = new Class[] 
				{
						Boolean.class, String.class, String.class, String.class, Object.class, Object.class
				};
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Class getColumnClass(int columnIndex) 
		{
			return columnTypes[columnIndex];
		}
		} );
	tableQueResult.getColumnModel().getColumn(0).setResizable(false);
	tableQueResult.getColumnModel().getColumn(0).setPreferredWidth(30);
	tableQueResult.getColumnModel().getColumn(0).setMinWidth(30);
	tableQueResult.getColumnModel().getColumn(0).setMaxWidth(30);	
	scrollpane_code_result.setViewportView(tableQueResult);
	panel_code.add(panel_code_fnum,BorderLayout.NORTH);
	panel_code.add(scrollpane_code_result,BorderLayout.SOUTH);
	
	chckbxNewCheckBox = new JCheckBox("New check box");
	scrollpane_code_result.setColumnHeaderView(chckbxNewCheckBox);
	internalFrame.getContentPane().add(panel_code,BorderLayout.NORTH);
	
	lblK0 = new JLabel("工业电价k00");
	lblK0.setFont(new Font("Dialog", Font.BOLD, 20));
	panel_coffi.add(lblK0 , "cell 0 0,alignx left");
	
	textFK0 = new JTextField();
	textFK0.setEditable(false);
	try
	{
		textFK0.setText(k.getK("k00"));
	}
	catch (SQLException ex) {}
	panel_coffi.add(textFK0, "cell 1 0,alignx left");
	textFK0 .setColumns(15);
	
	lblK1 = new JLabel("直接人工_保险系数K01");
	panel_coffi.add(lblK1, "cell 0 1,alignx left");
	textFK1 = new JTextField("0");
	textFK1.setEditable(false);
	panel_coffi.add(textFK1, "cell 1 1,alignx left");
	textFK1.setColumns(15);
 	try
 	{
 		textFK1.setText(k.getK("k01"));
 	}
 	catch (SQLException ex) {}
			
	lblK2 = new JLabel("制造费用_间接人工系数k02");
	lblK2.setForeground(Color.BLACK);
	panel_coffi.add(lblK2, "cell 0 2,alignx left");
	
	textFK2 = new JTextField();
	textFK2.setEditable(false);
	try
	{
		textFK2.setText(k.getK("k02"));
	}
	catch (SQLException ex) {}
	panel_coffi.add(textFK2, "cell 1 2,alignx left");
	textFK2.setColumns(15);
	
	lblK3 = new JLabel("厂房折旧分摊金额K03");
	panel_coffi.add(lblK3, "cell 0 3,alignx left");
	
	textFK3 = new JTextField();
	textFK3.setEditable(false);
	try
	{
		textFK3.setText(k.getK("k03"));
	}
	catch (SQLException ex) {}
	panel_coffi.add(textFK3, "cell 1 3,alignx left");
	textFK3.setColumns(15);
	
	lblK4 = new JLabel("成品不良系数K04");
	lblK4.setForeground(Color.BLACK);
	panel_coffi.add(lblK4, "cell 0 4,alignx left");
	
	textFK4 = new JTextField();
	textFK4.setEditable(false);
	panel_coffi.add(textFK4, "cell 1 4,alignx left");
	textFK4.setColumns(15);
	try
	{
		textFK4.setText(k.getK("k04"));
	}
	catch (SQLException ex) {}
	
	lblK5 = new JLabel("财务费用系数K05");
	panel_coffi.add(lblK5, "cell 3 0,alignx left");
	
	textFK5 = new JTextField();
	textFK5.setEditable(false);
	panel_coffi.add(textFK5, "cell 4 0,alignx left");
	textFK5.setColumns(15);
	try
	{
		textFK5.setText(k.getK("k05"));
	}
	catch (SQLException ex) {}
	
	lblK6 = new JLabel("管理费用系数K06");
	lblK6.setForeground(Color.BLACK);
	panel_coffi.add(lblK6, "cell 3 1,alignx left");
	
	textFK6 = new JTextField();
	textFK6.setEditable(false);
	panel_coffi.add(textFK6 , "cell 4 1,alignx left");
	textFK6 .setColumns(15);
	try
	{
		textFK6.setText(k.getK("k06"));
	}
	catch (SQLException ex) {}
	
	lblK7 = new JLabel("销售费用系数K07");
	panel_coffi.add(lblK7, "cell 3 2,alignx left");
	
	textFK7 = new JTextField();
	textFK7.setEditable(false);
	panel_coffi.add(textFK7, "cell 4 2,alignx left");
	textFK7.setColumns(15);
	try
	{
		textFK7.setText(k.getK("k07"));
	}
	catch (SQLException ex) {}
	
	lblK8 = new JLabel("内贸运输费用K08");
	lblK8.setForeground(Color.BLACK);
	panel_coffi.add(lblK8, "cell 3 3,alignx left");
	
	textFK8 = new JTextField();
	textFK8.setEditable(false);
	panel_coffi.add(textFK8, "cell 4 3,alignx left");
	textFK8.setColumns(15);
	try
	{
		textFK8.setText(k.getK("k08"));
	}
	catch (SQLException ex) {}
	
	lblK20 = new JLabel("FOB青岛整柜费用 K20");
	panel_coffi.add(lblK20, "cell 3 4,alignx left");
	
	textFK20 = new JTextField();
	textFK20.setEditable(false);
	panel_coffi.add(textFK20, "cell 4 4,alignx left");
	textFK20.setColumns(15);
	try
	{
		textFK20.setText(k.getK("k20"));
	}
	catch (SQLException ex) {}
	
	lblK21 = new JLabel("FOB青岛整柜体积 K21");
	panel_coffi.add(lblK21, "cell 3 5,alignx left");
	
	textFK21 = new JTextField();
	textFK21.setEditable(false);
	panel_coffi.add(textFK21, "cell 4 5,alignx left");
	textFK20.setColumns(15);
	try
	{
		textFK21.setText(k.getK("k21"));
	}
	catch (SQLException ex) {}
	
	lblK10 = new JLabel("国内增值税率K10");
	panel_coffi.add(lblK10, "cell 6 1,alignx left");
	
	textFK10 = new JTextField();
	textFK10.setEditable(false);
	panel_coffi.add(textFK10, "cell 7 1,alignx left");
	textFK10.setColumns(15);
	try
	{
		textFK10.setText(k.getK("k10"));
	}
	catch (SQLException ex) {}
	
	lblK11 = new JLabel("国内账期(天数)K11");
	panel_coffi.add(lblK11, "cell 6 2,alignx left");
	
	textFK11 = new JTextField();
	textFK11.setEditable(false);
	panel_coffi.add(textFK11, "cell 7 2,alignx left");
	textFK11.setColumns(15);
	try
	{
		textFK11.setText(k.getK("k11"));
	}
	catch (SQLException ex) {}
	
	lblK22 = new JLabel("国外账期(天数)K22");
	panel_coffi.add(lblK22, "cell 9 2,alignx left");
	textFK22 = new JTextField();
	textFK22.setEditable(false);
	panel_coffi.add(textFK22, "cell 10 2,alignx left");
	textFK22.setColumns(15);
	try
	{
		textFK22.setText(k.getK("k22"));
	}
	catch (SQLException ex) {}
	
	lblK12 = new JLabel("预算汇率美元/人民币 K12");
	panel_coffi.add(lblK12, "cell 6 3,alignx left");
	
	textFK12 = new JTextField();
	textFK12.setEditable(false);
	panel_coffi.add(textFK12, "cell 7 3,alignx left");
	textFK12.setColumns(15);
	try
	{
		textFK12.setText(k.getExRate());
	}
	catch (SQLException ex) {}
			
	lblK13 = new JLabel("货款年利率K13");
	panel_coffi.add(lblK13, "cell 6 4,alignx left");
	
	textFK13 = new JTextField();
	textFK13.setEditable(false);
	panel_coffi.add(textFK13, "cell 7 4,alignx left");
	textFK13.setColumns(15);
	try
	{
		textFK13.setText(k.getK("k13"));
	}
	catch (SQLException ex) {}	
	
	lblK14 = new JLabel("直接人工-清洗K14");
	panel_coffi.add(lblK14, "cell 0 5,alignx left");
	
	textFK14 = new JTextField();
	textFK14.setEditable(false);
	panel_coffi.add(textFK14, "cell 1 5,alignx left");
	textFK14.setColumns(20);
	try
	{
		textFK14.setText(k.getK("k14"));
	}
	catch (SQLException ex) {}	
	
	lblK141 = new JLabel("制造费用-清洗K141");
	panel_coffi.add(lblK141, "cell 3 6,alignx left");		

	textFK141 = new JTextField();
	textFK141.setEditable(false);
	panel_coffi.add(textFK141, "cell 4 6,alignx left");
	textFK141.setColumns(20);
	try
	{
		textFK141.setText(k.getK("k141"));
	}		
	catch (SQLException ex) {}	
			
	lblK15 = new JLabel("管理费用-土地摊销K15");
	panel_coffi.add(lblK15, "cell 0 6,alignx left");		
	textFK15 = new JTextField();
	textFK15.setEditable(false);
	panel_coffi.add(textFK15, "cell 1 6,alignx left");
	textFK15.setColumns(20);
	try
	{
		textFK15.setText(k.getK("k15"));
	}
	catch (SQLException ex) {}	
	
	/*
	lblK16 = new JLabel("新开模具费用K16");
	panel_coffi.add(lblK16, "cell 6 5,alignx left");
	
	textFK16 = new JTextField();
	panel_coffi.add(textFK16, "cell 7 5,alignx left");
	textFK16.setColumns(20);
 	textFK16.setText(String.valueOf(0));
	
	lblK17 = new JLabel("新开模具计划摊销数量 K17");
	panel_coffi.add(lblK17, "cell 6 6,alignx left");
	
	textFK17 = new JTextField();
	panel_coffi.add(textFK17, "cell 7 6,alignx left");
	textFK17.setColumns(20);
	textFK17.setText(String.valueOf(2000));
	*/
	lblK18 = new JLabel("制造费用_其他成本K18");
	panel_coffi.add(lblK18, "cell 9 3,alignx left");
	textFK18 = new JTextField();
	textFK18.setEditable(false);
	panel_coffi.add(textFK18, "cell 10 3,alignx left");
	textFK18.setColumns(20);
	try
	{
		textFK18.setText(k.getK("k18"));
	}
	catch (SQLException ex) {}
	
	labModel = new JLabel("规格型号");
	panel_code_fnum.add(labModel, "cell 0 1");
	lblFnumber = new JLabel("物料长代码");
	panel_code_fnum.add(lblFnumber, "cell 3 1,alignx trailing");
	
	texFmodel = new JTextField();
	panel_code_fnum.add(texFmodel, "cell 1 1,alignx left");
	texFmodel.setForeground(Color.BLUE);
	texFmodel.setText("11298");
	texFmodel.setColumns(15);
	texFnum = new JTextField();
	panel_code_fnum.add(texFnum, "cell 4 1 2 1,alignx left");
	texFnum.setColumns(30);
	texFnum.setForeground(Color.BLUE);

	btnQuery = new JButton("查找");
	panel_code_fnum.add(btnQuery, "cell 6 1");
	btnGenerate   = new JButton("生成报价");
	btnGenerate .setForeground(Color.BLUE);
	btnGenerate .setBackground(new Color(0, 255, 0));
	panel_code_fnum.add(btnGenerate , "cell 7 1,alignx right");		
	
	lblComments = new JLabel("New label");
	panel_code_fnum.add(lblComments, "cell 10 1");
	
	internalFrame.setVisible(true);
	scrollPane_report = new JScrollPane();
	tabbedPane.addTab("标准成本核价单", null, scrollPane_report, null);
					
	panel = new JPanel();
	scrollPane_report.setViewportView(panel);
	panel.setLayout(new BorderLayout(0, 0));
					
	panel_reporHead = new JPanel();
	panel_reporHead.setBackground(Color.WHITE);
	panel_reporHead.setPreferredSize(new Dimension(800,100));
	panel.add(panel_reporHead,BorderLayout.NORTH);
	panel_reporHead.setLayout(new MigLayout("", "[100px][200px,grow][100px][300px,grow][100px][:200px:300px,grow]", "[][100px][]"));
	
	lblCompany = new JLabel("威海邦德散热系统股份有限公司");
	panel_reporHead.add(lblCompany, "cell 0 0 3 1,alignx left,aligny center");
	lblTitle = new JLabel("标 准 成 本 核 价 单");
	lblTitle.setFont(new Font("宋体", Font.BOLD, 15));
	panel_reporHead.add(lblTitle, "cell 3 1 2 1,alignx center");
	
	lbl_billno = new JLabel("表单编号：           ");
	panel_reporHead.add(lbl_billno,"cell 5 0");
	/*
	 * button save
	 */
	btnR2Save = new JButton("保存到系统");
	btnR2Save.setForeground(Color.BLUE);
	btnR2Save.setBackground(new Color(0, 255, 0));
	panel_reporHead.add(btnR2Save, "cell 5 1");
	lbl_ProdName0 = new JLabel("物料代码");	
	panel_reporHead.add(lbl_ProdName0, "cell 0 2 1 1");
	lbl_ProdName = new JLabel("");	
	panel_reporHead.add(lbl_ProdName, "cell 1 2 1 1");
	lbl_Model0 = new JLabel("规格型号");	
	panel_reporHead.add(lbl_Model0, "cell 2 2 1 1");
	lbl_Model = new JLabel("");	
	panel_reporHead.add(lbl_Model, "cell 3 2 1 1");
	labItemname0 = new JLabel("物料名称");	    
	panel_reporHead.add(labItemname0, "cell 4 2 1 1 ");
	labItemname = new JLabel("");	    
	panel_reporHead.add(labItemname, "cell 5 2 1 1 ");
	scrollPane_tabledata = new JScrollPane();
	panel.add(scrollPane_tabledata, BorderLayout.CENTER);
	panel_bottom = new JPanel();
	panel_bottom.setPreferredSize(new Dimension(800,50));
	panel.add(panel_bottom, BorderLayout.SOUTH);
	panel_bottom.setLayout(new MigLayout("", "[100px][100px][130px][100px][100px][100px]", "[][]"));
	lblNewJigAmt = new JLabel("新开模具费用");
	panel_bottom.add(lblNewJigAmt,"cell 0 1,alignx right");
	txtNewJigAmt = new JTextField();
	txtNewJigAmt.setColumns(15);
	panel_bottom.add(txtNewJigAmt,"cell 1 1");		
	lblNewJigPlanAmortizeQty = new JLabel("新开模具计划摊销数量");
	panel_bottom.add(lblNewJigPlanAmortizeQty,"cell 2 1 ,alignx right");
	txtNewJigPlanAmortizeQty = new JTextField();
	txtNewJigPlanAmortizeQty.setColumns(15);
	panel_bottom.add(txtNewJigPlanAmortizeQty,"cell 3 1");
	lblHistorySaledQty= new JLabel("历史销售数量");
	panel_bottom.add(lblHistorySaledQty,"cell 4 1,alignx right");
	txtHistorySaledQty = new JTextField();
	txtHistorySaledQty.setColumns(15);
	panel_bottom.add(txtHistorySaledQty,"cell 5 1");
				
	tableReport = new JTable();
	tableReport.setEnabled(false);
	tableReport.setFont(new Font("Dialog", Font.PLAIN, 14));
	scrollPane_tabledata.setViewportView(tableReport);
	tableReport.setModel(new DefaultTableModel(
				new Object[][] 
						{{null, null, null, null, null, null, null},
					},
				new String[] 
						{
						"No.", "\u9879\u76EE", "\u8D39\u7528\uFF08\u56FD\u5185\uFF09"
						, "\u5355\u4F4D\u6BD4\u4F8B", "\u9879\u76EE"
						, "\u8D39\u7528\uFF08\u56FD\u5916\uFF09"
						, "\u6210\u672C\u8D39\u7528\u6807\u51C6"
						}
					));

	scrollPane_DevReq = new JScrollPane();

	panel_DevReq = new JPanel();
	panel_DevReq.setLayout(new MigLayout("", "[100px][144.00px][6px][80px][125.00px][6px][80.00px][16px][4px][6px][40px][80.00px][4px][80px][80.00px]", "[][60px,center][][50px,center][][][][][][][][][][][50px,center][][][]"));
	scrollPane_DevReq.setViewportView(panel_DevReq);
	
	lblDev0 = new JLabel("单据编号");
	panel_DevReq.add(lblDev0, "cell 0 2");		
	lblDev1= new JLabel("市场适用属性");
	panel_DevReq.add(lblDev1, "cell 0 4");	
	lblDev2= new JLabel("名称(成品)");
	panel_DevReq.add(lblDev2, "cell 0 5");
	lblDev3= new JLabel("代码");
	panel_DevReq.add(lblDev3, "cell 0 6");
	lblDev4= new JLabel("适用车型中文");
	panel_DevReq.add(lblDev4, "cell 0 7");	
	lblDev5= new JLabel("适用车型英文");
	panel_DevReq.add(lblDev5, "cell 0 8");	
	lblDev6= new JLabel("OEM 代码");
	panel_DevReq.add(lblDev6, "cell 0 9");	
	lblDev7= new JLabel("NISSENS 代码");
	panel_DevReq.add(lblDev7, "cell 0 10");	
	lblDev8= new JLabel("DPI 代码");
	panel_DevReq.add(lblDev8, "cell 0 11");	
	lblDev9= new JLabel("区域限制时限");
	panel_DevReq.add(lblDev9, "cell 0 12");	
	
	lblDev10= new JLabel("适用车型市场前景");
	panel_DevReq.add(lblDev10, "cell 0 13");
	lblDev11= new JLabel("BOM工艺路线 完成时间");
	panel_DevReq.add(lblDev11, "cell 0 15");	
	lblDev12= new JLabel("开发样品 完成时间");
	panel_DevReq.add(lblDev12, "cell 0 16");			

	lblDev13= new JLabel("批产准备 完成时间");
	panel_DevReq.add(lblDev13, "cell 0 17");
	
	lblDev14 = new JLabel("制单人");
	panel_DevReq.add(lblDev14, "cell 3 2");
	lblDev15= new JLabel("对应市场");
	panel_DevReq.add(lblDev15, "cell 3 4");
	lblDev16= new JLabel("物料分类");
	panel_DevReq.add(lblDev16, "cell 3 5");	
	lblDev17= new JLabel("规格型号");
	panel_DevReq.add(lblDev17, "cell 3 6");
	lblDev18= new JLabel("价格限时时限");
	panel_DevReq.add(lblDev18, "cell 3 12");
	lblDev19= new JLabel("适用车型区域保有量");
	panel_DevReq.add(lblDev19, "cell 3 13");	
	
	lblDev20 = new JLabel("制单日期");
	panel_DevReq.add(lblDev20, "cell 6 2");
	lblDev21= new JLabel("客户代码");
	panel_DevReq.add(lblDev21, "cell 6 4");
	lblDev22= new JLabel("样品来源");
	panel_DevReq.add(lblDev22, "cell 6 5");	
	lblDev23= new JLabel("图号");
	panel_DevReq.add(lblDev23, "cell 6 6");
	lblDev24= new JLabel("区域限制时限内订单总数量/PCS");
	panel_DevReq.add(lblDev24, "cell 6 12 2 1");
	lblDev25= new JLabel("适用车型市场保有量");
	panel_DevReq.add(lblDev25, "cell 6 13");	
	
	lblDev26= new JLabel("交货条件");
	panel_DevReq.add(lblDev26, "cell 10 4");
	lblDev27= new JLabel("开发条件");
	panel_DevReq.add(lblDev27, "cell 10 6");
	
	lblDev28= new JLabel("审核人");
	panel_DevReq.add(lblDev28, "cell 10 17");
	lblDev29= new JLabel("客户要求");
	panel_DevReq.add(lblDev29, "cell 13 4");
	lblDev30= new JLabel("样品形式");
	panel_DevReq.add(lblDev30, "cell 13 5");	
	lblDev31= new JLabel("首次裸包批产数量");
	panel_DevReq.add(lblDev31, "cell 13 6");
	lblDev32= new JLabel("审核日期");
	panel_DevReq.add(lblDev32, "cell 13 17");
	lblDev33= new JLabel(" 备注");
	panel_DevReq.add(lblDev33, "cell 10 12");
	/*
	 * title
	 */
	lblDev34= new JLabel("营销部");
	lblDev34.setFont(new Font("Dialog", Font.BOLD, 16));
	panel_DevReq.add(lblDev34, "cell 0 3 5 1");	
	lblDev35= new JLabel("技术部");
	lblDev35.setFont(new Font("Dialog", Font.BOLD, 16));
	panel_DevReq.add(lblDev35, "cell 0 14 5 1");	
	lblDev36= new JLabel("威海邦德散热系统股份有限公司");
	panel_DevReq.add(lblDev36, "cell 0 0 5 1");	
	lblDev37= new JLabel("表单编号:BD/XS-010-B/6");
	panel_DevReq.add(lblDev37, "cell 11 0 3 1");
	lblDev38= new JLabel("产品开发需求书");
	lblDev38.setFont(new Font("Dialog", Font.BOLD, 22));
	panel_DevReq.add(lblDev38, "cell 4 1 3 1");
	/* 
	 * value
	 */
	txtDev0=new JTextField("单据编号");
	txtDev0.setColumns(15);
	panel_DevReq.add(txtDev0, "cell 1 2");		
	txtDev1= new JTextField("市场适用属性");		
	txtDev1.setColumns(15);
	panel_DevReq.add(txtDev1, "cell 1 4");	
	txtDev2= new JTextField();		
	txtDev2.setColumns(15);
	panel_DevReq.add(txtDev2, "cell 1 5");
	txtDev3= new JTextField();		
	txtDev3.setColumns(15);
	panel_DevReq.add(txtDev3, "cell 1 6");
	txtDev4= new JTextField("适用车型中文适用车型中文适用车型中文适用车型中文适用车型中文适用车型中文适用车型中文适用车型中文适用车型中文适用车型中文适用车型中文适用车型中文");
	txtDev4.setColumns(105);
	panel_DevReq.add(txtDev4, "cell 1 7 14 1");	
	txtDev5= new JTextField();
	txtDev5.setColumns(105);
	panel_DevReq.add(txtDev5, "cell 1 8 14 1");	
	txtDev6= new JTextField();
	txtDev6.setColumns(105);
	panel_DevReq.add(txtDev6, "cell 1 9 14 1");	
	txtDev7= new JTextField();
	txtDev7.setColumns(105);
	panel_DevReq.add(txtDev7, "cell 1 10 14 1");	
	txtDev8= new JTextField();
	txtDev8.setColumns(105);
	panel_DevReq.add(txtDev8, "cell 1 11 14 1");	
	txtDev9= new JTextField();
	txtDev9.setColumns(15);
	panel_DevReq.add(txtDev9, "cell 1 12");	
	txtDev10= new JTextField();
	txtDev10.setColumns(15);
	panel_DevReq.add(txtDev10, "cell 1 13");		
	txtDev11= new JTextField();
	txtDev11.setColumns(25);
	panel_DevReq.add(txtDev11, "cell 1 15 4 1 ");	
	txtDev12= new JTextField();
	txtDev12.setColumns(25);
	panel_DevReq.add(txtDev12, "cell 1 16 4 1 ");	
	txtDev13= new JTextField();
	txtDev13.setColumns(25);
	panel_DevReq.add(txtDev13, "cell 1 17 4 1");
	
	txtDev14= new JTextField("制单人");
	txtDev14.setColumns(15);
	panel_DevReq.add(txtDev14, "cell 4 2");
	txtDev15= new JTextField("对应市场");
	txtDev15.setColumns(15);
	panel_DevReq.add(txtDev15, "cell 4 4");
	txtDev16= new JTextField();
	txtDev16.setColumns(15);
	panel_DevReq.add(txtDev16, "cell 4 5");	
	txtDev17= new JTextField();
	txtDev17.setColumns(15);
	panel_DevReq.add(txtDev17, "cell 4 6");
	txtDev18= new JTextField();
	txtDev18.setColumns(15);
	panel_DevReq.add(txtDev18, "cell 4 12");
	txtDev19= new JTextField();
	txtDev19.setColumns(15);
	panel_DevReq.add(txtDev19, "cell 4 13");	
	
	txtDev20= new JTextField("制单日期");
	txtDev20.setColumns(10);
	panel_DevReq.add(txtDev20, "cell 7 2 2 1");
	txtDev21= new JTextField("客户代码");
	txtDev21.setColumns(10);
	panel_DevReq.add(txtDev21, "cell 7 4 2 1");
	txtDev22= new JTextField("样品来源");
	txtDev22.setColumns(30);
	panel_DevReq.add(txtDev22, "cell 7 5 5 1");	
	txtDev23= new JTextField();
	txtDev23.setColumns(10);
	panel_DevReq.add(txtDev23, "cell 7 6 2 1");
	txtDev24= new JTextField();
	txtDev24.setColumns(4);
	panel_DevReq.add(txtDev24, "cell 8 12");
	txtDev25= new JTextField();
	txtDev25.setColumns(10);
	panel_DevReq.add(txtDev25, "cell 7 13 2 1");	
	
	txtDev26= new JTextField();
	txtDev26.setColumns(10);
	panel_DevReq.add(txtDev26, "cell 11 4");
	txtDev27= new JTextField();
	txtDev27.setColumns(10);
	panel_DevReq.add(txtDev27, "cell 11 6");
	txtDev28= new JTextField();
	txtDev28.setColumns(10);
	panel_DevReq.add(txtDev28, "cell 11 17");

	txtDev29= new JTextField();
	txtDev29.setColumns(10);
	panel_DevReq.add(txtDev29, "cell 14 4");
	txtDev30= new JTextField();
	txtDev30.setColumns(10);
	panel_DevReq.add(txtDev30, "cell 14 5");	
	txtDev31= new JTextField();
	txtDev31.setColumns(10);
	panel_DevReq.add(txtDev31, "cell 14 6");
	txtDev32= new JTextField();
	txtDev32.setColumns(10);
	panel_DevReq.add(txtDev32, "cell 14 17");
	textArea = new JTextArea(4 , 2);
	textArea.setColumns(35);
	textArea.setBackground(Color.LIGHT_GRAY);
	textArea.setForeground(Color.BLACK);
	panel_DevReq.add(textArea, "cell 11 12 4 2");
	tabbedPane.addTab("产品开发需求书", scrollPane_DevReq  );
	scrollPane_material = new JScrollPane();
	tabbedPane.addTab("材料成本明细", null, scrollPane_material, null);
	
	tableMaterial = new JTable();
	tableMaterial.setModel(new DefaultTableModel(
		new Object[][] {
			{null, null, null, null, null, null, null, null, null, null},
			{null, null, null, null, null, null, null,null, null, null},
			},
		new String[] {
			"物料长代码", "物料名称", "规格型号", "计量单位", "单位数量","数量", "材料单价", 
			"材料金额","一年平均采购单价", "计划单价"
			}
			));
	scrollPane_material.setViewportView(tableMaterial);
	
	scrollPaneCostMake = new JScrollPane();
	tabbedPane.addTab("直接人工与制造费用明细", null, scrollPaneCostMake, null);
	tableCalculate = new JTable();
	tableCalculate.setModel(new DefaultTableModel(
		new Object[][] {
			{null, null, null, null, null, null, null, null, null, null, null, null, null, null},
			{null, null, null, null, null, null, null, null, null, null, null, null, null, null},
			},
		new String[] {"零部件代码", "零部件名", "数量", "工序名称", "工序价格", "工资系数", "工件数量", 
			"工资", "保险", "制造费用-间接人工", "制造费用-电费", "制造费用-设备折旧分摊"
			, "制造费用-辅料费用", "制造费用-工装模具费用"}
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
				"固定资产内码","固定资产编号","芯体长度（米）","芯体高度（米）"
				}
			));
	scrollPane_energe.setViewportView(tableEnergy);
	
	scrollPane_adi = new JScrollPane();
	tabbedPane.addTab("辅料明细", null, scrollPane_adi, null);
	tableAdi = new JTable();
	tableAdi.setModel(new DefaultTableModel(
		new Object[][] {
			{null, null, null, null, null, null, null, null, null,null,
				null, null, null, null,null,null,null },
			},
		new String[] {
				"foperid","fopersn","零部件代码","零部件名","零件数量","工序","工价","工资系数"
				,"工件数量","辅料名","用量","价格","辅料费用","一年采购发票平均单价",  "计划单价"
				,"芯体长度","芯体高度"
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
				"工资系数","工件数量","工装模具名称","工装模具分摊费用","米重(kg/m)"
				,"最大使用量(kg)","模具费用","芯体长度","芯体高度"
				}
		));
	scrollPane_model.setViewportView(tableModel);
	
	scrollPane_bom = new JScrollPane();
	tabbedPane.addTab("BOM", null, scrollPane_bom, null);
	
	tableBOM = new JTable();
	tableBOM.setModel(new DefaultTableModel(
		new Object[][] {
			{null, null, null, null, null, null, null, null, null, null, null, null, 
				null, null, null, null, null, null,null,null},
			},
		new String[] {
				"层级","FParentID","FItemID","物料代码","物料名称","规格","加工类型",
				"叶物料","单位数量","数量","单位","损耗率","坯料尺寸","BOM状态",
				"BOM禁用","BOM跳层","工艺路线ID","fbominterid","BOM编号","sn"
				}
			));
	scrollPane_bom.setViewportView(tableBOM);

    }
}
