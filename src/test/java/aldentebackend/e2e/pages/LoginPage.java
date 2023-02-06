package aldentebackend.e2e.pages;

import aldentebackend.e2e.util.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;


public class LoginPage {

    private final WebDriver driver;

    @FindBy(id = "username")
    private WebElement usernameEl;

    @FindBy(id = "password")
    private WebElement passwordEl;

    private WebElement pinEl;

    @FindBy(id = "masterPassword")
    private WebElement masterPasswordEl;

    @FindBy(id = "worker-login-submit")
    private WebElement workerLoginBtnEl;

    @FindBy(id = "manager-login-submit")
    private WebElement managerLoginBtnEl;

    @FindBy(id = "mat-tab-label-0-3")
    private WebElement navbarCookEl;

    @FindBy(id = "hamburger")
    private WebElement hamburgerEl;

    @FindBy(id = "logout-btn")
    private WebElement logoutBtn;

    @FindBy(id = "admin-logout-btn")
    private WebElement logoutAdminBtn;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void loginManager(String username, String password) {
        this.setUsernameEl(username);
        this.setPasswordEl(password);

        this.managerloginBtnClick();
    }

    public void loginWorker(String pin, String masterPassword) {
        this.setMasterPasswordEl(masterPassword);
        this.workerloginBtnClick();

        this.setPinEl(pin);
        this.workerLoginBtnEl = Util.visibilityWait(driver, this.workerLoginBtnEl, 10);
        this.workerloginBtnClick();
    }

    public void loginWithoutMasterPassword(String pin) {
        this.setPinEl(pin);
    }

    public void logoutManager() {
        this.hamburgerEl = Util.visibilityWait(driver, this.hamburgerEl, 10);
        this.hamburgerElClick();

        this.logoutAdminBtn = Util.clickableWait(driver, this.logoutAdminBtn, 10);
        this.logoutAdminBtnClick();
    }

    public void logoutWorker() {
        this.logoutBtnClick();
    }

    private WebElement getUsernameEl() {
        return Util.visibilityWait(driver, this.usernameEl, 10);
    }

    private void setUsernameEl(String username) {
        WebElement usernameEl = this.getUsernameEl();
        usernameEl.clear();
        usernameEl.sendKeys(username);
    }

    private WebElement getPasswordEl() {
        return Util.visibilityWait(driver, this.passwordEl, 10);
    }

    private void setPasswordEl(String password) {
        WebElement passwordEl = this.getPasswordEl();
        passwordEl.clear();
        passwordEl.sendKeys(password);
    }

    private WebElement getPinEl() {
        return Util.visibilityWait(driver, By.cssSelector("#pin input"), 10).get(0);
    }

    private void setPinEl(String pin) {
        WebElement pinEl = this.getPinEl();
        pinEl.clear();
        pinEl.sendKeys(pin);
    }

    private WebElement getMasterPasswordEl() {
        return Util.visibilityWait(driver, this.masterPasswordEl, 10);
    }

    private void setMasterPasswordEl(String masterPassword) {
        WebElement masterPasswordEl = this.getMasterPasswordEl();
        masterPasswordEl.clear();
        masterPasswordEl.sendKeys(masterPassword);
    }

    public void navbarCookClick() {
        Util.clickableWait(driver, this.navbarCookEl, 10).click();
    }

    private void workerloginBtnClick() {
        Util.clickableWait(driver, this.workerLoginBtnEl, 10).click();
    }

    private void managerloginBtnClick() {
        Util.clickableWait(driver, this.managerLoginBtnEl, 10).click();
    }

    private void hamburgerElClick() {
        Util.clickableWait(driver, this.hamburgerEl, 10).click();
    }

    private void logoutAdminBtnClick() {
        Util.clickableWait(driver, this.logoutAdminBtn, 10).click();
    }

    private void logoutBtnClick() {
        Util.clickableWait(driver, this.logoutBtn, 10).click();
    }


}
