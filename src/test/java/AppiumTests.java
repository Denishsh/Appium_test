import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.*;
import org.openqa.selenium.remote.DesiredCapabilities;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AppiumTests {
    private AndroidDriver driver;

    @BeforeAll
    public void createDriver() throws MalformedURLException {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("platformName", "Android");
        desiredCapabilities.setCapability("appium:deviceName", "RFCR60S83HK");
        desiredCapabilities.setCapability("appium:appPackage", "ru.netology.testing.uiautomator");
        desiredCapabilities.setCapability("appium:appActivity", "MainActivity");
        desiredCapabilities.setCapability("appium:ensureWebviewsHavePages", true);
        desiredCapabilities.setCapability("appium:nativeWebScreenshot", true);
        desiredCapabilities.setCapability("appium:newCommandTimeout", 3600);
        desiredCapabilities.setCapability("appium:connectHardwareKeyboard", true);

        URL remoteUrl = new URL("http://127.0.0.1:4723/wd/hub");

        driver = new AndroidDriver(remoteUrl, desiredCapabilities);
    }

    @Test
    public void emptyStringTest() {
        MainActivityScreen mainScreen = new MainActivityScreen(driver);
        mainScreen.inputString.click();
        mainScreen.inputString.sendKeys("  ");
        mainScreen.editStringButton.click();
        String result = mainScreen.titleString.getText();

        Assertions.assertEquals("Привет, UiAutomator!", result);
    }

    @Test
    public void secondActivityTest() throws InterruptedException {
        String text = "123qwe";

        MainActivityScreen mainScreen = new MainActivityScreen(driver);
        SecondActivityScreen secondScreen = new SecondActivityScreen(driver);
        mainScreen.inputString.click();
        mainScreen.inputString.sendKeys(text);
        mainScreen.editStringButton.click();
        mainScreen.secActivityButton.click();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        String result = secondScreen.text.getText();

        Assertions.assertEquals(text, result);
    }

    @AfterAll
    public void tearDown() {
        driver.quit();
    }

}