package testCases;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import TestComponents.BaseTest;
import TestComponents.Retry;
import pageObjects.CartPage;
import pageObjects.ProductCataloguePage;

public class ErrorValidationTest extends BaseTest{
	
	@Test(groups = {"LoginError"}, retryAnalyzer = Retry.class)
	public void LoginErrorValidationTest() {
		String useremail = "mayuresh.talekar87@gmail.com";
		String userpass = "Mayur@1234";
		String errorMessage = "Incorrect email or passwor";
				
		//Login with user and password
		landingpage.login(useremail, userpass);
		
		//Check for error message
		System.out.println("Actual Error Message:"+landingpage.getErrorMessage()+"	Expected Error Message :"+errorMessage);
		Assert.assertEquals(landingpage.getErrorMessage(), errorMessage);
	}
	
	@Test
	public void ProductErrorValidationTest() throws InterruptedException {
		String useremail = "mayuresh.talekar87@gmail.com";
		String userpass = "Mayur@123";
		String productName = "ZARA COAT 3";
				
		//Login with user and password
		ProductCataloguePage productcataloguepage = landingpage.login(useremail, userpass);
		
		//Wait until product list is displayed and Collect all the displayed products card in one list
		List<WebElement> products = productcataloguepage.getProductList();
		
		//Add product to cart
		productcataloguepage.addProductToCart(productName);
		
		//Open the cart - Parent method
		CartPage cartpage = productcataloguepage.goToCartPage();
		
		//Get the list of added cart products
		//Check for ZARA COAT 3 text if present under cart products using streams, here anyMatch returns boolean value.
		Boolean match = cartpage.verifyProductDisplay("ZARA COAT 33");
		
		//Assertion check for true value return --> Should be always on test case - not at all in page object page.
		Assert.assertFalse(match);
	}
}
