import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class master {

	/*
	 * initialising chrome driver
	 * 
	 * @Param Webdriver driver object
	 * 
	 * @return instantised driver object
	 */
	public WebDriver initialiseChromeDriver(WebDriver driver) {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "//chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		return driver;
	}

	/*
	 * read config.properties file to fetch the values
	 * 
	 * @Param String Property Name for which value to be fetched
	 * 
	 * @return String Property value
	 */
	public String getConfigProperty(String propertyName) throws IOException {
		Properties prop = new Properties();
		InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties");
		if (input != null) {
			prop.load(input);
		} else {
			throw new FileNotFoundException("config.properties not found on desired path");
		}
		return prop.getProperty(propertyName);

	}

	/*
	 * to generate random number between 0 to 999
	 *
	 * @return int random integer value
	 */
	public int getRandomNumber() {
		Random rand = new Random();
		return rand.nextInt(1000);
	}

	/*
	 * capture the screenshot of the webpage
	 * 
	 * @Param WebDriver driver object, String filename used to store the image
	 */
	public void takeScreenshot(WebDriver driver, String filename) {
		TakesScreenshot scr = (TakesScreenshot) driver;
		File SrcFile = scr.getScreenshotAs(OutputType.FILE);
		File DestFile = new File(System.getProperty("user.dir") + "//orderSummary//" + filename);
		try {
			FileUtils.copyFile(SrcFile, DestFile);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	/*
	 * to get the current time stamp
	 * 
	 * @return String time stamp in format "yyMMddHHmm"
	 */
	public String currTimeStamp() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmm");
		return sdf.format(new Date());
	}

	/*
	 * scroll down the web page by 500 pixals
	 * 
	 * @Param Webdriver driver object
	 * 
	 */
	public void scrollPage(WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,500)");

	}
	
	/*
	 * close the browser 
	 * 
	 * @param Webdriver driver object
	 */
	public void teardown(WebDriver driver) {
		driver.quit();
	}
}
