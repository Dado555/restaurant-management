package aldentebackend.e2e.pages;

import aldentebackend.e2e.util.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class WorkerHomePage {

    private final WebDriver driver;

    @FindBy(css = "#table-page tr")
    private List<WebElement> orderItems;

    @FindBy(id = "checkout-btn")
    private WebElement checkoutBtn;

    private List<WebElement> deliverBtns;

    public WorkerHomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void markAllOrderItemsAsReady() {
        List<WebElement> readys = Util.visibilityWait(driver, By.cssSelector(".ready-btn"), 10);
        try {
            Util.clickableWait(driver, readys.get(0), 10).click();
        } catch (Exception e) {
            Util.clickableWait(driver, readys.get(0), 10).click();
        }
    }

    public void takeAllOrderItems() {
        try {
            Util.clickableWait(driver, getTakeButtons().get(0), 10).click();
        } catch (Exception e) {
            Util.clickableWait(driver, getTakeButtons().get(0), 10).click();
        }
    }


    public void deliverAllOrderItems() {
        this.setDeliverBtns();
        deliverBtns.forEach(btn -> Util.clickableWait(driver, btn, 10).click());
    }

    public void checkoutBtnClick() {
        Util.clickableWait(driver, this.checkoutBtn, 10).click();
    }

    private List<WebElement> getTakeButtons() {
        return Util.visibilityWait(driver, By.cssSelector(".take-btn"), 10);
    }

    private List<WebElement> getPanels() {
        return Util.visibilityWait(driver, By.cssSelector("mat-expansion-panel.mat-expansion-panel"), 10);
    }

    private void setDeliverBtns() {
        this.deliverBtns = Util.visibilityWait(driver, By.cssSelector(".deliver-btn"), 10);
    }
}
