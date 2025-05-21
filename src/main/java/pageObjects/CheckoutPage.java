package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import CommonUtilities.Utilities;

public class CheckoutPage extends Utilities {

	WebDriver driver;
	
	public CheckoutPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css="[placeholder='Select Country']")
	WebElement selectCountry;
	
	@FindBy(css=".btnn.action__submit.ng-star-inserted")
	WebElement actionSubmit;
	
	@FindBy(css="button.ta-item.list-group-item.ng-star-inserted:nth-of-type(2)")
	WebElement selectCountryFromList;
	
	By countrylistLocator = By.cssSelector(".ta-results.list-group.ng-star-inserted");
	
	public void selectCountry(String country) throws InterruptedException {
		Actions a = new Actions(driver);
		a.sendKeys(selectCountry, country).build().perform();
		
		//wait for country selection options from dropdown.
		waitForElementToAppear(countrylistLocator);
		
		//Select last one from the list
		selectCountryFromList.click();
		Thread.sleep(1000);
	}
	
	public ConfirmationPage submitOrder() {
		Actions a = new Actions(driver);
		a.moveToElement(actionSubmit).build().perform();
		actionSubmit.click();
		return new ConfirmationPage(driver);
	}
}
