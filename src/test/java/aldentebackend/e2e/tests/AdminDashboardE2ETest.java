package aldentebackend.e2e.tests;

import aldentebackend.e2e.pages.AdminHomePage;
import aldentebackend.e2e.pages.LoginPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
public class AdminDashboardE2ETest {
    private WebDriver driver;

    private LoginPage loginPage;

    private AdminHomePage adminHomePage;

    @BeforeEach
    public void setUp() {
        String os = System.getProperty("os.name");
        System.out.println(os);
        if(os.contains("Windows")) {
            System.setProperty("webdriver.firefox.driver", "src/test/resources/geckodriver.exe");
            driver = new FirefoxDriver();
        } else if (os.contains("Linux")) {
//            System.setProperty("webdriver.gecko.driver", "src/test/resources/geckodriver");
//            driver = new FirefoxDriver();
            System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver_linux");
            driver = new ChromeDriver();
        } else {
            System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver");
            driver = new ChromeDriver();
        }

        loginPage = PageFactory.initElements(driver, LoginPage.class);
        adminHomePage = PageFactory.initElements(driver, AdminHomePage.class);

        driver.manage().window().maximize();
        driver.get("http://localhost:4200/");
    }

    @Test
    public void testDashboard() {
        loginPage.loginManager("admin", "sifra123");
        new WebDriverWait(driver, 10).until(ExpectedConditions.urlToBe("http://localhost:4200/admin-home"));

        adminHomePage.hamburgerBtnClick();
        adminHomePage.adminAnalyticsBtnClick();

        assertTrue(new WebDriverWait(driver, 10).until(ExpectedConditions.urlToBe("http://localhost:4200/admin-analytics")));

        adminHomePage.hamburgerBtnClick();
        adminHomePage.adminDashboardBtnClick();

        assertTrue(new WebDriverWait(driver, 10).until(ExpectedConditions.urlToBe("http://localhost:4200/admin-home")));

        adminHomePage.ordersSummaryDayBtnClick();
        assertTrue(adminHomePage.ordersCountTextsCheck("0", "0", "0", "0"));

        adminHomePage.ordersSummaryMonthBtnClick();
        assertTrue(adminHomePage.ordersCountTextsCheck("1", "1", "0", "1"));

        adminHomePage.revenueSummaryDayBtnClick();
        assertTrue(adminHomePage.revenueTextsCheck("0", "0"));

        adminHomePage.revenueSummaryMonthBtnClick();
        assertTrue(adminHomePage.revenueTextsCheck("1350", "950"));
    }

    @AfterEach
    public void closeSelenium() {
        // Shutdown the browser
        driver.quit();
    }
}
