--drop table 	BDStdCostProgVersion
--drop table 	BDBomMulExpose
--drop table  	t_costmaterialbd
--drop table  	t_BDLabourAndMake
--drop table  	t_bdStandCostRPT 

--truncate table BDStdCostProgVersion
--truncate table BDBomMulExpose
--truncate table t_costmaterialbd
--truncate table t_BDLabourAndMake
--truncate table t_bdStandCostRPT

--update icmaxnum set fmaxnum = 0 where FTableName like 't_bdStandCostRPT'

--select * from BDStdCostProgVersion
--select * from BDBomMulExpose
--select * from t_costmaterialbd
--select * from t_BDLabourAndMake
--select * from t_bdStandCostRPT

select count(*) from t_bdlabourandmake
select  datediff(day,getdate(),'2018-08-01') from BDStdCostProgVersion  

select * from icclasstableinfo where fclasstypeid = '1007006'
select * from sys.objects where name like '%action%' and type like 'U'
/*
 * icbillaction
 * icclassaction
 * icclassactionlist
 */
select * from icclassaction
select * from icclassactionlist where fclasstypeid = 1007006
select round(f_101,6)/100 from t_item_3015 where fnumber like 'k15'
select * from t_CostCalculateBD 
select * from t_costcalculatebd_entry1 


select fbase,count(*) from t_bos200000025entry where  fdate2 > getdate() group by fbase
delete from t_bos200000025entry where fbase = 317157 and fdate2 > getdate() and fid =1024 and fentryid = 14417

 update t_icitemstandard set fstandardcost=0
 where fitemid in(select distinct fitemid from icprcplyentry
 where fprice = 9000 and finterid = 3 )
  
 with recursive_cte as ( select 150626 as firstitemid,cast(0 as varchar(101)) as flevel ,0 as FParentID,a.FItemID as fitemid,cast(1 as decimal(12,4) ) as fQtyPerPro ,cast(1  as decimal(12,4) )as fQty,a.funitid as funitid ,cast(0 as decimal(10,4) ) as fscrap ,cast('' as varchar(10)) as  fitemsize,a.FInterID as fbominterid,a.fbomnumber,cast('1' as varchar(300)) as sn,a.fstatus,a.fusestatus , cast ('.' as varchar(101)) as point,7590 as finterid from icbom a   where a.fusestatus = 1072 and a.fstatus = 1 and a.fitemid = 150626 union all  select 150626 as firstitemid  ,cast(cast(c.flevel as int)+1 as varchar(101)) as flevel ,a.fitemid as FParentID,b.FItemID as fitemid ,cast(b.fqty as decimal(12,4) ) as fQtyPerPro ,cast(round(b.fqty*c.fqty/(1-b.fscrap*0.01),4)  as decimal(12,4) ) as fQty  ,b.funitid as funitid,cast(b.fscrap as decimal(10,4)) as fscrap  ,cast(b.fitemsize as varchar(10)) as  fitemsize  ,a.FInterID as fbominterid,a.fbomnumber  ,cast(c.sn+right('000'+cast(b.fentryid as varchar(20)),3) as varchar(300)) as sn ,a.fstatus,a.fusestatus,cast(c.point+'......' as varchar(101)) as point  ,7590 as finterid from icbom a join icbomchild b on a.finterid = b.finterid and a.fusestatus = 1072  and a.fstatus = 1  join recursive_cte c on a.fitemid = c.fitemid  where a.fitemid = c.fitemid  )  select * into #bom from recursive_cte  order by sn 
 insert into BDBomMulExpose(firstitemid,flevel,flevel0,FParentID ,FItemID,fQtyPer,fQty,funitid,fscrap,fitemsize,fbominterid,fbomnumber,sn,fstatus,fusestatus,finterid) select firstitemid,flevel,point+flevel,FParentID, FItemID,fQtyPerpro,fQty,funitid,fscrap,fitemsize, fbominterid,fbomnumber,sn,fstatus,fusestatus,finterid from #bom 
 drop table #bom
 update BDBomMulExpose  set enditem = 1 where firstitemid = 150626 and finterid = 7590 and fitemid in ( select  fitemid  from BDBomMulExpose  where fitemid not in (select fparentid from BDBomMulExpose))
 update BDBomMulExpose set bomskip = b.fbomskip  from BDBomMulExpose a join icbom b  on a.fitemid = b.fitemid and b.fusestatus = 1072 and b.fstatus = 1
 update BDBomMulExpose set froutingid=b.froutingid from BDBomMulExpose a  join icbom b on a.fitemid = b.fitemid and a.firstitemid = 150626 and a.finterid = 7590 and b.fusestatus = 1072 and b.fstatus = 1
 update BDBomMulExpose set maketype=b.ferpclsid from BDBomMulExpose a  join t_icitem b on a.fitemid = b.fitemid and a.firstitemid = 150626 and a.finterid = 7590
  
 select  *
 from bdbommulexpose 
 
  select  a.fitemid,A.FROUTINGID,b.finterid
 from bdbommulexpose a 
 join t_routing b on a.froutingid = b.finterid
 join t_routingoper c 
 on c.finterid = b.finterid
 and isnull(c.foperid,0) = 40142
 --year
update icprcplyentry set  fprice = 9000  from icprcplyentry 
where finterid = 3  --and fprice <> 0 
and fitemid not in (select b.fitemid 
from t_icitem b join icbom c on c.fitemid= b.fitemid
and c.fstatus = 1 and c.fusestatus = 1072 
and b.fnumber like '01.%')

update t_icitemstandard  
set fstandardcost= 0 
from t_icitemstandard a
join t_icitemcore b on a.fitemid = b.fitemid and b.fnumber like '01.%' 
where a.fstandardcost <> 0
 and a.fitemid not in (
select b.fitemid from t_icitem b 
join icbom c on c.fitemid= b.fitemid 
and c.fstatus = 1 and c.fusestatus = 1072
and b.fnumber like '01.%')

