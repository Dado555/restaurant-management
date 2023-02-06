package aldentebackend.e2e.pages;

import aldentebackend.e2e.util.Util;
import lombok.Value;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class AdminTableLayoutPage {

    private final WebDriver driver;

    @FindBy(id = "hamburger")
    private WebElement dashboardBtn;

    @FindBy(id = "add-table-btn")
    private WebElement addTableBtn;

    @FindBy(id = "delete-table-btn")
    private WebElement deleteTableBtn;

    @FindBy(id = "save-table-btn")
    private WebElement saveTableBtn;


    @FindBy(id = "add-sector-btn")
    private WebElement addSectorBtn;

    @FindBy(id = "delete-sector-btn")
    private WebElement deleteSectorBtn;

    @FindBy(id = "edit-sector-btn")
    private WebElement editSectorBtn;

    @FindBy(xpath = "//*[@class='drag-cont']/div[1]")
    private WebElement firstTable;
    @FindBy(xpath = "//*[@class='drag-cont']/div[2]")
    private WebElement secondTable;



    @FindBy(css = "#sector-modal #val-name")
    private WebElement sectorNameInput;
    @FindBy(css = "#sector-modal #submitButton")
    private WebElement sectorFormSubmitBtn;
    @FindBy(css = "#submitButton")
    private WebElement submitDeleteBtn;

//    @FindBy(css="#admin-sectors .mat-tab-label-container .mat-tab-label")
//    private ArrayList<WebElement> sectorTabs;

    private String sectorTabsCss = "#admin-sectors .mat-tab-label-container .mat-tab-label";

//    @FindBy(xpath = "//*[@class='drag-cont']/div")
    private String bthAllTalbes = "//*[@class='drag-cont']/div";


    public AdminTableLayoutPage(WebDriver driver) { this.driver = driver; }

    public WebElement getDashboardBtn() {
        return Util.visibilityWait(driver, this.dashboardBtn, 10);
    }

    public WebElement getFirstTable() {
        return Util.clickableWait(driver, this.firstTable, 10);
    }
    public WebElement getSecondTable() {
        return Util.visibilityWait(driver, this.secondTable, 10);
    }
    private WebElement getAddTableBtn() {
        return Util.clickableWait(driver, this.addTableBtn, 10);
    }
    private WebElement getSaveTableBtn() {
        return Util.clickableWait(driver, this.saveTableBtn, 10);
    }
    private WebElement getDeleteTableBtn() {
        return Util.clickableWait(driver, this.deleteTableBtn, 10);
    }
    private WebElement getAddSectorBtn() {
        return Util.clickableWait(driver, this.addSectorBtn, 10);
    }
    private WebElement getEditSectorBtn() {
        return Util.clickableWait(driver, this.editSectorBtn, 10);
    }
    private WebElement getDeletedSectorBtn() {
        return Util.clickableWait(driver, this.deleteSectorBtn, 10);
    }
    private WebElement getSectorNameInput() {
        return Util.visibilityWait(driver, this.sectorNameInput, 10);
    }
    private WebElement getSectorFormSubmitBtn() {
        return Util.clickableWait(driver, this.sectorFormSubmitBtn, 10);
    }
    private WebElement getSubmitDeleteBtn() {
        return Util.clickableWait(driver, this.submitDeleteBtn, 10);
    }
    public List<WebElement> getAllSectorsTab() {
        return Util.visibilityWait(driver, new By.ByCssSelector(sectorTabsCss), 10);
    }


    public WebElement dragFirstTable(WebElement from, int x, int y) {
        Actions builder = new Actions(driver);
        builder.dragAndDropBy(from, x, y).perform();

        return from;
    }

    public WebElement saveTables() {
        WebElement saveBtn = getSaveTableBtn();
        Util.keepTryingClick(saveBtn);
        return saveBtn;
    }

    public WebElement deleteTable() {
        WebElement delBtn = getDeleteTableBtn();
        Util.keepTryingClick(delBtn);
        return delBtn;
    }

    public void selectTable(WebElement table) {
        Util.keepTryingClick(table);
    }
    public void selectTab(WebElement tab) {
        Util.keepTryingClick(tab);
    }

    public List<WebElement> getAllTables() {
        return Util.visibilityWait(driver, new By.ByXPath("//*[@class='drag-cont']/div"), 10);
    }

    public WebElement addTable() {
        WebElement addBtn = getAddTableBtn();
        Util.keepTryingClick(addBtn);
        return addBtn;

    }

    public void addSector() {
        Util.keepTryingClick(getAddSectorBtn());
    }
    public void deleteSector() {
        Util.keepTryingClick(getDeletedSectorBtn());
    }
    public void editSector() {
        Util.keepTryingClick(getEditSectorBtn());
    }

    public void submitFormSector() {
        Util.keepTryingClick(getSectorFormSubmitBtn());
    }

    public void submitDelete() {
        Util.keepTryingClick(getSubmitDeleteBtn());
    }

    public void setSectorNameInput(String text) {
        WebElement we = getSectorNameInput();
        we.clear();
        we.sendKeys(text);
    }

}
