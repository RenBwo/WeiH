package bd.connection;

import java.sql.*;

public class getcon {
	//constructor
    public void  kingdee() throws SQLException
	{    	
	String url = "jdbc:sqlserver://192.168.200.5:1433;databaseName="+database;
	String username = "sa";
	String password = "whyb2009";
	con = DriverManager.getConnection(url,username,password);
	state = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	}
    
	public void database(String k3) {
		if (k3.equals("test")) { database="AIS20161026113020";}
    	else {database="AIS20091217151735";}
	}
    //methods
	public ResultSet query(String k3,String sql) throws SQLException {		
		database(k3);
    	kingdee();
    	return state.executeQuery(sql);
    	}
    	
    public void update(String k3,String sql) throws SQLException {    
    	database(k3);
    	kingdee();
    	state.executeUpdate(sql);
    }
    public void close() throws SQLException {
    	con.close();
    }
    //fields
    Connection con;
	public Statement state;
	private String database;
	

}