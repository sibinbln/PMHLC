package TestCases.ZigyCare;

import java.util.Properties;

import Operation.ExecutionStatusChecker;
import Operation.Launchbrowser;
import Operation.ReadExcel;
import Operation.ReadObjectRepository;
import Operation.SeleniumOperation;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CareLogin 
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
	public void testLogin() throws Exception 
	{
		
		ReadExcel re = new ReadExcel();
		String xllocation = System.getProperty("user.dir")+"\\TestData\\TestData.xls";
		re.setInputFile(xllocation, 0);
		String[][] data = re.readFile();
		
		try
		{	
			
			//----operation.perform(allObjects, Keyword, ObjectName, Object Type, Value)----
			operation.execute(allObjects, "ACCESSURL", "", "", data[0][1]);
			operation.execute(allObjects, "CLICK", "ZigyCareLabel", "xpath", data[0][1]);
			Thread.sleep(3000);
			operation.execute(allObjects, "CLICK", "NotLoggedInAccountLink", "xpath", "");
			operation.execute(allObjects, "SENDKEYS", "ZigyCareUserName", "xpath", data[1][1]);
			operation.execute(allObjects, "SENDKEYS", "ZigyCarePassword", "xpath", data[2][1]);
			Thread.sleep(3000);
			operation.execute(allObjects, "CLICK", "ZigyCareLogin", "xpath", "");
			Thread.sleep(3000);
			operation.execute(allObjects, "CLICK", "HVAccountLink", "xpath", "");
			operation.execute(allObjects, "CLICK", "Logout", "xpath", "");
			webdriver.quit();
		}
		catch (NoSuchElementException e) 
		{	
			e.printStackTrace();
			webdriver.quit();
		}	
		
	}
	
}
