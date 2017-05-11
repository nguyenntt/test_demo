package pack.Test;

import java.sql.SQLException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


import packBase.TestBaseSetup;
import pageObject.BasePage;
import pageObject.LoginPage;


public class LoginTest extends TestBaseSetup {
	
	
	private WebDriver driver;
	private LoginPage loginPage;
	private BasePage basePage;
	
	private static String loginUserName = "admin@tsc.com";
	private static String loginPwd = "123456";
	
	@Parameters({ "browserType", "appURL" })
	@BeforeClass
	public void setUp(String browserType, String appURL) throws SQLException, InterruptedException {
		
		TestBaseSetup.SetDriver(browserType, appURL);
		
		driver = GetDriver(); 
		
		basePage = new BasePage(driver);
		loginPage = new LoginPage(driver);
		
}
	@AfterClass
	public void tearDown(){
		 driver.quit();
	}
	@Test
	public void loginSuccess()throws SQLException, InterruptedException  {
		
		LoginPage loginPage = new LoginPage(driver);
		loginPage.ExecuteLogin( "vu@test.com", "Abc123123" );
		
	}
}