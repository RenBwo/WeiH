package bd.View;
/*
 * 
 * 20171028 
 * 取消过冷式储液器
 * 纠正扁管切断产能公式
 * 同一固定资产编号的设备卡片取最近日期
 */
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import bd.DAO.*;

public class StdCostCalculate 
{
	public 		static MainFrame mainFrame=new MainFrame();
	private 	static VersionCompare verCompare=new VersionCompare();
	private 	static CurrentPeriod  currentPeriod=new CurrentPeriod();
    private  	static ActListenerBtnGenerate actListenerGenerate=new ActListenerBtnGenerate(); 
    private  	static ActListenerBtnSave actListenerBtnSave=new ActListenerBtnSave(); 
    private  	static ActListenerBtnQuery actListenerBtnQuery=new  ActListenerBtnQuery();
	private  	static ActListenserSelected actListenserSelected=new ActListenserSelected();
	private  	static ActListenerQueryByFmodel actListenerQueryByFmodel=new ActListenerQueryByFmodel();
    private  	static ActListenerQueryByFnumber actListenerQueryByFnumber=new ActListenerQueryByFnumber();
    private     static EnvForAuto envForAuto = new EnvForAuto();
    public 		static String programName="standCostCal",localVer="1.0.1.4"
    		,autoFlag;
    public 		static int versionOK,autoRun;
    private 	static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	
	
	  
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
					currentPeriod.getYear();
					currentPeriod.getMonth();					
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