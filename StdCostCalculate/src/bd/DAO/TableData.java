package bd.DAO;
/*
 * table data
 */

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class TableData {

	public void myTableModel(JTable table0,String sqlstr,int[] col)  throws SQLException
	{
		table0.getTableHeader().setResizingAllowed(true);
		DefaultTableModel dataModel = (DefaultTableModel) table0.getModel(); 
		DefaultTableCellRenderer r = new DefaultTableCellRenderer(); 
		r.setHorizontalAlignment(JLabel.CENTER); 
		table0.setDefaultRenderer(Object.class, r);
		table0.setRowHeight(24);
		int count = dataModel.getRowCount();
				for (int j = count ; j > 0 ; j--) {
					dataModel.removeRow(j - 1);	
					}		
		DBConnect getcon =  new DBConnect();
	    ResultSet 	rs	=	getcon.query("",sqlstr); 
	    ResultSetMetaData 	rsmd 	= rs.getMetaData(); 
		int 	numberOfColumns 	= rsmd.getColumnCount();
	    while (rs.next())
	    {   Vector<String> data = new Vector<String>(); 
	     	for(int i=1;i<=numberOfColumns;i++)
			{ 
				data.add(rs.getString(i)); 
				////System.out.println(data);
				}   	  
			dataModel.addRow(data);
			}
	    rs.close();
	    
	    if (col.length >0) /*有求和项*/
	    {
	    Vector<String> dataSum = new Vector<String>(); 
	    dataSum.add("合计");
	    for(int j = 1; j<col[0];j++){
	    	dataSum.add("");
	    }	    
	    dataSum.add(sumColumn(table0,col[0]));
	    
	    for(int i=1;i<col.length;i++){
	    	for(int j = col[i - 1] +1; j<col[i];j++){
	    	dataSum.add(""); }
	    dataSum.add(sumColumn(table0,col[i]));
	    }
	    //System.out.println(dataSum);
	    dataModel.addRow(dataSum);	}	
	}   
	public String sumColumn(JTable table0,int col0){
		double sum0 = 0;
		for(int i = 0 ; i<table0.getRowCount();i++){
			sum0 += Double.parseDouble(table0.getValueAt(i, col0).toString());
		}
		DecimalFormat df = new DecimalFormat("#######0.0000");
		return df.format(sum0);
	}
	
	} 


