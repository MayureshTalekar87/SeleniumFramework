package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import CommonUtilities.Utilities;

public class LandingPage extends Utilities {

	WebDriver driver;
	
	// Constructor
    public LandingPage(WebDriver driver) {
    	super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
	
	//Login with user and password
	/*driver.findElement(By.id("userEmail")).sendKeys("mayuresh.talekar87@gmail.com");
	driver.findElement(By.id("userPassword")).sendKeys("Mayur@123");
	driver.findElement(By.id("login")).click();*/
	
	// PageFactory declarations
    @FindBy(id = "userEmail")
    WebElement useremail;

    @FindBy(id = "userPassword")
    WebElement password;

    @FindBy(id = "login")
    WebElement loginBtn;
    
    @FindBy(css = "[class*='flyInOut']")
    WebElement loginerrorMessage;

    // Actions
    public ProductCataloguePage login(String email, String pass) {
        useremail.sendKeys(email);
        password.sendKeys(pass);
        loginBtn.click();
        ProductCataloguePage productcataloguepage = new ProductCataloguePage(driver);
        return productcataloguepage;
    }
	
    public void launchURL() {
    	driver.get("https://rahulshettyacademy.com/client/");
    }
    
    public String getErrorMessage() {
    	waitForElementToAppear(loginerrorMessage);
    	return loginerrorMessage.getText();
    }
	
}
