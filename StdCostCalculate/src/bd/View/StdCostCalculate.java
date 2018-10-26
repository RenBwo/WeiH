package bd.View;
/*
 * 
 * 20171028 
 * 取消过冷式储液器
 * 纠正扁管切断产能公式
 * 同一固定资产编号的设备卡片取最近日期
 */
import java.awt.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
//import java.util.Timer;

import bd.DAO.*;

public class StdCostCalculate 
{
	public 		static MainFrame mainFrame=new MainFrame();
	public 		static Coefficient coefficient=new Coefficient();
	private 	static VersionCompare verCompare=new VersionCompare();
	private 	static CurrentPeriod  currentPeriod=new CurrentPeriod();
    private  	static ActListenerBtnGenerate actListenerGenerate=new ActListenerBtnGenerate(); 
    private  	static ActListenerBtnSave actListenerBtnSave=new ActListenerBtnSave(); 
    private  	static ActListenerBtnQuery actListenerBtnQuery=new  ActListenerBtnQuery();
	private  	static ActListenserSelected actListenserSelected=new ActListenserSelected();
	private  	static ActListenerQueryByFmodel actListenerQueryByFmodel=new ActListenerQueryByFmodel();
    private  	static ActListenerQueryByFnumber actListenerQueryByFnumber=new ActListenerQueryByFnumber();
    private     static EnvForAuto envForAuto = new EnvForAuto();
    public 		static String programName="standCostCal",localVer="1.0.1.8"
    		,autoFlag;
    public 		static int versionOK,autoRun;
    private 	static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
//	private 	Timer timer=new Timer();
	
	  
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		mainFrame.lblVersionInfo.setText("   版本: "+localVer);
		autoFlag=dateFormat.format(new Date()).toString();
		if (args.length>0 && args[0].equals("auto"))
		{
			autoRun=1;
		}
		else
		{
			autoRun=0;
		}
		
		//System.out.println(autoFlag);
		verCompare.createTableVersionControl();
		verCompare.get();
		versionOK=verCompare.versionCompare();	
		
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					currentPeriod.get();
					mainFrame.textFK0.setText(String.valueOf(new DecimalFormat("######0.0000").format(Coefficient.k00)));
					mainFrame.textFK1.setText(String.valueOf(new DecimalFormat("######0.0000").format(Coefficient.k01)));
					mainFrame.textFK2.setText(String.valueOf(new DecimalFormat("######0.0000").format(Coefficient.k02)));
					mainFrame.textFK3.setText(String.valueOf(new DecimalFormat("######0.0000").format(Coefficient.k03)));
					mainFrame.textFK4.setText(String.valueOf(new DecimalFormat("######0.0000").format(Coefficient.k04)));
					mainFrame.textFK5.setText(String.valueOf(new DecimalFormat("######0.0000").format(Coefficient.k05)));
					mainFrame.textFK6.setText(String.valueOf(new DecimalFormat("######0.0000").format(Coefficient.k06)));
					mainFrame.textFK7.setText(String.valueOf(new DecimalFormat("######0.0000").format(Coefficient.k07)));
					mainFrame.textFK8.setText(String.valueOf(new DecimalFormat("######0.0000").format(Coefficient.k08)));
					//mainFrame.textFK9.setText(String.valueOf(new DecimalFormat("######0.0000").format(Coefficient.k09)));
					mainFrame.textFK10.setText(String.valueOf(new DecimalFormat("######0.00").format(Coefficient.k10)));
					mainFrame.textFK11.setText(String.valueOf(new DecimalFormat("######0").format(Coefficient.k11)));
					mainFrame.textFK12.setText(String.valueOf(new DecimalFormat("######0.0000").format(Coefficient.k12)));
					mainFrame.textFK13.setText(String.valueOf(new DecimalFormat("######0.0000").format(Coefficient.k13)));
					mainFrame.textFK14.setText(String.valueOf(new DecimalFormat("######0.0000").format(Coefficient.k14)));
					mainFrame.textFK141.setText(String.valueOf(new DecimalFormat("######0.0000").format(Coefficient.k141)));
					mainFrame.textFK15.setText(String.valueOf(new DecimalFormat("######0.0000").format(Coefficient.k15)));
					//mainFrame.textFK16.setText(String.valueOf(new DecimalFormat("######0.0000").format(Coefficient.k16)));
					//mainFrame.textFK17.setText(String.valueOf(new DecimalFormat("######0.0000").format(Coefficient.k17)));
					mainFrame.textFK18.setText(String.valueOf(new DecimalFormat("######0.0000").format(Coefficient.k18)));
					//mainFrame.textFK19.setText(String.valueOf(new DecimalFormat("######0.0000").format(Coefficient.k19)));
					mainFrame.textFK20.setText(String.valueOf(new DecimalFormat("######0.00").format(Coefficient.k20)));
					mainFrame.textFK21.setText(String.valueOf(new DecimalFormat("######0.00").format(Coefficient.k21)));
					mainFrame.textFK22.setText(String.valueOf(new DecimalFormat("######0").format(Coefficient.k22)));
					mainFrame.textExRate.setText(String.valueOf(new DecimalFormat("######0.0000").format(Coefficient.ExRate)));
					if (versionOK !=1)
					{
						mainFrame.frame.setVisible(false);
						ProgramUpdate programUpdate=new ProgramUpdate();
						programUpdate.setVisible(true);
					}
					if (versionOK==1 && autoRun==1 )
					{
						envForAuto.set();
						mainFrame.frame.setVisible(false);
						mainFrame.btnGenerate.addActionListener(actListenerGenerate);
						mainFrame.btnR2Save.addActionListener(actListenerBtnSave);
						mainFrame.btnGenerate.doClick();
						System.exit(0);
					}
					
					if (versionOK==1 && autoRun==0 )
					{
						mainFrame.frame.setVisible(true);
						mainFrame.btnR2Save.setVisible(false);
						mainFrame.tableQueResult.getTableHeader().addMouseListener(actListenserSelected);
						mainFrame.texFmodel.addActionListener(actListenerQueryByFmodel);
						mainFrame.texFnum.addActionListener(actListenerQueryByFnumber);
						mainFrame.btnQuery.addActionListener(actListenerBtnQuery);
						
						mainFrame.btnGenerate.addActionListener(actListenerGenerate);
						mainFrame.btnR2Save.addActionListener(actListenerBtnSave);
					}
				} 
				catch (Exception e) 
				{	
					e.printStackTrace();
				}
			}
		});
	}
	public StdCostCalculate() 
	{
			
			
	}
}