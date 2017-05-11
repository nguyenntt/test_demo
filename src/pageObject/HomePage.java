package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
	
	private WebDriver driver;	
	private BasePage basePage;
	
	
	 public HomePage(WebDriver driver) 
		{
			this.driver=driver;
			basePage = new BasePage(driver);
			PageFactory.initElements(driver, this);
		}
		
}
