package aldentebackend.e2e.tests;

import aldentebackend.e2e.pages.LoginPage;
import aldentebackend.e2e.util.Util;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
public class LogoutE2ETest {

    private WebDriver driver;

    private LoginPage loginPage;

    @BeforeEach
    public void setUp() {
        String os = System.getProperty("os.name");
        System.out.println(os);
        if(os.contains("Windows")) {
            System.setProperty("webdriver.firefox.driver", "src/test/resources/geckodriver.exe");
            driver = new FirefoxDriver();
        } else if (os.contains("Linux")) {
            System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver_linux");
            driver = new ChromeDriver();
        } else {
            System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver");
            driver = new ChromeDriver();
        }

        loginPage = PageFactory.initElements(driver, LoginPage.class);

        driver.manage().window().maximize();
        driver.get("http://localhost:4200");
    }

    @Test
    public void logoutTest() {
        loginPage.loginManager("admin", "sifra123");
        assertTrue(Util.urlWait(driver, "http://localhost:4200/admin-home", 10));

        loginPage.logoutManager();
        assertTrue(Util.urlWait(driver, "http://localhost:4200/login", 10));

        driver.get("http://localhost:4200");

        loginPage.navbarCookClick();
        loginPage.loginWorker("1357", "MASTER");

        assertTrue(Util.urlWait(driver, "http://localhost:4200/waiter-home", 10));

        loginPage.logoutWorker();
        assertTrue(Util.urlWait(driver, "http://localhost:4200/login", 10));
    }

    @AfterEach
    public void closeSelenium() {
        // Shutdown the browser
        driver.quit();
    }
}
