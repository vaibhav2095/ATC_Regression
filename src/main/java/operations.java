import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class operations {

	WebDriver driver;
	master master = new master();

	/*
	 * constructor to assign value to direct object
	 * 
	 * @Para Webdriver driver object
	 */
	public operations(WebDriver driver) {
		this.driver = driver;
	}

	/*
	 * to login into the application
	 */
	public void login() throws IOException {
		driver.get("http://automationpractice.com/index.php");
		driver.findElement(By.xpath("//a[@class='login']")).click();
		driver.findElement(By.xpath("//input[@id='email']")).sendKeys(master.getConfigProperty("username"));
		driver.findElement(By.xpath("//input[@id='passwd']")).sendKeys(master.getConfigProperty("password"));
		driver.findElement(By.xpath("//button[@id='SubmitLogin']")).click();
		System.out.println(driver.getTitle());
	}

	/*
	 * to logout into the application
	 */
	public void signout() {
		driver.findElement(By.xpath("//a[@class='logout']")).click();
	}

}
