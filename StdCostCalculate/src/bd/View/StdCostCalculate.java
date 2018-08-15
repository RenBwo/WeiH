package bd.View;
/*
 * 
 * 20171028 
 * 取消过冷式储液器
 * 纠正扁管切断产能公式
 * 同一固定资产编号的设备卡片取最近日期
 */
import java.awt.*;
import javax.swing.*;
import bd.DAO.*;
import bd.connection.*;

public class StdCostCalculate 
{
	public 		static MainFrame mainFrame=new MainFrame();	
	private  	static ActListenerBtnGenerate actListenerGenerate=new ActListenerBtnGenerate(); 
    private  	static ActListenerBtnSave actListenerBtnSave=new ActListenerBtnSave(); 
    private  	static ActListenerBtnQuery actListenerBtnQuery=new  ActListenerBtnQuery();
	private  	static ActListenserSelected actListenserSelected=new ActListenserSelected();
	private  	static ActListenerQueryByFmodel actListenerQueryByFmodel=new ActListenerQueryByFmodel();
    private  	static ActListenerQueryByFnumber actListenerQueryByFnumber=new ActListenerQueryByFnumber();
    public 		static CompareVersion compareVersion=new CompareVersion();
    public 		static String auto,programName="standCostCal";
    public 		static int v1=1,v2=0,v3=0,v4=4,verifyVersionOK;
	  
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		if (args.length > 0) 
		{
			auto = args[0];
		}
		else 
		{
			auto="";
		}
		
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					VerifyFrame verifyFrame = new VerifyFrame();
					verifyFrame.Verifyframe.setVisible(true);
					verifyVersionOK=compareVersion.compareVersion(programName,v1,v2,v3,v4);
					if (verifyVersionOK==1) 
					{
						if (auto.equals("auto")) 
						{
							verifyFrame.Verifyframe.setVisible(false);	
							mainFrame.frame.setVisible(false);
							mainFrame.tableQueResult.getTableHeader().addMouseListener(actListenserSelected);
							mainFrame.texFmodel.addActionListener(actListenerQueryByFmodel);
							mainFrame.texFnum.addActionListener(actListenerQueryByFnumber);
							mainFrame.btnQuery.addActionListener(actListenerBtnQuery);
							mainFrame.btnGenerate.addActionListener(actListenerGenerate);
							mainFrame.btnR2Save.addActionListener(actListenerBtnSave);
							mainFrame.btnGenerate.doClick();
							System.exit(0);
						}
						else 
						{
							verifyFrame.Verifyframe.setVisible(false);
							mainFrame.frame.setVisible(true);
							mainFrame.tableQueResult.getTableHeader().addMouseListener(actListenserSelected);
							mainFrame.texFmodel.addActionListener(actListenerQueryByFmodel);
							mainFrame.texFnum.addActionListener(actListenerQueryByFnumber);
							mainFrame.btnQuery.addActionListener(actListenerBtnQuery);
							mainFrame.btnGenerate.addActionListener(actListenerGenerate);
							mainFrame.btnR2Save.setVisible(false);
							mainFrame.btnR2Save.addActionListener(actListenerBtnSave);
						}
					}
					else 
					{
						JOptionPane.showMessageDialog(verifyFrame.Verifyframe,"程序版本错误，请使用最新版本"); 
					}
				} 
				catch (Exception e) 
				{	
					e.printStackTrace();
				}
			}
		});
	}    
}