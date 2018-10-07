package bd.DAO;
/*
 * all kinds of rate
 */


import java.sql.ResultSet;
import java.sql.SQLException;

public class Coefficient 
{
	/*
	 * 系数
	 */
	public Coefficient()
	{
		try
		{
			k00=getK("k00");
			k01=getK("k01")/100;
			k02=getK("k02")/100;
			k03=getK("k03")/100;
			k04=getK("k04")/100;
			k05=getK("k05")/100;
			k06=getK("k06")/100;
			k07=getK("k07")/100;
			k08=getK("k08")/100;
			k09=getK("k09")/100;
			k10=getK("k10")/100;//国内增值税
			k11=getK("k11");
			k12=getK("k12");//燃气价格
			k13=getK("k13")/100;
			k14=getK("k14");
			k141=getK("k141");
			k15=getK("k15")/100;
			k16=getK("k16");
			k17=getK("k17");
			k18=getK("k18")/100;
			k19=getK("k19");
			k20=getK("k20");
			k21=getK("k21");
			k22=getK("k22");
			ExRate=getExRate();
			
		}
		catch(SQLException e) {}
		
	}
  	/*
  	 * 标准成本核算系数
  	 */
	private Double getK(String k) throws SQLException
	{
		
		String sqlcomments = ";select round(f_101,4) from t_item_3015 " + " where fnumber like '"+k+"'";
			ResultSet   rs = conn.query(sqlcomments);
	   	if (rs.next())
	   	{ 
	   		returnv = rs.getDouble(1);
	   	  	rs.close(); 
	   	}	 
	   	else
	   	{
	   		returnv = 0.0;
	    	rs.close();
		}  
	   	return returnv;	
	}
	
	//exchangerate 预算汇率
	private Double getExRate() throws SQLException
	{
		String sqlcomments = ";select fexchangerate from t_exchangerateentry where fexchangeratetype = 4 "
				+ " and fcyfor = 1 and fcyto = 1000 and fbegdate <= getdate()  and getdate()< dateadd(day,1,fenddate) "
				+ " and isnull(fchkuserid,0)<>0 and isnull(fchkdate,'1999-01-01') <> '1999-01-01' "
				+ " order by fentryid desc ";
			ResultSet   rs = conn.query(sqlcomments);
	   	if (rs.next())
	   	{  returnv = rs.getDouble(1);
	   	  	rs.close();  
		   	return returnv;	
		}	 
	   	else
	   	{
	   		returnv = 0.0;
	    	rs.close();  
		   	return returnv;	
		}    
	}
	private Double returnv;
  	private DBConnect conn = new DBConnect();
  	static public Double k00,k01,k02,k03,k04,k05,k06,k07,k08,k09
  	,k10,k11,k12,k13,k14,k141,k15,k16,k17,k18,k19,k20,k21,k22
  	,ExRate;
  
}
	
	   	     
	
