package aldentebackend.e2e.tests;

import aldentebackend.e2e.pages.AdminOrdersPage;
import aldentebackend.e2e.pages.AdminWorkersPage;
import aldentebackend.e2e.pages.LoginPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
public class AdminOrdersE2ETest {

    private WebDriver driver;

    private LoginPage loginPage;

    private AdminOrdersPage adminOrdersPage;

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
        adminOrdersPage = PageFactory.initElements(driver, AdminOrdersPage.class);

        driver.manage().window().maximize();
        driver.get("http://localhost:4200/");
    }

    @Test
    public void filterOrders() {
        loginPage.loginManager("menadzer", "sifra123");
        new WebDriverWait(driver, 10).until(ExpectedConditions.urlToBe("http://localhost:4200/admin-home"));

        driver.get("http://localhost:4200/admin-orders");
        new WebDriverWait(driver, 10).until(ExpectedConditions.urlToBe("http://localhost:4200/admin-orders"));

        List<WebElement> rows;

        // checking for preparation
        // ===================================================================================
        adminOrdersPage.getFilterButton().click();
        adminOrdersPage.getForPreparationFilterButton().click();

        rows = adminOrdersPage.getTable().findElements(By.tagName("tr"));
        rows.remove(0);
        for (WebElement element : rows) {
            assertEquals("FOR_PREPARATION", element.findElement(By.id("status")).getText());
        }
        // ===================================================================================


        // checking in progress
        // ===================================================================================
        adminOrdersPage.getFilterButton().click();
        adminOrdersPage.getInProgressFilterButton().click();

        rows = adminOrdersPage.getTable().findElements(By.tagName("tr"));
        rows.remove(0);
        for (WebElement element : rows) {
            assertEquals("IN_PROGRESS", element.findElement(By.id("status")).getText());
        }
        // ===================================================================================


        // checking ready
        // ===================================================================================
        adminOrdersPage.getFilterButton().click();
        adminOrdersPage.getReadyFilterButton().click();

        rows = adminOrdersPage.getTable().findElements(By.tagName("tr"));
        rows.remove(0);
        for (WebElement element : rows) {
            assertEquals("READY", element.findElement(By.id("status")).getText());
        }
        // ===================================================================================


        // checking finished
        // ===================================================================================
        adminOrdersPage.getFilterButton().click();
        adminOrdersPage.getFinishedFilterButton().click();

        rows = adminOrdersPage.getTable().findElements(By.tagName("tr"));
        rows.remove(0);
        for (WebElement element : rows) {
            assertEquals("FINISHED", element.findElement(By.id("status")).getText());
        }
        // ===================================================================================


        // checking all status
        // ===================================================================================
        adminOrdersPage.getFilterButton().click();
        adminOrdersPage.getAllFilterButton().click();

        rows = adminOrdersPage.getTable().findElements(By.tagName("tr"));
        rows.remove(0);
        for (WebElement element : rows) {
            assertEquals(4, rows.size());
        }
        // ===================================================================================
    }

    @Test
    public void searchOrders() {
        loginPage.loginManager("menadzer", "sifra123");
        new WebDriverWait(driver, 10).until(ExpectedConditions.urlToBe("http://localhost:4200/admin-home"));

        driver.get("http://localhost:4200/admin-orders");
        new WebDriverWait(driver, 10).until(ExpectedConditions.urlToBe("http://localhost:4200/admin-orders"));

        List<WebElement> rows;

        // search by worker name
        // ===================================================================================
        adminOrdersPage.getSearchInput().clear();
        adminOrdersPage.getSearchInput().sendKeys("Vladan");
        adminOrdersPage.getSearchButton().click();

        rows = adminOrdersPage.getTable().findElements(By.tagName("tr"));
        rows.remove(0);
        assertEquals(4, rows.size());
        for (WebElement element : rows) {
            assertTrue(element.getText().contains("Vladan"));
        }
        // ===================================================================================


        // search by sector name
        // ===================================================================================
        adminOrdersPage.getSearchInput().clear();
        adminOrdersPage.getSearchInput().sendKeys("Prvi");
        adminOrdersPage.getSearchButton().click();

        rows = adminOrdersPage.getTable().findElements(By.tagName("tr"));
        rows.remove(0);
        assertEquals(2, rows.size());
        for (WebElement element : rows) {
            assertTrue(element.getText().contains("Prvi"));
        }
        // ===================================================================================


        // search by status name
        // ===================================================================================
        adminOrdersPage.getSearchInput().clear();
        adminOrdersPage.getSearchInput().sendKeys("READY");
        adminOrdersPage.getSearchButton().click();

        rows = adminOrdersPage.getTable().findElements(By.tagName("tr"));
        rows.remove(0);
        assertEquals(1, rows.size());
        for (WebElement element : rows) {
            assertTrue(element.getText().contains("READY"));
        }
        // ===================================================================================
    }

    @AfterEach
    public void closeSelenium() {
        // Shutdown the browser
        driver.quit();
    }

}
