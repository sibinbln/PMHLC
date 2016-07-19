package TestCases.ZigyCure;

import java.util.Properties;

import Operation.ExecutionStatusChecker;
import Operation.Launchbrowser;
import Operation.ReadExcel;
import Operation.ReadObjectRepository;
import Operation.SeleniumOperation;
import Operation.WriteExcel;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AddtoOrder 
{
	static WebDriver webdriver;
	static Properties allObjects;
	static SeleniumOperation operation;
	String className = getClass().getName();
		
	@BeforeClass
	public void ExecutionStatusCheck() throws Exception
	{
		boolean status = ExecutionStatusChecker.getExecutionStatus(className);
		ExecutionStatusChecker.executetestcase(className, status);
		webdriver=Launchbrowser.launchBrowser();
		ReadObjectRepository object = new ReadObjectRepository();
		allObjects = object.getObject();
		operation = new SeleniumOperation(webdriver);
	}
	
	@Test
	public void testAdd2Order() throws Exception 
	{
		
		ReadExcel re = new ReadExcel();
		String xllocation = System.getProperty("user.dir")+"\\TestData\\TestData.xls";
		re.setInputFile(xllocation, 0);
		String[][] data = re.readFile();
		
		try
		{	
			
			//----operation.perform(allObjects, Keyword, ObjectName, Object Type, Value)----
			operation.execute(allObjects, "ACCESSURL", "", "", data[0][1]);
			operation.execute(allObjects, "CLICK", "ZigyCureLabel", "xpath", data[0][1]);
			boolean presence = SeleniumOperation.isdisplayed(allObjects, "ISDISPLAYED", "PromotionAdd", "xpath", "");
				Thread.sleep(3000);
				if (presence == true)
				{
					operation.execute(allObjects, "CLICK", "PromotionAdd_Close", "xpath", "");
				}
						
			operation.execute(allObjects, "CLICK", "OrderForm_MedicineName", "xpath", "");
			operation.execute(allObjects, "CLEAR", "DeliveryPinCode", "xpath", "");
			operation.execute(allObjects, "SENDKEYS", "DeliveryPinCode", "xpath", "560076");
			operation.execute(allObjects, "CLICK", "DeliveryPinCode_Proceed", "xpath", "");
				if (presence == true)
				{
					operation.execute(allObjects, "CLICK", "PromotionAdd_Close", "xpath", "");
				}

			operation.execute(allObjects, "CLICK", "OrderForm_MedicineName", "xpath", "");
			
			Actions action1 = new Actions(webdriver);
			Actions action2 = new Actions(webdriver);
			SeleniumOperation.action(allObjects, "SENDKEYS", "OrderForm_MedicineName", "xpath", action1, "Paracet");
			Thread.sleep(3000);
			SeleniumOperation.action(allObjects, "CLICK", "MedicineName_Selection", "xpath", action2, "");
			Thread.sleep(3000);
			
			String medicine_name = operation.execute(allObjects, "GETTEXTVALUE", "OrderForm_MedicineName", "xpath", "value");
			String medicine_qty = operation.execute(allObjects, "GETTEXTVALUE", "Medicine_Qty", "xpath", "value");
			String medicine_cost = operation.execute(allObjects, "GETTEXT", "Medicine_Cost", "xpath", "");
			Thread.sleep(3000);
			
			WriteExcel objExcelFile = new WriteExcel();
			String MedicineDetails = "Name = "+medicine_name+"- Qty = "+medicine_qty+"- Price = INR"+medicine_cost;
			String[] MedDetails =  new String[] {MedicineDetails};
			objExcelFile.writeExcel(System.getProperty("user.dir")+"\\TestData","Output.xls","MedicineDetails",MedDetails);
			
			operation.execute(allObjects, "SCROLLFORM", "OrderForm_Scroller", "id", "1500");
			operation.execute(allObjects, "CLICK", "AddtoOrder", "xpath", "");
			Thread.sleep(5000);
						
			webdriver.quit();
		}
		catch (NoSuchElementException e) 
		{	
			e.printStackTrace();
			webdriver.quit();
		}	
		
	}
}
