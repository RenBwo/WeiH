
create view vwitemclassBD 
as 
select row_number() over(order by fitemid)  as fid
,200000021 as fclasstypeid ,fnumber as fbillno,fitemid,fname
from t_item 
where flevel=1 and fitemclassid = 4 and funused = 0

select * from vwitemclassbd
drop view vwitemclassbd
