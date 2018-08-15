package bd.DAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import bd.View.*;
import bd.connection.*;

public class ActListenerBtnGenerate implements ActionListener  
{
	public void actionPerformed(ActionEvent event) 
	{
		double powerprice = Double.parseDouble(StdCostCalculate.mainFrame.textFK0.getText());
		tableData data = new tableData();
		TableFormat formReport = new TableFormat();
		String sqlMaterial,sqlLabourAndMake,sqlPriceRPTform,sqlEnergy,sqlAdi,sqlModel,sqlBOM;
		StateSQl mysqlstate=new StateSQl();	
		
	    // items count
	    int k = 0;
	    int sumFails = 0;
	    try 
	    {
	    	currentyear=getCurrentYear.getCurrentYear();
	    	currentperiod=getCurrentMonth.getCurrentMonth();
	    	createTableRPT.createTableRPT();
	    	createTableBOM.createTableBOM();
	    	createTableMaterial.createTableMaterial();
	    	crteTabLabourAndMake.crteTabLabourAndMake();
	    	updateAdiMaterialPrice.updateAdiMatrialPrice();
	    	updateMachineInfo.updateMachineInfo();
		
	    	if (StdCostCalculate.auto.equals("auto")) 
	    	{
	    		String	sqlauto = ";select a.fnumber"
	    			+ " from t_icitem a join icbom b on a.fitemid = b.fitemid  "
        			+ " and b.fstatus = 1 and b.fusestatus = 1072 and a.fnumber like '01.%' "
        			+ " order by a.fnumber";
	    	
	    		ResultSet rs_auto = conn.query("",sqlauto);
	    		////System.out.println("auto "+sqlauto);

	    		if(rs_auto.next()) 
	    		{
	    			rs_auto.last();
	    			k = rs_auto.getRow();
	    			//System.out.println("批量计算成本,产品数量"+k);
	    			rs_auto.beforeFirst();
	    			fnumbers = new String[k];
	    			for (int l=0; l<k;l++ ) 
	    			{
	    				rs_auto.next();
	    				fnumbers[l] = rs_auto.getString(1); 
	    				//System.out.println("auto  array:"+l+"value:"+fnumbers[l]);
	    			}
	    		}
	    	}
	    	else 
	    	{
	    		for (int i = 0; i<StdCostCalculate.mainFrame.tableQueResult.getRowCount();i++ ) 
	    		{
	    			if ((Boolean)StdCostCalculate.mainFrame.tableQueResult.getValueAt(i,0)) 
	    			{
	    				k++;
	    			} 
	    		}
	    		fnumbers = new String[k]; 
	    		int y=0;
	    		for (int l = 0; l<StdCostCalculate.mainFrame.tableQueResult.getRowCount();l++ ) 
	    		{
	    			if ((Boolean)StdCostCalculate.mainFrame.tableQueResult.getValueAt(l,0)) 
	    			{
	    				fnumbers[y] = String.valueOf(StdCostCalculate.mainFrame.tableQueResult.getValueAt(l,1));
	    				y++;
	    			} 
	    		} 
	    	}
	    	System.out.println("开始计算标准成本 ,"+df.format(new Date()).toString()+",");
	    	//lblstatus.setText("计算开始，请等待………………");
	    	// multiple items
	    	if (k>1) 
	    	{
	    		for (int x=0;x<fnumbers.length;x++)
	    		{
	    			fnumber = fnumbers[x];
	    			firstitemid =getFirstItemID.getFirstItemID(fnumber) ;
	    			finterid = getFinterID.getFinterID(firstitemid);
	    			//gainrate = getGainRate.getGainRate(fnumber);
	    			itemname=getItemName.getItemName(fnumber);
	    			model=getModel.getModel(fnumber);
	    			packagesize=getPackageSize.getPackageSize(fnumber);
	    			itselfQtySaled=getItselfQtySaled.getItselfQtySaled(firstitemid);
	    			//modelQtySaled=getModelQtySaled.getModelQtySaled(model);
	    			cleanBom.cleanBom(firstitemid, finterid);		
	    			bomExpose.bomExpose(firstitemid, finterid);	
	    			bomVerifyError=verifyBOM.verifyBOM(firstitemid, finterid);	
	    			RoutVerifyErr=verifyRout.verifyRout(firstitemid, finterid);
	    			
	    			length=getLenWidHgt.Len(firstitemid, finterid);	
	    			width=getLenWidHgt.Wid(firstitemid, finterid);	
	    			height=getLenWidHgt.Hgt(firstitemid, finterid);	
	    			RoutLWHVerifyErr=verifyRoutLWH.verifyRoutLWH(firstitemid, finterid, length, width, height);
	    			getCapacityQianHanLu.getCapacityQianHanLu(length*1000,height*1000);	
	    			costMateiral.costMateiral( firstitemid, finterid,currentyear,currentperiod);
	    			materialVerifyError=verifyMaterialPrice.verifyMaterialPrice(firstitemid, finterid);
	    			adiVerifyError=verifyAdiMaterialPrice.verifyAdiPrice(firstitemid, finterid);
	    		
	    			if (StdCostCalculate.mainFrame.textFK12.getText().equals(String.valueOf(0.0))) 
	    			{
	    				++sumFails ;
			    		cleanBom.cleanBom(finterid,finterid);
			    		System.out.println(" 没有有效的预算汇率,," );				    		
		    		}
	    			else if (firstitemid == 0) 
	    			{
			    		++sumFails ;
			    		System.out.println("产品itemid 不存在, "+df.format(new Date()).toString() +" ," +fnumber);
			    	}
	    			else if (bomVerifyError > 0) 
	    			{
			    		++sumFails ;
			    		cleanBom.cleanBom(finterid,finterid);
			    		System.out.println("bom 不完整," +df.format(new Date()).toString() +" ,"+fnumber);
			    	}
	    			else if (RoutVerifyErr > 0) 
	    			{
			    		++sumFails ;
			    		cleanBom.cleanBom(finterid,finterid);
			    		System.out.println("工艺路线 不完整," +df.format(new Date()).toString() +" ,"+fnumber);
			    	}
			    	else if (RoutLWHVerifyErr > 0) 
			    	{
			    		++sumFails ;
			    		cleanBom.cleanBom(finterid,finterid);
			    		System.out.println("芯体数据不完整 长:"+length+" 宽:"+width
			    				+" 高:"+height+" ," +df.format(new Date()).toString() +" ,"+fnumber);
			    	}
			    	else if (materialVerifyError > 0) 
			    	{
			    		++sumFails ;
			    		System.out.println("直接材料价格 不完整,"+df.format(new Date()).toString()  +" ,"+fnumber);
			    	}
			    	else if (adiVerifyError > 0) 
			    	{
			    		++sumFails ;
			    		cleanBom.cleanBom(finterid,finterid);
			    		System.out.println("辅料价格 不完整," +df.format(new Date()).toString() +" ,"+fnumber );
			    	}
			    	else 
			    	{	
			    		//System.out.println("2.直接成本计算 成功，下一步，计算人工与制造费用");
			    		costLabourAndMake.costLabourAndMake(Double.parseDouble(
			    				StdCostCalculate.mainFrame.textFK1.getText())
			    				, Double.parseDouble(StdCostCalculate.mainFrame.textFK2.getText())
			    				, Double.parseDouble(StdCostCalculate.mainFrame.textFK3.getText())
			    				, Double.parseDouble(StdCostCalculate.mainFrame.textFK0.getText())
			    				,firstitemid,finterid,currentyear,currentperiod
			    				,length,GetCapacityQianHanLu.capacity);
			    		//System.out.println("3.制造费用与直接人工 成功，下一步，生成报价");
			    		sqlMaterial = mysqlstate.getSQLStatement("sqlMaterial"
			    				,currentyear,currentperiod,firstitemid,finterid
			    				,GetCapacityQianHanLu.capacity,length,width,powerprice );
			    		sqlLabourAndMake = mysqlstate.getSQLStatement(
			    				"sqlLabourAndMake",currentyear,currentperiod
			    				,firstitemid,finterid,GetCapacityQianHanLu.capacity,length,width
			    				,powerprice);
			    		//System.out.println("人工和制造费用:"+sqlLabourAndMake);			
			    		sqlPriceRPTform =mysqlstate.getSQLStatement(
			    				"sqlPriceRPTform",currentyear,currentperiod
			    				,firstitemid,finterid,GetCapacityQianHanLu.capacity,length
			    				,width,powerprice);
			    		data.myTableModel(StdCostCalculate.mainFrame.tableMaterial,sqlMaterial,new int[]{5,7});
			    		data.myTableModel(StdCostCalculate.mainFrame.tableCalculate,sqlLabourAndMake
			    				,new int[]{7,8,9,10,11,12,13});
			    		data.myTableModel(StdCostCalculate.mainFrame.tableReport,sqlPriceRPTform,new int[]{});

			    		int a = StdCostCalculate.mainFrame.tableReport.getColumnCount();
			    		if (a == 7) 
			    		{
			    			formReport.ColumnFilter(StdCostCalculate.mainFrame.tableReport,0);
			    		}
			    		StdCostCalculate.mainFrame.tableReport.setRowHeight(24);
			    		setReportValue.setReprotValue(StdCostCalculate.mainFrame.tableReport
			    				, StdCostCalculate.mainFrame.tableMaterial, StdCostCalculate.mainFrame.tableCalculate
			    				, packagesize, GetGainRate.gainrate);				    
			    		StdCostCalculate.mainFrame.btnR2Save.doClick();
			    		System.out.println("结束第 "+(x+1)+"/"+k
			    				+" 个产品计算,"+df.format(new Date()).toString()
			    				+","+fnumber);
			    	}
	    			StdCostCalculate.mainFrame.lblstatus.setText("^^^^^^^"+k +" 个产品的标准成本报价已经生成，请到ERP查看！");
	    		}					    		/* end for*/
	    		System.out.println( "全部计算结束, "+df.format(new Date()).toString()+","+k
	    				+"个产品 "+ sumFails+"个不满足计算条件");
	    	} 		    	/*end multiple*/
/* 
* start single 
* */
	    	else 
	    	{
	    		fnumber = fnumbers[0];		    
	    		firstitemid =getFirstItemID.getFirstItemID(fnumber) ;
	    		finterid = getFinterID.getFinterID(firstitemid);
	    		//gainrate= getGainRate.getGainRate(fnumber);
	    		itemname=getItemName.getItemName(fnumber);
	    		model=getModel.getModel(fnumber);
	    		packagesize=getPackageSize.getPackageSize(fnumber);
	    		itselfQtySaled=getItselfQtySaled.getItselfQtySaled(firstitemid);
	    		cleanBom.cleanBom(firstitemid, finterid);		
	    		bomExpose.bomExpose(firstitemid, finterid);	
	    		bomVerifyError=verifyBOM.verifyBOM(firstitemid, finterid);	
	    		RoutVerifyErr=verifyRout.verifyRout(firstitemid, finterid);
	    		
	    		length=getLenWidHgt.Len(firstitemid, finterid);	
	    		width=getLenWidHgt.Wid(firstitemid, finterid);	
	    		height=getLenWidHgt.Hgt(firstitemid, finterid);	
	    		RoutLWHVerifyErr=verifyRoutLWH.verifyRoutLWH(firstitemid, finterid, length, width, height);
	    		getCapacityQianHanLu.getCapacityQianHanLu(length*1000,height*1000);	
	    		//System.out.println("1.取得参数 成功，下一步，计算材料成本，显示BOM和直接材料清单");
	    		costMateiral.costMateiral( firstitemid, finterid,currentyear,currentperiod);
	    		materialVerifyError=verifyMaterialPrice.verifyMaterialPrice(firstitemid, finterid);
	    		//System.out.println("验证直接材料价格是否完整");
	    		adiVerifyError=verifyAdiMaterialPrice.verifyAdiPrice(firstitemid, finterid);
	    		//System.out.println("验证辅料价格是否完整");   	    
	    		sqlBOM =mysqlstate.getSQLStatement("sqlBOM",currentyear,currentperiod,firstitemid,finterid,GetCapacityQianHanLu.capacity,length,width,powerprice);
	    		//System.out.println("BOM："+sqlBOM );
	    		sqlMaterial = mysqlstate.getSQLStatement("sqlMaterial",currentyear,currentperiod,firstitemid,finterid,GetCapacityQianHanLu.capacity,length,width,powerprice );
	    		//System.out.println("直接材料成本："+sqlMaterial);
	    		sqlEnergy=mysqlstate.getSQLStatement("sqlEnergy",currentyear,currentperiod,firstitemid,finterid,GetCapacityQianHanLu.capacity,length,width,powerprice);
	    		//System.out.println("电费与折旧明细: "+sqlEnergy);
	    		sqlAdi =mysqlstate.getSQLStatement("sqlAdi",currentyear,currentperiod,firstitemid,finterid,GetCapacityQianHanLu.capacity,length,width,powerprice);
	    		//System.out.println("辅料明细: "+sqlAdi);
	    		sqlModel =mysqlstate.getSQLStatement("sqlModel",currentyear,currentperiod,firstitemid,finterid,GetCapacityQianHanLu.capacity,length,width,powerprice);
	    		//System.out.println("工装模具明细: "+sqlModel);
	    		sqlLabourAndMake = mysqlstate.getSQLStatement("sqlLabourAndMake",currentyear,currentperiod,firstitemid,finterid,GetCapacityQianHanLu.capacity,length,width,powerprice);
	    		//System.out.println("人工与制造费用: "+sqlLabourAndMake );
	    		sqlPriceRPTform =mysqlstate.getSQLStatement("sqlPriceRPTform",currentyear,currentperiod,firstitemid,finterid,GetCapacityQianHanLu.capacity,length,width,powerprice);
		
	    		data.myTableModel(StdCostCalculate.mainFrame.tableBOM,sqlBOM,new int[]{});
	    		data.myTableModel(StdCostCalculate.mainFrame.tableMaterial,sqlMaterial,new int[]{5,7});
	    		//System.out.println("2.直接材料成本计算 成功，下一步，验证计算条件");
	    		if (StdCostCalculate.mainFrame.textFK12.getText().equals(String.valueOf(0.0))) 
	    		{
	    			cleanBom.cleanBom(finterid,finterid);
	    			StdCostCalculate.mainFrame.lblstatus.setText(" 没有有效的预算汇率！");
		    		JOptionPane.showMessageDialog(StdCostCalculate.mainFrame.frame, " 没有有效的预算汇率！");
		    		System.out.println(" 没有有效的预算汇率,," );
	    		}								
		    	else if (firstitemid == 0)	
		    	{
		    		StdCostCalculate.mainFrame.lblstatus.setText(" 产品 " +fnumber +"itemid  不存在！");
		    		JOptionPane.showMessageDialog(StdCostCalculate.mainFrame.frame, " 产品 " +fnumber +"itemid  不存在！");
		    		System.out.println(" 产品 " +fnumber +" itemid 不存在！" );
		    	}
		    	else if (bomVerifyError > 0)
		    	{
		    		cleanBom.cleanBom(finterid,finterid);
		    		StdCostCalculate.mainFrame.lblstatus.setText(" 产品 " +fnumber +" bom 不完整！");
		    		JOptionPane.showMessageDialog(StdCostCalculate.mainFrame.frame, "BOM 不完整,请查看BOM");
		    		System.out.println("bom 不完整");
		    	}
		    	else if (RoutVerifyErr > 0) 
		    	{
		    		cleanBom.cleanBom(finterid,finterid);
		    		JOptionPane.showMessageDialog(StdCostCalculate.mainFrame.frame, "工艺路线 不完整");
			    	System.out.println("工艺路线 不完整," +df.format(new Date()).toString() +" ,"+fnumber);
			    }
		    	else if (RoutLWHVerifyErr > 0) 
		    	{
		    		cleanBom.cleanBom(finterid,finterid);
		    		JOptionPane.showMessageDialog(StdCostCalculate.mainFrame.frame, "芯体数据不完整 长:"+length
		    				+"宽:"+width+"高:"+height);
			    	System.out.println("芯体数据不完整 长:"+length+"宽:"+width
			    			+"高:"+height+" ," +df.format(new Date()).toString() 
			    			+" ,"+fnumber);
			    }
		    	else if (adiVerifyError > 0)
		    	{
		    		cleanBom.cleanBom(finterid,finterid);
		    		StdCostCalculate.mainFrame.lblstatus.setText(" 产品 " +fnumber +" 辅料价格 不完整！");
		    		JOptionPane.showMessageDialog(StdCostCalculate.mainFrame.frame, "有 "+ adiVerifyError 
		    				+" 种辅料在过去一年没有采购价格并且没有计划单价,"
		    				+ "请查看辅料明细");
		    		System.out.println("辅料价格 不完整");
		    	}
		    	else if (materialVerifyError  > 0)	
		    	{
		    		cleanBom.cleanBom(finterid,finterid);
		    		StdCostCalculate.mainFrame.lblstatus.setText(" 产品 " +fnumber +" 直接材料 价格 不完整！");
		    		JOptionPane.showMessageDialog(StdCostCalculate.mainFrame.frame, "有 直接材料 在过去一年没"
		    				+ "有采购价格并且没有计划单价,请查看直接材料成本明细");
		    		System.out.println("直接材料价格 不完整");
		    	}
		    	else 
		    	{
		    		//System.out.println("3.符合计算条件，下一步，计算人工与制造费用");
		    		costLabourAndMake.costLabourAndMake(
		    				Double.parseDouble(StdCostCalculate.mainFrame.textFK1.getText())
		    				, Double.parseDouble(StdCostCalculate.mainFrame.textFK2.getText())
		    				, Double.parseDouble(StdCostCalculate.mainFrame.textFK3.getText())
		    				, Double.parseDouble(StdCostCalculate.mainFrame.textFK0.getText())
		    				,firstitemid,finterid,currentyear,currentperiod
		    				,GetLenWidHgt.length,GetCapacityQianHanLu.capacity);
		    		//System.out.println("4.制造费用与直接人工 成功，下一步，生成报价");			
		    		data.myTableModel(StdCostCalculate.mainFrame.tableEnergy,sqlEnergy,new int[]{13,15});
		    		data.myTableModel(StdCostCalculate.mainFrame.tableAdi,sqlAdi,new int[]{12});
		    		data.myTableModel(StdCostCalculate.mainFrame.tableModel,sqlModel,new int[]{10});			
		    		data.myTableModel(StdCostCalculate.mainFrame.tableCalculate,sqlLabourAndMake
		    				,new int[]{7,8,9,10,11,12,13});
		    		data.myTableModel(StdCostCalculate.mainFrame.tableReport,sqlPriceRPTform,new int[]{});
			
		    		StdCostCalculate.mainFrame.lbl_ProdName.setText(fnumber); 
		    		StdCostCalculate.mainFrame.lbl_Model.setText(model);
		    		StdCostCalculate.mainFrame.labItemname.setText(itemname);
		    		//lbl_OEMNo.setText(oem);
			
		    		formReport.ColAlignLeft(StdCostCalculate.mainFrame.tableMaterial, new int[] {0,1,2});	
		    		formReport.ColAlignLeft(StdCostCalculate.mainFrame.tableCalculate, new int[] {0,1,3});	
		    		formReport.ColAlignLeft(StdCostCalculate.mainFrame.tableReport, new int[] {1,4,6});
		    		formReport.ColAlignLeft(StdCostCalculate.mainFrame.tableBOM, new int[] {0,1,2,3,4,5,18});
		    		formReport.ColAlignLeft(StdCostCalculate.mainFrame.tableEnergy, new int[] {0,1,2,3,5,9});	
		    		formReport.ColAlignLeft(StdCostCalculate.mainFrame.tableAdi, new int[] {0,1,2,3,5,9});	
		    		formReport.ColAlignLeft(StdCostCalculate.mainFrame.tableModel, new int[] {0,1,2,3,5,9});	
							
		    		int a = StdCostCalculate.mainFrame.tableReport.getColumnCount();
		    		if (a == 7) 
		    		{
		    			formReport.ColumnFilter(StdCostCalculate.mainFrame.tableReport,0);
		    		}
		    		StdCostCalculate.mainFrame.tableReport.setRowHeight(24);
		    		setReportValue.setReprotValue(StdCostCalculate.mainFrame.tableReport
		    				, StdCostCalculate.mainFrame.tableMaterial, StdCostCalculate.mainFrame.tableCalculate
		    				, packagesize, GetGainRate.gainrate);				    
		    		StdCostCalculate.mainFrame.txtNewModel.setText(StdCostCalculate.mainFrame.textFK16.getText());
		    		StdCostCalculate.mainFrame.txtNewModelSalQty.setText(StdCostCalculate.mainFrame.textFK17.getText());
		    		StdCostCalculate.mainFrame.txtHistSalQty.setText(String.valueOf(itselfQtySaled));
		    		setDevelopeRequestValue.setDevelopRequestValue(firstitemid);
		    		StdCostCalculate.mainFrame.btnR2Save.doClick();
		    
		    		StdCostCalculate.mainFrame.btnR2Save.doClick();
		    		StdCostCalculate.mainFrame.lblstatus.setText("^^^^^^^"+fnumber +" 的标准成本报价已经生成"
		    				+ "，请到报价单界面查看！");
		    		JOptionPane.showMessageDialog(StdCostCalculate.mainFrame.frame,fnumber +" 的标准成本"
		    				+ "报价已经生成，请到报价单界面查看！");
		    	} 									/* end else */
	    		System.out.println("calculate finish,"+df.format(new Date()).toString()
	    				+",");
	    	}										/* end else1*/
	    } 
	    catch(SQLException e) {}
	}
	private GetDBConnect conn=new GetDBConnect();
	private GetFirstItemID getFirstItemID=new GetFirstItemID();
	private GetFinterID getFinterID= new GetFinterID();
	private GetCurrentYear getCurrentYear=new GetCurrentYear();
	private GetCurrentMonth getCurrentMonth = new GetCurrentMonth();
	private GetItemName getItemName = new GetItemName();
	private GetModel getModel=new GetModel();
	//private GetModelQtySaled getModelQtySaled=new GetModelQtySaled();
	private GetItselfQtySaled getItselfQtySaled = new GetItselfQtySaled();
	private GetPackageSize getPackageSize=new GetPackageSize();
	private GetLenWidHgt getLenWidHgt = new GetLenWidHgt();
	//private GetGainRate getGainRate  = new GetGainRate ();
	private GetCapacityQianHanLu getCapacityQianHanLu = new GetCapacityQianHanLu();
	private CreateTableRPT createTableRPT=new CreateTableRPT() ;
	private CreateTableBOM createTableBOM=new CreateTableBOM() ;
	private CreateTableMaterial createTableMaterial=new CreateTableMaterial();
	private CreateTableLaborAndMake crteTabLabourAndMake= new CreateTableLaborAndMake();
	private BomExpose bomExpose=new BomExpose();
	private CleanBom cleanBom=new CleanBom();	
	private CostMateiral costMateiral=new CostMateiral();
	private CostLabourAndMake costLabourAndMake=new CostLabourAndMake();
	private VerifyBOM verifyBOM=new VerifyBOM();
    private VerifyMaterialPrice verifyMaterialPrice=new VerifyMaterialPrice();
    private VerifyAdiMaterialPrice verifyAdiMaterialPrice = new VerifyAdiMaterialPrice();
    private VerifyRoutLWH verifyRoutLWH =new  VerifyRoutLWH();
    private VerifyRout verifyRout=new VerifyRout();
	private UpdateAdiMaterialPrice updateAdiMaterialPrice=new UpdateAdiMaterialPrice();
    private UpdateMachineInfo updateMachineInfo=new UpdateMachineInfo();
    private SetReportValue setReportValue=new SetReportValue(); 
    private SetDevelopeRequestValue setDevelopeRequestValue=new SetDevelopeRequestValue();
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        		
	private String currentyear,currentperiod,itemname,model,fnumber;  
    private String[] fnumbers ;
	private int 	firstitemid,finterid,materialVerifyError
	,bomVerifyError,adiVerifyError,RoutVerifyErr,RoutLWHVerifyErr;
	private Double packagesize,width,length,height,itselfQtySaled/*,modelQtySaled,gainrate*/	;
	

} 												


