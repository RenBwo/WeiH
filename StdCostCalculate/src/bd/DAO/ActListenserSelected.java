/*
 * 监听器
 * 选择框全部选择
 */
package bd.DAO;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.JTableHeader;

import bd.View.*;

public class ActListenserSelected extends MouseAdapter
{
	public void mouseClicked(MouseEvent e)
	{ if(e.getClickCount() == 2 )
	{ 
		int  col=((JTableHeader)e.getSource()).columnAtPoint(e.getPoint()); //获得列位置
		if (col == (int)0 ) 
		{
			int  countRow = StdCostCalculate.mainFrame.tableQueResult.getRowCount();
			if(selectedAll_yn) 
			{
				for (int x = 0;x<countRow;x++) 
				{
					StdCostCalculate.mainFrame.tableQueResult.setValueAt(false,x,col);
				} 
				selectedAll_yn=false; 
			} 
			else 
			{
				for (int x = 0;x<countRow;x++) 
				{
					StdCostCalculate.mainFrame.tableQueResult.setValueAt(true,x,col);
				}
				selectedAll_yn=true;
			}
		}
	} 
	else 
		return; 
	} 
	private Boolean selectedAll_yn =false ;
	
}
