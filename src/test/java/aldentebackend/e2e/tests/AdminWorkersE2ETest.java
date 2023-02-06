package aldentebackend.e2e.tests;

import aldentebackend.e2e.pages.LoginPage;
import aldentebackend.e2e.pages.AdminWorkersPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
public class AdminWorkersE2ETest {

    private WebDriver driver;

    private LoginPage loginPage;

    private AdminWorkersPage adminWorkersPage;

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
        adminWorkersPage = PageFactory.initElements(driver, AdminWorkersPage.class);

        driver.manage().window().maximize();
        driver.get("http://localhost:4200/");
    }

    @Test
    public void deleteWorker() {
        loginPage.loginManager("menadzer", "sifra123");
        new WebDriverWait(driver, 10).until(ExpectedConditions.urlToBe("http://localhost:4200/admin-home"));

        driver.get("http://localhost:4200/admin-workers");
        new WebDriverWait(driver, 10).until(ExpectedConditions.urlToBe("http://localhost:4200/admin-workers"));

        adminWorkersPage.getSelectDeleteButton().click();
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(adminWorkersPage.getDeleteButton()));
        adminWorkersPage.getDeleteButton().click();
        assertEquals(4, adminWorkersPage.getRowNum());
    }

    @Test
    public void updateWorker() {
        loginPage.loginManager("menadzer", "sifra123");
        new WebDriverWait(driver, 10).until(ExpectedConditions.urlToBe("http://localhost:4200/admin-home"));

        driver.get("http://localhost:4200/admin-workers");
        new WebDriverWait(driver, 10).until(ExpectedConditions.urlToBe("http://localhost:4200/admin-workers"));

        adminWorkersPage.getSelectUpdateButton().click();
        adminWorkersPage.getUpdateButton().click();
        adminWorkersPage.getLastName().clear();
        adminWorkersPage.getLastName().sendKeys("Sinson");
        adminWorkersPage.getPassword().sendKeys("mickoloz");
        adminWorkersPage.getPasswordConfirm().sendKeys("mickoloz");
        adminWorkersPage.getSubmitButton().click();

        new WebDriverWait(driver, 10).until(ExpectedConditions.textToBe(By.id("lastName10009"), "Sinson"));

        assertEquals("Sinson", adminWorkersPage.getLastNameUpdate().getText());
    }

    @Test
    public void createWorker() {
        loginPage.loginManager("menadzer", "sifra123");
        new WebDriverWait(driver, 10).until(ExpectedConditions.urlToBe("http://localhost:4200/admin-home"));

        driver.get("http://localhost:4200/admin-workers");
        new WebDriverWait(driver, 10).until(ExpectedConditions.urlToBe("http://localhost:4200/admin-workers"));

        adminWorkersPage.getAddNew().click();
        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(adminWorkersPage.getFirstName()));
        adminWorkersPage.getFirstName().sendKeys("Micko");
        adminWorkersPage.getLastName().sendKeys("Loznica");
        adminWorkersPage.getPassword().sendKeys("mickoloz123");
        adminWorkersPage.getPasswordConfirm().sendKeys("mickoloz123");
        adminWorkersPage.getUsername().sendKeys("mickoloz");
        adminWorkersPage.getRole().click();
        adminWorkersPage.getBartender().click();
        adminWorkersPage.getSalary().sendKeys("50000");
        adminWorkersPage.getPhoneNumber().sendKeys("064332567");
        adminWorkersPage.getSubmitButton().click();

        driver.get("http://localhost:4200/admin-workers");
        new WebDriverWait(driver, 10).until(ExpectedConditions.urlToBe("http://localhost:4200/admin-workers"));

        assertEquals(5, adminWorkersPage.getRowNum());
    }

    @AfterEach
    public void closeSelenium() {
        // Shutdown the browser
        driver.quit();
    }
}
