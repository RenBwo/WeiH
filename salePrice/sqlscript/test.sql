select fcostmake_power,fCostMake_deprication,* from  t_bdStandCostRPT where fcostmake_power = 0

--update icmaxnum set fmaxnum = 0 where FTableName like 't_bdStandCostRPT'
--truncate table t_bdStandCostRPT
--drop table t_bdStandCostRPT
--drop truncate table BDBomMulExpose
//0614  无计划单价
select  b.fitemid,b.fnumber,b.fname  
		 from t_icitem b where b.fitemid not in (select w.fitemid from 
	 (select b.fitemid ,sum(b.fstdamount) as funtaxamount, sum(b.fqty) as fqty
				,case  when sum(b.fqty)>0 then round(sum(b.fstdamount)/sum(b.fqty),4) else 0 end as avrprice 
				 from icpurchase a join icpurchaseentry b on a.FInterID = b.FInterID
				 and ( a.ftrantype = 75 or a.ftrantype = 76 ) 
				 where a.fcheckdate > dateadd(year,-1,dateadd(day,datediff(day,0,getdate()),0)) 
				 and isnull(b.fqty , 0)<> 0 and isnull(b.fstdamount ,0) <> 0 and a.fstatus = 1 
				 and a.frob =1 and (isnull(fheadselfi0252,0) =0 and isnull(fheadselfi0349,0 ) = 0) 
				 group by b.fitemid 
		) w )	
		and b.fdeleted = 0	
		 and b.ferpclsid = 1 and isnull(b.fplanprice,0) = 0 
select count(*) from t_icitem where ferpclsid = 1 and isnull(fplanprice,0) = 0 
		 //0612 exact icprcplyentry and t_bdstandcostrpt
use ais20161026113020
select count(*) from icprcplyentry where fbegdate > '2018-06-11' and finterid = 3
--no prcplyentry finterid = 3
select a.fproditemid,b.fname,b.fnumber,b.fmodel,a.fdate
,round(a.fPrice_China_InTax_Delay,0),round(a.fPrice_FobQD_Delay_USD,2)
 from ais20091217151735.dbo.t_bdstandcostrpt a 
 join t_icitem b on a.fproditemid = b.fitemid 
where a.fdate > '2018-06-11' and a.fproditemid not in (select fitemid
from ais20161026113020.dbo.icprcplyentry where  fbegdate > '2018-06-11' and finterid = 3)
--exact result
select a.fbegdate,a.frelatedid,b.fdate,a.fcuryid,a.fitemid,b.fproditemid
,a.fprice - case a.fcuryid when 1 then round(b.fPrice_China_InTax_Delay,0)
else round(b.fPrice_FobQD_Delay_USD,2) end as diffvalue
,a.fprice
,case a.fcuryid when 1 then round(b.fPrice_China_InTax_Delay,0)
else round(b.fPrice_FobQD_Delay_USD,2) end as stdprice
from ais20161026113020.dbo.icprcplyentry a  join ais20091217151735.dbo.t_bdstandcostrpt b
on a.finterid = 3 and a.fitemid = b.fproditemid and b.fdate > '2018-06-11' 
order by diffvalue desc ,a.fitemid,a.fcuryid,a.frelatedid

select * from BDBomMulExpose where enditem=1 and maketype = 2
select round(f_101,4) from t_item_3015 
select top 10 * from ICMaxNum where FTableName like 't_bdStandCostRPT'

\\0518
select  * from icprcplyentry a join icprcplyentryspec b on a.finterid = a.finterid and a.fitemid = b.fitemid 
and a.frelatedid = b.frelatedid and a.fcuryid <> b.flpricecuryid
--update icprcplyentryspec set flpricecuryid = a.fcuryid
from icprcplyentry a join icprcplyentryspec b on a.finterid = a.finterid and a.fitemid = b.fitemid 
and a.frelatedid = b.frelatedid and a.fcuryid <> b.flpricecuryid

select fpricetype,* from  PORFQEntry where isnull(fpricetype,0) <> 0 


