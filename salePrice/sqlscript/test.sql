--update icmaxnum set fmaxnum = 0 where FTableName like 't_bdStandCostRPT'
--truncate table t_bdStandCostRPT
--drop table  t_bdStandCostRPT
--truncate table BDBomMulExpose
--truncate table t_BDLabourAndMake
select count(*) from t_bdlabourandmake
delete from t_BDLabourAndMake where fproditemid = 215525 and finterid < 68814
