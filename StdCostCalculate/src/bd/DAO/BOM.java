package bd.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BOM {
	/*
	* create BOM多级展开表 BDBomMulExpose
	*/
	public void createTableBOM() throws SQLException
	{ 
		rs0 = conn.query("",";select count(*) from sysobjects where type = 'u' and name like 'BDBomMulExpose'");
		if(rs0.next() && rs0.getInt(1) >0 ) 
		{
			//System.out.println("table_bom exists ");
		}
		else
		{ 
			String command1 = ";CREATE TABLE BDBomMulExpose"
					+ "("
					+ "firstitemid int"
					+ ",flevel varchar(101)"
					+ ",FParentID int"
					+ ",FItemID int"
					+ ",fQtyPer decimal(10,4)"
					+ ",fQty decimal(10,4)"
					+ ",funitid int"
					+ ",fscrap decimal(10,4)"
					+ ",fitemsize varchar(10)"
					+ ",fbominterid int"
					+ ",fbomnumber varchar(20)"
					+ ",sn varchar(300)"
					+ ",fstatus int"
					+ ",fusestatus int"
					+ ",flevel0 varchar(101)"
					+ ",froutingid int"
					+ ",finterid int"
					+ ",enditem int"
					+ ",maketype int"
					+ ",bomskip int"
					+ ")" ;
		
			conn.update("",command1);
			conn.close();
			//System.out.println("create table_bom success ");
		}
	}
	/* 
  	 * BOM EXPOSE 
  	 */
	public void bomExpose() throws SQLException
	{//conn.update("","TRUNcate table  BDBomMulExpose;");/*清除数据*/	 
		 int level = 0;
		 String cBomExpose = ";with recursive_cte as ("
		 		+ " select "+ ProductInfo.firstitemid+ " as firstitemid"
		 		+ ",cast("+level+ " as varchar(101)) as flevel "
		 		+ ",0 as FParentID,a.FItemID as fitemid"
		 		+ ",cast(1 as decimal(12,4) ) as fQtyPerPro "
		 		+ ",cast(1  as decimal(12,4) )as fQty"
		 		+ ",a.funitid as funitid "
		 		+ ",cast(0 as decimal(10,4) ) as fscrap "
		 		+ ",cast('' as varchar(10)) as  fitemsize"
		 		+ ",a.FInterID as fbominterid"
		 		+ ",a.fbomnumber"
		 		+ ",cast('1' as varchar(300)) as sn"
		 		+ ",a.fstatus,a.fusestatus , cast ('.' as varchar(101)) as point"
		 		+ ","+ProductInfo.finterid+" as finterid"
				+ " from icbom a  "
				+ " where a.fusestatus = 1072 and a.fstatus = 1 and a.fitemid = "+ProductInfo.firstitemid
				+ " union all "
				+ " select "+ ProductInfo.firstitemid+ " as firstitemid "
				+ " ,cast(cast(c.flevel as int)+1 as varchar(101)) as flevel"
				+ " ,a.fitemid as FParentID,b.FItemID as fitemid"
				+ " ,cast(b.fqty as decimal(12,4) ) as fQtyPerPro"
				+ " ,cast(round(b.fqty*c.fqty/(1-b.fscrap*0.01),4)  as decimal(12,4) ) as fQty "
				+ " ,b.funitid as funitid,cast(b.fscrap as decimal(10,4)) as fscrap "
				+ " ,cast(b.fitemsize as varchar(10)) as  fitemsize "
				+ " ,a.FInterID as fbominterid,a.fbomnumber "
				+ " ,cast(c.sn+right('000'+cast(b.fentryid as varchar(20)),3) as varchar(300)) as sn"
				+ " ,a.fstatus,a.fusestatus,cast(c.point+'......' as varchar(101)) as point "
		 		+ " ,"+ProductInfo.finterid+" as finterid"
				+ " from icbom a join icbomchild b on a.finterid = b.finterid and a.fusestatus = 1072 "
				+ " and a.fstatus = 1 "
				+ " join recursive_cte c on a.fitemid = c.fitemid "
				+ " where a.fitemid = c.fitemid "
				+ " ) "
				+ " select * into #bom from recursive_cte  order by sn  ";
		 String cInBomEx = ";insert into BDBomMulExpose(firstitemid,flevel,flevel0,FParentID"
			 		+ " ,FItemID,fQtyPer,fQty,funitid,fscrap,fitemsize,fbominterid,fbomnumber"
			 		+ ",sn,fstatus,fusestatus,finterid)" 
				    + " select firstitemid,flevel,point+flevel,FParentID"
				    + ", FItemID,fQtyPerpro,fQty,funitid,fscrap,fitemsize, fbominterid,fbomnumber"
				    + ",sn,fstatus,fusestatus,finterid"
			 		+ " from #bom ";
		 String cDropTmp=";drop table #bom"; 
		 String upEndItem=";update BDBomMulExpose  set enditem = 1 where firstitemid = "+ProductInfo.firstitemid
				+ " and finterid = "+ProductInfo.finterid+" and fitemid in ("
		 		+ " select  fitemid  from BDBomMulExpose  where fitemid not in (select fparentid from BDBomMulExpose))"	;			 ;
		 String upbomskip=";update BDBomMulExpose set bomskip = b.fbomskip "
		 		+ " from BDBomMulExpose a join icbom b "
		 		+ " on a.fitemid = b.fitemid and b.fusestatus = 1072 and b.fstatus = 1";
		 String upRoutingID=";update BDBomMulExpose set froutingid=b.froutingid"
		 		+ " from BDBomMulExpose a "
		 		+ " join icbom b on a.fitemid = b.fitemid and a.firstitemid = "+ProductInfo.firstitemid
		 		+ " and a.finterid = "+ProductInfo.finterid
		 		+ " and b.fusestatus = 1072 and b.fstatus = 1";
		 String upMakeType=";update BDBomMulExpose set maketype=b.ferpclsid"
			 		+ " from BDBomMulExpose a "
			 		+ " join t_icitem b on a.fitemid = b.fitemid and a.firstitemid = "+ProductInfo.firstitemid
			 		+ " and a.finterid = "+ProductInfo.finterid;
		//System.out.println("bomexpose:::: "+cBomExpose+cInBomEx+cDropTmp+upEndItem+upbomskip+upRoutingID+upMakeType);
		conn.update("",cBomExpose+cInBomEx+cDropTmp+upEndItem+upbomskip+upRoutingID+upMakeType);		 
		conn.close();
	}

	/* 
	 * clean bom
	 */
	public void clean() throws SQLException
	{
			String cCleanBom=";delete from BDBomMulExpose "			   		
   	 		+" where  firstitemid = "+ProductInfo.firstitemid 
   	 		+" and finterid = "+ProductInfo.finterid ;
   			conn.update("",cCleanBom);
   			conn.close();
	}
	/*
	 * BOM integrity Verify	 最终物料并且加工类型为自制的，说明BOM不完整
	 */
	public int verifyBOM() throws SQLException
	{
		String cmdverify =";select count(*) from BDBomMulExpose where enditem = 1 and maketype = 2"
				+ " and firstitemid = "+ProductInfo.firstitemid +" and finterid = "+ProductInfo.finterid;
		rs0 = conn.query("",cmdverify);
		if(rs0.next() && rs0.getInt(1) >0) 
		{conn.close();
		return 1;
		}
		else
		{ conn.close();
		return 0;
		}
		
	}
	private ResultSet 	rs0;
	private DBConnect conn =new DBConnect();
}
