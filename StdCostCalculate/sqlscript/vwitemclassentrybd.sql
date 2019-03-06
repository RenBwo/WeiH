
create view vwitemclassentrybd 
as 
select 1 as fid,row_number() over(order by fitemid) as fentryid
,fitemid,fnumber,fname,0 as fprofitRate 
from t_item 
where flevel=2 
and fitemclassid = 4 
and fparentid = 27703 
and funused = 0
 
select * from vwitemclassentrybd
drop view vwitemclassentrybd
