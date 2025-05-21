package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import CommonUtilities.Utilities;

public class ProductCataloguePage extends Utilities {

	WebDriver driver;
	
	// Constructor
    public ProductCataloguePage(WebDriver driver) {
    	super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
	
    //Collected all the displayed products card in one list
  	//List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
	
	// PageFactory declarations
    @FindBy(css = ".mb-3")
    List<WebElement> products;
    
    @FindBy(css = ".ng-animating")
    WebElement spinner;
    
    // By Locators
    By productsbylocator = By.cssSelector(".mb-3");
    By addToCart = By.cssSelector(".card-body button:last-of-type");
    By toastMessage = By.id("toast-container");

    // Function to get products list on product catalogue page
    public List<WebElement> getProductList() {
    	waitForElementToAppear(productsbylocator);
    	return products;
    }
    
    // Function to get product element by name
    public WebElement getProductByName(String productName) {
    	WebElement prod = getProductList().stream().filter(product->
		product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
    	return prod;
    }
	
    // Function to add product to the cart - by its name
    public void addProductToCart(String productName) throws InterruptedException {
    	WebElement prod = getProductByName(productName);
    	Thread.sleep(1000);
    	prod.findElement(addToCart).click();
    	waitForElementToAppear(toastMessage);
    	waitForElementToDisappear(spinner);
    }
	
}
