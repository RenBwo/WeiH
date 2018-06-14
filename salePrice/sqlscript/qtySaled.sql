select top 100 left(fmodel,5),count(*) from t_icitem  where fnumber like '01.%' GROUP BY left(fmodel,5) 
//10341 14
select fnumber,fname,fmodel from t_icitem  where fnumber like '01.%'  and fmodel like '10341%' order by fmodel

select left(c.fmodel,5),sum(fqty) from icsale a join icsaleentry b on a.finterid = b.finterid
and a.fstatus > 0 and a.fcancellation = 0  join t_icitem c on c.fitemid = b.fitemid and left(c.fmodel,5) = '14319'
group by left(c.fmodel,5)

;select sum(fqty) from icsale a join icsaleentry b  on a.finterid = b.finterid and a.fstatus > 0 and a.fcancellation = 0   
join t_icitem c on c.fitemid = b.fitemid and left(c.fmodel,5) = left(14319-3015,5) group by left(c.fmodel,5)


