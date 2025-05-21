package testCases;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import TestComponents.BaseTest;
import pageObjects.CartPage;
import pageObjects.CheckoutPage;
import pageObjects.ConfirmationPage;
import pageObjects.OrdersPage;
import pageObjects.ProductCataloguePage;

public class SubmitOrderTest extends BaseTest{
	
	@Test(dataProvider = "getData")
	public void SubmitOrder(HashMap<String, String> input) throws InterruptedException {
		//Data coming in from JSON with dataProvider getData method
		String useremail = input.get("email");
		String userpass = input.get("password");
		String productName = input.get("productName");
		
		String confirmationmessage = "Thankyou for the order.";
		String country = "India";
				
		//Login with user and password
		ProductCataloguePage productcataloguepage = landingpage.login(useremail, userpass);
		
		//Wait until product list is displayed and Collect all the displayed products card in one list
		List<WebElement> products = productcataloguepage.getProductList();
		
		//Search for ZARA COAT 3 text under each product card using streams, here filter returns WebElement
		//Now on this ZARA COAT 3 card, click on Add To Cart button
		//Wait until added to cart message is displayed, which disappears in some time.
		//wait until expectedconditions.visibilityofelementlocated is needed, simple find element by and isDisplayed will not wait
		productcataloguepage.addProductToCart(productName);
		
		//Open the cart - Parent method
		CartPage cartpage = productcataloguepage.goToCartPage();
		
		//Get the list of added cart products
		//Check for ZARA COAT 3 text if present under cart products using streams, here anyMatch returns boolean value.
		Boolean match = cartpage.verifyProductDisplay(productName);
		
		//Assertion check for true value return --> Should be always on test case - not at all in page object page.
		Assert.assertTrue(match);
		
		//Click on checkout button under cart
		CheckoutPage checkoutpage = cartpage.goToCheckout();
		
		//Select India into shipping information details
		checkoutpage.selectCountry(country);
		
		//Scroll to and Click on place order button
		ConfirmationPage confirmationpage = checkoutpage.submitOrder();
		
		//Check if Thank you message is displayed
		String thankyoumessage = confirmationpage.getConfirmationMessage();
		System.out.println("Actual Message:"+thankyoumessage+"	Expected :"+confirmationmessage);
		Assert.assertTrue(thankyoumessage.trim().equalsIgnoreCase(confirmationmessage));
	}

	@Test(dependsOnMethods = {"SubmitOrder"})
	public void OrderHistoryTest() throws InterruptedException {
		String useremail = "mayuresh.talekar87@gmail.com";
		String userpass = "Mayur@123";
		String productName = "ZARA COAT 3";
				
		//Login with user and password
		ProductCataloguePage productcataloguepage = landingpage.login(useremail, userpass);
		
		//Click on Order History Page
		OrdersPage orderspage = productcataloguepage.goToOrdersPage();
		
		//Check if orders display
		Assert.assertTrue(orderspage.verifyOrdersDisplay(productName));
	}
	
	@DataProvider
	public Object[][] getData() throws IOException {
		/*HashMap<String,String> hashmap = new HashMap<>();
		hashmap.put("email", "mayuresh.talekar87@gmail.com");
		hashmap.put("password", "Mayur@123");
		hashmap.put("productName", "ZARA COAT 3");
		
		HashMap<String,String> hashmap1 = new HashMap<>();
		hashmap1.put("email", "mltbeet@gmail.com");
		hashmap1.put("password", "Mayur@1234");
		hashmap1.put("productName", "ADIDAS ORIGINAL");
		
		return new Object[][]{{hashmap},{hashmap1}}; */
		
		List<HashMap<String, String>> data = 
				getJsonDataToMap(System.getProperty("user.dir")+"\\src\\test\\java\\testData\\submitOrder.json");
		
		return new Object[][] {{data.get(0)},{data.get(1)}};
		
	}
}
