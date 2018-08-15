package bd.DAO;

import java.sql.SQLException;

import bd.connection.GetDBConnect;

public class BomExpose {

	 private GetDBConnect conn =new GetDBConnect();
 	/* 
   	 * BOM EXPOSE 
   	 */
	public void bomExpose(int firstitemid,int finterid) throws SQLException
	{//conn.update("","TRUNcate table  BDBomMulExpose;");/*清除数据*/	 
		 int level = 0;
		 String cBomExpose = ";with recursive_cte as ("
		 		+ " select "+ firstitemid+ " as firstitemid"
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
		 		+ ","+finterid+" as finterid"
				+ " from icbom a  "
				+ " where a.fusestatus = 1072 and a.fstatus = 1 and a.fitemid = "+firstitemid
				+ " union all "
				+ " select "+ firstitemid+ " as firstitemid "
				+ " ,cast(cast(c.flevel as int)+1 as varchar(101)) as flevel"
				+ " ,a.fitemid as FParentID,b.FItemID as fitemid"
				+ " ,cast(b.fqty as decimal(12,4) ) as fQtyPerPro"
				+ " ,cast(round(b.fqty*c.fqty/(1-b.fscrap*0.01),4)  as decimal(12,4) ) as fQty "
				+ " ,b.funitid as funitid,cast(b.fscrap as decimal(10,4)) as fscrap "
				+ " ,cast(b.fitemsize as varchar(10)) as  fitemsize "
				+ " ,a.FInterID as fbominterid,a.fbomnumber "
				+ " ,cast(c.sn+right('000'+cast(b.fentryid as varchar(20)),3) as varchar(300)) as sn"
				+ " ,a.fstatus,a.fusestatus,cast(c.point+'......' as varchar(101)) as point "
		 		+ " ,"+finterid+" as finterid"
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
		 String upEndItem=";update BDBomMulExpose  set enditem = 1 where firstitemid = "+firstitemid
				+ " and finterid = "+finterid+" and fitemid in ("
		 		+ " select  fitemid  from BDBomMulExpose  where fitemid not in (select fparentid from BDBomMulExpose))"	;			 ;
		 String upbomskip=";update BDBomMulExpose set bomskip = b.fbomskip "
		 		+ " from BDBomMulExpose a join icbom b "
		 		+ " on a.fitemid = b.fitemid and b.fusestatus = 1072 and b.fstatus = 1";
		 String upRoutingID=";update BDBomMulExpose set froutingid=b.froutingid"
		 		+ " from BDBomMulExpose a "
		 		+ " join icbom b on a.fitemid = b.fitemid and a.firstitemid = "+firstitemid
		 		+ " and a.finterid = "+finterid
		 		+ " and b.fusestatus = 1072 and b.fstatus = 1";
		 String upMakeType=";update BDBomMulExpose set maketype=b.ferpclsid"
			 		+ " from BDBomMulExpose a "
			 		+ " join t_icitem b on a.fitemid = b.fitemid and a.firstitemid = "+firstitemid
			 		+ " and a.finterid = "+finterid;
		//System.out.println("bomexpose:::: "+cBomExpose+cInBomEx+cDropTmp+upEndItem+upbomskip+upRoutingID+upMakeType);
		conn.update("",cBomExpose+cInBomEx+cDropTmp+upEndItem+upbomskip+upRoutingID+upMakeType);		 
		conn.close();
	}

}
