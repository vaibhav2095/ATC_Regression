import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class testClass {

	WebDriver driver;
	master master = new master();
	operations operations;
	String newAddress = null;

	@BeforeTest
	public void initialization() throws IOException {
		this.driver = master.initialiseChromeDriver(driver);
		operations = new operations(driver);
		operations.login();
	}

	@Test(priority = 1)
	public void addAddress() throws IOException {
		newAddress = "My Address " + master.getRandomNumber();
		driver.findElement(By.xpath("//span[.='My addresses']")).click();
		driver.findElement(By.xpath("//span[.='Add a new address']")).click();
		driver.findElement(By.xpath("//input[@id='company']")).sendKeys("ATC");
		driver.findElement(By.xpath("//input[@id='address1']")).sendKeys("Test Street");
		driver.findElement(By.xpath("//input[@id='address2']")).sendKeys("T Nagar");
		driver.findElement(By.xpath("//input[@id='city']")).sendKeys(master.getConfigProperty("city"));
		driver.findElement(By.xpath("//input[@id='postcode']")).sendKeys(master.getConfigProperty("zip"));
		driver.findElement(By.xpath("//input[@id='phone_mobile']")).sendKeys(master.getConfigProperty("phone"));
		driver.findElement(By.xpath("//input[@id='city']")).clear();
		driver.findElement(By.xpath("//input[@id='city']")).sendKeys(master.getConfigProperty("city"));
		driver.findElement(By.xpath("//input[@name='alias']")).clear();
		driver.findElement(By.xpath("//input[@name='alias']")).sendKeys(newAddress);
		try {
			Select sel1 = new Select(driver.findElement(By.xpath("//select[@id='id_state']")));
			sel1.selectByVisibleText(master.getConfigProperty("state"));
		} catch (ElementNotVisibleException e) {
			System.out.println(e.getMessage());
		}
		driver.findElement(By.xpath("//span[contains(.,'Save')]")).click();
		driver.findElement(By.xpath("//h3[.=\"" + newAddress + "\"]")).isDisplayed();
	}

	@Test(priority = 2)
	public void addToCart() throws InterruptedException {
		int noOfItmesToBeSelected = 3;
		Actions action = new Actions(driver);
		for (int i = 0; i < noOfItmesToBeSelected; i++) {
			action.moveToElement(driver.findElement(By.xpath("//a[contains(.,'Women')]"))).perform();
			driver.findElement(By.xpath("(//ul[contains(@class,'clearfix first-in-line')]//a[.='Summer Dresses'])[1]"))
					.click();
			driver.findElement(By.xpath("//i[@class='icon-th-list']")).click();
			List<WebElement> itemsOnPage = driver
					.findElements(By.xpath("//div[@class='product-container']//a[@class='product-name']"));
			if (itemsOnPage.size() < noOfItmesToBeSelected) {
				System.out.println("there are not enough unique items on the page. Kindly modify your search criteria");
				break;
			}
			itemsOnPage.get(i).click();
			for (int ii = 0; ii < 4; ii++) {
				driver.findElement(By.xpath("//i[@class='icon-plus']")).click();
			}
			try {
				driver.findElement(By.xpath("(//a[contains(@id,'color')])[2]")).click();
			} catch (NoSuchElementException e) {
				System.out.println(e.getMessage());
			}
			driver.findElement(By.xpath("//button[@name='Submit']")).click();
			WebDriverWait wait = new WebDriverWait(driver, 15);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@name='Submit']")));
			WebElement continuebtn = driver.findElement(By.xpath("//span[contains(@class,'continue btn')]"));
			continuebtn.click();
		}

	}

	@Test(dependsOnMethods = { "addToCart" })
	public void checkout() throws InterruptedException {
		Actions action = new Actions(driver);
		action.moveToElement(driver.findElement(By.xpath("//a[@title='View my shopping cart']"))).perform();
		driver.findElement(By.xpath("//span[contains(.,'Check out')]")).click();
		driver.findElement(By.xpath("//a[contains(@class,'checkout button')]")).click();
		if (newAddress != null) {
			Select sel = new Select(driver.findElement(By.xpath("//select[@id='id_address_delivery']")));
			sel.selectByVisibleText(newAddress);
			Thread.sleep(5);
		}
		driver.findElement(By.xpath("//button[@name='processAddress']")).click();
		driver.findElement(By.xpath("//input[@id='cgv']")).click();
		driver.findElement(By.xpath("//button[@name='processCarrier']")).click();
		driver.findElement(By.xpath("//a[@class='bankwire']")).click();
		driver.findElement(By.xpath("//button[contains(@class,'button-medium')]")).click();
	}

	@Test(dependsOnMethods = { "checkout" })
	public void history() {
		driver.findElement(By.xpath("//a[@title='View my customer account']")).click();
		driver.findElement(By.xpath("//a[@title='Orders']")).click();
		master.scrollPage(driver);
		master.takeScreenshot(driver, "screenshot" + master.currTimeStamp() + ".png");
	}

	@AfterTest
	public void finalise() {
		operations.signout();
		master.teardown(driver);
	}
}
