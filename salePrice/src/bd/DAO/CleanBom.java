package bd.DAO;

import java.sql.SQLException;

import bd.connection.getcon;

public class CleanBom {
	private getcon conn =new getcon();
   	/*
   	 * clean bomExpose
   	 */
   	public void cleanBom(int firstitemid,int finterid) throws SQLException {
   	 String cCleanBom=";delete from BDBomMulExpose where  firstitemid = "+firstitemid 
				+" and finterid = "+finterid ;
	 conn.update("",cCleanBom);
	 conn.close();
   	}

}
