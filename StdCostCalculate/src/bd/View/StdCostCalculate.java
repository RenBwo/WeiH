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
import java.text.SimpleDateFormat;
import java.util.Date;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

import bd.DAO.*;
import bd.connection.getcon;
import bd.connection.verifyVersion;

public class StdCostCalculate 
{
	JFrame frame;
	private JInternalFrame internalFrame; 
	private getcon conn=new getcon();
	private GetFirstItemID getFirstItemID=new GetFirstItemID();
	private GetFinterID getFinterID= new GetFinterID();
	private GetCurrentYear getCurrentYear=new GetCurrentYear();
	private GetCurrentMonth getCurrentMonth = new GetCurrentMonth();
	//private GetOEM getOEM = new GetOEM();
	private GetItemName getItemName = new GetItemName();
	private GetModel getModel=new GetModel();
	private GetModelQtySaled getModelQtySaled=new GetModelQtySaled();
	private GetItselfQtySaled getItselfQtySaled = new GetItselfQtySaled();
	private GetGainRate getGainRate = new GetGainRate();
	private GetPackageSize getPackageSize=new GetPackageSize();
	private GetLenWidHgt lengthWidHgt = new GetLenWidHgt();
	private GetCapacityQianHanLu getCapacityQianHanLu = new GetCapacityQianHanLu();
	
    private CreateTableRPT createTableRPT=new CreateTableRPT() ;
	private CreateTableBOM createTableBOM=new CreateTableBOM() ;
	private CreateTableMaterial createTableMaterial=new CreateTableMaterial();
	private CreateTableLaborAndMake crteTabLabourAndMake= new CreateTableLaborAndMake();
	private BomExpose bomExpose=new BomExpose();
	private CleanBom cleanBom=new CleanBom();
	
    private CostMateiral costMateiral=new CostMateiral();
    private CostLabourAndMake costLabourAndMake=new CostLabourAndMake();
	private DelStandardCostReport delStandardCostReport=new DelStandardCostReport(); 
	private VerifyBOM verifyBOM=new VerifyBOM();
    private VerifyMaterialPrice verifyMaterialPrice=new VerifyMaterialPrice();
    private VerifyAdiMaterialPrice verifyAdiMaterialPrice = new VerifyAdiMaterialPrice();
    private VerifyRoutLWH verifyRoutLWH =new  VerifyRoutLWH();
    private VerifyRout verifyRout=new VerifyRout();
	private UpdateAdiMaterialPrice updateAdiMaterialPrice=new UpdateAdiMaterialPrice();
    private UpdateMachineInfo updateMachineInfo=new UpdateMachineInfo();
    private UpdateProductStdCost updateProductStdCost=new UpdateProductStdCost();
    private UpdateStandardCostReport udateStandardCostReport=new UpdateStandardCostReport();
    private UpdateCompanyPricePolicy updateCompanyPricePolicy = new UpdateCompanyPricePolicy();
    private JPanel top,center,bottom,panel_coffi,panel_code,panel_code_fnum,panel_reporHead;
	private JPanel panel,panel_bottom,panel_DevReq;
	
	private JTabbedPane tabbedPane; 
	
	private JScrollPane scrollPane_report,scrollPane_coffi ,scrollpane_code_result;
	private JScrollPane scrollPane_material,scrollPaneCostMake,scrollPane_energe;
    private JScrollPane scrollPane_adi,scrollPane_model,scrollPane_bom;
    private JScrollPane scrollPane_DevReq,scrollPane_tabledata;
	
	private JTable tableCalculate,tableMaterial,tableReport;
	private JTable tableEnergy,tableAdi,tableModel,tableBOM,tableQueResult;
	
	private JLabel lblCompany,lblTitle,labModel, lblFnumber;
	private JLabel lblK0,lblK1 ,lblK2 ,lblK3 ,lblK4 ,lblK5,lblK6,lblK7,lblK8,lblK10 ;
	private JLabel lblK11,lblK12,lblK13,lblK14,lblK15,lblK16,lblK17,lblK18,lblK20,lblK21,lblK22;
	private JLabel lblK141;
	private JLabel lbl_billno,lbl_ProdName,lbl_ProdName0,lbl_Model0,lbl_Model,labItemname0,labItemname;
	private JLabel lblTopciTitle,lblINFOR,lblstatus;
    private JLabel lblDev0,lblDev1,lblDev2,lblDev3,lblDev4,lblDev5,lblDev6,lblDev7,lblDev8,lblDev9;
    private JLabel lblDev10,lblDev11,lblDev12,lblDev13,lblDev14,lblDev15,lblDev16,lblDev17,lblDev18,lblDev19;
    private JLabel lblDev20,lblDev21,lblDev22,lblDev23,lblDev24,lblDev25,lblDev26,lblDev27,lblDev28,lblDev29;
    private JLabel lblDev30,lblDev31,lblDev32,lblDev33,lblDev34,lblDev35,lblDev36,lblDev37,lblDev38;
    private JLabel lblComments,lblNewModel,lblNewModelSaleQty,lblHistSalQty;
      
	private JTextField texFmodel;
	private JTextField textFK0,textFK1,textFK2,textFK3,textFK4,textFK5,textFK6,textFK7,textFK8,/*textFK9,*/textFK10;
	private JTextField textFK11,textFK12,textFK13,textFK14,textFK15,textFK16,textFK17,textFK18,textFK20;
	private JTextField textFK21,textFK22;
	private JTextField textFK141/*,textFK151,textFK161,textFK171*/;
	private JTextField txtDev0,txtDev1,txtDev2,txtDev3,txtDev4,txtDev5,txtDev6,txtDev7,txtDev8,txtDev9;
    private JTextField txtDev10,txtDev11,txtDev12,txtDev13,txtDev14,txtDev15,txtDev16,txtDev17,txtDev18,txtDev19;
    private JTextField txtDev20,txtDev21,txtDev22,txtDev23,txtDev24,txtDev25,txtDev26,txtDev27,txtDev28,txtDev29;
    private JTextField txtDev30,txtDev31,txtDev32,txtNewModel,txtNewModelSalQty,txtHistSalQty;
	
    private DecimalFormat df0,df2,df4;
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
    private JButton btnR2Save,btnQuery;
    private JButton btnGenerate; 
    private JTextField texFnum;
    private JCheckBox chckbxNewCheckBox;
    
    private Boolean selectedAll_yn =false ;
    private JTextArea textArea;
    String[] fnumbers ;
    String fnumber;
    private String currentyear,currentperiod,itemname,model;
    static  String auto="default";
    private int 	firstitemid,finterid,materialVerifyError
    	,bomVerifyError,adiVerifyError,RoutVerifyErr,RoutLWHVerifyErr ;
    private double width,length,height,capacity,gainrate
    	,itselfQtySaled,modelQtySaled,packagesize;
      
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		if (args.length > 0) 
		{
			auto = args[0];
		}
		
