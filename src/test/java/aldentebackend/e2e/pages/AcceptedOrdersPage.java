package aldentebackend.e2e.pages;

import aldentebackend.e2e.util.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class AcceptedOrdersPage {

    private final WebDriver driver;

    @FindBy(css = ".vertical-scrollbar button")
    private List<WebElement> acceptedOrderButtons;

    public AcceptedOrdersPage(WebDriver driver) {
        this.driver = driver;
    }

    public void firstReadyBtnElClick() {
        WebElement firstReadyBtnEl = getFirstReadyBtnEl();
        Util.clickableWait(driver, firstReadyBtnEl, 10).click();
    }

    public Integer getNumberOfAcceptedOrders() {
        return acceptedOrderButtons.size();
    }

    private WebElement getFirstReadyBtnEl() {
        return Util.visibilityWait(driver, By.cssSelector(".vertical-scrollbar button"), 10).get(0);
    }
}
