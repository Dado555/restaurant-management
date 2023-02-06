package aldentebackend.e2e.pages;

import aldentebackend.e2e.util.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AdminMenuItemsPage {
    private final WebDriver driver;
    private final JavascriptExecutor js;

    @FindBy(id = "addNew")
    private WebElement addNewMenuItemBtn;

    @FindBy(id = "search-menu-items-input")
    private WebElement searchMenuItemsInput;

    @FindBy(id = "search-button")
    private WebElement searchMenuItemsBtn;

    @FindBy(id = "filter-menu-items-button")
    private WebElement filterMenuItemsBtn;

    @FindBy(id = "filter-option-Aperitif")
    private WebElement filterMenuItemOption;

    @FindBy(id = "menu-item-AAAAAAAAAA")
    private WebElement menuItem;

    @FindBy(id = "val-name")
    private WebElement formMenuItemName;

    @FindBy(id = "val-price")
    private WebElement formMenuItemPrice;

    @FindBy(id = "val-expense")
    private WebElement formMenuItemExpense;

    @FindBy(id = "val-preparation")
    private WebElement formMenuItemPreparationTime;

    @FindBy(id = "val-ingredients")
    private WebElement formMenuItemIngredients;

    @FindBy(id = "type-food")
    private WebElement formMenuItemTypeFoodRadio;

    @FindBy(id = "existing-category")
    private WebElement formExistingCategoryRadio;

    @FindBy(id = "existing-category-select")
    private WebElement formExistingCategorySelectForm;

    @FindBy(id = "existing-category-Aperitif")
    private WebElement formExistingCategoryOption;

    @FindBy(id = "val-description")
    private WebElement formMenuItemDescription;

    @FindBy(id = "submitBtn")
    private WebElement formSubmitBtn;

    @FindBy(id = "name-sort-0")
    private WebElement nameSort;

    @FindBy(className = "mat-paginator-range-label")
    private WebElement paginatorText;

    @FindBy(id = "menu-item-counter")
    private WebElement menuItemsCounter;


    public AdminMenuItemsPage(WebDriver driver) {
        this.driver = driver;
        this.js = ((JavascriptExecutor) driver);
    }

    public void addNewMenuItemBtnClick() {
        Util.visibilityWait(driver, addNewMenuItemBtn, 10);
        Util.clickableWait(driver, addNewMenuItemBtn, 10).click();
    }

    public boolean formEnterData() {
        Util.visibilityWait(driver, formMenuItemName, 10);
        formMenuItemName.sendKeys("AAAAAAAAAA");

        Util.visibilityWait(driver, formMenuItemPrice, 10);
        formMenuItemPrice.sendKeys("300RSD");

        Util.visibilityWait(driver, formMenuItemExpense, 10);
        formMenuItemExpense.sendKeys("200RSD");

        Util.visibilityWait(driver, formMenuItemPreparationTime, 10);
        formMenuItemPreparationTime.sendKeys("1h 20m");

        Util.visibilityWait(driver, formMenuItemIngredients, 10);
        formMenuItemIngredients.sendKeys("100g secera...");

        Util.visibilityWait(driver, formMenuItemTypeFoodRadio, 10);
        Util.clickableWait(driver, formMenuItemTypeFoodRadio, 10).click();

        Util.visibilityWait(driver, formExistingCategoryRadio, 10);
        Util.clickableWait(driver, formExistingCategoryRadio, 10).click();

        Util.visibilityWait(driver, formExistingCategorySelectForm, 10);
        Util.clickableWait(driver, formExistingCategorySelectForm, 10).click();

        Util.visibilityWait(driver, formExistingCategoryOption, 10);
        Util.clickableWait(driver, formExistingCategoryOption, 10).click();

        Util.visibilityWait(driver, formMenuItemDescription, 10);
        formMenuItemDescription.sendKeys("100g secera...");

        Util.visibilityWait(driver, formSubmitBtn, 10);
        Util.clickableWait(driver, formSubmitBtn, 10).click();

        return true;
    }

    public void waitCounterUpdate() {
        Util.textWait(driver, menuItemsCounter, "8", 10);
    }

    public void sortAsc() {
        Util.visibilityWait(driver, nameSort, 10);
        Util.clickableWait(driver, nameSort, 10).click();
    }

    public boolean itemVisibleAndClickable() {
        Util.clickableWait(driver, menuItem, 10).click();
        return true;
    }

    public boolean changeDataUpdate() {
        Util.visibilityWait(driver, formMenuItemName, 10);
        formMenuItemName.sendKeys("BBBBBBBBBBB");

        Util.visibilityWait(driver, formSubmitBtn, 10);
        Util.clickableWait(driver, formSubmitBtn, 10).click();

        return true;
    }

//    public boolean ordersCountTextsCheck(String t1, String t2, String t3, String t4) {
//        Util.textWait(driver, ordersInProgressCountText, t1, 10);
//        Util.textWait(driver, ordersReadyCountText, t2, 10);
//        Util.textWait(driver, ordersDeliveredCountText, t3, 10);
//        Util.textWait(driver, ordersFinishedCountText, t4, 10);
//        return true;
//    }
//
//    public void adminDashboardBtnClick() {
//        Util.visibilityWait(driver, adminDashboardBtn, 10);
//        Util.clickableWait(driver, adminDashboardBtn, 10);
//        js.executeScript("arguments[0].click();", adminDashboardBtn);
//    }
}
