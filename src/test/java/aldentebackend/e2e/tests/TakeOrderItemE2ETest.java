package aldentebackend.e2e.tests;

import aldentebackend.e2e.pages.AcceptedOrdersPage;
import aldentebackend.e2e.pages.LoginPage;
import aldentebackend.e2e.pages.NewOrdersPage;
import aldentebackend.e2e.util.Util;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TakeOrderItemE2ETest {

    private WebDriver driver;

    private LoginPage loginPage;

    private NewOrdersPage newOrdersPage;

    private AcceptedOrdersPage acceptedOrdersPage;

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
        newOrdersPage = PageFactory.initElements(driver, NewOrdersPage.class);
        acceptedOrdersPage = PageFactory.initElements(driver, AcceptedOrdersPage.class);

        driver.manage().window().maximize();
        driver.get("http://localhost:4200");
    }

    @Test
    public void takeOrderItemTest() {
        loginPage.navbarCookClick();
        loginPage.loginWorker("3579", "MASTER");
        assertTrue(Util.urlWait(driver, "http://localhost:4200/worker-home", 10));

        Integer oldNumberOfNewOrderItems = newOrdersPage.getNumberOfNewOrderItems();
        Integer oldNumberOfAcceptedOrderItems = acceptedOrdersPage.getNumberOfAcceptedOrders();

        newOrdersPage.firstTakeBtnElClick();

        driver.get("http://localhost:4200/worker-home");
        assertTrue(Util.urlWait(driver, "http://localhost:4200/worker-home", 10));

        Integer newNumberOfNewOrderItems = newOrdersPage.getNumberOfNewOrderItems();
        Integer newNumberOfAcceptedOrderItems = acceptedOrdersPage.getNumberOfAcceptedOrders();

        assertEquals(newNumberOfNewOrderItems, oldNumberOfNewOrderItems - 1);
        assertEquals(newNumberOfAcceptedOrderItems, oldNumberOfAcceptedOrderItems + 1);
    }

    @AfterEach
    public void closeSelenium() {
        // Shutdown the browser
        driver.quit();
    }
}
