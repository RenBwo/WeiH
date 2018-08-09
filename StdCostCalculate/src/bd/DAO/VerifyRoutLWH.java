package bd.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import bd.connection.getcon;

public class VerifyRoutLWH {	
	/*
	 * 钎焊焊接工艺40336产能： 	根据产品不同计算 
	 * 扁管压出工艺40494产能： 	根据产品不同计算 60*扁管压出速度/扁管长度（坯料尺寸）
	 * 扁管切断工艺 40495产能：	根据芯体长度不同计算
	 * 400mm < l 			then 7800 pc/h
	 * 400mm <= l <600mm	then 6600 pc/h
	 * 600mm <= l <800mm	then 5400 pc/h
	 * 800mm <= l <1000mm	then 4200 pc/h
	 * 1000mm <= l 		then 2400 pc/h
	 * 芯体烘干喷漆 40142 辅料用量：集流管长<=200MM，用量为普通产品的1/2
	 * 这三个工艺要用芯体的长或宽或体积，不能为0
	 */
	public int verifyRoutLWH(int firstitemid,int finterid
			,double length,double width,double height) 
			throws SQLException
	{
			String cmdLWH =";select count(*) from BDBomMulExpose  a "
				+ " join t_routing b on a.froutingid = b.finterid "
				+ " and a.firstitemid = "+firstitemid +" and a.finterid = "+finterid
				+ " join t_routingoper c on c.finterid = b.finterid"
				+ " and (isnull(c.foperid,0) = 40142 or isnull(c.foperid,0) = 40336"
				+ " or isnull(c.foperid,0) = 40494 or isnull(c.foperid,0) = 40495)";
		rs0 = conn.query("",cmdLWH);
		if(rs0.next() && (rs0.getInt(1) >0)
			&& ( length <=0||width <=0 || height <=0)) 
		{
		conn.close();
		return 1;		
		}
		else 
		{
		conn.close();
		return 0;		
		}
	
	}
	private getcon conn =new getcon();
	private ResultSet 	rs0;

}
