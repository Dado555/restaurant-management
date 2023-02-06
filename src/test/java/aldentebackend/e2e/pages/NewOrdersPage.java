package aldentebackend.e2e.pages;

import aldentebackend.e2e.util.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class NewOrdersPage {

    private final WebDriver driver;

    @FindBy(css = ".horizontal-scrollbar button")
    private List<WebElement> newOrderItemButtons;

    public NewOrdersPage(WebDriver driver) {
        this.driver = driver;
    }

    public void firstTakeBtnElClick() {
        WebElement firstTakeBtnEl = getFirstTakeBtnEl();
        Util.clickableWait(driver, firstTakeBtnEl, 10).click();
    }

    public Integer getNumberOfNewOrderItems() {
        return newOrderItemButtons.size();
    }

    private WebElement getFirstTakeBtnEl() {
        return Util.visibilityWait(driver, By.cssSelector(".horizontal-scrollbar button"), 10).get(0);
    }
}
