package bd.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import bd.connection.getcon;

public class GetLenWidHgt {
	private getcon conn =new getcon();
	private ResultSet 	rs0;	
	/* 
	 * 芯体长宽 单位 米	
	 * 长：喷锌扁管'30.%' 下级物料扁管盘料 --03.a18.%--的坯料尺寸
	 * 宽：集流管--'%集流管%'--下级物料圆铝--'03.a01.%' or '03.a19.%--用量/圆铝米重
	 */
	public double Len(int firstitemid,int finterid) throws SQLException
	{
			rs0= conn.query("","; select isnull(a.f_173,0) as len"
					+ " from t_icitemcustom a join BDBomMulExpose b on a.fitemid = b.fitemid"
					+ " and b.firstitemid = "+firstitemid+ " and b.finterid = "+finterid
					+ " join t_icitemcore c on a.fitemid = c.fitemid and c.fnumber like '13.%'"
					+ " ");
			if(rs0.next()) 
			{
				return rs0.getDouble(1);
				}
			else
			{
				conn.close();
				return 0;
			}
	}

	public double Wid(int firstitemid,int finterid) throws SQLException
	{
		rs0= conn.query("","; select isnull(a.f_174,0) as wid"
						+ " from t_icitemcustom a join BDBomMulExpose b on a.fitemid = b.fitemid"
						+ " and b.firstitemid = "+firstitemid+ " and b.finterid = "+finterid
						+ " join t_icitemcore c on a.fitemid = c.fitemid and c.fnumber like '13.%'"
						+ " ");
				if(rs0.next()) 
				{
					return rs0.getDouble(1);
				}
				else 
				{
					conn.close();
					return 0.0;
				}
				}
	public double Hgt(int firstitemid,int finterid) throws SQLException
	{
				rs0= conn.query("","; select isnull(a.f_177 ,0) as height "
						+ " from t_icitemcustom a join BDBomMulExpose b on a.fitemid = b.fitemid"
						+ " and b.firstitemid = "+firstitemid+ " and b.finterid = "+finterid
						+ " join t_icitemcore c on a.fitemid = c.fitemid and c.fnumber like '13.%'"
						+ " ");
				if(rs0.next()) 
				{
					return rs0.getDouble(1);
				}
				else
				{
					conn.close();
					return 0.0;
				}
		}
}
/*
 * 
 *  the old method

	if (length == 0 || height==0)
	{	rs0 = conn.query("",";select max(a.fitemsize) "
				+ " from BDBomMulExpose a "
				+ " join t_icitem b on a.fparentid  = b.fitemid and a.firstitemid =  "+firstitemid+" "
				+ " and a.finterid = "+finterid
				+ " where b.fnumber like '30.%' and isnull(a.fitemsize,'0') > '0' and "
				+ " a.fitemid in  (select fitemid from t_icitem where fnumber like '03.a18.%')");
		if(rs0.next()) {	
		length = rs0.getDouble(1);	//System.out.println("裸包产品长： "+length);
		}
		rs0 = conn.query("","; select max(round(a.fqtyper/c.f_140,4)) as y "
				+ " from BDBomMulExpose a "
				+ " join t_icitem b on a.fparentid  = b.fitemid and a.firstitemid = "+firstitemid
				+" and a.finterid = "+finterid
				+ " join t_icitemcustom c on c.fitemid = a.fitemid "
				+ " where b.fname like '%集流管%' and "
				+ " isnull(c.f_140,0) >0 and isnull(a.fqtyper,'0') > '0' and "
				+ " a.fitemid in (select fitemid from t_icitem"
				+ " where fnumber like '03.a01.%' or fnumber like '03.a19.%')");
		if(rs0.next()) {
		height = rs0.getDouble(1);//System.out.println("裸包产品高： "+ height);
		}
		String upLenHgt = "; update t_icitemcustom set f_173 = "+length+" ,f_177 = "+height
				+ "  from t_icitemcustom a join BDBomMulExpose b "
				+ " on a.fitemid = b.fitemid and b.firstitemid = "+firstitemid
				+ " and b.finterid = "+finterid
				+ " join t_icitemcore c on a.fitemid = c.fitemid and c.fnumber like '13.%'";
		conn.update("",upLenHgt );
		System.out.println("升级芯体长高： "+ upLenHgt);	
		  
	}
	*/


