package bd.DAO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;

public class MachineInfo 
{
	/*
	 * 	升级设备最新折旧和功率信息
	 */
	public void updateMachineInfo() throws SQLException
	{
		String sql1 = "; update t_costcalculatebd_entry0 set fpower=b.fpowercap,fassetinterid=b.fassetnumber"
				+ ",fmanname=b.fdevicename,fmachinespection=b.fspecification"
				+ " from t_costcalculatebd_entry0 a join icdeviceaccount b on a.fmanum = b.fdevicenumber";
		//System.out.println("升级设备信息：" + sql1);
		conn.update("",sql1);
		sql1 = "; update t_costcalculatebd_entry0 set fdepreciation = c.fdeprshould"
				+ " from t_costcalculatebd_entry0 a "
				+ " join (select fassetid,max(fdeprperiods) as fdeprperiods"
				+ " from t_fabalance "
				+ "  where fdeprshould > 0 group by fassetid) b on b.fassetid = a.fassetinterid"
				+ " join t_fabalance c on c.fdeprperiods = b.fdeprperiods and c.fassetid = a.fassetinterid";
		conn.update("",sql1);
		//System.out.println("升级设备折旧信息："+sql1);
		conn.close();
	}
	/*
	 * 钎焊炉产能
	 */
	public void  getCapacity()
	{   
		if(400<ProductInfo.length*1000 && ProductInfo.length*1000<=900 && 900<ProductInfo.height*1000 )//1
		{
			 cap = new BigDecimal(1000*60/(ProductInfo.height*1000+240));
			 cap =  cap.setScale(2, RoundingMode.HALF_UP);
			 capacity= Double.parseDouble( cap.toString());			
		}
		if( 0<ProductInfo.length*1000 && ProductInfo.length*1000<=400 && 900<ProductInfo.height*1000) //2
		{
			 cap = new BigDecimal(2*1000*60/(ProductInfo.height*1000+240));
			 cap =  cap.setScale(2, RoundingMode.HALF_UP);
			 capacity= Double.parseDouble( cap.toString());
		}
		if(900<ProductInfo.length*1000 && 400<ProductInfo.height*1000 && ProductInfo.height*1000<=900) //3
		{
			 cap = new BigDecimal(1000*60/(ProductInfo.length*1000+240));
			 cap =  cap.setScale(2, RoundingMode.HALF_UP);
			 capacity=  Double.parseDouble( cap.toString());
		}
		if(900<ProductInfo.length*1000 && 0< ProductInfo.height*1000 && ProductInfo.height*1000<=400 ) //4
		{
			 cap = new BigDecimal(2*1000*60/(ProductInfo.length*1000+240));
			 cap =  cap.setScale(2, RoundingMode.HALF_UP);
			 capacity=  Double.parseDouble( cap.toString());
		}
		if(400<ProductInfo.length*1000 && ProductInfo.length*1000<=900 && 400<ProductInfo.height*1000  && ProductInfo.height*1000<=900  ) //5
		{
			 cap = new BigDecimal(1000*60/(ProductInfo.height*1000+240) > 1000*60/(ProductInfo.length*1000+240)?1000*60/(ProductInfo.height*1000+240):1000*60/(ProductInfo.length*1000+240));
			 cap =  cap.setScale(2, RoundingMode.HALF_UP);
			 capacity=  Double.parseDouble( cap.toString());
		}
		if(0<ProductInfo.length*1000 && ProductInfo.length*1000<=400 && 400<ProductInfo.height*1000  && ProductInfo.height*1000<=900  ) //6
		{
			 cap = new BigDecimal(2*1000*60/(ProductInfo.height*1000+240) > 1000*60/(ProductInfo.length*1000+240)?2*1000*60/(ProductInfo.height*1000+240):1000*60/(ProductInfo.length*1000+240));
			 cap =  cap.setScale(2, RoundingMode.HALF_UP);
			 capacity=  Double.parseDouble( cap.toString());
		}
		if(400<ProductInfo.length*1000  && ProductInfo.length*1000<=900 && 0< ProductInfo.height*1000 && ProductInfo.height*1000<=400 ) //7
		{
			 cap = new BigDecimal(2*1000*60/(ProductInfo.length*1000+240) > 1000*60/(ProductInfo.height*1000+240)?2*1000*60/(ProductInfo.length*1000+240):1000*60/(ProductInfo.height*1000+240));
			 cap =  cap.setScale(2, RoundingMode.HALF_UP);
			 capacity= Double.parseDouble( cap.toString());
		}
		if( 0<ProductInfo.length*1000 && ProductInfo.length*1000<=400 &&  0<ProductInfo.height*1000 &&  ProductInfo.height*1000<=400 ) //8
		{
			 cap = new BigDecimal(2*1000*60/(ProductInfo.length*1000+240) > 2*1000*60/(ProductInfo.height*1000+240)?2*1000*60/(ProductInfo.length*1000+240):2*1000*60/(ProductInfo.height*1000+240));
			 cap =  cap.setScale(2, RoundingMode.HALF_UP);
			 capacity=  Double.parseDouble( cap.toString());
		}
		
		if(900<ProductInfo.length*1000 && 900< ProductInfo.height*1000) //9
		{
			capacity= 0.0;
			//System.out.println("超过900毫米的产品，超出了钎焊炉的工艺范围");
		}
		if(ProductInfo.length*1000<=0 || ProductInfo.height*1000<=0) //9
		{
			capacity= 0.0;
			//System.out.println("芯体长或者宽必须大于0");
		}
	}	
	
	
	private DBConnect conn=new DBConnect();
	private BigDecimal  cap ;
	public static double capacity;


}
