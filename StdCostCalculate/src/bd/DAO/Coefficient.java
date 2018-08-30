package bd.DAO;
/*
 * all kinds of rate
 */


import java.sql.ResultSet;
import java.sql.SQLException;

public class Coefficient 
{
  	//标准成本核算系数
	public String getK(String k) throws SQLException
	{
		
		String sqlcomments = ";select round(f_101,6) from t_item_3015 " + " where fnumber like '"+k+"'";
			ResultSet   rs = conn.query("",sqlcomments);
	   	if (rs.next())
	   	{ 
	   		returnv = rs.getString(1);
	   	  	rs.close(); 
	   	}	 
	   	else
	   	{
	   		returnv = String.valueOf(0);
	    	rs.close();
		}  
	   	return returnv;	
	}
	
	//exchangerate 预算汇率
	public String getExRate() throws SQLException
	{
		String sqlcomments = ";select fexchangerate from t_exchangerateentry where fexchangeratetype = 4 "
				+ " and fcyfor = 1 and fcyto = 1000 and fbegdate <= getdate()  and getdate()< dateadd(day,1,fenddate) "
				+ " and isnull(fchkuserid,0)<>0 and isnull(fchkdate,'1999-01-01') <> '1999-01-01' "
				+ " order by fentryid desc ";
			ResultSet   rs = conn.query("",sqlcomments);
	   	if (rs.next())
	   	{  returnv = String.valueOf(rs.getFloat(1));
	   	  	rs.close();  
		   	return returnv;	
		}	 
	   	else
	   	{
	   		returnv = String.valueOf(0.0);
	    	rs.close();  
		   	return returnv;	
		}    
	}
	private String returnv;
  	private DBConnect conn = new DBConnect();
  
}
	
	   	     
	