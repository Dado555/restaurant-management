package aldentebackend.e2e.tests;

import aldentebackend.e2e.pages.AdminHomePage;
import aldentebackend.e2e.pages.AdminTableLayoutPage;
import aldentebackend.e2e.pages.LoginPage;
import aldentebackend.e2e.util.Util;
import org.h2.mvstore.Page;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class AdminDragDropE2ETest {

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
    @Order(1)
    public void testMovingAndSave() {
        loginPage.loginManager("menadzer", "sifra123");
        new WebDriverWait(driver, 20).until(ExpectedConditions.urlToBe("http://localhost:4200/admin-home"));

        adminHomePage.getDashboardBtn().click();
        Util.keepTryingClick(adminHomePage.getTableLayoutBtn());
        assertTrue(new WebDriverWait(driver, 10).until(ExpectedConditions.urlToBe("http://localhost:4200/admin-table-layout")));


//        JavascriptExecutor jse = ((JavascriptExecutor) driver);
//        jse.executeScript("document.getElementById('analytics').scrollIntoView(true);");

        WebElement firstTable = adminTableLayoutPage.getFirstTable();
        String tableId = firstTable.getAttribute("id");
        Point point1 = firstTable.getLocation();

        firstTable = Util.presenceWait(driver, new By.ById(tableId), 10);
        WebElement webElement = adminTableLayoutPage.dragFirstTable(firstTable, 100, 30);

        Point point2 = webElement.getLocation();
        adminTableLayoutPage.saveTables();

        driver.get("http://localhost:4200/admin-table-layout");
        firstTable = Util.presenceWait(driver, new By.ById(tableId), 10);
        Point point3 = Util.visibilityWait(driver, firstTable, 10).getLocation();

        System.out.println("INITIAL POSITION = " + point1);
        System.out.println("MOVED POSITION = " + point2);
        System.out.println("RELOAD POSITION = " + point3);

        point1.x += 100;
        point1.y += 25;
        assertTrue(Math.abs(point1.x-point2.x) < 5);
        assertTrue(Math.abs(point1.y-point2.y) < 5);


        assertTrue(Math.abs(point2.x-point3.x) < 5);
        assertTrue(Math.abs(point2.y-point3.y) < 5);

    }

    @Test
    @Order(2)
    public void testAddingTable() {
        loginPage.loginManager("menadzer", "sifra123");
        new WebDriverWait(driver, 10).until(ExpectedConditions.urlToBe("http://localhost:4200/admin-home"));

        adminHomePage.goToTables();
        assertTrue(new WebDriverWait(driver, 10).until(ExpectedConditions.urlToBe("http://localhost:4200/admin-table-layout")));

        int total_tables = adminTableLayoutPage.getAllTables().size();

        adminTableLayoutPage.addTable();
        adminTableLayoutPage.saveTables();
        driver.get("http://localhost:4200/admin-table-layout");

        int new_total_tables = adminTableLayoutPage.getAllTables().size();

        assertEquals(total_tables + 1, new_total_tables);


    }

    @Test
    @Order(3)
    public void testDeletingTable() {
        loginPage.loginManager("menadzer", "sifra123");
        new WebDriverWait(driver, 10).until(ExpectedConditions.urlToBe("http://localhost:4200/admin-home"));

        adminHomePage.goToTables();
        assertTrue(new WebDriverWait(driver, 10).until(ExpectedConditions.urlToBe("http://localhost:4200/admin-table-layout")));

        List<WebElement> tables = adminTableLayoutPage.getAllTables();
        int total_tables = tables.size();

        WebElement forDelete = tables.get(total_tables - 1);
        adminTableLayoutPage.selectTable(forDelete);
        adminTableLayoutPage.deleteTable();
        adminTableLayoutPage.saveTables();
        driver.get("http://localhost:4200/admin-table-layout");

        int new_total_tables = adminTableLayoutPage.getAllTables().size();

        assertEquals(total_tables - 1, new_total_tables);
    }

    @AfterEach
    public void closeSelenium() {
        // Shutdown the browser
        driver.quit();
    }
}