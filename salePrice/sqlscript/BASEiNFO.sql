
--update  ICMaxNum set fmaxnum=1000 where ftablename like 't_bdproductclass'
--truncate table t_bdproductclass
--truncate table t_bdproductclassentry
select * from t_BDProductclass
select * from t_BDProductclassentry
update t_bdproductclassentry set fgainrate = 0.2 where fgainrate = 0
select * from ICMaxNum  where ftablename like 't_bdproductclass'

--exec dbo.GetICMaxNum  't_bdproductclass',@FID output, 1
--select @fid
--检测，如果没有，就新增加一条
declare @fid int,@x int

select row_number() over(order by fitemid) as finterid
,fitemid,fnumber,fname
into #t1
from t_item 
where flevel=2 
and fitemclassid = 4 
and fparentid = 27703 
and funused = 0
set @x=1
while @x < 19
begin
exec dbo.GetICMaxNum  't_bdproductclass',@FID output, 1

insert into t_bdproductclass(fid,fname,fnumber,fparentid,flogic,fdetail,fdiscontinued,flevels,ffullnumber,fclasstypeid)
select @fid,fname,fnumber,0,-1,1,0,1,fnumber,200000020  
from #t1
where finterid =@x
insert into t_bdproductclassentry(fclasstypeid,fid,fnumber,fname,fitemid,fdatestart,fdateend)
 select 200000020,@fid,fnumber,fname,fitemid,'2018-05-01','2018-12-31'
from #t1
where finterid =@x
set @x=@x+1
end



