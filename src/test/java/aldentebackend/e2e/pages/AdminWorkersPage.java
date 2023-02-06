package aldentebackend.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AdminWorkersPage {

    private final WebDriver driver;

    @FindBy(id = "select10008")
    private WebElement selectDeleteButton;

    @FindBy(id = "select10009")
    private WebElement selectUpdateButton;

    @FindBy(id = "delete10008")
    private WebElement deleteButton;

    @FindBy(id = "update10009")
    private WebElement updateButton;

    @FindBy(id = "lastName10009")
    private WebElement lastNameUpdate;

    @FindBy(id = "val-firstName")
    private WebElement firstName;

    @FindBy(id = "val-lastName")
    private WebElement lastName;

    @FindBy(id = "val-password")
    private WebElement password;

    @FindBy(id = "val-confirm-password")
    private WebElement passwordConfirm;

    @FindBy(id = "val-username")
    private WebElement username;

    @FindBy(id = "val-salary")
    private WebElement salary;

    @FindBy(id = "val-phone")
    private WebElement phoneNumber;

    @FindBy(id = "val-role")
    private WebElement role;

    @FindBy(id = "bartender")
    private WebElement bartender;

    @FindBy(id = "addNew")
    private WebElement addNew;

    @FindBy(id = "submitButton")
    private WebElement submitButton;

    public AdminWorkersPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getSelectDeleteButton() {
        return selectDeleteButton;
    }

    public WebElement getSelectUpdateButton() {
        return selectUpdateButton;
    }

    public WebElement getDeleteButton() {
        return deleteButton;
    }

    public WebElement getUpdateButton() {
        return updateButton;
    }

    public WebElement getFirstName() {
        return firstName;
    }

    public WebElement getLastName() {
        return lastName;
    }

    public WebElement getLastNameUpdate() {
        return lastNameUpdate;
    }

    public WebElement getPassword() {
        return password;
    }

    public WebElement getPasswordConfirm() {
        return passwordConfirm;
    }

    public WebElement getUsername() {
        return username;
    }

    public WebElement getSalary() {
        return salary;
    }

    public WebElement getPhoneNumber() {
        return phoneNumber;
    }

    public WebElement getRole() {
        return role;
    }

    public WebElement getBartender() {
        return bartender;
    }

    public WebElement getAddNew() {
        return addNew;
    }

    public WebElement getSubmitButton() {
        return submitButton;
    }

    public int getRowNum() {
        return new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[@id='table']/tbody/tr"))).size();
    }

}
