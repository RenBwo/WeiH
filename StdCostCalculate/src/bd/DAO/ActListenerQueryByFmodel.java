package bd.DAO;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.table.DefaultTableModel;
import bd.View.StdCostCalculate;

public class ActListenerQueryByFmodel implements ActionListener
{
	public void actionPerformed(ActionEvent e)
	{
		try 
		{
			if (StdCostCalculate.mainFrame.chckbxNewProduct.isSelected())
			{
				String	sqlQuery = ";select distinct '0' as selected,a.fnumber,a.fmodel,a.fname,a.f_161,a.fsize"
		    			+ " from t_icitem a join icbom b on a.fitemid = b.fitemid "
		    			+ " and b.fstatus = 1 and b.fusestatus = 1072 and a.fnumber like '01.%' "
		    			+ " and a.fmodel like '%"+StdCostCalculate.mainFrame.texFmodel.getText()+"%' "
		    			+ " and a.fnumber like '%"+StdCostCalculate.mainFrame.texFnum.getText()+"%' "
		    			+ " join icprcplyentry c on c.fprice = 1000 and c.finterid = 3 "
		    			+ " and  c.fitemid = a.fitemid "
		    			+ " order by a.fnumber,a.fmodel";
					rs_code = conn.query(sqlQuery);
					rsmd_code 	= rs_code.getMetaData();
			}
			else
			{
				String	sqlQuery = " ;select '0' as selected,a.fnumber,a.fmodel,a.fname,a.f_161,a.fsize"
				+ " from t_icitem a join icbom b on a.fitemid = b.fitemid "
    			+ " and b.fstatus = 1 and b.fusestatus = 1072 and a.fnumber like '01.%' "
    			+ " and a.fmodel  like '%"+StdCostCalculate.mainFrame.texFmodel.getText()+"%' "
    			+ " and a.fnumber like '%"+StdCostCalculate.mainFrame.texFnum.getText()+"%' "
    			+ " order by a.fnumber,a.fmodel";
			rs_code = conn.query(sqlQuery);
			rsmd_code 	= rs_code.getMetaData();
			}
			int 	numberOfColumns 	= rsmd_code.getColumnCount();
			DefaultTableModel Model_code = (DefaultTableModel) StdCostCalculate.mainFrame.tableQueResult.getModel(); 
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
			StdCostCalculate.mainFrame.lblComments.setText("有 "+String.valueOf(i)+" 个物料符合条件");	
		}
		catch(SQLException e1) {}
	}
	private DBConnect conn=new DBConnect();
	private ResultSet rs_code ;
	private ResultSetMetaData 	rsmd_code ;

	


}
