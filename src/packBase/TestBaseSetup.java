package packBase;

import java.io.File;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;

import pack.base.ExtentManager;
import pack.base.ExtentTestManager;

public class TestBaseSetup {
	private static WebDriver driver;
	private static String chromeDriverPath = ".\\Libs\\chromedriver.exe";
	public static String screenshotFileConfig = ".\\.\\log\\Screenshot\\";
	public static String screenshotFileExt = ".png";
	
//	 public static String loginUserName = "admin@tsc.com";
//	 public static String loginPwd = "123456";	
	
	public static String clearCacheUrl;
	public static ExtentReports extent;
	public static String pathTestData = ".\\src\\TestData\\"; 
	
	public static WebDriver GetDriver() 
	{
		return driver;
	}
		
	public static void SetDriver(String browserType, String appURL) 
	{
		switch (browserType) 
		{
		case "chrome":
			driver = InitChromeDriver(appURL);
			break;
		case "firefox":
			driver = InitFirefoxDriver(appURL);
			break;
		default:
			driver = InitFirefoxDriver(appURL);
		}
	}
	
	private static WebDriver InitChromeDriver(String appURL) 
	{
		System.setProperty("webdriver.chrome.driver", chromeDriverPath);        
		WebDriver driver = new ChromeDriver(); 
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.navigate().to(appURL);
		return driver;
	}
	
	private static WebDriver InitFirefoxDriver(String appURL) 
	{
		
		System.setProperty("webdriver.gecko.driver", "E:\\\\auto\\geckodriver-v0.9.0-win64\\geckodriver.exe");
		WebDriver driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.navigate().to(appURL);
		return driver;
		
	}

	@Parameters({ "clearCacheURL" })
	@BeforeTest
	public void initializeTestBaseSetup(String clearCacheURL)
	{
		try 
		{		
			clearCacheUrl = clearCacheURL;
		} 
		catch (Exception e) 
		{
			ExtentTestManager.getTest().log(LogStatus.ERROR, "Exception while Init TestBaseSetup");			
		}
	}
	
	@BeforeMethod
    public void beforeMethod(Method method) 
	{
		ExtentTestManager.extentTest = ExtentTestManager.startTest(method.getDeclaringClass() + " - function " + method.getName());
    }
	
	@AfterMethod
    protected void afterMethod(ITestResult result) throws Exception 
	{
        if (result.getStatus() == ITestResult.FAILURE) 
        {
        	// take screenshot if error
        	String filename = "error_" + result.getName();
        	TakeScreenshot(filename);
        	
        	// add screenshot to Extent Report
        	String image= ExtentTestManager.AddScreenCapture(screenshotFileConfig + filename + screenshotFileExt);
        	ExtentTestManager.getTest().log(LogStatus.FAIL, "Screenshot error", image);
			
        	// get error stack trace
            ExtentTestManager.getTest().log(LogStatus.FAIL, result.getThrowable());
        } 
        else if (result.getStatus() == ITestResult.SKIP) 
        {
            ExtentTestManager.getTest().log(LogStatus.SKIP, "Test skipped " + result.getThrowable());
        } 
        else 
        {
            ExtentTestManager.getTest().log(LogStatus.PASS, "Test passed");
        }
        
        ExtentManager.getReporter().endTest(ExtentTestManager.getTest());        
        ExtentManager.getReporter().flush();
    }
	
	@AfterTest
	public void tearDown() throws InterruptedException, SQLException {
		Thread.sleep(4000);
		driver.quit();
	}
	
	@AfterClass
	public void CloseBrower(){
		driver.quit();
	}
	
	public static void TakeScreenshot(String filename) throws Exception 
	{              
	      File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	      FileUtils.copyFile(scrFile, new File(screenshotFileConfig + filename + screenshotFileExt));
	}
}
