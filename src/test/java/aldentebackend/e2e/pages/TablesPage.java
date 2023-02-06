package aldentebackend.e2e.pages;

import aldentebackend.e2e.util.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class TablesPage {

    private final WebDriver driver;

    private List<WebElement> tables;

    @FindBy(id = "add-new-order-item-btn")
    private WebElement addNewOrderItemBtn;

    @FindBy(css = ".card button")
    private List<WebElement> addOrderItemBtns;

    @FindBy(id = "order-btn")
    private WebElement orderBtn;

    public TablesPage(WebDriver driver) {
        this.driver = driver;
    }

    public Integer getNumberOfOrderItems() {
        List<WebElement> orderItems = Util.visibilityWait(driver, By.cssSelector("table tbody tr"), 20);
        return orderItems.size();
    }

    public void firstTableBtnClick() {
        this.setTables();
        Util.clickableWait(driver, this.tables.get(0), 10).click();
    }

    public void secondTableBtnClick() {
        this.setTables();
        Util.clickableWait(driver, this.tables.get(1), 10).click();
    }


    public void thirdTableBtnClick() {
        this.setTables();
        Util.clickableWait(driver, this.tables.get(2), 10).click();
    }

    public void addNewOrderItemBtnClick() {
        Util.clickableWait(driver, this.addNewOrderItemBtn, 10).click();
    }

    public void addFirstItemToCart() {
        List<WebElement> addOrderItemBtns = Util.visibilityWait(driver, By.cssSelector(".card button"), 10);
        Util.clickableWait(driver, addOrderItemBtns.get(0), 10).click();

        this.orderBtnClick();
    }

    private void orderBtnClick() {
        Util.clickableWait(driver, this.orderBtn, 10).click();
    }

    private void setTables() {
        this.tables = Util.visibilityWait(driver, By.cssSelector(".draggable-table"), 10);
    }
}
