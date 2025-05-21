package testCases;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class StandAloneTest {
	
	public static void main(String[] args) throws InterruptedException {
		String productName = "ZARA COAT 3";
		String confirmationmessage = "Thankyou for the order.";
		
		// Define driver of WebDriver type and assign it to ChromeDriver object
		WebDriver driver = new ChromeDriver();
		
		//Implicit wait timeout of 10 seconds applied throughout driver life span.
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		//Open the URL
		driver.get("https://rahulshettyacademy.com/client/");
		//Maximize the window
		driver.manage().window().maximize();
		//Login with user and password
		driver.findElement(By.id("userEmail")).sendKeys("mayuresh.talekar87@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Mayur@123");
		driver.findElement(By.id("login")).click();
		
		//Wait until product list appears.
		//wait until expectedconditions.visibilityofelementlocated is needed, simple find element by and isDisplayed will not wait
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
		
		//Collected all the displayed products card in one list
		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
		
		//Search for ZARA COAT 3 text under each product card using streams, here filter returns WebElement
		WebElement prod = products.stream().filter(product->
			product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
		
		//Now on this ZARA COAT 3 card, click on Add To Cart button
		prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
		
		//Wait until added to cart message is displayed, which disappears in some time.
		//wait until expectedconditions.visibilityofelementlocated is needed, simple find element by and isDisplayed will not wait
		//WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("toast-container")));
		Thread.sleep(1000);
		
		//Open the cart
		driver.findElement(By.cssSelector("[routerlink*='cart']")).click();
		
		//Get the list of added cart products
		List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));
		
		//Check for ZARA COAT 3 text if present under cart products using streams, here anyMatch returns boolean value.
		Boolean match = cartProducts.stream().anyMatch(product->product.getText().equalsIgnoreCase(productName));
		
		//Assertion check for true value return
		Assert.assertTrue(match);
		
		//Click on checkout button under cart
		driver.findElement(By.cssSelector(".totalRow .btn")).click();
		
		//Select India into shipping information details - here for practice purpose action class used
		Actions a = new Actions(driver);
		a.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "India").build().perform();
		
		//wait for country selection options from dropdown.
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results.list-group.ng-star-inserted")));
		
		//Select India from the list
		driver.findElement(By.cssSelector("button.ta-item.list-group-item.ng-star-inserted:nth-of-type(2)")).click();
		Thread.sleep(1000);
		
		//Scroll to and Click on place order button
		a.moveToElement(driver.findElement(By.cssSelector(".btnn.action__submit.ng-star-inserted"))).build().perform();
		driver.findElement(By.cssSelector(".btnn.action__submit.ng-star-inserted")).click();
		Thread.sleep(1000);
		
		//Check if Thank you message is displayed
		String thankyoumessage = driver.findElement(By.className("hero-primary")).getText();
		System.out.println("Actual Message:"+thankyoumessage+"	Expected :"+confirmationmessage);
		Assert.assertTrue(thankyoumessage.trim().equalsIgnoreCase(confirmationmessage));
		
		//Close the browser
		driver.close();
	}
}
