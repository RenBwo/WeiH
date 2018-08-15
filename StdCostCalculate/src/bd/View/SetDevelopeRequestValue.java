package bd.View;

import java.sql.ResultSet;
import java.sql.SQLException;

import bd.DAO.StateSQl;
import bd.connection.GetDBConnect;

public class SetDevelopeRequestValue {
	/* 
	 * product devolopment request 
	 * */
	public void setDevelopRequestValue(int firstitemid) throws SQLException 
	{
    	StateSQl mysql=new StateSQl();	
    	String	sqlQuery = mysql.getSQLStatement("sqlProdDevRpt","",""
    			,firstitemid,0,0.0,0.0,0.0,0.0);    	
    	ResultSet rs_code = conn.query("",sqlQuery);
    	
    	frame.txtDev0.setText("");    
    	frame.txtDev1.setText("");
    	frame.txtDev2.setText("");    
    	frame.txtDev3.setText("");
    	frame.txtDev4.setText("");    
    	frame.txtDev5.setText(""); 
    	frame.txtDev6.setText("");    
    	frame.txtDev7.setText("");  
    	frame.txtDev8.setText("");    
    	frame.txtDev9.setText("");  
    	frame.txtDev10.setText("");    
    	frame.txtDev11.setText("");  
    	frame.txtDev12.setText("");    
    	frame.txtDev13.setText("");  
    	frame.txtDev14.setText("");    
    	frame.txtDev15.setText("");  
    	frame.txtDev16.setText("");    
    	frame.txtDev17.setText("");  
    	frame.txtDev18.setText("");    
    	frame.txtDev19.setText("");  
    	frame.txtDev20.setText("");    
    	frame.txtDev21.setText("");  
    	frame.txtDev22.setText("");    
    	frame.txtDev23.setText("");  
    	frame.txtDev24.setText("");    
    	frame.txtDev25.setText("");  
    	frame.txtDev26.setText("");    
    	frame.txtDev27.setText("");  
    	frame.txtDev28.setText("");    
    	frame.txtDev29.setText("");  
    	frame.txtDev30.setText("");    
    	frame.txtDev31.setText("");  
    	frame.txtDev32.setText("");    
    	frame.textArea.setText("");  
		while (rs_code.next())
	    {  
			frame.txtDev0.setText(rs_code.getString(1));
			frame.txtDev1.setText(rs_code.getString(2));
			frame.txtDev2.setText(rs_code.getString(3));
			frame.txtDev3.setText(rs_code.getString(4)); 
			frame.txtDev4.setText(rs_code.getString(5));    
			frame.txtDev5.setText(rs_code.getString(6));  
			frame.txtDev6.setText(rs_code.getString(7));    
			frame.txtDev7.setText(rs_code.getString(8));  
			frame.txtDev8.setText(rs_code.getString(9));    
			frame.txtDev9.setText(rs_code.getString(10));  
			frame.txtDev10.setText(rs_code.getString(11));    
			frame.txtDev11.setText(rs_code.getString(12));  
			frame.txtDev12.setText(rs_code.getString(13));    
			frame.txtDev13.setText(rs_code.getString(14));  
			frame.txtDev14.setText(rs_code.getString(15));    
			frame.txtDev15.setText(rs_code.getString(16));  
			frame.txtDev16.setText(rs_code.getString(17));    
			frame.txtDev17.setText(rs_code.getString(18));  
			frame.txtDev18.setText(rs_code.getString(19));    
			frame.txtDev19.setText(rs_code.getString(20));  
			frame.txtDev20.setText(rs_code.getString(21));    
			frame.txtDev21.setText(rs_code.getString(22));  
			frame.txtDev22.setText(rs_code.getString(23));    
			frame.txtDev23.setText(rs_code.getString(24));  
			frame.txtDev24.setText(rs_code.getString(25));    
			frame.txtDev25.setText(rs_code.getString(26));  
			frame.txtDev26.setText(rs_code.getString(27));    
			frame.txtDev27.setText(rs_code.getString(28));  
			frame.txtDev28.setText(rs_code.getString(29));    
			frame.txtDev29.setText(rs_code.getString(30));  
			frame.txtDev30.setText(rs_code.getString(31));    
			frame.txtDev31.setText(rs_code.getString(32));  
			frame.txtDev32.setText(rs_code.getString(33));    
			frame.textArea.setText(rs_code.getString(34));   
	    }
		rs_code.close();
		conn.close();
	}  
		

	private GetDBConnect conn=new GetDBConnect();
	private MainFrame frame = new MainFrame();


}