		EventQueue.invokeLater(new Runnable() 
		{
			@SuppressWarnings("static-access")
			public void run() 
			{
				try 
				{
					verifyVersion checkVer = new verifyVersion();
					checkVer.Verifyframe.setVisible(true);					
					if (checkVer.verifyVer==1) 
					{
						StdCostCalculate window = new StdCostCalculate();
						if (auto.equals("auto")) 
						{
							checkVer.Verifyframe.setVisible(false);	
							window.frame.setVisible(false);
							window.btnGenerate.doClick();
							System.exit(0);
						}
						else 
						{
							checkVer.Verifyframe.setVisible(false);
							window.frame.setVisible(true);
						}
					}
					else 
					{
						JOptionPane.showMessageDialog(checkVer.Verifyframe,"程序版本错误，请使用最新版本"); 
					}
				} 
				catch (Exception e) 
				{	
					e.printStackTrace();
				}
			}
		});
	}
	

	/**
	 * Create the application.
	 * @throws PropertyVetoException 
	 */

    
	public StdCostCalculate()  
	{
		frame = new JFrame();
		frame.setTitle("标准成本计算");
		frame.setBounds(0, 0,872,698);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		frame.getContentPane().setLayout(new BorderLayout());
		
		top = new JPanel();
		top.setBackground(Color.LIGHT_GRAY);
		top.setForeground(Color.WHITE);
		top.setPreferredSize(new Dimension(800,50));
		frame.getContentPane().add(top,BorderLayout.NORTH);	
		lblTopciTitle = new JLabel("标 准 成 本 计 算");
		lblTopciTitle.setForeground(Color.BLUE);
		lblTopciTitle.setFont(new Font("宋体", Font.BOLD, 27));
		top.add(lblTopciTitle);
		
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
			private static final long serialVersionUID = 1L;
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

		tableQueResult.getTableHeader().addMouseListener(new MouseAdapter()	
		{ 
			public void mouseClicked(MouseEvent e)
			{ if(e.getClickCount() == 2 )
			{ 
				int  col=((JTableHeader)e.getSource()).columnAtPoint(e.getPoint()); //获得列位置
				if (col == (int)0 ) 
				{
					int  countRow = tableQueResult.getRowCount();
					if(selectedAll_yn) 
					{
						for (int x = 0;x<countRow;x++) 
						{
							tableQueResult.setValueAt(false,x,col);
						} 
						selectedAll_yn=false; 
					} 
					else 
					{
						for (int x = 0;x<countRow;x++) 
						{
							tableQueResult.setValueAt(true,x,col);
						}
						selectedAll_yn=true;
					}
				}
			} 
			else 
				return; 
			} 
		});
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
		CoefficientCalculate k0 = new CoefficientCalculate();
		try
		{
			textFK0.setText(k0.CoeValue("k00"));
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
	 	CoefficientCalculate k1 = new CoefficientCalculate();
	 	try
	 	{
	 		textFK1.setText(k1.CoeValue("k01"));
	 	}
	 	catch (SQLException ex) {}
				
		lblK2 = new JLabel("制造费用_间接人工系数k02");
		lblK2.setForeground(Color.BLACK);
		panel_coffi.add(lblK2, "cell 0 2,alignx left");
		
		textFK2 = new JTextField();
		textFK2.setEditable(false);
		CoefficientCalculate k2 = new CoefficientCalculate();
		try
		{
			textFK2.setText(k2.CoeValue("k02"));
		}
		catch (SQLException ex) {}
		panel_coffi.add(textFK2, "cell 1 2,alignx left");
		textFK2.setColumns(15);
		
		lblK3 = new JLabel("厂房折旧分摊金额K03");
		panel_coffi.add(lblK3, "cell 0 3,alignx left");
		
		textFK3 = new JTextField();
		textFK3.setEditable(false);
		CoefficientCalculate k3 = new CoefficientCalculate();
		try
		{
			textFK3.setText(k3.CoeValue("k03"));
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
		CoefficientCalculate k4 = new CoefficientCalculate();
		try
		{
			textFK4.setText(k4.CoeValue("k04"));
		}
		catch (SQLException ex) {}
		
		lblK5 = new JLabel("财务费用系数K05");
		panel_coffi.add(lblK5, "cell 3 0,alignx left");
		
		textFK5 = new JTextField();
		textFK5.setEditable(false);
		panel_coffi.add(textFK5, "cell 4 0,alignx left");
		textFK5.setColumns(15);
		CoefficientCalculate k5 = new CoefficientCalculate();
		try
		{
			textFK5.setText(k5.CoeValue("k05"));
		}
		catch (SQLException ex) {}
		
		lblK6 = new JLabel("管理费用系数K06");
		lblK6.setForeground(Color.BLACK);
		panel_coffi.add(lblK6, "cell 3 1,alignx left");
		
		textFK6 = new JTextField();
		textFK6.setEditable(false);
		panel_coffi.add(textFK6 , "cell 4 1,alignx left");
		textFK6 .setColumns(15);
		CoefficientCalculate k6 = new CoefficientCalculate();
		try
		{
			textFK6.setText(k6.CoeValue("k06"));
		}
		catch (SQLException ex) {}
		
		lblK7 = new JLabel("销售费用系数K07");
		panel_coffi.add(lblK7, "cell 3 2,alignx left");
		
		textFK7 = new JTextField();
		textFK7.setEditable(false);
		panel_coffi.add(textFK7, "cell 4 2,alignx left");
		textFK7.setColumns(15);
		CoefficientCalculate k7 = new CoefficientCalculate();
		try
		{
			textFK7.setText(k7.CoeValue("k07"));
		}
		catch (SQLException ex) {}
		
		lblK8 = new JLabel("内贸运输费用K08");
		lblK8.setForeground(Color.BLACK);
		panel_coffi.add(lblK8, "cell 3 3,alignx left");
		
		textFK8 = new JTextField();
		textFK8.setEditable(false);
		panel_coffi.add(textFK8, "cell 4 3,alignx left");
		textFK8.setColumns(15);
		CoefficientCalculate k8 = new CoefficientCalculate();
		try
		{
			textFK8.setText(k8.CoeValue("k08"));
		}
		catch (SQLException ex) {}
		
		lblK20 = new JLabel("FOB青岛整柜费用 K20");
		panel_coffi.add(lblK20, "cell 3 4,alignx left");
		
		textFK20 = new JTextField();
		textFK20.setEditable(false);
		panel_coffi.add(textFK20, "cell 4 4,alignx left");
		textFK20.setColumns(15);
		CoefficientCalculate k20 = new CoefficientCalculate();
		try
		{
			textFK20.setText(k20.CoeValue("k20"));
		}
		catch (SQLException ex) {}
		
		lblK21 = new JLabel("FOB青岛整柜体积 K21");
		panel_coffi.add(lblK21, "cell 3 5,alignx left");
		
		textFK21 = new JTextField();
		textFK21.setEditable(false);
		panel_coffi.add(textFK21, "cell 4 5,alignx left");
		textFK20.setColumns(15);
		CoefficientCalculate k21 = new CoefficientCalculate();
		try
		{
			textFK21.setText(k21.CoeValue("k21"));
		}
		catch (SQLException ex) {}
		
		lblK10 = new JLabel("国内增值税率K10");
		panel_coffi.add(lblK10, "cell 6 1,alignx left");
		
		textFK10 = new JTextField();
		textFK10.setEditable(false);
		panel_coffi.add(textFK10, "cell 7 1,alignx left");
		textFK10.setColumns(15);
		CoefficientCalculate k10 = new CoefficientCalculate();
		try
		{
			textFK10.setText(k10.CoeValue("k10"));
		}
		catch (SQLException ex) {}
		
		lblK11 = new JLabel("国内账期(天数)K11");
		panel_coffi.add(lblK11, "cell 6 2,alignx left");
		
		textFK11 = new JTextField();
		textFK11.setEditable(false);
		panel_coffi.add(textFK11, "cell 7 2,alignx left");
		textFK11.setColumns(15);
		CoefficientCalculate k11 = new CoefficientCalculate();
		try
		{
			textFK11.setText(k11.CoeValue("k11"));
		}
		catch (SQLException ex) {}
		
		lblK22 = new JLabel("国外账期(天数)K22");
		panel_coffi.add(lblK22, "cell 9 2,alignx left");
		textFK22 = new JTextField();
		textFK22.setEditable(false);
		panel_coffi.add(textFK22, "cell 10 2,alignx left");
		textFK22.setColumns(15);
		CoefficientCalculate k22 = new CoefficientCalculate();
		try
		{
			textFK22.setText(k22.CoeValue("k22"));
		}
		catch (SQLException ex) {}
		
		lblK12 = new JLabel("预算汇率美元/人民币 K12");
		panel_coffi.add(lblK12, "cell 6 3,alignx left");
		
		textFK12 = new JTextField();
		textFK12.setEditable(false);
		panel_coffi.add(textFK12, "cell 7 3,alignx left");
		textFK12.setColumns(15);
		CoefficientCalculate k12 = new CoefficientCalculate();
		try
		{
			textFK12.setText(k12.exchangerate());
		}
		catch (SQLException ex) {}
				
		lblK13 = new JLabel("货款年利率K13");
		panel_coffi.add(lblK13, "cell 6 4,alignx left");
		
		textFK13 = new JTextField();
		textFK13.setEditable(false);
		panel_coffi.add(textFK13, "cell 7 4,alignx left");
		textFK13.setColumns(15);
		CoefficientCalculate k13 = new CoefficientCalculate();
		try
		{
			textFK13.setText(k13.CoeValue("k13"));
		}
		catch (SQLException ex) {}	
		
		lblK14 = new JLabel("直接人工-清洗K14");
		panel_coffi.add(lblK14, "cell 0 5,alignx left");
		
		textFK14 = new JTextField();
		textFK14.setEditable(false);
		panel_coffi.add(textFK14, "cell 1 5,alignx left");
		textFK14.setColumns(20);
		CoefficientCalculate k14 = new CoefficientCalculate();
		try
		{
			textFK14.setText(k14.CoeValue("k14"));
		}
		catch (SQLException ex) {}	
		
		lblK141 = new JLabel("制造费用-清洗K141");
		panel_coffi.add(lblK141, "cell 3 6,alignx left");		

		textFK141 = new JTextField();
		textFK141.setEditable(false);
		panel_coffi.add(textFK141, "cell 4 6,alignx left");
		textFK141.setColumns(20);
		CoefficientCalculate k141 = new CoefficientCalculate();
		try
		{
			textFK141.setText(k141.CoeValue("k141"));
		}		
		catch (SQLException ex) {}	
				
		lblK15 = new JLabel("管理费用-土地摊销K15");
		panel_coffi.add(lblK15, "cell 0 6,alignx left");		
		textFK15 = new JTextField();
		textFK15.setEditable(false);
		panel_coffi.add(textFK15, "cell 1 6,alignx left");
		textFK15.setColumns(20);
		CoefficientCalculate k15 = new CoefficientCalculate();
		try
		{
			textFK15.setText(k15.CoeValue("k15"));
		}
		catch (SQLException ex) {}	
		
		lblK16 = new JLabel("新开模具费用K16");
		panel_coffi.add(lblK16, "cell 6 5,alignx left");
		
		textFK16 = new JTextField();
		panel_coffi.add(textFK16, "cell 7 5,alignx left");
		textFK16.setColumns(20);
	 	textFK16.setText(String.valueOf(0));
		
		lblK17 = new JLabel("新模产品预测销量 K17");
		panel_coffi.add(lblK17, "cell 6 6,alignx left");
		
		textFK17 = new JTextField();
		panel_coffi.add(textFK17, "cell 7 6,alignx left");
		textFK17.setColumns(20);
		textFK17.setText(String.valueOf(2000));
		
		lblK18 = new JLabel("制造费用_其他成本K18");
		panel_coffi.add(lblK18, "cell 9 3,alignx left");
		textFK18 = new JTextField();
		textFK18.setEditable(false);
		panel_coffi.add(textFK18, "cell 10 3,alignx left");
		textFK18.setColumns(20);
		CoefficientCalculate k18= new CoefficientCalculate();
		try
		{
			textFK18.setText(k18.CoeValue("k18"));
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
		
		texFmodel.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				try 
				{
					String	sqlQuery = " ;select '0' as selected,a.fnumber,a.fmodel,a.fname,a.f_161,a.fsize"
            			+ " from t_icitem a join icbom b on a.fitemid = b.fitemid "
            			+ " and b.fstatus = 1 and b.fusestatus = 1072 and a.fnumber like '01.%' "
            			+ " and a.fmodel  like '%"+texFmodel.getText()+"%' "
            			+ " and a.fnumber like '%"+texFnum.getText()+"%' "
            			+ " order by a.fnumber,a.fmodel";
					ResultSet rs_code = conn.query("",sqlQuery);
					ResultSetMetaData 	rsmd_code 	= rs_code.getMetaData();
					int 	numberOfColumns 	= rsmd_code.getColumnCount();
					DefaultTableModel Model_code = (DefaultTableModel) tableQueResult.getModel(); 
					for (int z = Model_code.getRowCount();z>0 ;z--) 
					{
						Model_code.removeRow(z -1);
					}
					int i = 0; 
					while (rs_code.next())
					{   
						Model_code.addRow(new Object[] {null,null,null,null});
						Model_code.setValueAt(Boolean.getBoolean(rs_code.getString(1)), i, 0);   	    	 
						for(int j=1;j<numberOfColumns-1;j++) 
						{
							Model_code.setValueAt(rs_code.getString(j+1), i, j);
						} 
						Model_code.setValueAt(rs_code.getDouble(numberOfColumns), i, numberOfColumns-1);
						i++;
        	        }
					rs_code.close();
					lblComments.setText("有 "+String.valueOf(i)+" 个物料符合条件");	
				}
				catch(SQLException e1) {}
			}
		});
		texFnum.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					String	sqlQuery = " ;select '0' as selected,a.fnumber,a.fmodel,a.fname,a.f_161,a.fsize"
							+ " from t_icitem a join icbom b on a.fitemid = b.fitemid "
							+ " and b.fstatus = 1 and b.fusestatus = 1072 and a.fnumber like '01.%' "
							+ " and a.fmodel  like '%"+texFmodel.getText()+"%' "
							+ " and a.fnumber like '%"+texFnum.getText()+"%' "
							+ " order by a.fnumber,a.fmodel";
					ResultSet rs_code = conn.query("",sqlQuery);
					ResultSetMetaData 	rsmd_code 	= rs_code.getMetaData();
					int 	numberOfColumns 	= rsmd_code.getColumnCount();
					DefaultTableModel Model_code = (DefaultTableModel) tableQueResult.getModel(); 
					for (int z = Model_code.getRowCount();z>0 ;z--) 
					{
						Model_code.removeRow(z -1);
					}
					int i = 0; 
					while (rs_code.next())
					{   
						Model_code.addRow(new Object[] {null,null,null,null});
						Model_code.setValueAt(Boolean.getBoolean(rs_code.getString(1)), i, 0);   	    	 
						for(int j=1;j<numberOfColumns-1;j++) 
						{
							Model_code.setValueAt(rs_code.getString(j+1), i, j);
						}
						Model_code.setValueAt(rs_code.getDouble(numberOfColumns), i, numberOfColumns-1);
						i++;
					}
					rs_code.close();
					lblComments.setText("有 "+String.valueOf(i)+" 个物料符合条件");	
				}
				catch(SQLException e1) {} 
			}
		});
		
		btnQuery = new JButton("查找");
		panel_code_fnum.add(btnQuery, "cell 6 1");
		btnQuery.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					String	sqlQuery = ";select '0' as selected,a.fnumber,a.fmodel,a.fname,a.f_161,a.fsize"
            			+ " from t_icitem a join icbom b on a.fitemid = b.fitemid "
            			+ " and b.fstatus = 1 and b.fusestatus = 1072 and a.fnumber like '01.%' "
            			+ " and a.fmodel like '%"+texFmodel.getText()+"%' "
            			+ " and a.fnumber like '%"+texFnum.getText()+"%' "
            			+ " order by a.fnumber,a.fmodel";
					ResultSet rs_code = conn.query("",sqlQuery);
					ResultSetMetaData 	rsmd_code 	= rs_code.getMetaData();
					int 	numberOfColumns 	= rsmd_code.getColumnCount();
					DefaultTableModel Model_code = (DefaultTableModel) tableQueResult.getModel(); 
					for (int z = Model_code.getRowCount();z>0 ;z--) 
					{
						Model_code.removeRow(z -1);
					}
					int i = 0; 
					while (rs_code.next())
					{   
						Model_code.addRow(new Object[] {null,null,null,null});
						Model_code.setValueAt(Boolean.getBoolean(rs_code.getString(1)), i, 0);   	    	 
						for(int j=1;j<numberOfColumns-1;j++) 
						{
							Model_code.setValueAt(rs_code.getString(j+1), i, j);
						}
						Model_code.setValueAt(rs_code.getDouble(numberOfColumns), i,numberOfColumns-1); 
						i++;
					}
					rs_code.close();
					lblComments.setText("有 "+String.valueOf(i)+" 个物料符合条件");	
				}
				catch(SQLException e1) {} 
			}
		});
		generate activeGen = new generate();
		btnGenerate   = new JButton("生成报价");
		btnGenerate .setForeground(Color.BLUE);
		btnGenerate .setBackground(new Color(0, 255, 0));
		panel_code_fnum.add(btnGenerate , "cell 7 1,alignx right");		
		btnGenerate.addActionListener(activeGen );		
		
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
		btnR2Save.setVisible(false);
		btnR2Save.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					udateStandardCostReport.udateStandardCostReport(
							currentyear,firstitemid,finterid
							, Double.parseDouble(tableReport.getValueAt(0,2).toString()) 			/*一.生产成本合计（1+2+3）*/
							, Double.parseDouble(tableReport.getValueAt(1,2).toString()) 		/*1.直接材料成本*/
							, Double.parseDouble(tableReport.getValueAt(2,2).toString())		/*2.直接人工*/
							, Double.parseDouble(tableCalculate.getValueAt(tableCalculate.getRowCount() 
									- 1,7).toString())											/*2.1直接人工-工资*/
							, Double.parseDouble(tableCalculate.getValueAt(tableCalculate.getRowCount() 
									- 1,8).toString())											/*2.2直接人工-保险*/
							, Double.parseDouble(tableReport.getValueAt(3,2).toString())		/*3.制造费用（合计）*/
							, Double.parseDouble(tableReport.getValueAt(4,2).toString())		/*3.1制造费用-间接人工 */	
							, Double.parseDouble(tableReport.getValueAt(5,2).toString())		/*3.2制造费用-电费*/ 
							, Double.parseDouble(tableReport.getValueAt(6,2).toString())		/*3.3制造费用-辅料*/
							, Double.parseDouble(tableReport.getValueAt(7,2).toString())		/*3.4制造费用-设备折旧 */
							, Double.parseDouble(tableReport.getValueAt(8,2).toString())		/*3.5制造费用-工装模具 */
							, Double.parseDouble(tableReport.getValueAt(9,2).toString())		/*3.6制造费用-其他成本 */
							, Double.parseDouble(tableReport.getValueAt(10,2).toString())		/*3.7厂房折旧  */
							, Double.parseDouble(tableReport.getValueAt(11,2).toString())		/*二.期间费用合计（内贸）*/
							, Double.parseDouble(tableReport.getValueAt(11,5).toString())		/*二.期间费用合计（FOB青岛）*/
							, Double.parseDouble(tableReport.getValueAt(12,2).toString())		/*成品不良成本 */
							, Double.parseDouble(tableReport.getValueAt(13,2).toString())		/*财务费用 */
							, Double.parseDouble(tableReport.getValueAt(14,2).toString())		/*销售费用 */
							, Double.parseDouble(tableReport.getValueAt(15,2).toString())		/*管理费用 */
							, Double.parseDouble(tableReport.getValueAt(16,2).toString())		/*管理费用--土地摊销 */
							, Double.parseDouble(tableReport.getValueAt(17,2).toString())		/*新开模具工装费用 */
							, itselfQtySaled													/*产品自身历史销售数量 */	
							, modelQtySaled														/*产品型号历史销售数量 */
							, 0.0																/*新开模具摊销数量 */	
							, Double.parseDouble(tableReport.getValueAt(18,2).toString())		/*运输费用 （内贸） */
							, Double.parseDouble(tableReport.getValueAt(18,5).toString())		/*运输费用（FOB青岛） */
							, Double.parseDouble(tableReport.getValueAt(0,2).toString())		/*标准成本=生产成本合计+期间费用合计（内贸)-运输费用 （内贸）*/
								+Double.parseDouble(tableReport.getValueAt(11,2).toString())
								-Double.parseDouble(tableReport.getValueAt(18,2).toString())
							, Double.parseDouble(tableReport.getValueAt(0,5).toString())		/*标准成本=生产成本合计+期间费用合计（FOB青岛)-运输费用（FOB青岛）*/	
								+Double.parseDouble(tableReport.getValueAt(11,5).toString())
								-Double.parseDouble(tableReport.getValueAt(18,5).toString())
							, Double.parseDouble(tableReport.getValueAt(19,2).toString())		/*产品利润（内贸） */
							, Double.parseDouble(tableReport.getValueAt(19,5).toString())		/*产品利润（FOB青岛） */
							, Double.parseDouble(tableReport.getValueAt(20,2).toString())		/*核价（内贸）*/	
							, Double.parseDouble(tableReport.getValueAt(20,5).toString())		/*核价（FOB青岛）RMB*/		
							, Double.parseDouble(tableReport.getValueAt(21,2).toString())		/*增值税*/
							, Double.parseDouble(tableReport.getValueAt(22,2).toString())		/*出厂核价 含税  RMB Days 30*/
							, Double.parseDouble(tableReport.getValueAt(22,5).toString())		/*出厂核价 含税  USD Days 60 FOB青岛*/	
							, Double.parseDouble(textFK0.getText().toString())					/*电费价格 */
							, Double.parseDouble(textFK1.getText().toString())					/*直接人工-保险公积金补助福利系数 */	
							, Double.parseDouble(textFK2.getText().toString())					/*制造费用-间接人工系数 */	
							, Double.parseDouble(textFK4.getText().toString())					/*产品不良系数 */
							, Double.parseDouble(textFK5.getText().toString())					/*产品不良系数 */	
							, Double.parseDouble(textFK6.getText().toString())					/*财务费用系数 */	
							, Double.parseDouble(textFK7.getText().toString())					/*管理费用系数 */
							, gainrate															/*产品利润率 */
							, Double.parseDouble(textFK10.getText().toString())					/*销售费用系数 */	
							, Double.parseDouble(textFK11.getText().toString())					/*国内增值税率 */
							, Double.parseDouble(textFK22.getText().toString())					/*国内账期 */
							, Double.parseDouble(textFK12.getText().toString())					/*国外账期 */
							, Double.parseDouble(textFK13.getText().toString())					/*预算汇率 */
							, Double.parseDouble(textFK14.getText().toString())					/*贷款利率 */
							+Double.parseDouble(textFK141.getText().toString())
							);
					delStandardCostReport.delHistPrice(firstitemid, finterid);
					updateProductStdCost.updateProductStdCost(
							firstitemid
							, Double.parseDouble(tableReport.getValueAt(0,5).toString())
							+Double.parseDouble(tableReport.getValueAt(11,5).toString())
							-Double.parseDouble(tableReport.getValueAt(18,5).toString())
							);
					cleanBom.cleanBom(firstitemid, finterid);;
					updateCompanyPricePolicy.updateCompanyPricePolicy(firstitemid
							,Double.parseDouble(tableReport.getValueAt(22,2).toString())	
							,Double.parseDouble(tableReport.getValueAt(22,5).toString())	
							);
					lblstatus.setText(" 标准成本保存成功！客户价格体系更新完成。");
				} 
				catch (SQLException e1) 
				{
					e1.printStackTrace();
				}
			}
		});
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
		lblNewModel = new JLabel("新开模具费用");
		panel_bottom.add(lblNewModel,"cell 0 1,alignx right");
		txtNewModel = new JTextField();
		txtNewModel.setColumns(15);
		panel_bottom.add(txtNewModel,"cell 1 1");		
		lblNewModelSaleQty = new JLabel("新开模具预测销量");
		panel_bottom.add(lblNewModelSaleQty,"cell 2 1 ,alignx right");
		txtNewModelSalQty = new JTextField();
		txtNewModelSalQty.setColumns(15);
		panel_bottom.add(txtNewModelSalQty,"cell 3 1");
		lblHistSalQty= new JLabel("历史销售数量");
		panel_bottom.add(lblHistSalQty,"cell 4 1,alignx right");
		txtHistSalQty = new JTextField();
		txtHistSalQty.setColumns(15);
		panel_bottom.add(txtHistSalQty,"cell 5 1");
					
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
					"固定资产内码","固定资产编号","芯体长度（米）","芯体宽度（米）"
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
					,"芯体长度","芯体宽度"
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
					,"最大使用量(kg)","模具费用","芯体长度","芯体宽度"
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
	/*
	 * event listener
	 */
	class generate implements ActionListener  
	{
		public void actionPerformed(ActionEvent event) 
		{
			double powerprice = Double.parseDouble(textFK0.getText());
			tableData data = new tableData();
			TableFormat formReport = new TableFormat();
			String sqlMaterial,sqlLabourAndMake,sqlPriceRPTform,sqlEnergy,sqlAdi,sqlModel,sqlBOM;
			StateSQl mysqlstate=new StateSQl();	
			
		    // items count
		    int k = 0;
		    int sumFails = 0;
		    try 
		    {
		    	currentyear=getCurrentYear.getCurrentYear();
		    	currentperiod=getCurrentMonth.getCurrentMonth();
		    	createTableRPT.createTableRPT();
		    	createTableBOM.createTableBOM();
		    	createTableMaterial.createTableMaterial();
		    	crteTabLabourAndMake.crteTabLabourAndMake();
		    	updateAdiMaterialPrice.updateAdiMatrialPrice();
		    	updateMachineInfo.updateMachineInfo();
			
		    	if (auto.equals("auto")) 
		    	{
		    		String	sqlauto = ";select a.fnumber"
		    			+ " from t_icitem a join icbom b on a.fitemid = b.fitemid  "
            			+ " and b.fstatus = 1 and b.fusestatus = 1072 and a.fnumber like '01.%' "
            			+ " order by a.fnumber";
		    	
		    		ResultSet rs_auto = conn.query("",sqlauto);
		    		////System.out.println("auto "+sqlauto);

		    		if(rs_auto.next()) 
		    		{
		    			rs_auto.last();
		    			k = rs_auto.getRow();
		    			//System.out.println("批量计算成本,产品数量"+k);
		    			rs_auto.beforeFirst();
		    			fnumbers = new String[k];
		    			for (int l=0; l<k;l++ ) 
		    			{
		    				rs_auto.next();
		    				fnumbers[l] = rs_auto.getString(1); 
		    				//System.out.println("auto  array:"+l+"value:"+fnumbers[l]);
		    			}
		    		}
		    	}
		    	else 
		    	{
		    		for (int i = 0; i<tableQueResult.getRowCount();i++ ) 
		    		{
		    			if ((Boolean)tableQueResult.getValueAt(i,0)) 
		    			{
		    				k++;
		    			} 
		    		}
		    		fnumbers = new String[k]; 
		    		int y=0;
		    		for (int l = 0; l<tableQueResult.getRowCount();l++ ) 
		    		{
		    			if ((Boolean)tableQueResult.getValueAt(l,0)) 
		    			{
		    				fnumbers[y] = String.valueOf(tableQueResult.getValueAt(l,1));
		    				y++;
		    			} 
		    		} 
		    	}
		    	System.out.println("开始计算标准成本 ,"+df.format(new Date()).toString()+",");
		    	//lblstatus.setText("计算开始，请等待………………");
		    	// multiple items
		    	if (k>1) 
		    	{
		    		for (int x=0;x<fnumbers.length;x++)
		    		{
		    			fnumber = fnumbers[x];
		    			firstitemid =getFirstItemID.getFirstItemID(fnumber) ;
		    			finterid = getFinterID.getFinterID(firstitemid);
		    			gainrate= getGainRate.getGainRate(fnumber);
		    			itemname=getItemName.getItemName(fnumber);
		    			model=getModel.getModel(fnumber);
		    			packagesize=getPackageSize.getPackageSize(fnumber);
		    			itselfQtySaled=getItselfQtySaled.getItselfQtySaled(firstitemid);
		    			modelQtySaled=getModelQtySaled.getModelQtySaled(model);
		    			cleanBom.cleanBom(firstitemid, finterid);		
		    			bomExpose.bomExpose(firstitemid, finterid);	
		    			bomVerifyError=verifyBOM.verifyBOM(firstitemid, finterid);	
		    			RoutVerifyErr=verifyRout.verifyRout(firstitemid, finterid);
		    			
		    			length=lengthWidHgt.Len(firstitemid, finterid);	
		    			width=lengthWidHgt.Wid(firstitemid, finterid);	
		    			height=lengthWidHgt.Hgt(firstitemid, finterid);	
		    			RoutLWHVerifyErr=verifyRoutLWH.verifyRoutLWH(firstitemid, finterid, length, width, height);
		    			capacity=getCapacityQianHanLu.getCapacityQianHanLu(length*1000,height*1000);	
		    			costMateiral.costMateiral( firstitemid, finterid,currentyear,currentperiod);
		    			materialVerifyError=verifyMaterialPrice.verifyMaterialPrice(firstitemid, finterid);
		    			adiVerifyError=verifyAdiMaterialPrice.verifyAdiPrice(firstitemid, finterid);
		    		
		    			if (textFK12.getText().equals(String.valueOf(0.0))) 
		    			{
		    				++sumFails ;
				    		cleanBom.cleanBom(finterid,finterid);
				    		System.out.println(" 没有有效的预算汇率,," );				    		
			    		}
		    			else if (firstitemid == 0) 
		    			{
				    		++sumFails ;
				    		System.out.println("产品itemid 不存在, "+df.format(new Date()).toString() +" ," +fnumber);
				    	}
		    			else if (bomVerifyError > 0) 
		    			{
				    		++sumFails ;
				    		cleanBom.cleanBom(finterid,finterid);
				    		System.out.println("bom 不完整," +df.format(new Date()).toString() +" ,"+fnumber);
				    	}
		    			else if (RoutVerifyErr > 0) 
		    			{
				    		++sumFails ;
				    		cleanBom.cleanBom(finterid,finterid);
				    		System.out.println("工艺路线 不完整," +df.format(new Date()).toString() +" ,"+fnumber);
				    	}
				    	else if (RoutLWHVerifyErr > 0) 
				    	{
				    		++sumFails ;
				    		cleanBom.cleanBom(finterid,finterid);
				    		System.out.println("芯体数据不完整 长:"+length+" 宽:"+width
				    				+" 高:"+height+" ," +df.format(new Date()).toString() +" ,"+fnumber);
				    	}
				    	else if (materialVerifyError > 0) 
				    	{
				    		++sumFails ;
				    		System.out.println("直接材料价格 不完整,"+df.format(new Date()).toString()  +" ,"+fnumber);
				    	}
				    	else if (adiVerifyError > 0) 
				    	{
				    		++sumFails ;
				    		cleanBom.cleanBom(finterid,finterid);
				    		System.out.println("辅料价格 不完整," +df.format(new Date()).toString() +" ,"+fnumber );
				    	}
				    	else 
				    	{	
				    		//System.out.println("2.直接成本计算 成功，下一步，计算人工与制造费用");
				    		costLabourAndMake.costLabourAndMake(Double.parseDouble(
				    				textFK1.getText()), Double.parseDouble(textFK2.getText())
				    				, Double.parseDouble(textFK3.getText()), Double.parseDouble(textFK0.getText())
				    				,firstitemid,finterid,currentyear,currentperiod
				    				,length,capacity);
				    		//System.out.println("3.制造费用与直接人工 成功，下一步，生成报价");
				    		sqlMaterial = mysqlstate.getSQLStatement("sqlMaterial"
				    				,currentyear,currentperiod,firstitemid,finterid
				    				,capacity,length,width,powerprice );
				    		sqlLabourAndMake = mysqlstate.getSQLStatement(
				    				"sqlLabourAndMake",currentyear,currentperiod
				    				,firstitemid,finterid,capacity,length,width
				    				,powerprice);
				    		//System.out.println("人工和制造费用:"+sqlLabourAndMake);			
				    		sqlPriceRPTform =mysqlstate.getSQLStatement(
				    				"sqlPriceRPTform",currentyear,currentperiod
				    				,firstitemid,finterid,capacity,length
				    				,width,powerprice);
				    		data.myTableModel(tableMaterial,sqlMaterial,new int[]{5,7});
				    		data.myTableModel(tableCalculate,sqlLabourAndMake
				    				,new int[]{7,8,9,10,11,12,13});
				    		data.myTableModel(tableReport,sqlPriceRPTform,new int[]{});

				    		int a = tableReport.getColumnCount();
				    		if (a == 7) 
				    		{
				    			formReport.ColumnFilter(tableReport,0);
				    		}
				    		tableReport.setRowHeight(24);
				    		rptvalue(tableReport);				    
				    		btnR2Save.doClick();
				    		System.out.println("结束第 "+(x+1)+"/"+k
				    				+" 个产品计算,"+df.format(new Date()).toString()
				    				+","+fnumber);
				    	}
		    			lblstatus.setText("^^^^^^^"+k +" 个产品的标准成本报价已经生成，请到ERP查看！");
		    		}					    		/* end for*/
		    		System.out.println( "全部计算结束, "+df.format(new Date()).toString()+","+k
		    				+"个产品 "+ sumFails+"个不满足计算条件");
		    	} 		    	/*end multiple*/
/* 
 * start single 
 * */
		    	else 
		    	{
		    		fnumber = fnumbers[0];		    
		    		firstitemid =getFirstItemID.getFirstItemID(fnumber) ;
		    		finterid = getFinterID.getFinterID(firstitemid);
		    		gainrate= getGainRate.getGainRate(fnumber);
		    		itemname=getItemName.getItemName(fnumber);
		    		model=getModel.getModel(fnumber);
		    		packagesize=getPackageSize.getPackageSize(fnumber);
		    		itselfQtySaled=getItselfQtySaled.getItselfQtySaled(firstitemid);
		    		cleanBom.cleanBom(firstitemid, finterid);		
		    		bomExpose.bomExpose(firstitemid, finterid);	
		    		bomVerifyError=verifyBOM.verifyBOM(firstitemid, finterid);	
		    		RoutVerifyErr=verifyRout.verifyRout(firstitemid, finterid);
		    		
		    		length=lengthWidHgt.Len(firstitemid, finterid);	
		    		width=lengthWidHgt.Wid(firstitemid, finterid);	
		    		height=lengthWidHgt.Hgt(firstitemid, finterid);	
		    		RoutLWHVerifyErr=verifyRoutLWH.verifyRoutLWH(firstitemid, finterid, length, width, height);
		    		capacity=getCapacityQianHanLu.getCapacityQianHanLu(length*1000,height*1000);	
		    		//System.out.println("1.取得参数 成功，下一步，计算材料成本，显示BOM和直接材料清单");
		    		costMateiral.costMateiral( firstitemid, finterid,currentyear,currentperiod);
		    		materialVerifyError=verifyMaterialPrice.verifyMaterialPrice(firstitemid, finterid);
		    		//System.out.println("验证直接材料价格是否完整");
		    		adiVerifyError=verifyAdiMaterialPrice.verifyAdiPrice(firstitemid, finterid);
		    		//System.out.println("验证辅料价格是否完整");   	    
		    		sqlBOM =mysqlstate.getSQLStatement("sqlBOM",currentyear,currentperiod,firstitemid,finterid,capacity,length,width,powerprice);
		    		//System.out.println("BOM："+sqlBOM );
		    		sqlMaterial = mysqlstate.getSQLStatement("sqlMaterial",currentyear,currentperiod,firstitemid,finterid,capacity,length,width,powerprice );
		    		//System.out.println("直接材料成本："+sqlMaterial);
		    		sqlEnergy=mysqlstate.getSQLStatement("sqlEnergy",currentyear,currentperiod,firstitemid,finterid,capacity,length,width,powerprice);
		    		//System.out.println("电费与折旧明细: "+sqlEnergy);
		    		sqlAdi =mysqlstate.getSQLStatement("sqlAdi",currentyear,currentperiod,firstitemid,finterid,capacity,length,width,powerprice);
		    		//System.out.println("辅料明细: "+sqlAdi);
		    		sqlModel =mysqlstate.getSQLStatement("sqlModel",currentyear,currentperiod,firstitemid,finterid,capacity,length,width,powerprice);
		    		//System.out.println("工装模具明细: "+sqlModel);
		    		sqlLabourAndMake = mysqlstate.getSQLStatement("sqlLabourAndMake",currentyear,currentperiod,firstitemid,finterid,capacity,length,width,powerprice);
		    		//System.out.println("人工与制造费用: "+sqlLabourAndMake );
		    		sqlPriceRPTform =mysqlstate.getSQLStatement("sqlPriceRPTform",currentyear,currentperiod,firstitemid,finterid,capacity,length,width,powerprice);
			
		    		data.myTableModel(tableBOM,sqlBOM,new int[]{});
		    		data.myTableModel(tableMaterial,sqlMaterial,new int[]{5,7});
		    		//System.out.println("2.直接材料成本计算 成功，下一步，验证计算条件");
		    		if (textFK12.getText().equals(String.valueOf(0.0))) 
		    		{
		    			cleanBom.cleanBom(finterid,finterid);
			    		lblstatus.setText(" 没有有效的预算汇率！");
			    		JOptionPane.showMessageDialog(frame, " 没有有效的预算汇率！");
			    		System.out.println(" 没有有效的预算汇率,," );
		    		}								
			    	else if (firstitemid == 0)	
			    	{
			    		lblstatus.setText(" 产品 " +fnumber +"itemid  不存在！");
			    		JOptionPane.showMessageDialog(frame, " 产品 " +fnumber +"itemid  不存在！");
			    		System.out.println(" 产品 " +fnumber +" itemid 不存在！" );
			    	}
			    	else if (bomVerifyError > 0)
			    	{
			    		cleanBom.cleanBom(finterid,finterid);
			    		lblstatus.setText(" 产品 " +fnumber +" bom 不完整！");
			    		JOptionPane.showMessageDialog(frame, "BOM 不完整,请查看BOM");
			    		System.out.println("bom 不完整");
			    	}
			    	else if (RoutVerifyErr > 0) 
			    	{
			    		cleanBom.cleanBom(finterid,finterid);
			    		JOptionPane.showMessageDialog(frame, "工艺路线 不完整");
				    	System.out.println("工艺路线 不完整," +df.format(new Date()).toString() +" ,"+fnumber);
				    }
			    	else if (RoutLWHVerifyErr > 0) 
			    	{
			    		cleanBom.cleanBom(finterid,finterid);
			    		JOptionPane.showMessageDialog(frame, "芯体数据不完整 长:"+length
			    				+"宽:"+width+"高:"+height);
				    	System.out.println("芯体数据不完整 长:"+length+"宽:"+width
				    			+"高:"+height+" ," +df.format(new Date()).toString() 
				    			+" ,"+fnumber);
				    }
			    	else if (adiVerifyError > 0)
			    	{
			    		cleanBom.cleanBom(finterid,finterid);
			    		lblstatus.setText(" 产品 " +fnumber +" 辅料价格 不完整！");
			    		JOptionPane.showMessageDialog(frame, "有 "+ adiVerifyError 
			    				+" 种辅料在过去一年没有采购价格并且没有计划单价,"
			    				+ "请查看辅料明细");
			    		System.out.println("辅料价格 不完整");
			    	}
			    	else if (materialVerifyError  > 0)	
			    	{
			    		cleanBom.cleanBom(finterid,finterid);
			    		lblstatus.setText(" 产品 " +fnumber +" 直接材料 价格 不完整！");
			    		JOptionPane.showMessageDialog(frame, "有 直接材料 在过去一年没"
			    				+ "有采购价格并且没有计划单价,请查看直接材料成本明细");
			    		System.out.println("直接材料价格 不完整");
			    	}
			    	else 
			    	{
			    		//System.out.println("3.符合计算条件，下一步，计算人工与制造费用");
			    		costLabourAndMake.costLabourAndMake(
			    				Double.parseDouble(textFK1.getText())
			    				, Double.parseDouble(textFK2.getText())
			    				, Double.parseDouble(textFK3.getText())
			    				, Double.parseDouble(textFK0.getText())
			    				,firstitemid,finterid,currentyear,currentperiod
			    				,length,capacity);
			    		//System.out.println("4.制造费用与直接人工 成功，下一步，生成报价");			
			    		data.myTableModel(tableEnergy,sqlEnergy,new int[]{13,15});
			    		data.myTableModel(tableAdi,sqlAdi,new int[]{12});
			    		data.myTableModel(tableModel,sqlModel,new int[]{10});			
			    		data.myTableModel(tableCalculate,sqlLabourAndMake
			    				,new int[]{7,8,9,10,11,12,13});
			    		data.myTableModel(tableReport,sqlPriceRPTform,new int[]{});
				
			    		lbl_ProdName.setText(fnumber); 
			    		lbl_Model.setText(model);
			    		labItemname.setText(itemname);
			    		//lbl_OEMNo.setText(oem);
				
			    		formReport.ColAlignLeft(tableMaterial, new int[] {0,1,2});	
			    		formReport.ColAlignLeft(tableCalculate, new int[] {0,1,3});	
			    		formReport.ColAlignLeft(tableReport, new int[] {1,4,6});
			    		formReport.ColAlignLeft(tableBOM, new int[] {0,1,2,3,4,5,18});
			    		formReport.ColAlignLeft(tableEnergy, new int[] {0,1,2,3,5,9});	
			    		formReport.ColAlignLeft(tableAdi, new int[] {0,1,2,3,5,9});	
			    		formReport.ColAlignLeft(tableModel, new int[] {0,1,2,3,5,9});	
								
			    		int a = tableReport.getColumnCount();
			    		if (a == 7) 
			    		{
			    			formReport.ColumnFilter(tableReport,0);
			    		}
			    		tableReport.setRowHeight(24);
			    		rptvalue(tableReport);
			    		txtNewModel.setText(textFK16.getText());
			    		txtNewModelSalQty.setText(textFK17.getText());
			    		txtHistSalQty.setText(String.valueOf(itselfQtySaled));
			    		DevReqValue(firstitemid);
			    		btnR2Save.doClick();
			    
			    		btnR2Save.doClick();
			    		lblstatus.setText("^^^^^^^"+fnumber +" 的标准成本报价已经生成"
			    				+ "，请到报价单界面查看！");
			    		JOptionPane.showMessageDialog(frame,fnumber +" 的标准成本"
			    				+ "报价已经生成，请到报价单界面查看！");
			    	} 									/* end else */
		    		System.out.println("calculate finish,"+df.format(new Date()).toString()
		    				+",");
		    	}										/* end else1*/
		    } 
		    catch(SQLException e) {}
		}
	} 												/*  end class-generate*/
	/* 
	 * set tableReport value
	 */
	public void rptvalue(JTable tableReport) 
	{
		df0 = new DecimalFormat("######0");
		df2 = new DecimalFormat("######0.00%");
		df4 = new DecimalFormat("######0.00");
		/*
		 *  1.直接材料成本 
		 */
		tableReport.setValueAt(df4.format(Double.parseDouble(tableMaterial.getValueAt(tableMaterial.getRowCount() - 1,7).toString())),1,2);
		/*
		* 2.直接人工=工资 + 保险
		*/
		tableReport.setValueAt(df4.format(Double.parseDouble(tableCalculate.getValueAt(tableCalculate.getRowCount() - 1,7).toString()) 
					+ Double.parseDouble(tableCalculate.getValueAt(tableCalculate.getRowCount() - 1,8).toString())
					+ Double.parseDouble(textFK14.getText())  ),2,2);
			/*
		     * 3-1.间接人工
		     */
			tableReport.setValueAt(	df2.format(Double.parseDouble(textFK2.getText())),4,3);
			tableReport.setValueAt(	df4.format(Double.parseDouble(tableCalculate.getValueAt(
					tableCalculate.getRowCount() - 1,9).toString())),4,2);
			/*
		     * 3-2.制造费用--电费
		     */
			tableReport.setValueAt(df4.format(Double.parseDouble(tableCalculate.getValueAt(
					tableCalculate.getRowCount() - 1,10).toString())),5,2);		   
			/*
		     * 3-3.制造费用--辅料费
		     */
			tableReport.setValueAt(df4.format(Double.parseDouble(tableCalculate.getValueAt(
					tableCalculate.getRowCount() - 1,12).toString())),6,2);				/*
		     * 3-4.制造费用--设备折旧费
		     */
			tableReport.setValueAt(df4.format(Double.parseDouble(tableCalculate.getValueAt(
					tableCalculate.getRowCount() - 1,11).toString())),7,2);				/*
		     * 3-5.制造费用--工装模具折旧费
		     */
			tableReport.setValueAt(df4.format(Double.parseDouble(tableCalculate.getValueAt(
					tableCalculate.getRowCount() - 1,13).toString())),8,2);			   
			/*
		     * 3-6.制造费用--其他成本
		     */
			tableReport.setValueAt(df4.format(Double.parseDouble(textFK18.getText())),9,3);
			tableReport.setValueAt(df4.format(Double.parseDouble(textFK18.getText())),9,2);
			/*
			 * 3-7.厂房折旧 = K3	
			 */			
			tableReport.setValueAt(df4.format(Double.parseDouble(textFK3.getText())),10,3);
			tableReport.setValueAt(df4.format(Double.parseDouble(textFK3.getText())),10,2);
			/*
		     * 3.制造费用合计
		     */
			tableReport.setValueAt(df4.format(Double.parseDouble(tableReport.getValueAt(4, 2).toString())
					+ Double.parseDouble(tableReport.getValueAt(5, 2).toString())
					+ Double.parseDouble(tableReport.getValueAt(6, 2).toString())
					+ Double.parseDouble(tableReport.getValueAt(7, 2).toString())
					+ Double.parseDouble(tableReport.getValueAt(8, 2).toString())
					+ Double.parseDouble(tableReport.getValueAt(9, 2).toString())
					+ Double.parseDouble(tableReport.getValueAt(10, 2).toString())),3,2);
			/*
			 * 一、生产成本合计=1.直接材料成本+2.直接人工+3.制造费用合计
			 */
			tableReport.setValueAt(df4.format(Double.parseDouble(tableReport.getValueAt(1, 2).toString())
			    	+Double.parseDouble(tableReport.getValueAt(2, 2).toString())
                    +Double.parseDouble(tableReport.getValueAt(3, 2).toString())),0,2);
			/*
			 * 成品不良成本
			 */
			tableReport.setValueAt(df2.format(Double.parseDouble(textFK4.getText())),12,3);/*成品不良成本k4*/
			tableReport.setValueAt(df4.format(Double.parseDouble(tableReport.getValueAt(0, 2).toString())
					*Double.parseDouble(textFK4.getText())),12,2);
			/*
			 * 财务费用
			 */
			tableReport.setValueAt(df2.format(Double.parseDouble(textFK5.getText())),13,3);
			tableReport.setValueAt(df4.format(Double.parseDouble(tableReport.getValueAt(0, 2).toString())
					*Double.parseDouble(textFK5.getText())),13,2);
			/*
			 * 销售费用
			 */
			tableReport.setValueAt(df2.format(Double.parseDouble(textFK7.getText())),14,3);
			tableReport.setValueAt(df4.format(Double.parseDouble(tableReport.getValueAt(0, 2).toString())
					*Double.parseDouble(textFK7.getText())),14,2);
			/*
			 * 管理费用
			 */
			tableReport.setValueAt(df2.format(Double.parseDouble(textFK6.getText())),15,3);
			tableReport.setValueAt(df4.format(Double.parseDouble(tableReport.getValueAt(0, 2).toString())
					*Double.parseDouble(textFK6.getText())),15,2);
			/*
			 * 管理费用--土地摊销
			 */
			tableReport.setValueAt(df4.format(Double.parseDouble(textFK15.getText())),16,3);
			tableReport.setValueAt(df4.format(Double.parseDouble(textFK15.getText())),16,2);
			/*
			 * 新开模具工装费用 
			 */
			tableReport.setValueAt(df4.format(Double.parseDouble(textFK16.getText())
					/Double.parseDouble(textFK17.getText())),17,2);
			/*
			 * 外贸项目
			 */
			int l;
			for (l=0;l<18;l++) {
				if (l==11) {}
				else {tableReport.setValueAt(tableReport.getValueAt(l, 2), l, 5);}
			}
			
			/*
			 * 运输费用
			 */
			tableReport.setValueAt(df4.format(Double.parseDouble(textFK8.getText())),18,2);
			/*
			 * FOB青岛费用
			 */
			tableReport.setValueAt(df4.format(Double.parseDouble(textFK20.getText())
					/Double.parseDouble(textFK21.getText())
					*packagesize),18,5);
			tableReport.setValueAt("FOB青岛费用="+Double.parseDouble(textFK20.getText())
			+"（整柜 BY FCL,40HQ/GP,45HQ/GP）/"+Double.parseDouble(textFK21.getText())
			+"立方米*单只产品体积 "+packagesize,18,6);
			/*
			 * 二、期间费用合计
			 */
			tableReport.setValueAt(df4.format(Double.parseDouble(tableReport.getValueAt(12, 2).toString())
					    	+Double.parseDouble(tableReport.getValueAt(13, 2).toString())
							+Double.parseDouble(tableReport.getValueAt(14, 2).toString())
							+Double.parseDouble(tableReport.getValueAt(15, 2).toString())
							+Double.parseDouble(tableReport.getValueAt(16, 2).toString())
							+Double.parseDouble(tableReport.getValueAt(17, 2).toString())
							+Double.parseDouble(tableReport.getValueAt(18, 2).toString())),11,2);
			tableReport.setValueAt(df4.format(Double.parseDouble(tableReport.getValueAt(12, 5).toString())
			    	+Double.parseDouble(tableReport.getValueAt(13, 5).toString())
					+Double.parseDouble(tableReport.getValueAt(14, 5).toString())
					+Double.parseDouble(tableReport.getValueAt(15, 5).toString())
					+Double.parseDouble(tableReport.getValueAt(16, 5).toString())
					+Double.parseDouble(tableReport.getValueAt(17, 5).toString())
					+Double.parseDouble(tableReport.getValueAt(18, 5).toString())),11,5);
			/*
			 * 三、产品利润=（期间成本+生产成本）×利润率/（1-利润率）
			 */
			tableReport.setValueAt(df2.format(gainrate),19,3);
			tableReport.setValueAt(df4.format((Double.parseDouble(tableReport.getValueAt(0, 2).toString())
					+Double.parseDouble(tableReport.getValueAt(11, 2).toString()))
					*gainrate
					/(1-gainrate)),19,2);
			tableReport.setValueAt(df4.format((Double.parseDouble(tableReport.getValueAt(0, 5).toString())
					+Double.parseDouble(tableReport.getValueAt(11, 5).toString()))
					*gainrate
					/(1-gainrate)),19,5);
			/*
			 * 四、核价=（一、生产成本合计 + 二、期间费用合计 + 三、产品利润）
			 */
			tableReport.setValueAt(df4.format(Double.parseDouble(tableReport.getValueAt(0, 2).toString())
					+Double.parseDouble(tableReport.getValueAt(11, 2).toString())
					+Double.parseDouble(tableReport.getValueAt(19, 2).toString())),20,2); 
			tableReport.setValueAt(df4.format(Double.parseDouble(tableReport.getValueAt(0, 5).toString())
					+Double.parseDouble(tableReport.getValueAt(11, 5).toString())
					+Double.parseDouble(tableReport.getValueAt(19, 5).toString())),20,5); 
			/*
			 * 五、增值税额
			 */
			tableReport.setValueAt(df2.format(Double.parseDouble(textFK10.getText())),21,3);
			tableReport.setValueAt(df4.format(Double.parseDouble(tableReport.getValueAt(20, 2).toString())
					*Double.parseDouble(textFK10.getText())),21,2); 
			/*
			 * 六、出厂核价_含税RMB=(四、核价+五、增值税额)+(四、核价+五、增值税额)*货款年利率/365*内贸账期
			 * 					=(四、核价+五、增值税额)*（1+货款年利率*内贸账期/365）					
			 */
			tableReport.setValueAt(df4.format((Double.parseDouble(tableReport.getValueAt(20, 2).toString())
					+Double.parseDouble(tableReport.getValueAt(21, 2).toString()))
					*(1+Double.parseDouble(textFK13.getText())
					*Double.parseDouble(textFK11.getText())/365)),22,2);
			tableReport.setValueAt("货款年利率： "+df2.format(Double.parseDouble(textFK13.getText()))
					+";账期： "+df0.format(Double.parseDouble(textFK11.getText()))+"天",22,3);
			/*
			 * 六、FOB青岛核价_USD=(四、核价/预算汇率）+（四、核价/预算汇率）*货款年利率/365*外贸账期
			 * 					=（四、核价/预算汇率）*(1+货款年利率*外贸账期/365)
			 */
		tableReport.setValueAt(df4.format(Double.parseDouble(tableReport.getValueAt(20, 5).toString())
					/Double.parseDouble(textFK12.getText())*(1+Double.parseDouble(textFK13.getText())
					*Double.parseDouble(textFK22.getText())/365)), 22, 5);
			
			//	tableReport.setValueAt("货款年利率： "+df2.format(Double.parseDouble(textFK13.getText())),19,5);
			tableReport.setValueAt("货款年利率： "+df2.format(Double.parseDouble(textFK13.getText()))
					+";账期： "+df0.format(Double.parseDouble(textFK11.getText()))+"天;"
					+"预算汇率： "+textFK12.getText(),22,6);
	}
	/* 
	 * product devolopment request 
	 * */
	public void DevReqValue(int firstitemid) throws SQLException {
    	StateSQl mysql=new StateSQl();	
    	String	sqlQuery = mysql.getSQLStatement("sqlProdDevRpt","","",firstitemid,0,0.0,0.0,0.0,0.0);
    	
    	ResultSet rs_code = conn.query("",sqlQuery);
		   txtDev0.setText("");    txtDev1.setText("");
	       txtDev2.setText("");    txtDev3.setText("");
	       txtDev4.setText("");    txtDev5.setText(""); 
	       txtDev6.setText("");    txtDev7.setText("");  
	       txtDev8.setText("");    txtDev9.setText("");  
	       txtDev10.setText("");    txtDev11.setText("");  
	       txtDev12.setText("");    txtDev13.setText("");  
	       txtDev14.setText("");    txtDev15.setText("");  
	       txtDev16.setText("");    txtDev17.setText("");  
	       txtDev18.setText("");    txtDev19.setText("");  
	       txtDev20.setText("");    txtDev21.setText("");  
	       txtDev22.setText("");    txtDev23.setText("");  
	       txtDev24.setText("");    txtDev25.setText("");  
	       txtDev26.setText("");    txtDev27.setText("");  
	       txtDev28.setText("");    txtDev29.setText("");  
	       txtDev30.setText("");    txtDev31.setText("");  
	       txtDev32.setText("");    textArea.setText("");  
		while (rs_code.next())
	    {  txtDev0.setText(rs_code.getString(1));    txtDev1.setText(rs_code.getString(2)); 
	       txtDev2.setText(rs_code.getString(3));    txtDev3.setText(rs_code.getString(4)); 
	       txtDev4.setText(rs_code.getString(5));    txtDev5.setText(rs_code.getString(6));  
	       txtDev6.setText(rs_code.getString(7));    txtDev7.setText(rs_code.getString(8));  
	       txtDev8.setText(rs_code.getString(9));    txtDev9.setText(rs_code.getString(10));  
	       txtDev10.setText(rs_code.getString(11));    txtDev11.setText(rs_code.getString(12));  
	       txtDev12.setText(rs_code.getString(13));    txtDev13.setText(rs_code.getString(14));  
	       txtDev14.setText(rs_code.getString(15));    txtDev15.setText(rs_code.getString(16));  
	       txtDev16.setText(rs_code.getString(17));    txtDev17.setText(rs_code.getString(18));  
	       txtDev18.setText(rs_code.getString(19));    txtDev19.setText(rs_code.getString(20));  
	       txtDev20.setText(rs_code.getString(21));    txtDev21.setText(rs_code.getString(22));  
	       txtDev22.setText(rs_code.getString(23));    txtDev23.setText(rs_code.getString(24));  
	       txtDev24.setText(rs_code.getString(25));    txtDev25.setText(rs_code.getString(26));  
	       txtDev26.setText(rs_code.getString(27));    txtDev27.setText(rs_code.getString(28));  
	       txtDev28.setText(rs_code.getString(29));    txtDev29.setText(rs_code.getString(30));  
	       txtDev30.setText(rs_code.getString(31));    txtDev31.setText(rs_code.getString(32));  
	       txtDev32.setText(rs_code.getString(33));    textArea.setText(rs_code.getString(34));   
	    }
		rs_code.close();  
	        }  
		
		/*DevReqValue end*/

	
	
	
}