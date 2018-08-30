package bd.DAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import bd.View.*;

public class ActListenerBtnGenerate implements ActionListener  
{
	public void actionPerformed(ActionEvent event) 
	{
		// items count
	    k = 0;
	    sumFails = 0;
	    try 
	    {
	    	stdCostReport.createTableRPT();
	    	bom.createTableBOM();
	    	directMaterial.createTableMaterial();
	    	labourAndMake.createTableLaborAndMake();
	    	adiMaterial.updateAdiMatrialPrice();
	    	machineInfo.updateMachineInfo();
		
	    	if (StdCostCalculate.autoRun ==1 ) 
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
	    			productInfo.getFirstItemID(fnumber);
	    			productInfo.productInfoBeforeBOM(fnumber);
	    			bom.clean();		
		    		bom.bomExpose();
		    		productInfo.productInfoAfterBOM();
		    		machineInfo.getCapacity();
	    			bomVerifyError=bom.verifyBOM();	
	    			RoutVerifyErr=route.verifyRout();
	    			RoutLWHVerifyErr=route.verifyRoutLWH();
	    			directMaterial.costMaterial();
	    			materialVerifyError=directMaterial.verifyMaterialPrice();
	    			adiVerifyError=adiMaterial.verifyAdiPrice();
	    		
	    			if (StdCostCalculate.mainFrame.textFK12.getText().equals(String.valueOf(0.0))) 
	    			{
	    				++sumFails ;
			    		bom.clean();
			    		System.out.println(" 没有有效的预算汇率,," );				    		
		    		}
	    			else if (ProductInfo.firstitemid == 0) 
	    			{
			    		++sumFails ;
			    		System.out.println("产品itemid 不存在, "+df.format(new Date()).toString() +" ," +fnumber);
			    	}
	    			else if (bomVerifyError > 0) 
	    			{
			    		++sumFails ;
			    		bom.clean();
			    		System.out.println("bom 不完整," +df.format(new Date()).toString() +" ,"+fnumber);
			    	}
	    			else if (RoutVerifyErr > 0) 
	    			{
			    		++sumFails ;
			    		bom.clean();
			    		System.out.println("工艺路线 不完整," +df.format(new Date()).toString() +" ,"+fnumber);
			    	}
			    	else if (RoutLWHVerifyErr > 0) 
			    	{
			    		++sumFails ;
			    		bom.clean();
			    		System.out.println("芯体数据不完整 长:"+ProductInfo.length+" 宽:"+ProductInfo.width
			    				+" 高:"+ProductInfo.height+" ," +df.format(new Date()).toString() +" ,"+fnumber);
			    	}
			    	else if (materialVerifyError > 0) 
			    	{
			    		++sumFails ;
			    		System.out.println("直接材料价格 不完整,"+df.format(new Date()).toString()  +" ,"+fnumber);
			    	}
			    	else if (adiVerifyError > 0) 
			    	{
			    		++sumFails ;
			    		bom.clean();
			    		System.out.println("辅料价格 不完整," +df.format(new Date()).toString() +" ,"+fnumber );
			    	}
			    	else 
			    	{	
			    		//System.out.println("2.直接成本计算 成功，下一步，计算人工与制造费用");
			    		labourAndMake.clean();
			    		labourAndMake.costLabourAndMake(Double.parseDouble(
			    				StdCostCalculate.mainFrame.textFK1.getText())
			    				, Double.parseDouble(StdCostCalculate.mainFrame.textFK2.getText())
			    				, Double.parseDouble(StdCostCalculate.mainFrame.textFK3.getText())
			    				, Double.parseDouble(StdCostCalculate.mainFrame.textFK0.getText())
			    				);
			    		//System.out.println("3.制造费用与直接人工 成功，下一步，生成报价");
			    		sqlMaterial = mysqlstate.getSQLStatement("sqlMaterial",powerprice );
			    		sqlLabourAndMake = mysqlstate.getSQLStatement("sqlLabourAndMake",powerprice);
			    		//System.out.println("人工和制造费用:"+sqlLabourAndMake);			
			    		sqlPriceRPTform =mysqlstate.getSQLStatement("sqlPriceRPTform",powerprice);
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
			    		stdCostReport.set(StdCostCalculate.mainFrame.tableReport
			    				, StdCostCalculate.mainFrame.tableMaterial, StdCostCalculate.mainFrame.tableCalculate
			    				, ProductInfo.packagesize, ProductInfo.gainrate);				    
			    		StdCostCalculate.mainFrame.btnR2Save.doClick();
			    		System.out.println("结束第 "+(x+1)+"/"+k
			    				+" 个产品计算,"+df.format(new Date()).toString()
			    				+","+fnumber);
			    	}
	    			StdCostCalculate.mainFrame.lblstatus.setText("^^^^^^^"+k +" 个产品的标准成本报价已经生成，请到ERP查看！");
	    		}					    		/* end for*/
	    	    if (StdCostCalculate.autoRun ==1 )
	    	    {
	    	    	updateCompanyPricePolicy.clean(); //没有计算的，公司价格体系里价格设为 1000
		    	 }
	    		System.out.println( "全部计算结束, "+df.format(new Date()).toString()+","+k
	    				+"个产品 "+ sumFails+"个不满足计算条件");
	    	} 		    	/*end multiple*/
/* 
* start single 
* */
	    	else 
	    	{
	    		fnumber = fnumbers[0];
	    		productInfo.getFirstItemID(fnumber);
    			productInfo.productInfoBeforeBOM(fnumber);
    			//System.out.println("prodInfo: "+ProductInfo.model);
    			bom.clean();		
	    		bom.bomExpose();
	    		productInfo.productInfoAfterBOM();
	    		machineInfo.getCapacity();
	    		bomVerifyError=bom.verifyBOM();	
	    		RoutVerifyErr=route.verifyRout();
	    		RoutLWHVerifyErr=route.verifyRoutLWH();
	    		//System.out.println("1.取得参数 成功，下一步，计算材料成本，显示BOM和直接材料清单");
	    		directMaterial.costMaterial( );
	    		materialVerifyError=directMaterial.verifyMaterialPrice();
	    		//System.out.println("验证直接材料价格是否完整");
	    		adiVerifyError=adiMaterial.verifyAdiPrice();
	    		//System.out.println("验证辅料价格是否完整");   	    
	    		sqlBOM =mysqlstate.getSQLStatement("sqlBOM",powerprice);
	    		//System.out.println("BOM："+sqlBOM );
	    		sqlMaterial = mysqlstate.getSQLStatement("sqlMaterial",powerprice );
	    		//System.out.println("直接材料成本："+sqlMaterial);
	    		sqlEnergy=mysqlstate.getSQLStatement("sqlEnergy",powerprice);
	    		//System.out.println("电费与折旧明细: "+sqlEnergy);
	    		sqlAdi =mysqlstate.getSQLStatement("sqlAdi",powerprice);
	    		//System.out.println("辅料明细: "+sqlAdi);
	    		sqlModel =mysqlstate.getSQLStatement("sqlModel",powerprice);
	    		//System.out.println("工装模具明细: "+sqlModel);
	    		sqlLabourAndMake = mysqlstate.getSQLStatement("sqlLabourAndMake",powerprice);
	    		//System.out.println("人工与制造费用: "+sqlLabourAndMake );
	    		sqlPriceRPTform =mysqlstate.getSQLStatement("sqlPriceRPTform",powerprice);
		
	    		data.myTableModel(StdCostCalculate.mainFrame.tableBOM,sqlBOM,new int[]{});
	    		data.myTableModel(StdCostCalculate.mainFrame.tableMaterial,sqlMaterial,new int[]{5,7});
	    		//System.out.println("2.直接材料成本计算 成功，下一步，验证计算条件");
	    		if (StdCostCalculate.mainFrame.textFK12.getText().equals(String.valueOf(0.0))) 
	    		{
	    			bom.clean();
	    			StdCostCalculate.mainFrame.lblstatus.setText(" 没有有效的预算汇率！");
		    		JOptionPane.showMessageDialog(StdCostCalculate.mainFrame.frame, " 没有有效的预算汇率！");
		    		System.out.println(" 没有有效的预算汇率,," );
	    		}								
		    	else if (ProductInfo.firstitemid == 0)	
		    	{
		    		StdCostCalculate.mainFrame.lblstatus.setText(" 产品 " +fnumber +"itemid  不存在！");
		    		JOptionPane.showMessageDialog(StdCostCalculate.mainFrame.frame, " 产品 " +fnumber +"itemid  不存在！");
		    		System.out.println(" 产品 " +fnumber +" itemid 不存在！" );
		    	}
		    	else if (bomVerifyError > 0)
		    	{
		    		bom.clean();
		    		StdCostCalculate.mainFrame.lblstatus.setText(" 产品 " +fnumber +" bom 不完整！");
		    		JOptionPane.showMessageDialog(StdCostCalculate.mainFrame.frame, "BOM 不完整,请查看BOM");
		    		System.out.println("bom 不完整");
		    	}
		    	else if (RoutVerifyErr > 0) 
		    	{
		    		bom.clean();
		    		JOptionPane.showMessageDialog(StdCostCalculate.mainFrame.frame, "工艺路线 不完整");
			    	System.out.println("工艺路线 不完整," +df.format(new Date()).toString() +" ,"+fnumber);
			    }
		    	else if (RoutLWHVerifyErr > 0) 
		    	{
		    		bom.clean();
		    		JOptionPane.showMessageDialog(StdCostCalculate.mainFrame.frame, "芯体数据不完整 长:"+ProductInfo.length
		    				+"宽:"+ProductInfo.width+"高:"+ProductInfo.height);
			    	System.out.println("芯体数据不完整 长:"+ProductInfo.length+"宽:"+ProductInfo.width
			    			+"高:"+ProductInfo.height+" ," +df.format(new Date()).toString() 
			    			+" ,"+fnumber);
			    }
		    	else if (adiVerifyError > 0)
		    	{
		    		bom.clean();
		    		StdCostCalculate.mainFrame.lblstatus.setText(" 产品 " +fnumber +" 辅料价格 不完整！");
		    		JOptionPane.showMessageDialog(StdCostCalculate.mainFrame.frame, "有 "+ adiVerifyError 
		    				+" 种辅料在过去一年没有采购价格并且没有计划单价,"
		    				+ "请查看辅料明细");
		    		System.out.println("辅料价格 不完整");
		    	}
		    	else if (materialVerifyError  > 0)	
		    	{
		    		bom.clean();
		    		StdCostCalculate.mainFrame.lblstatus.setText(" 产品 " +fnumber +" 直接材料 价格 不完整！");
		    		JOptionPane.showMessageDialog(StdCostCalculate.mainFrame.frame, "有 直接材料 在过去一年没"
		    				+ "有采购价格并且没有计划单价,请查看直接材料成本明细");
		    		System.out.println("直接材料价格 不完整");
		    	}
		    	else 
		    	{
		    		//System.out.println("3.符合计算条件，下一步，计算人工与制造费用");
		    		labourAndMake.clean();
		    		labourAndMake.costLabourAndMake(
		    				Double.parseDouble(StdCostCalculate.mainFrame.textFK1.getText())
		    				, Double.parseDouble(StdCostCalculate.mainFrame.textFK2.getText())
		    				, Double.parseDouble(StdCostCalculate.mainFrame.textFK3.getText())
		    				, Double.parseDouble(StdCostCalculate.mainFrame.textFK0.getText())
		    				);
		    		//System.out.println("4.制造费用与直接人工 成功，下一步，生成报价");			
		    		data.myTableModel(StdCostCalculate.mainFrame.tableEnergy,sqlEnergy,new int[]{13,15});
		    		data.myTableModel(StdCostCalculate.mainFrame.tableAdi,sqlAdi,new int[]{12});
		    		data.myTableModel(StdCostCalculate.mainFrame.tableModel,sqlModel,new int[]{10});			
		    		data.myTableModel(StdCostCalculate.mainFrame.tableCalculate,sqlLabourAndMake
		    				,new int[]{7,8,9,10,11,12,13});
		    		data.myTableModel(StdCostCalculate.mainFrame.tableReport,sqlPriceRPTform,new int[]{});
			
		    		StdCostCalculate.mainFrame.lbl_ProdName.setText(fnumber); 
		    		StdCostCalculate.mainFrame.lbl_Model.setText(ProductInfo.model);
		    		StdCostCalculate.mainFrame.labItemname.setText(ProductInfo.itemname);
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
		    		stdCostReport.set(StdCostCalculate.mainFrame.tableReport
		    				, StdCostCalculate.mainFrame.tableMaterial, StdCostCalculate.mainFrame.tableCalculate
		    				, ProductInfo.packagesize, ProductInfo.gainrate);				    
		    		StdCostCalculate.mainFrame.txtNewModel.setText(StdCostCalculate.mainFrame.textFK16.getText());
		    		StdCostCalculate.mainFrame.txtNewModelSalQty.setText(StdCostCalculate.mainFrame.textFK17.getText());
		    		StdCostCalculate.mainFrame.txtHistSalQty.setText(String.valueOf(ProductInfo.itselfQtySaled));
		    		setDevelopeRequestValue.setDevelopRequestValue(ProductInfo.firstitemid);
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
	private ProductInfo productInfo=new ProductInfo();
	private DBConnect conn=new DBConnect();
	private BOM bom=new BOM();	
	private MachineInfo machineInfo=new MachineInfo();
    private MaterialDirect directMaterial=new MaterialDirect();
	private MaterialAdi adiMaterial = new MaterialAdi();
	private LabourAndMake labourAndMake=new LabourAndMake();
	private StdCostReport stdCostReport =new StdCostReport();
	private CompanyPricePolicy updateCompanyPricePolicy = new CompanyPricePolicy();
    private Route route=new Route();
	private DevelopeRequest setDevelopeRequestValue=new DevelopeRequest();
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        		
	private String fnumber;  
    private String[] fnumbers ;
	private int materialVerifyError
	,bomVerifyError,adiVerifyError,RoutVerifyErr,RoutLWHVerifyErr
	,k,sumFails;
	double powerprice = Double.parseDouble(StdCostCalculate.mainFrame.textFK0.getText());
	TableData data = new TableData();
	TableFormat formReport = new TableFormat();
	String sqlMaterial,sqlLabourAndMake,sqlPriceRPTform,sqlEnergy,sqlAdi,sqlModel,sqlBOM;
	SQlStatement mysqlstate=new SQlStatement();	
} 												


