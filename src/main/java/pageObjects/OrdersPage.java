package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import CommonUtilities.Utilities;

public class OrdersPage extends Utilities {

	WebDriver driver;
	
	// Constructor
    public OrdersPage(WebDriver driver) {
    	super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
	
	// PageFactory declarations
    @FindBy(css = "tr td:nth-child(3)")
    List<WebElement> productNames;

   public boolean verifyOrdersDisplay(String productName) {
	   Boolean match = productNames.stream().anyMatch(product->product.getText().equalsIgnoreCase(productName));
	   return match;
   }
	
}
