package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import CommonUtilities.Utilities;

public class CartPage extends Utilities {

	WebDriver driver;
	
	// Constructor
    public CartPage(WebDriver driver) {
    	super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
	
	// PageFactory declarations
    @FindBy(css = ".cartSection h3")
    List<WebElement> cartProducts;
    
    @FindBy(css=".totalRow .btn")
    WebElement checkoutBtn;

   public boolean verifyProductDisplay(String productName) {
	   Boolean match = cartProducts.stream().anyMatch(product->product.getText().equalsIgnoreCase(productName));
	   return match;
   }
   
   public CheckoutPage goToCheckout() {
	   checkoutBtn.click();
	   return new CheckoutPage(driver);
   }
	
}
