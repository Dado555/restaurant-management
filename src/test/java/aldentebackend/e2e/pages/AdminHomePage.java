package aldentebackend.e2e.pages;

import aldentebackend.e2e.util.Util;
import lombok.Getter;
//import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

@Getter
public class AdminHomePage {

    private final WebDriver driver;
    private final JavascriptExecutor js;
//    private final Actions action;

    @FindBy(id = "hamburger")
    private WebElement dashboardBtn;

    @FindBy(id = "admin-analytics-btn")
    private WebElement adminAnalyticsBtn;

    @FindBy(id = "admin-table-btn")
    private WebElement tableLayoutBtn;

    public WebElement getDashboardBtn() {
        return Util.clickableWait(driver, this.dashboardBtn, 10);
    }

    public WebElement getTableLayoutBtn() {
        return Util.clickableWait(driver, this.tableLayoutBtn, 10);
    }

    public void goToTables() {
        getDashboardBtn().click();
        Util.keepTryingClick(getTableLayoutBtn());
    }


    @FindBy(id = "admin-dashboard-btn")
    private WebElement adminDashboardBtn;

    @FindBy(id = "admin-workers-btn")
    private WebElement adminWorkersBtn;

    @FindBy(id = "admin-orders-btn")
    private WebElement adminOrdersBtn;

    @FindBy(id = "admin-menu-items-btn")
    private WebElement adminMenuItemsBtn;

    @FindBy(id = "admin-table-btn")
    private WebElement adminTableBtn;

    @FindBy(id = "admin-logout-btn")
    private WebElement adminLogoutBtn;

    @FindBy(id = "orders-summary-month")
    private WebElement ordersSummaryMonthBtn;

    @FindBy(id = "orders-summary-day")
    private WebElement ordersSummaryDayBtn;

    @FindBy(id = "revenue-summary-month")
    private WebElement revenueSummaryMonthBtn;

    @FindBy(id = "revenue-summary-day")
    private WebElement revenueSummaryDayBtn;

    @FindBy(id = "orders-in-progress-count")
    private WebElement ordersInProgressCountText;

    @FindBy(id = "orders-ready-count")
    private WebElement ordersReadyCountText;

    @FindBy(id = "orders-delivered-count")
    private WebElement ordersDeliveredCountText;

    @FindBy(id = "orders-finished-count")
    private WebElement ordersFinishedCountText;

    @FindBy(id = "revenue-income-text")
    private WebElement revenueIncomeText;

    @FindBy(id = "revenue-expense-text")
    private WebElement revenueExpenseText;

    public AdminHomePage(WebDriver driver) {
        this.driver = driver;
        this.js = ((JavascriptExecutor) driver);
//        this.action = new Actions(driver);
    }

    public void hamburgerBtnClick() {
        Util.visibilityWait(driver, dashboardBtn, 10);
        Util.clickableWait(driver, dashboardBtn, 10).click();
    }

    public void ordersSummaryMonthBtnClick() {
        Util.clickableWait(driver, ordersSummaryMonthBtn, 10).click();
    }

    public void ordersSummaryDayBtnClick() {
        Util.clickableWait(driver, ordersSummaryDayBtn, 10).click();
    }

    public void revenueSummaryMonthBtnClick() {
        Util.clickableWait(driver, revenueSummaryMonthBtn, 10).click();
    }

    public void revenueSummaryDayBtnClick() {
        Util.clickableWait(driver, revenueSummaryDayBtn, 10).click();
    }

    public boolean ordersCountTextsCheck(String t1, String t2, String t3, String t4) {
        Util.textWait(driver, ordersInProgressCountText, t1, 10);
        Util.textWait(driver, ordersReadyCountText, t2, 10);
        Util.textWait(driver, ordersDeliveredCountText, t3, 10);
        Util.textWait(driver, ordersFinishedCountText, t4, 10);
        return true;
    }

    public boolean revenueTextsCheck(String t1, String t2) {
        Util.textWait(driver, revenueIncomeText, t1, 10);
        Util.textWait(driver, revenueExpenseText, t2, 10);
        return true;
    }

    public void adminAnalyticsBtnClick() {
        Util.visibilityWait(driver, adminAnalyticsBtn, 10);
        Util.clickableWait(driver, adminAnalyticsBtn, 10);
        js.executeScript("arguments[0].click();", adminAnalyticsBtn);
    }

    public void adminDashboardBtnClick() {
        Util.visibilityWait(driver, adminDashboardBtn, 10);
        Util.clickableWait(driver, adminDashboardBtn, 10);
        js.executeScript("arguments[0].click();", adminDashboardBtn);
    }

    public void adminWorkersBtnClick() {
        Util.visibilityWait(driver, adminWorkersBtn, 10);
        Util.clickableWait(driver, adminWorkersBtn, 10);
        js.executeScript("arguments[0].click();", adminWorkersBtn);
    }

    public void adminOrdersBtnClick() {
        Util.visibilityWait(driver, adminOrdersBtn, 10);
        Util.clickableWait(driver, adminOrdersBtn, 10);
        js.executeScript("arguments[0].click();", adminOrdersBtn);
    }

    public void adminMenuItemsBtnClick() {
        Util.visibilityWait(driver, adminMenuItemsBtn, 10);
        Util.clickableWait(driver, adminMenuItemsBtn, 10);
        js.executeScript("arguments[0].click();", adminMenuItemsBtn);
    }

    public void adminTableBtnClick() {
        Util.visibilityWait(driver, adminTableBtn, 10);
        Util.clickableWait(driver, adminTableBtn, 10);
        js.executeScript("arguments[0].click();", adminTableBtn);
    }

    public void adminLogoutBtnClick() {
        Util.visibilityWait(driver, adminLogoutBtn, 10);
        Util.clickableWait(driver, adminLogoutBtn, 10);
        js.executeScript("arguments[0].click();", adminLogoutBtn);
//        action.moveToElement(adminLogoutBtn).click().perform();
    }
}
