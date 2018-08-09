package bd.DAO;

import java.math.BigDecimal;
import java.math.RoundingMode;
/* 
 * 钎焊炉产能 unit 毫米mm
 */
public class GetCapacityQianHanLu 
{
	public double  getCapacityQianHanLu(double len,double hgt)
	{   
		if(400<len && len<=900 && 900<hgt )//1
		{
			capacity = new BigDecimal(1000*60/(hgt+240));
			capacity = capacity.setScale(2, RoundingMode.HALF_UP);
			return Double.parseDouble(capacity.toString());			
		}
		if( 0<len && len<=400 && 900<hgt) //2
		{
			capacity = new BigDecimal(2*1000*60/(hgt+240));
			capacity = capacity.setScale(2, RoundingMode.HALF_UP);
			return  Double.parseDouble(capacity.toString());
		}
		if(900<len && 400<hgt && hgt<=900) //3
		{
			capacity = new BigDecimal(1000*60/(len+240));
			capacity = capacity.setScale(2, RoundingMode.HALF_UP);
			return  Double.parseDouble(capacity.toString());
		}
		if(900<len && 0< hgt && hgt<=400 ) //4
		{
			capacity = new BigDecimal(2*1000*60/(len+240));
			capacity = capacity.setScale(2, RoundingMode.HALF_UP);
			return  Double.parseDouble(capacity.toString());
		}
		if(400<len && len<=900 && 400<hgt  && hgt<=900  ) //5
		{
			capacity = new BigDecimal(1000*60/(hgt+240) > 1000*60/(len+240)?1000*60/(hgt+240):1000*60/(len+240));
			capacity = capacity.setScale(2, RoundingMode.HALF_UP);
			return  Double.parseDouble(capacity.toString());
		}
		if(0<len && len<=400 && 400<hgt  && hgt<=900  ) //6
		{
			capacity = new BigDecimal(2*1000*60/(hgt+240) > 1000*60/(len+240)?2*1000*60/(hgt+240):1000*60/(len+240));
			capacity = capacity.setScale(2, RoundingMode.HALF_UP);
			return  Double.parseDouble(capacity.toString());
		}
		if(400<len  && len<=900 && 0< hgt && hgt<=400 ) //7
		{
			capacity = new BigDecimal(2*1000*60/(len+240) > 1000*60/(hgt+240)?2*1000*60/(len+240):1000*60/(hgt+240));
			capacity = capacity.setScale(2, RoundingMode.HALF_UP);
			return  Double.parseDouble(capacity.toString());
		}
		if( 0<len && len<=400 &&  0<hgt &&  hgt<=400 ) //8
		{
			capacity = new BigDecimal(2*1000*60/(len+240) > 2*1000*60/(hgt+240)?2*1000*60/(len+240):2*1000*60/(hgt+240));
			capacity = capacity.setScale(2, RoundingMode.HALF_UP);
			return  Double.parseDouble(capacity.toString());
		}
		
		if(900<len && 900< hgt) //9
		{
			return 0.0;
			//System.out.println("超过900毫米的产品，超出了钎焊炉的工艺范围");
		}
		if(len<=0 || hgt<=0) //9
		{
			return 0.0;
			//System.out.println("芯体长或者宽必须大于0");
		}
		return cap;
	}
	private BigDecimal capacity ;
	private double cap=0.0;	
}
