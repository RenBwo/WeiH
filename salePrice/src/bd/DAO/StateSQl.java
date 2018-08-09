package bd.DAO;

public class StateSQl {
	String export,sqlMaterial,sqlLabourAndMake,sqlPriceRPTform,sqlEnergy,sqlAdi,sqlModel,sqlBOM,sqlProductDevRpt;

	public String getSQLStatement(String sqlname,String currentyear,String currentperiod
			,int firstitemid,int finterid,double capacity0,double length,double width
			,double powerprice) {
	export = "";

	sqlMaterial = ";select a.fnumber,b.fname,b.fmodel,c.fname,a.fqtyper,a.fqty,a.fprice,a.famtmaterial"
			+ ",w.avrprice,b.fplanprice "
			+ " from t_CostMaterialBD a join t_icitem b on a.fitemid = b.fitemid"
			+ " left join t_measureunit c on c.fmeasureunitid = b.funitid"
			+ " left join  (select b.fitemid,sum(b.fstdamount) as funtaxamount, sum(b.fqty) as fqty"
					+ ",case  when sum(b.fqty)>0 then round(sum(b.fstdamount)/sum(b.fqty),4) else 0 end as avrprice "
					+ " from icpurchase a join icpurchaseentry b on a.FInterID = b.FInterID"
					+ " and ( a.ftrantype = 75 or a.ftrantype = 76 ) "
					+ " where a.fcheckdate > dateadd(year,-1,dateadd(day,datediff(day,0,getdate()),0)) "
					+ " and isnull(b.fqty , 0)<> 0 and isnull(b.fstdamount ,0) <> 0 and a.fstatus = 1 "
					+ " and a.frob =1 and (isnull(fheadselfi0252,0) =0 or isnull(fheadselfi0349,0 ) = 0) "
					+ " group by b.fitemid) w 		on w.fitemid = a.fitemid "
			+ " where a.fproditemid = "+firstitemid+" and a.finterid = "+finterid
			+ " order by b.fnumber";

	
	sqlEnergy =" ;select t4.foperid,t4.fopersn,t2.fnumber ,t2.fname as assyname,"
			+" (case isnull((select 1 from t_icitem where fitemid = t1.fitemid and fname like '扁管盘料'),0) "
			+" when 1 then round(t1.fqty*(100-t1.fscrap)/100/t1.fqtyper,0) else t1.fqty end) as fqty,"
			+" t5.fname as opername,t4.fpiecerate,t4.fentryselfz0236 as frate, "
			+" t4.fentryselfz0237 as fmakeqty, "
			+" t6.fmanName,t6.fpower,"
			
			/*产能*/					
			+"isnull((case t4.foperid when 40336 then " + capacity0   
			/*钎焊炉工艺 40336*/
			+ " when 40494 then round(t11.fpressrate*60/"+length+",4)" 
			/*扁管压出工艺40494*/
			+ " when 40495 then (case  when " + length*1000 +" < 400 then 7800 " 
															+ " when " + length*1000 +">=400 and "+length*1000 +"< 600 then 6600" 
															+ " when " + length*1000 +">=600 and "+length*1000 +"< 800 then 5400"
															+ " when " + length*1000 +">=800 and "+length*1000 +"< 1000 then 4200" 
															+ " else  2400 end )" 
			/*扁管切断工艺 40495*/
			+ " else round(t6.fcapacity/(case  when isnull(t4.fentryselfz0236,0)>0 then t4.fentryselfz0236 else 1 end),4) end) "
			/*其余工艺*/
			+ " ,0 ) as fcap,  " 					
			+" t6.funit,"
			
			 /*电费*/
			+"isnull("
			+" (case isnull((select 1 from t_icitem where fitemid = t1.fitemid and fname like '扁管盘料'),0) "
			+" when 1 then round(t1.fqty*(100-t1.fscrap)/100/t1.fqtyper,0) else t1.fqty end)"
			+ "*t4.fentryselfz0237*t6.fpower*0.4*"+powerprice+"/"
			/*钎焊炉工艺 40336*/
			+ "(case t4.foperid when 40336 then " + capacity0   
			/*扁管压出工艺40494*/
			+ " when 40494 then round(t11.fpressrate*60/"+length+",4)" 
			/*扁管切断工艺 40495*/
			+ " when 40495 then (case  when " + length*1000 +" < 400 then 7800 "
									+ " when " + length*1000 +">=400 and "+length*1000 +"< 600 then 6600" 
									+ " when " + length*1000 +">=600 and "+length*1000 +"< 800 then 5400"
									+ " when " + length*1000 +">=800 and "+length*1000 +"< 1000 then 4200" 
									+ " else  2400 end )" 								
			/*其余工艺*/
			+ " else round(t6.fcapacity/(case  when isnull(t4.fentryselfz0236,0)>0 then t4.fentryselfz0236 else 1 end),4) end) "
			+ " ,0 ) as famtpower,  "
			
			/*月折旧额*/
			+" round(t6.fdepreciation/t101.fnum,4) ,"  
			
			/*分摊折旧*/
			+" isnull("
			+" (case isnull((select 1 from t_icitem where fitemid = t1.fitemid and fname like '扁管盘料'),0) "
			+" when 1 then round(t1.fqty*(100-t1.fscrap)/100/t1.fqtyper,0) else t1.fqty end)"
			+ "*t4.fentryselfz0237*t6.fdepreciation/( t101.fnum*30*8*"
			/*钎焊炉工艺 40336*/
			+ "(case t4.foperid when 40336 then " + capacity0        
			/*扁管压出工艺40494*/
			+ " when 40494 then round(t11.fpressrate*60/"+length+",4)" 
			/*扁管切断工艺 40495*/
			+ " when 40495 then (case "														 
			 		+ " when " + length*1000 +" < 400 then 7800 "  
			 		+ " when " + length*1000 +">=400 and "+length*1000 +"< 600 then 6600" 
			 		+ " when " + length*1000 +">=600 and "+length*1000 +"< 800 then 5400"  
			 		+ " when " + length*1000 +">=800 and "+length*1000 +"< 1000 then 4200"  
			 		+ " else  2400 end )" 								
			/*其余工艺*/
			+ " else round(t6.fcapacity/(case  when isnull(t4.fentryselfz0236,0)>0 then t4.fentryselfz0236 else 1 end),4) end) "
			+ "),0) as fdepr, " 
			+" t6.fassetInterId,t101.fassetnumber,"+length+","+width
			+" from BDBomMulExpose t1  "
			+" join t_icitem t2 on t1.fitemid = t2.fitemid and t1.firstitemid = "+firstitemid+" and t1.finterid = "+finterid
			+" join t_Routing t3 on t3.finterid = t1.froutingid"
			+" join t_routingoper t4 on t3.finterid = t4.finterid "		
			+" left join t_submessage t5 on t5.finterid = t4.foperid	and t5.fparentid = 61 "		
			+" left join (select b.fassetinterid,a.foperno,b.fmanname,b.fpower,b.fcapacity,b.fdepreciation,b.funit"
			+ " from t_CostCalculateBD	a join	t_costcalculatebd_entry0 b on a.fid = b.fid	"
			+ ")		t6		on t6.foperno = t4.foperid "		
			+" left  join (select a.fassetid,max(a.falterid) as falterid,b.fnum,b.fassetnumber"
			+ " from t_FAAlter a join t_facard b on a.falterid = b.falterid and b.fnum > 0"
			+ " group by a.fassetid,b.fnum,b.fassetnumber    ) t101 on t6.fassetinterid = t101.fassetid "
			+" left join t_costcalculate_flatpipe   t11		on t11.fpipenumber = t1.fitemid "
			+"and t11.fcheckbox = 0"	
			+" where t2.ferpclsid <> 1 "
			+ " and t1.fqty > 0 "
			+ " and  t1.sn not in (select a.sn  from BDBomMulExpose 	a  "
					+ "	join t_icitem b on a.fitemid = b.fitemid  and b.ferpclsid <> 1 and a.firstitemid = "+firstitemid
					+ " and a.finterid = "+finterid
					+ " join (select a.sn from BDBomMulExpose  a join icbom b  "
						+ " on a.fitemid = b.fitemid  and a.firstitemid = "+firstitemid+" and a.finterid = "+finterid
						+" and b.fbomskip = 1058 "
						+ " and b.fusestatus = 1072 )   a0 	on a.sn  like a0.sn+'%')  " 
			+" order by t2.fnumber ,t4.fopersn "	;
	
	sqlAdi="; select t4.foperid,t4.fopersn,t2.fnumber ,t2.fname as assyname,"
			+ " (case isnull((select 1 from t_icitem where fitemid = t1.fitemid and fname like '扁管盘料'),0) "
				+ " when 1 then round(t1.fqty*(100-t1.fscrap)/100/t1.fqtyper,0) else t1.fqty end) as fqty,"
			+ " t5.fname as opername,t4.fpiecerate,t4.fentryselfz0236 as frate, "
			+ " t4.fentryselfz0237 as fmakeqty, t6.faidname,t6.fqty,isnull(t6.fprice,0) as fprice,"
			+ " isnull("
				+ " (case isnull((select 1 from t_icitem where fitemid = t1.fitemid and fname like '扁管盘料'),0) "
				+ " when 1 then round(t1.fqty*(100-t1.fscrap)/100/t1.fqtyper,0) else t1.fqty end)"
				+ "*t4.fentryselfz0237*t6.Fprice*(case "
				+ " when (t4.foperid = 40336 and (t6.faidname like '%钎剂%' or  t6.faidname like'%液氮%')) "
				+ " then t6.fqty/"+capacity0	
				+ " when (t4.foperid = 40494 and t6.faidname like '%锌丝%' ) then t11.fqtyzn/1000*"+length
				+ " when (t4.foperid = 40142 and "+length +"<=0.2 ) then t6.fqty/2 else t6.fqty end),0) as famtAdi, "
	        + "w.avrprice,t110.fplanprice,"+length+","+width 
	        + " from BDBomMulExpose 				t1  "
			+ " join t_icitem 						t2 		on t1.fitemid = t2.fitemid and t1.firstitemid ="+firstitemid
			+ " and t1.finterid = "+finterid
	        + " join t_Routing 						t3 		on t3.finterid = t1.froutingid"
	        + " join t_routingoper 					t4 		on t3.finterid = t4.finterid "		
	        + " left join t_submessage 				t5 		on t5.finterid = t4.foperid	and t5.fparentid = 61 "		
	        + " left join (select a.foperno,b.fcolor,b.faidname,b.faidnumber,b.fqty,b.fprice "
	        		+ " from t_CostCalculateBD	a join	t_costcalculatebd_entry1 b on a.fid = b.fid"
	        + ") 									t6 		on t6.foperno = t4.foperid "		
	        + " and (t6.fcolor = 0 or (t6.fcolor = t2.f_135"
	        		+ " and t2.fitemid = (select t34.fitemid from t_icitemcore 	t33 "
	        			+ " join BDBomMulExpose t34 on t34.fitemid = t33.fitemid "
	        			+ " and t34.firstitemid = "+firstitemid+" and t34.finterid="+finterid+" and t33.fnumber like '13.%' ))) "		
	        + " left join t_costcalculate_flatpipe  t11		on t11.fpipenumber = t1.fitemid  and t11.fcheckbox = 0"
	        + " left join  (select b.fitemid,sum(b.fstdamount) as funtaxamount, sum(b.fqty) as fqty"
					+ ",case  when sum(b.fqty)>0 then round(sum(b.fstdamount)/sum(b.fqty),4) else 0 end as avrprice "
					+ " from icpurchase 		a "
					+ "	join icpurchaseentry 	b on a.FInterID = b.FInterID"
					+ " and ( a.ftrantype = 75 or a.ftrantype = 76 ) "
					+ " where a.fcheckdate > dateadd(year,-1,dateadd(day,datediff(day,0,getdate()),0)) "
					+ " and isnull(b.fqty , 0)<> 0 and isnull(b.fstdamount ,0) <> 0 and a.fstatus = 1 "
					+ " and a.frob =1 and (isnull(fheadselfi0252,0) =0 or isnull(fheadselfi0349,0 ) = 0) "
					+ " group by b.fitemid"
			+ " ) 									w 		on w.fitemid = t6.faidnumber "
			+ " left join t_icitem 					t110 	on t110.fitemid = t6.faidnumber "
		    + " where t2.ferpclsid <> 1 "
		    + " and t1.fqty > 0  "
		    + " and  t1.sn not in (select a.sn  from BDBomMulExpose 	a  "
		   	+ "	join t_icitem b on a.fitemid = b.fitemid  and b.ferpclsid <> 1 and"
		   	+ " a.firstitemid = "+firstitemid+" and a.finterid="+finterid
		   	+ " join (select a.sn from BDBomMulExpose  a join icbom b  "
		   		+ " on a.fitemid = b.fitemid  and a.firstitemid = "+firstitemid+" and a.finterid="+finterid
		   		+" and b.fbomskip = 1058 "
		   		+ " and b.fusestatus = 1072 )   a0 	on a.sn  like a0.sn+'%')  "
		   + " order by t2.fnumber ,t4.fopersn "	;	
	sqlModel =" ;select t4.foperid,t4.fopersn,t2.fnumber ,t2.fname as assyname,"
			+" (case isnull((select 1 from t_icitem where fitemid = t1.fitemid and fname like '扁管盘料'),0) "
			+" when 1 then round(t1.fqty*(100-t1.fscrap)/100/t1.fqtyper,0) else t1.fqty end) as fqty,"
			+" t5.fname as opername,t4.fpiecerate,t4.fentryselfz0236 as frate,"
			+" t4.fentryselfz0237 as fmakeqty, "	
			+" (case t4.foperid when 40494 then t11.fmodelname else t6.fnamemodel end) as fnamemodel,"
			+ "isnull("
			+" (case isnull((select 1 from t_icitem where fitemid = t1.fitemid and fname like '扁管盘料'),0) "
			+" when 1 then round(t1.fqty*(100-t1.fscrap)/100/t1.fqtyper,0) else t1.fqty end)"
			+ "*t4.fentryselfz0237*"
			+ "(case t4.foperid when 40494 then round(t11.famtmodel*"+length+"*1000*t13.f_140/t11.cap,10)"/* 扁管压出工艺40494模具产能：模具最大使用量/(扁管长度*米克重)*/
			+ " else round(t6.famtperoper,10) end),0 ) as famtmodel "
			+ ",t13.f_140,t11.cap/1000 ,"
			+ "(case t4.foperid when 40494 then t11.famtmodel else t6.famtmodel end) as famtmodel0,"
			+length+","+width		
			+" from BDBomMulExpose t1  "
			+" join t_icitem t2 on t1.fitemid = t2.fitemid and t1.firstitemid = "+firstitemid+" and t1.finterid="+finterid		
			+" join t_Routing t3 on t3.finterid = t1.froutingid"		
			+" join t_routingoper t4 on t3.finterid = t4.finterid "		
			+" left join t_submessage t5 on t5.finterid = t4.foperid	and t5.fparentid = 61 "		
			+" left join (select a.foperno,b.fnamemodel,b.famtperoper,b.famtmodel "
			+ " from t_CostCalculateBD	a join	t_costcalculatebd_entry2 b on a.fid = b.fid"
			+ ") t6 on t6.foperno = t4.foperid and t6.foperno <> 40494  "		
			+" left join (select t001.fpipenumber,'挤压模具' as fmodelname,t001.fmaxqtymodel as cap,t003.famtmodel as famtmodel "
			+ " 	from t_costcalculate_flatpipe 		t001  "
			+ " 	left join t_CostCalculateBD 		t002 on t002.foperno = 40494 "
			+ " 	left join t_costcalculatebd_entry2 	t003 on t002.fid = t003.fid and t003.FNameModel like '%挤压模%'"
			+ " 	where fcheckbox = 0"
			+ " 	union   "
			+ "		select t001.fpipenumber,'挤压轮' as fmodelname,50000000 as cap ,t003.famtmodel as famtmodel "
			+ " 	from t_costcalculate_flatpipe 		t001  "
			+ " 	left join t_CostCalculateBD 		t002 on t002.foperno = 40494  "
			+ " 	left join t_costcalculatebd_entry2 	t003 on t002.fid = t003.fid and t003.FNameModel like '%挤压轮%' "
			+ " 	where fcheckbox = 0)  t11 on t11.fpipenumber = t1.fitemid and t4.foperid = 40494 "
			+" left join t_icitemcustom t13 on t13.fitemid = t1.fitemid and t4.foperid = 40494"		
			+" where t2.ferpclsid <> 1 "
			+ " and t1.fqty > 0 "
			+ " and  t1.sn not in (select a.sn  from BDBomMulExpose 	a  "
		   	+ "	join t_icitem b on a.fitemid = b.fitemid  and b.ferpclsid <> 1 and"
		   	+ " a.firstitemid = "+firstitemid+" and a.finterid="+finterid
		   	+ " join (select a.sn from BDBomMulExpose  a join icbom b  "
		   		+ " on a.fitemid = b.fitemid  and a.firstitemid = "+firstitemid+" and a.finterid="+finterid
		   		+" and b.fbomskip = 1058 "
		   		+ " and b.fusestatus = 1072 )   a0 	on a.sn  like a0.sn+'%')  "
			+" order by t2.fnumber ,t4.fopersn "	;
	
	sqlLabourAndMake = "; select Fnumber,assyname,fQty,fopername,fpiecerate,frate,"
			+ " fmakeqty,isnull(amtpay,0),isnull(amtassure,0),isnull(costworker,0),isnull(fmatpower,0),"
			+ "isnull(fdepr,0),isnull(famtadi,0),isnull(famtmodel,0) "
			+ " from t_BDLabourAndMake "
			+ " where fproditemid = "+firstitemid+" and finterid = "+finterid
			+ " order by fnumber,fopersn";
	
	
	sqlPriceRPTform =";select  0 as no, 	'一.生产成本合计 =（1+2+3）' as item1 ,	'' as item2,	'    /' as item3,								'一.生产成本合计 =（1+2+3）' as item4,		''as item5,	'/'as item6 " 
			+" union select  1 as no, 	'   1.直接材料成本' as item1,				'' as item2,	'    /' as item3,								'   1.直接材料成本' as item4,				''as item5,	''as item6 " 
			+" union select  2 as no, 	'   2.直接人工' as item1,					'' as item2,	'    /' as item3,								'   2.直接人工' as item4,					''as item5,	'按照工艺路线计算的计件人员工资；上年度按照计件工资占比计算的社保、公积金、餐费、补贴、工龄'as item6 " 
			+" union select  3 as no, 	'   3.制造费用合计' as item1,				'' as item2,	'    /' as item3,								'   3.制造费用合计' as item4,				''as item5,	'/'as item6 " 
			+" union select  4 as no,  	'       间接人工' as item1,				'' as item2,	'' as item3, 								'       间接人工' as item4,				''as item5,	'上年度生产员工之外的车间班组长以上的管理人员、调机员、车间计时人员的工资、社保、公积金（暂定标准，系统工资实现后调整过去12个月）'as item6 " 
			+" union select  5 as no,  	'       制造费用-电费' as item1,			'' as item2,	'    /' as item3,								'       制造费用-电费' as item4,			''as item5,	'按照工艺路线计算的使用设备的电费'as item6 " 
			+" union select  6 as no,  	'       制造费用-辅料费' as item1,		'' as item2,	'    /' as item3,								'       制造费用-辅料费' as item4,		''as item5,	'按照工艺路线计算的辅料费'as item6 " 
			+" union select  7 as no,  	'       制造费用-设备折旧费' as item1,		'' as item2,	'    /' as item3,								'       制造费用-设备折旧费' as item4,		''as item5,	'按照工艺路线计算的使用设备折旧费用'as item6 " 
			+" union select  8 as no,  	'       制造费用-工装模具折旧费' as item1,	'' as item2,	'    /' as item3,								'       制造费用-工装模具折旧费' as item4,	''as item5,	'按照工艺路线计算的工装模具折旧费用（有重复分摊可能，暂时执行）'as item6 " 
			+" union select  9 as no,  	'       制造费用-其他成本' as item1,		'' as item2,	'' as item3,								'       制造费用-其他成本' as item4,		''as item5,	'上年度每台分摊的制造费用其他成本，包含修理费、加工费、运输费'as item6 " 
			+" union select  10 as no, 	'       厂房折旧' as item1,				'' as item2,	'' as item3,								'       厂房折旧' as item4,				''as item5,	'上年度每台产品分摊厂房折旧金额'as item6 " 
			+" union select  11 as no, 	'二.期间费用合计' as item1,				'' as item2,	'    /' as item3,								'二.期间费用合计' as item4,				''as item5,	'/'as item6 " 
			+" union select  12 as no, 	'   成品不良成本' as item1,				'' as item2,	'' as item3,								'   成品不良成本' as item4,				''as item5,	'/'as item6 "
			+" union select  13 as no, 	'   财务费用' as item1,					'' as item2,	'' as item3,								'   财务费用' as item4,					''as item5,	'取值过去12个月财务费用占营业成本的比例。'as item6 "  
			+" union select  14 as no, 	'   销售费用' as item1,					'' as item2,	'' as item3,								'   销售费用' as item4,					''as item5,	'过去12个月销售费用（不含运费）占营业成本的比例。'as item6 " 
			+" union select  15 as no, 	'   管理费用' as item1,					'' as item2,	'' as item3,								'   管理费用' as item4,					''as item5,	'过去12个月管理费用（不含研发费、土地摊销）、税金及附加占生产成本的比例'as item6 " 
			+" union select  16 as no, 	'   管理费用-土地摊销' as item1,			'' as item2,	'' as item3,								'   管理费用-土地摊销' as item4,			''as item5,	'上年度每台产品分摊土地摊销金额'as item6 " 
			+" union select  17 as no, 	'   模具工装费用' as item1,				'' as item2,	'支持系统人工录入：金额；分摊数量。' as item3,	'   模具工装费用' as item4,				''as item5,	'支持系统人工录入：金额；分摊数量。'as item6 " 
			+" union select  18 as no,  '   运输费用' as item1,					'' as item2,	'' as item3,								'   FOB青岛费用' as item4,				''as item5,	'FOB青岛费用=6000元（整柜 BY FCL,40HQ/GP,45HQ/GP）/70立方米*单只产品体积'as item6 " 
			+" union select  19 as no, 	'三.产品利润' as item1,					'' as item2,	'' as item3,								'三.产品利润' as item4,					''as item5,	'冷凝器20%，油冷器30%'as item6 " 
			+" union select  20 as no, 	'四.核价=（1+2+3+4+5) ' as item1,			'' as item2,	'    /' as item3,								'四.FOB青岛核价=（1+2+3+4+5)' as item4,	''as item5,	''as item6 " 
			+" union select  21 as no, 	'五.增值税' as item1,					'' as item2,	'' as item3,								'/' as item4,							'/'as item5,'/'as item6 " 
			+" union select  22 as no, 	'六.出厂核价_含税RMB：' as item1,				'' as item2,	'账期30/天核算' as item3,						'五.FOB青岛核价_USD：' as item4,				''as item5,	'提单日60/天核算，现贷款年利率： 4.785%'as item6 " 
			; 
	
	sqlBOM = "; select a.flevel0,a.FParentID,a.FItemID,b.fnumber,b.fname ,b.fmodel"
			+ ",d.fname as maketype,a.enditem,a.fQtyPer,a.fQty,c.fname as unitname,a.fscrap"
			+ ",a.fitemsize,a.fstatus,f.fname,e.fname,a.froutingid,a.fbominterid"
			+ ",a.fbomnumber,a.sn"
			+ " from BDBomMulExpose 	a"
			+ " join t_icitem b on a.fitemid = b.fitemid and a.firstitemid ="+firstitemid
			+ " and a.finterid="+finterid
			+ " join t_measureunit 		c on c.fitemid  = a.funitid"			
			+ " left join t_submessage 	d on d.finterid = a.maketype"
			+ " left join t_submessage  e on e.finterid = a.bomskip "
			+ " left join t_submessage 	f on f.finterid = a.fusestatus"
			+ " order by a.sn";	
	
	sqlProductDevRpt = "; select a.fbillno,a.fcombobox3,b.fname as proname,a.ftext8,a.ftext1,a.ftext2"
			+ ",a.ftext,a.ftext3,a.ftext4,a.ftext5,a.fcombobox1,convert(varchar(10),a.fdate1,120) as fdate1"
			+ ",convert(varchar(10),a.fdate2,120) as fdate2,convert(varchar(10),a.fdate3,120) as fdate3"
			+ ",e.fname as fbiller,a.fcombobox4,c.fname as itemclass,a.ftext10,a.ftext6,a.finteger1"
			+ ",convert(varchar(10),a.fdate,120) as fdate,d.fnumber as customercode,d.fname as samplefrom"
			+ ", a.ftext9,a.finteger2,a.finteger,a.ftext11,a.fcombobox2,f.fname,a.fcombobox6,a.fcombobox"
			+ ",a.fcombobox5,convert(varchar(10),a.fdate4,120) as fdate4,a.fnote "
			+ " from t_bos200000006 a "
			+ " left join t_submessage b on a.fbase3 = b.finterid "
			+ " left join t_submessage c on a.fbase2 = b.finterid "
			+ " join t_organization d on a.fbase = d.fitemid "
			+ " left join t_user e on a.fbiller = e.fuserid "
			+ " left join t_user f on a.fuser = f.fuserid "
			+ " join t_icitem g on g.fnumber = a.ftext8 "
			+ " where g.fitemid = "+firstitemid;
	
	if (sqlname =="sqlMaterial")  			{   export = "";export = sqlMaterial;		}
	if (sqlname =="sqlLabourAndMake")  		{   export = "";export = sqlLabourAndMake;	}
	if (sqlname =="sqlPriceRPTform")  		{   export = "";export = sqlPriceRPTform;	}
	if (sqlname =="sqlEnergy")  			{   export = "";export = sqlEnergy;			}
	if (sqlname =="sqlAdi")  				{   export = "";export = sqlAdi;			}
	if (sqlname =="sqlModel")  				{   export = "";export = sqlModel;			}
	if (sqlname =="sqlBOM")  				{   export = "";export = sqlBOM;			}
	if (sqlname =="sqlProdDevRpt")  		{   export = "";export = sqlProductDevRpt;	}
	//,,,,
	 return export;
		
	}

}
