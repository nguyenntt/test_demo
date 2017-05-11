package pageObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
	 private WebDriver driver;
	    private By ajaxloader = By.cssSelector("div.loading-content");

	    // constructor
	    public BasePage(WebDriver driver) {
	        this.driver = driver;
	    }


	    // Action
	    public String GetPageTitle() {
	        String title = driver.getTitle();
	        return title;
	    }

	    public void ClearTextbox(By webElement) {
	        try {
	            driver.findElement(webElement).clear();
	        } catch (Exception ex) {
	            throw ex;
	        }
	    }

	    public void ClickButton(By button) {
	        driver.findElement(button).click();
	    }


	    public void WaitForAjaxLoadComplete() {
	        new WebDriverWait(driver, 15).until(ExpectedConditions.invisibilityOfElementLocated(ajaxloader));
	    }

	    public void WaitForAjaxLoadCompleteLongTime() {
	        new WebDriverWait(driver, 60).until(ExpectedConditions.invisibilityOfElementLocated(ajaxloader));
	    }

	    public void WaitPresenceOfElementLocated(By by) {
	        (new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(by));
	    }

	    public By WaitPresenceOfElementLocated(String xpath) {
	        By by = By.xpath(xpath);

	        (new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(by));

	        return by;
	    }

	    public void WaitClickableOfElementLocated(By by) {
	        (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(by));
	    }

	    public void FindAndClick(By by) {
	        WebElement element = driver.findElement(by);
	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
	    }

	    public void Input(By by, String text) {
	        WebElement element = driver.findElement(by);
	        element.clear();
	        element.sendKeys(text);
	    }

	    public void InputAndSubmit(By by, String text) {
	        WebElement element = driver.findElement(by);
	        element.clear();
	        element.sendKeys(text);
	        element.submit();
	    }

	    public void InputAndEnter(By by, String text) {
	        WaitPresenceOfElementLocated(by);
	        WebElement element = driver.findElement(by);
	        element.clear();
	        element.sendKeys(text);
	        element.sendKeys(Keys.ENTER);
	    }

	    public void InputAndEnterWait(By by, String text) throws InterruptedException {
	        WaitPresenceOfElementLocated(by);
	        WebElement element = driver.findElement(by);
	        element.clear();
	        element.sendKeys(text);
	        Thread.sleep(1000);
	        element.sendKeys(Keys.ENTER);
	    }

	    public String GetTextOfElement(By by) {
	        WebElement element = driver.findElement(by);
	        return element.getText();
	    }

	    public String GetValueOfElement(By by) {
	        WebElement element = driver.findElement(by);
	        return element.getAttribute("value");
	    }

	    public Boolean GetValueOfCheckboxElement(By by) {
	        WebElement element = driver.findElement(by);
	        return element.isSelected();
	    }

	    public String GetValueOfSelectElement(By by) {
	        WebElement element = driver.findElement(by);
	        Select select = new Select(element);
	        WebElement option = select.getFirstSelectedOption();

	        return option.getText();
	    }

	    public String getValueFromAttribute(By by, String attributeName){
	        WebElement element = driver.findElement(by);
	        return element.getAttribute(attributeName);
	    }

	    /**
	     * get image source value
	     * @param by
	     * @return
	     */
	    public String getImageSource(By by){
	        WebElement element = driver.findElement(by);
	        return element.getAttribute("src");
	    }


	    public void DeleteCookies(){
	        driver.manage().deleteAllCookies();
	    }

	    public String readFile(String fileName) throws IOException {
	        BufferedReader br = new BufferedReader(new FileReader(fileName));
	        try {
	            StringBuilder sb = new StringBuilder();
	            String line = br.readLine();

	            while (line != null) {
	                sb.append(line);
	                sb.append("\n");
	                line = br.readLine();
	            }
	            return sb.toString();
	        } finally {
	            br.close();
	        }
	    }

	    public String AutoGenerateXId(){
	        Date now = new Date();
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(now);
	        return Long.toString(calendar.getTimeInMillis());
	    }

	    public Boolean ElementIsEmpty(By by){
	        try{
	            String classAtribute = driver.findElement(by).getAttribute("class");
	            if(classAtribute.contains("ng-hide")) return true;
	            return false;
	        }
	        catch(Exception ex){
	            return true;
	        }
	    }

	    public Boolean CheckElementExists(By by){
	        return driver.findElements(by).size() > 0;
	    }

	    public void GotoPageByUrl(String url){
	        driver.navigate().to(url);
	    }

	    public String FormatDate(Date date) throws ParseException{
	        if(date == null){
	            return "N/A";
	        }

	        DateFormat format = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss a");

	        return format.format(date);
	    }

	    public void SetValueSelectList(By by, String value){
	        // http://stackoverflow.com/questions/20138761/how-to-select-a-dropdown-value-in-selenium-webdriver-using-java
	        Select dropdown = new Select(driver.findElement(by));
	        dropdown.selectByValue(value);
	    }

	    /**
	     * check element existed
	     * @param by
	     * @return
	     */
	    public Boolean isExist(By by){
	        return driver.findElements(by).size()!= 0;
	    }
}
