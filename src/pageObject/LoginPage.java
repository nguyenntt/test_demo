package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
	private WebDriver driver;
	private BasePage basePage;
	private HomePage homePage;
	
	// locate web page element
//	private By usernameTextBox = By.name("email");
//	private By passwordTextBox = By.name("password");
//	private By loginBtn = By.linkText("Log in");
	
	
	private By txtemail = By.id("email");
	private By txtpassword = By.id("password");
	private By Btnlogin = By.id("btnLogin");
	
	// constructor
	public LoginPage(WebDriver driver) 
	{
		this.driver=driver;
		basePage = new BasePage(driver);
		homePage = new HomePage(driver);
	}
	
	public void EnterUserName(String userName) 
	{
		basePage.Input(txtemail, userName);
	}
	
	public void EnterPassword(String password) 
	{
		basePage.Input(txtpassword, password);
	}
	
	public void ClickOnSignIn()
	{
		basePage.ClickButton(Btnlogin);
		basePage.WaitForAjaxLoadComplete();
	}
	
	public void WaitUntilLoadLoginPage()
	{
		new WebDriverWait(driver, 15).until(ExpectedConditions.visibilityOfElementLocated(txtemail));
	}
	
	public void ExecuteLogin(String userName, String pwd) throws InterruptedException
	{
		WaitUntilLoadLoginPage();		
		
		// login
		EnterUserName(userName);
		Thread.sleep(500);
		EnterPassword(pwd);
		ClickOnSignIn();
	}
}
