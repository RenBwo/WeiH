
select a.fitemid,isnull(w.avrprice,isnull(a.fplanprice,0)) as fprice 
from t_icitem  a 
join (select b.fitemid ,sum(b.fstdamount) as funtaxamount
	, sum(b.fqty) as fqty
	,case  when sum(b.fqty)>0 then round(sum(b.fstdamount)/sum(b.fqty),4) else 0 
		end as avrprice
	from icpurchase a 
	join icpurchaseentry b on a.FInterID = b.FInterID
				 and ( a.ftrantype = 75 or a.ftrantype = 76 ) 
	where a.fcheckdate > dateadd(year,-1,dateadd(day,datediff(day,0,getdate()),0)) 
	and isnull(b.fqty , 0)<> 0 and isnull(b.fstdamount ,0) <> 0 and a.fstatus = 1 
	and a.frob =1 and (isnull(fheadselfi0252,0) =0 and isnull(fheadselfi0349,0 ) = 0) 
	group by b.fitemid 
		) w 		on w.fitemid = a.fitemid 
		
/*新的价格取数逻辑201809*/

declare @fitemid int ,@fname varchar(30) ,@fmodel varchar(30),@funitid int
	,@planprice decimal(20,3),@findex int,@fnumber varchar(30)
	
/*
 * 辅料 新的价格取数逻辑201809
 * delete from t_bos200000025entry
 * select *  from  t_bos200000025entry 
 */
declare cursor1 cursor for
select distinct a.fitemid,a.fname,a.fmodel,a.funitid,a.fplanprice,a.fnumber
	from t_icitem 	a 
join  	t_costcalculatebd_entry1 b	on b.faidnumber  = a.fitemid  
where a.fitemid not in (select w.fitemid from (select b.fitemid ,sum(b.fstdamount) as funtaxamount, sum(b.fqty) as fqty,case  
	when sum(b.fqty)>0 then round(sum(b.fstdamount)/sum(b.fqty),4) else 0 end as avrprice  
	from icpurchase a join icpurchaseentry b on a.FInterID = b.FInterID and ( a.ftrantype = 75 
	or a.ftrantype = 76 )  where a.fcheckdate > dateadd(year,-1,dateadd(day,datediff(day,0,getdate())
	,0))  and isnull(b.fqty , 0)<> 0 and isnull(b.fstdamount ,0) <> 0 and a.fstatus = 1  and 
	a.frob =1 and (isnull(fheadselfi0252,0) =0 and isnull(fheadselfi0349,0 ) = 0)  group by b.fitemid 
	) 								w)
and a.fitemid not in (select  x.fitemid from t_supplyentry x where x.fdisabledate >  getdate()  
and x.fprice > 0) order by a.fname,a.fnumber
open cursor1
fetch next from cursor1 into  @fitemid ,@fname,@fmodel ,@funitid,@planprice,@fnumber
--declare @findex int
select @findex=isnull(max(findex) ,0) from   t_bos200000025entry where fid = 1000
--select @findex
while @@fetch_status = 0 
begin
	set @findex=@findex+1
	insert into t_bos200000025entry(fid,findex,fbase,ftext,ftext1,fbase1,fprice,fdate2,fnote)
	values(1000,@findex,@fitemid ,@fname,@fmodel ,@funitid,@planprice,'2019-01-01','system')
	
	fetch next from cursor1 into  @fitemid ,@fname,@fmodel ,@funitid,@planprice,@fnumber
end
close cursor1
deallocate cursor1
	
/*外购物料*/
declare cursor0 cursor  for
select distinct a.fitemid,a.fname,a.fmodel,a.funitid,a.fplanprice,a.fnumber
	from t_icitem 					a	
	join icbomchild b on a.fitemid = b.fitemid 
	join icbom c on c.finterid =b.finterid and c.fusestatus = 1072 and c.fstatus = 1
where a.ferpclsid = 1 and a.fplanprice > 0 
and a.fitemid not in (select w.fitemid from (select b.fitemid ,sum(b.fstdamount) as funtaxamount, sum(b.fqty) as fqty,case  
	when sum(b.fqty)>0 then round(sum(b.fstdamount)/sum(b.fqty),4) else 0 end as avrprice  
	from icpurchase a join icpurchaseentry b on a.FInterID = b.FInterID and ( a.ftrantype = 75 
	or a.ftrantype = 76 )  where a.fcheckdate > dateadd(year,-1,dateadd(day,datediff(day,0,getdate())
	,0))  and isnull(b.fqty , 0)<> 0 and isnull(b.fstdamount ,0) <> 0 and a.fstatus = 1  and 
	a.frob =1 and (isnull(fheadselfi0252,0) =0 and isnull(fheadselfi0349,0 ) = 0)  group by b.fitemid 
	) 								w)
and a.fitemid not in (select  x.fitemid from t_supplyentry x where x.fdisabledate >  getdate()  and x.fprice > 0)
	order by a.fname,a.fnumber
open cursor0
fetch next from cursor0 into @fitemid ,@fname,@fmodel ,@funitid,@planprice,@fnumber
select @findex=isnull(max(findex) ,0) from   t_bos200000025entry where fid = 1000
while @@fetch_status =0
begin
	set @findex=@findex+1
	insert into t_bos200000025entry(fid,findex,fbase,ftext,ftext1,fbase1,fprice,fdate2,fnote)
	values(1000,@findex,@fitemid ,@fname,@fmodel ,@funitid,@planprice,'2019-01-01','system')
	
	fetch next from cursor0 into @fitemid ,@fname,@fmodel ,@funitid,@planprice,@fnumber
end 
close cursor0
deallocate cursor0

		
		
		
		
		