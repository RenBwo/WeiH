package bd.DAO;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Paths;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import bd.View.StdCostCalculate;

public class ProgramUpdate extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/* 
	 * main
	 */
	 public static void main(String[] args) 
	    {
			ProgramUpdate versionCheck = new ProgramUpdate();
			versionCheck.setVisible(true);
	    }
	 /*
	  * update version
	  */
	 public ProgramUpdate() 
	 {
		//设置窗体属性
		setAttb();
		JLabel title = new JLabel("自动更新程序");
		this.add(title, BorderLayout.NORTH);
		JTextArea msg = new JTextArea();
		this.add(msg, BorderLayout.CENTER);

		//启动更新线程
		new updatePrograme(msg).start();
		
		
	 }
	 /*
	  * define new thread
	  */
	 private class updatePrograme extends Thread
	 {
		 public updatePrograme(JTextArea msg)
		{
			
			this.msg = msg;
			//System.out.println(System.getProperty("os.name").toLowerCase().substring(0,5));
			msg.append(System.getProperty("os.name").toLowerCase().substring(0,5)+"\n");
			
			
		} 
	    public void run()
	    {
	    	if (System.getProperty("os.name").toLowerCase().substring(0,5).equals("linux"))
			{
	    		oldFile = new File(Paths.get(System.getProperty("user.dir"),"StdCostCalculate.jar").toString())	;
	    		newFile  =  new File(Paths.get(System.getProperty("user.dir"),"temp.jar").toString());
	    		downloadUrl  = 
	    				"http://192.168.200.20:83/BDStdCostCalUpd/StdCostCalculate.jar";
			}
	    	if (System.getProperty("os.name").toLowerCase().substring(0,3).equals("win"))
	    	{
	    		oldFile = new File(Paths.get(System.getProperty("user.dir"),"BDStdCostCal.exe").toString());
	    		newFile =  new File(Paths.get(System.getProperty("user.dir"),"temp.exe").toString());
		    	downloadUrl =
	    				"http://192.168.200.20:83/BDStdCostCalUpd/BDStdCostCal.exe";
	    	}
    			 
	    	if (StdCostCalculate.versionOK ==2)
	    	{
	    		msg.append("正在更新版本信息\n");
	    		String desc="标准成本计算程序";
	    		set(desc);
	    		try
	        	{
	        		msg.append("启动应用程序");
	            	Thread.sleep(500);
	                //根据操作系统不同，运行不同的程序 
	            	if (System.getProperty("os.name").toLowerCase().substring(0,5).equals("linux"))
	    			{
	            		Runtime.getRuntime().exec("java -jar "
	    					+ Paths.get(System.getProperty("user.dir"),"StdCostCalculate.jar")
	            			,null,null);
	    			}
	            	else
	            	{
	            		Runtime.getRuntime().exec("BDStdCostCal.exe",null
	            				,Paths.get(System.getProperty("user.dir")).toFile());
		            }
	            }
	        	catch (IOException ex5) 
	        	{
	        		
	        	}
	        	catch (InterruptedException ex) 
	        	{
	        		
	        	}
	    	   
	        	//退出更新程序
	        	System.exit(0);
	    	}
	    	if (StdCostCalculate.versionOK ==0)
	    	{
	    		try
    			{
	    			url = new URL(downloadUrl);
	    			updateConnection=url.openConnection();
	    			updateConnection.connect();
	    			getUpdateStream = updateConnection.getInputStream();
	    			bufferInputStream = new BufferedInputStream(getUpdateStream);
	    			byte[] buffer = new byte[1024]; 
	    			int size = 0;
	    			fileOutStream = new FileOutputStream(newFile);
	    			msg.append("正在从网络上下载新的更新文件\n");
	    			//保存文件
	    			try
	    			{
	    				while ((size = bufferInputStream.read(buffer)) != -1) 
	    				{
	    					//读取并刷新临时保存文件
	    					fileOutStream.write(buffer, 0, size);
	    					fileOutStream.flush(); 
			            }
	    			}
	    			catch (Exception ex4)
	    			{
	    				System.out.println(ex4.getMessage());
	    			}
	    			msg.append("\n文件下载完成\n");
	    			//把下载的临时文件替换原有文件
	    			CopyFile(oldFile,newFile); 
	    		}
    			catch (MalformedURLException ex2) 
    			{
	    		
    			}
    			catch (IOException ex)
    			{
    				msg.append("文件读取错误\n");
    			}
    			finally 
    			{
    				try
    				{
	    			fileOutStream.close();
	    			bufferInputStream.close();
	    			getUpdateStream.close();
    				}
    				catch (IOException ex3) 
    				{
	    			
    				}
    			}
	    	}
	    	
    	}
	    
	    //复制文件
	    private void CopyFile(File oldFile, File newFile) 
	    {
	    	FileInputStream getUpdateStream = null;
	    	FileOutputStream out = null;
	    	try
	    	{
	    		if(oldFile.exists())
	    		{
	    			//msg.append("删除旧文件 \n");
	    			oldFile.delete();
	    			msg.append("删除旧文件 \n");
	    		}
	    		getUpdateStream=  new FileInputStream(newFile);
	            out = new FileOutputStream(oldFile); 
	            byte[] buffer = new byte[1024 * 5];
	            int size;
	            while ((size = getUpdateStream .read(buffer)) != -1)
	            {
	            	out.write(buffer, 0, size);
	            	out.flush();
	            	msg.append("。");
	            }
	    	} 
	    	catch (FileNotFoundException ex) 
	    	{
	    		
	    	} 
	    	catch (IOException ex) 
	    	{
	    		
	    	}

	    	finally 
	    	{
	    		try 
	    		{
	    			out.close();
	    			getUpdateStream.close();
	    			msg.append("写入完成 \n");
	            } 
	    		catch (IOException ex1) 
	            {
	    			
	            }
	    	}
		//启动应用程序
    	try
    	{
    		msg.append("启动应用程序");
        	Thread.sleep(500);
            //根据操作系统不同，运行不同的程序 
        	if (System.getProperty("os.name").toLowerCase().substring(0,5).equals("linux"))
			{
        		Runtime.getRuntime().exec("java -jar "
					+ Paths.get(System.getProperty("user.dir")
							,"StdCostCalculate.jar")
        			,null,null);
			}
        	else
        	{
        		Runtime.getRuntime().exec("BDStdCostCal.exe",null
        				,Paths.get(System.getProperty("user.dir")).toFile());
            }
        }
    	catch (IOException ex5) 
    	{
    		
    	}
    	catch (InterruptedException ex) 
    	{
    		
    	}
	   
    	//退出更新程序
    	System.exit(0);
	   }
	    private JTextArea msg;
		 	
	 }
	 
	 /*
	  * 窗体设置
	  */
	 private void setAttb() 
	 {
		this.setTitle("Auto Update");
		this.setSize(600, 500);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE); 
		// 窗体居中
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    Dimension frameSize = this.getSize();
	    if (frameSize.height > screenSize.height) 
	    {
	    	frameSize.height = screenSize.height;
	    }
	    if (frameSize.width > screenSize.width) 
	    {
	    	frameSize.width = screenSize.width;
	    }
	    this.setLocation((screenSize.width - frameSize.width)/2,
	    		(screenSize.height-frameSize.height)/2);
	 }
	 /* 
	  * set
	  */
	 public void set(String desc) 
	 {
		 try
		 {

				cmdDelVersion=";delete BDStdCostProgVersion where programfile like '"+StdCostCalculate.programName+"'";
				conn.update("",cmdDelVersion);
				cmdInsertVersion=";insert into BDStdCostProgVersion("
						+ "version"
						+ ",programfile"
						+ ",description"
						+ ") "
				 		+ "values("
				 		+ "'"+StdCostCalculate.localVer+"'"
				 		+ ",'"+StdCostCalculate.programName+"'"
				 		+ ",'"+desc+"'"
				 		+ ")";
				 conn.update("",cmdInsertVersion);
				 conn.close();
		 }
		 catch (SQLException e)
		 {
			 
		 }
	 }
	
	 /*
	  * fields
	  */
	 URL url =null;						//更新文件版本标识URL
		
	/*	File oldFileLinux = new File(Paths.get(System.getProperty("user.dir"),"StdCostCalculate.jar").toString())	
		,newFileLinux  =  new File(Paths.get(System.getProperty("user.dir"),"temp.jar").toString())
		,oldFileWin = new File(Paths.get(System.getProperty("user.dir"),"BDStdCostCal.exe").toString())
		,newFileWin =  new File(Paths.get(System.getProperty("user.dir"),"temp.exe").toString());
		String downloadUrlLinux  = 
				"http://192.168.200.20:83/BDStdCostCalUpd/StdCostCalculate.jar"
			 ,downloadUrlWin =
						"http://192.168.200.20:83/BDStdCostCalUpd/BDStdCostCal.exe";
	*/	
	 File oldFile,newFile ;
	 String downloadUrl;
		 URLConnection updateConnection=null;
		 InputStream getUpdateStream=null ;	//本地需要被更新的文件
		 BufferedInputStream bufferInputStream = null;
		 FileOutputStream fileOutStream = null;
	

	 private String cmdDelVersion,cmdInsertVersion;
	 private DBConnect conn=new DBConnect(); 

}
