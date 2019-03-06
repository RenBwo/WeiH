package bd.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import bd.View.StdCostCalculate;

public class DevelopeRequest {
	/* 
	 * product devolopment request 
	 * */
	public void setDevelopRequestValue(int firstitemid) throws SQLException 
	{
    	SQlStatement mysql=new SQlStatement();	
    	String	sqlQuery = mysql.getSQLStatement("sqlProdDevRpt");    	
    	ResultSet rs_code = conn.query(sqlQuery);
    	
    	StdCostCalculate.mainFrame.txtDev0.setText("");    
    	StdCostCalculate.mainFrame.txtDev1.setText("");
    	StdCostCalculate.mainFrame.txtDev2.setText("");    
    	StdCostCalculate.mainFrame.txtDev3.setText("");
    	StdCostCalculate.mainFrame.txtDev4.setText("");    
    	StdCostCalculate.mainFrame.txtDev5.setText(""); 
    	StdCostCalculate.mainFrame.txtDev6.setText("");    
    	StdCostCalculate.mainFrame.txtDev7.setText("");  
    	StdCostCalculate.mainFrame.txtDev8.setText("");    
    	StdCostCalculate.mainFrame.txtDev9.setText("");  
    	StdCostCalculate.mainFrame.txtDev10.setText("");    
    	StdCostCalculate.mainFrame.txtDev11.setText("");  
    	StdCostCalculate.mainFrame.txtDev12.setText("");    
    	StdCostCalculate.mainFrame.txtDev13.setText("");  
    	StdCostCalculate.mainFrame.txtDev14.setText("");    
    	StdCostCalculate.mainFrame.txtDev15.setText("");  
    	StdCostCalculate.mainFrame.txtDev16.setText("");    
    	StdCostCalculate.mainFrame.txtDev17.setText("");  
    	StdCostCalculate.mainFrame.txtDev18.setText("");    
    	StdCostCalculate.mainFrame.txtDev19.setText("");  
    	StdCostCalculate.mainFrame.txtDev20.setText("");    
    	StdCostCalculate.mainFrame.txtDev21.setText("");  
    	StdCostCalculate.mainFrame.txtDev22.setText("");    
    	StdCostCalculate.mainFrame.txtDev23.setText("");  
    	StdCostCalculate.mainFrame.txtDev24.setText("");    
    	StdCostCalculate.mainFrame.txtDev25.setText("");  
    	StdCostCalculate.mainFrame.txtDev26.setText("");    
    	StdCostCalculate.mainFrame.txtDev27.setText("");  
    	StdCostCalculate.mainFrame.txtDev28.setText("");    
    	StdCostCalculate.mainFrame.txtDev29.setText("");  
    	StdCostCalculate.mainFrame.txtDev30.setText("");    
    	StdCostCalculate.mainFrame.txtDev31.setText("");  
    	StdCostCalculate.mainFrame.txtDev32.setText("");    
    	StdCostCalculate.mainFrame.textArea.setText("");  
		while (rs_code.next())
	    {  
			StdCostCalculate.mainFrame.txtDev0.setText(rs_code.getString(1));
			StdCostCalculate.mainFrame.txtDev1.setText(rs_code.getString(2));
			StdCostCalculate.mainFrame.txtDev2.setText(rs_code.getString(3));
			StdCostCalculate.mainFrame.txtDev3.setText(rs_code.getString(4)); 
			StdCostCalculate.mainFrame.txtDev4.setText(rs_code.getString(5));    
			StdCostCalculate.mainFrame.txtDev5.setText(rs_code.getString(6));  
			StdCostCalculate.mainFrame.txtDev6.setText(rs_code.getString(7));    
			StdCostCalculate.mainFrame.txtDev7.setText(rs_code.getString(8));  
			StdCostCalculate.mainFrame.txtDev8.setText(rs_code.getString(9));    
			StdCostCalculate.mainFrame.txtDev9.setText(rs_code.getString(10));  
			StdCostCalculate.mainFrame.txtDev10.setText(rs_code.getString(11));    
			StdCostCalculate.mainFrame.txtDev11.setText(rs_code.getString(12));  
			StdCostCalculate.mainFrame.txtDev12.setText(rs_code.getString(13));    
			StdCostCalculate.mainFrame.txtDev13.setText(rs_code.getString(14));  
			StdCostCalculate.mainFrame.txtDev14.setText(rs_code.getString(15));    
			StdCostCalculate.mainFrame.txtDev15.setText(rs_code.getString(16));  
			StdCostCalculate.mainFrame.txtDev16.setText(rs_code.getString(17));    
			StdCostCalculate.mainFrame.txtDev17.setText(rs_code.getString(18));  
			StdCostCalculate.mainFrame.txtDev18.setText(rs_code.getString(19));    
			StdCostCalculate.mainFrame.txtDev19.setText(rs_code.getString(20));  
			StdCostCalculate.mainFrame.txtDev20.setText(rs_code.getString(21));    
			StdCostCalculate.mainFrame.txtDev21.setText(rs_code.getString(22));  
			StdCostCalculate.mainFrame.txtDev22.setText(rs_code.getString(23));    
			StdCostCalculate.mainFrame.txtDev23.setText(rs_code.getString(24));  
			StdCostCalculate.mainFrame.txtDev24.setText(rs_code.getString(25));    
			StdCostCalculate.mainFrame.txtDev25.setText(rs_code.getString(26));  
			StdCostCalculate.mainFrame.txtDev26.setText(rs_code.getString(27));    
			StdCostCalculate.mainFrame.txtDev27.setText(rs_code.getString(28));  
			StdCostCalculate.mainFrame.txtDev28.setText(rs_code.getString(29));    
			StdCostCalculate.mainFrame.txtDev29.setText(rs_code.getString(30));  
			StdCostCalculate.mainFrame.txtDev30.setText(rs_code.getString(31));    
			StdCostCalculate.mainFrame.txtDev31.setText(rs_code.getString(32));  
			StdCostCalculate.mainFrame.txtDev32.setText(rs_code.getString(33));    
			StdCostCalculate.mainFrame.textArea.setText(rs_code.getString(34));   
	    }
		rs_code.close();
		conn.close();
	}  
		

	private DBConnect conn=new DBConnect();


}
