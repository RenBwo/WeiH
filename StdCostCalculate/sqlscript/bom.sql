
with recursive_cte as ( select 28788  as firstitemid,cast(0 as varchar(101)) as flevel 
,0 as FParentID,a.FItemID as fitemid,cast(1 as decimal(12,4) ) as fQtyPerPro 
,cast(1  as decimal(12,4) )as fQty,a.funitid as funitid 
,cast(0 as decimal(10,4) ) as fscrap ,cast('' as varchar(10)) as  fitemsize
,a.FInterID as fbominterid,a.fbomnumber,cast('1' as varchar(300)) as sn
,a.fstatus,a.fusestatus , cast ('.' as varchar(101)) as point,17 as finterid 
, 0 as enditem
from icbom a   
where a.fusestatus = 1072 and a.fstatus = 1 and a.fitemid = 28788 
union all  
select 28788 as firstitemid  ,cast(cast(c.flevel as int)+1 as varchar(101)) as flevel 
,a.fitemid as FParentID,b.FItemID as fitemid ,cast(b.fqty as decimal(12,4) ) as fQtyPerPro
,cast(round(b.fqty*c.fqty/(1-b.fscrap*0.01),4)  as decimal(12,4) ) as fQty  ,b.funitid as funitid
,cast(b.fscrap as decimal(10,4)) as fscrap  ,cast(b.fitemsize as varchar(10)) as  fitemsize  
,a.FInterID as fbominterid,a.fbomnumber  
,cast(c.sn+right('000'+cast(b.fentryid as varchar(20)),3) as varchar(300)) as sn 
,a.fstatus,a.fusestatus,cast(c.point+'......' as varchar(101)) as point  
,17 as finterid ,0 as enditem
from icbom a 
join icbomchild b on a.finterid = b.finterid and a.fusestatus = 1072  and a.fstatus = 1  
join recursive_cte c on a.fitemid = c.fitemid  
where a.fitemid = c.fitemid  )  
select *  into #bom from recursive_cte  order by sn 


update #bom set enditem = 1 where fitemid in (
select  fitemid  from #bom  where fitemid not in (select fparentid from #bom))

select a.*,b.ferpclsid,c.fname from #bom a join t_icitem b on a.fitemid = b.fitemid 
join t_submessage c on c.finterid = b.ferpclsid
 
select count(*) from #bom a join t_icitem b on a.fitemid = b.fitemid  where a.enditem = 1 and b.ferpclsid = 2
/////////////////////









