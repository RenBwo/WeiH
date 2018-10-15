package bd.DAO;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MachineInfo 
{
	public void get() throws SQLException
	{
		updateMachineInfo();
		getCapacity();
		getAmtPowerQHL();
	}
	/*
	 * 	升级设备最新折旧和功率信息
	 */
	private void updateMachineInfo() throws SQLException
	{
		String sql1 = "; update t_costcalculatebd_entry0 set fpower=b.fpowercap,fassetinterid=b.fassetnumber"
				+ ",fmanname=b.fdevicename,fmachinespection=b.fspecification"
				+ " from t_costcalculatebd_entry0 a join icdeviceaccount b on a.fmanum = b.fdevicenumber";
		//System.out.println("升级设备信息：" + sql1);
		conn.update(sql1);
		sql1 = "; update t_costcalculatebd_entry0 set fdepreciation = c.fdeprshould"
				+ " from t_costcalculatebd_entry0 a "
				+ " join (select fassetid,max(fdeprperiods) as fdeprperiods"
				+ " from t_fabalance "
				+ "  where fdeprshould > 0 group by fassetid) b on b.fassetid = a.fassetinterid"
				+ " join t_fabalance c on c.fdeprperiods = b.fdeprperiods and c.fassetid = a.fassetinterid";
		conn.update(sql1);
		//System.out.println("升级设备折旧信息："+sql1);
		conn.close();
	}
	/*
	 * 钎焊炉产能
	 */
	private void  getCapacity()
	{   Double length, height;
		length=ProductInfo.length*1000;
		height=ProductInfo.height*1000;
		if(400<length && length<=900 && 900<height )//1
		{
			 cap = new BigDecimal(1000*60/(height+240));
			 cap =  cap.setScale(2, RoundingMode.HALF_UP);
			 capacity= Double.parseDouble( cap.toString());			
		}
		if( 0<length && length<=400 && 900<height) //2
		{
			 cap = new BigDecimal(2*1000*60/(height+240));
			 cap =  cap.setScale(2, RoundingMode.HALF_UP);
			 capacity= Double.parseDouble( cap.toString());
		}
		if(900<length && 400<height && height<=900) //3
		{
			 cap = new BigDecimal(1000*60/(length+240));
			 cap =  cap.setScale(2, RoundingMode.HALF_UP);
			 capacity=  Double.parseDouble( cap.toString());
		}
		if(900<length && 0< height && height<=400 ) //4
		{
			 cap = new BigDecimal(2*1000*60/(length+240));
			 cap =  cap.setScale(2, RoundingMode.HALF_UP);
			 capacity=  Double.parseDouble( cap.toString());
		}
		if(400<length && length<=900 && 400<height  && height<=900  ) //5
		{
			 cap = new BigDecimal(1000*60/(height+240) > 1000*60/(length+240)?1000*60/(height+240):1000*60/(length+240));
			 cap =  cap.setScale(2, RoundingMode.HALF_UP);
			 capacity=  Double.parseDouble( cap.toString());
		}
		if(0<length && length<=400 && 400<height  && height<=900  ) //6
		{
			 cap = new BigDecimal(2*1000*60/(height+240) > 1000*60/(length+240)?2*1000*60/(height+240):1000*60/(length+240));
			 cap =  cap.setScale(2, RoundingMode.HALF_UP);
			 capacity=  Double.parseDouble( cap.toString());
		}
		if(400<length  && length<=900 && 0< height && height<=400 ) //7
		{
			 cap = new BigDecimal(2*1000*60/(length+240) > 1000*60/(height+240)?2*1000*60/(length+240):1000*60/(height+240));
			 cap =  cap.setScale(2, RoundingMode.HALF_UP);
			 capacity= Double.parseDouble( cap.toString());
		}
		if( 0<length && length<=400 &&  0<height &&  height<=400 ) //8
		{
			 cap = new BigDecimal(2*1000*60/(length+240) > 2*1000*60/(height+240)?2*1000*60/(length+240):2*1000*60/(height+240));
			 cap =  cap.setScale(2, RoundingMode.HALF_UP);
			 capacity=  Double.parseDouble( cap.toString());
		}
		
		if(900<length && 900< height) //9
		{
			capacity= 0.0;
			//System.out.println("超过900毫米的产品，超出了钎焊炉的工艺范围");
		}
		if(length<=0 || height<=0) //9
		{
			capacity= 0.0;
			//System.out.println("芯体长或者宽必须大于0");
		}
		
	}
	/*
	 * 钎焊炉电费,按体积×单位体积用电量×单价
	 */
	private void getAmtPowerQHL() throws SQLException
	{
		if (Route.hasQHL==true) 
		{
			rs0= conn.query("; select (isnull(sum(FelectricPerV),0)*"
					+Coefficient.k00
					+ " + isnull(sum(FGasPerV),0)*"+Coefficient.k12+")*" 
					+ProductInfo.volumn 
					+" from t_costcalculatebd_entry0"
					);
				if(rs0.next()) 
				{
					amtPowerQHL=rs0.getDouble(1);
				}
				else
				{
					amtPowerQHL=0.0;
				}
				rs0.close();
		}
		else
		{
			amtPowerQHL=0.0;
		}
		//System.out.println("钎焊炉电费："+amtPowerQHL);
		
	}

	
	
	private DBConnect conn=new DBConnect();
	private BigDecimal  cap ;
	private ResultSet 	rs0;
	public static double capacity,amtPowerQHL;
	


}
