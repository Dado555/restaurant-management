package aldentebackend.e2e.tests;

import aldentebackend.e2e.pages.AdminHomePage;
import aldentebackend.e2e.pages.AdminTableLayoutPage;
import aldentebackend.e2e.pages.LoginPage;
import aldentebackend.e2e.util.Util;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AdminSectorCrudE2ETest {

    private WebDriver driver;

    private LoginPage loginPage;

    private AdminHomePage adminHomePage;
    private AdminTableLayoutPage adminTableLayoutPage;


    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver");
        driver = new ChromeDriver();

        loginPage = PageFactory.initElements(driver, LoginPage.class);
        adminHomePage = PageFactory.initElements(driver, AdminHomePage.class);
        adminTableLayoutPage = PageFactory.initElements(driver, AdminTableLayoutPage.class);

        driver.manage().window().maximize();
        driver.get("http://localhost:4200/");
    }

    @Test
    public void addNewSector() {
        String newName = "NEW SECTOR";
        loginPage.loginManager("menadzer", "sifra123");
        new WebDriverWait(driver, 10).until(ExpectedConditions.urlToBe("http://localhost:4200/admin-home"));

        adminHomePage.goToTables();
        assertTrue(new WebDriverWait(driver, 10).until(ExpectedConditions.urlToBe("http://localhost:4200/admin-table-layout")));

        List<WebElement> oldTabs = adminTableLayoutPage.getAllSectorsTab();

        adminTableLayoutPage.addSector();
        adminTableLayoutPage.setSectorNameInput(newName);
        adminTableLayoutPage.submitFormSector();

        List<WebElement> tabs = adminTableLayoutPage.getAllSectorsTab();
        assertTrue(tabs.stream().anyMatch(x -> x.getText().equals(newName)));
        assertEquals(oldTabs.size() + 1, tabs.size());
    }

    @Test()
    public void testUpdateSector() {
        String newName = "NEW SECTOR EDIT EDIT";
        loginPage.loginManager("menadzer", "sifra123");
        new WebDriverWait(driver, 10).until(ExpectedConditions.urlToBe("http://localhost:4200/admin-home"));

        adminHomePage.goToTables();
        assertTrue(new WebDriverWait(driver, 10).until(ExpectedConditions.urlToBe("http://localhost:4200/admin-table-layout")));

        List<WebElement> oldTabs = adminTableLayoutPage.getAllSectorsTab();

        adminTableLayoutPage.selectTab(oldTabs.get(oldTabs.size()-1));
        adminTableLayoutPage.editSector();
        adminTableLayoutPage.setSectorNameInput(newName);
        adminTableLayoutPage.submitFormSector();

        List<WebElement> tabs = adminTableLayoutPage.getAllSectorsTab();
        assertTrue(tabs.stream().anyMatch(x -> x.getText().equals(newName)));
        assertEquals(oldTabs.size(), tabs.size());


    }

    @Test
    public void testDeletingSector() {
        loginPage.loginManager("menadzer", "sifra123");
        new WebDriverWait(driver, 10).until(ExpectedConditions.urlToBe("http://localhost:4200/admin-home"));

        adminHomePage.goToTables();
        assertTrue(new WebDriverWait(driver, 10).until(ExpectedConditions.urlToBe("http://localhost:4200/admin-table-layout")));

        List<WebElement> oldTabs = adminTableLayoutPage.getAllSectorsTab();
        WebElement forDelete = oldTabs.get(oldTabs.size()-1);
        String oldName = forDelete.getText();
        adminTableLayoutPage.selectTab(forDelete);
        adminTableLayoutPage.deleteSector();
        adminTableLayoutPage.submitDelete();

        List<WebElement> tabs = adminTableLayoutPage.getAllSectorsTab();
        assertTrue(!tabs.stream().anyMatch(x -> x.getText().equals(oldName)));
        assertEquals(oldTabs.size() - 1, tabs.size());
    }

    @AfterEach
    public void closeSelenium() {
        // Shutdown the browser
        driver.quit();
    }
}