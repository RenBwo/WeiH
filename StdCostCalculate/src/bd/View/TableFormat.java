package bd.View;


import java.util.HashSet;

import javax.swing.*;
import javax.swing.table.*;

public class TableFormat  
{
	DefaultTableCellRenderer r = new DefaultTableCellRenderer();
	//set color
	
	//align left
	public void ColAlignLeft(JTable table,int[] colindex) 
	{
		r.setHorizontalAlignment(JLabel.LEFT); 
		for(int i = 0;i<colindex.length;i++) 
		{
			table.getColumnModel().getColumn(colindex[i]).setCellRenderer(r);
		}
	}
	//align right
	public void ColAlignRight(JTable table,int[] colindex) 
	{
		r.setHorizontalAlignment(JLabel.RIGHT); 
		for(int i = 0;i<colindex.length;i++) 
		{
			table.getColumnModel().getColumn(colindex[i]).setCellRenderer(r);
		}
	}
	//过滤列
	public void ColumnFilter(JTable table,int colindex)
	{
		TableColumnModel  colmode = table.getColumnModel();
		colmode.getColumn(colindex).setMinWidth(0);
		colmode.getColumn(colindex).setMaxWidth(0);
	}	
    //过滤行
	public void RowFilter(JTable table,int[] Rowindex)
	{
		HashSet<Integer> noDisplay=new HashSet<>();
		final TableRowSorter<TableModel> sorter=new TableRowSorter<>(table.getModel());
	    table.setRowSorter(sorter);
	    final RowFilter<TableModel,Integer> filter = new RowFilter<TableModel,Integer>()
	    {
	    	public boolean include(Entry<? extends TableModel,? extends Integer> entry)
	    	{
	    		return !noDisplay.contains(entry.getIdentifier());
			}
		};
		for(int i:Rowindex)
		{
			noDisplay.add(Rowindex[i]);
			sorter.setRowFilter(filter);
		}
	}
}
