import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

public class testReg {

	WebDriver driver;
	master master = new master();

	@BeforeTest
	public void initialization() {
		this.driver = master.initialiseChromeDriver(driver);
	}

	@Test
	public void test1() {
		operations.Login(driver);
	}

	@Aftertest
	public void finalise() {
		master.teardown(driver);
	}
}
