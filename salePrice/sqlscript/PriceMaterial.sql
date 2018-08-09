
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