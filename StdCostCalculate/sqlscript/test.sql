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








