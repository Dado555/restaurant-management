package aldentebackend.e2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AdminOrdersPage {

    private final WebDriver driver;

    @FindBy(id = "filter")
    private WebElement filterButton;

    @FindBy(id = "allFilter")
    private WebElement allFilterButton;

    @FindBy(id = "forPreparationFilter")
    private WebElement forPreparationFilterButton;

    @FindBy(id = "inProgressFilter")
    private WebElement inProgressFilterButton;

    @FindBy(id = "readyFilter")
    private WebElement readyFilterButton;

    @FindBy(id = "finishedFilter")
    private WebElement finishedFilterButton;

    @FindBy(id = "table")
    private WebElement table;

    @FindBy(id = "searchInput")
    private WebElement searchInput;

    @FindBy(id = "searchButton")
    private WebElement searchButton;

    public AdminOrdersPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getFilterButton() {
        return filterButton;
    }

    public WebElement getAllFilterButton() {
        return allFilterButton;
    }

    public WebElement getForPreparationFilterButton() {
        return forPreparationFilterButton;
    }

    public WebElement getInProgressFilterButton() {
        return inProgressFilterButton;
    }

    public WebElement getReadyFilterButton() {
        return readyFilterButton;
    }

    public WebElement getFinishedFilterButton() {
        return finishedFilterButton;
    }

    public WebElement getTable() {
        return table;
    }

    public WebElement getSearchInput() {
        return searchInput;
    }

    public WebElement getSearchButton() {
        return searchButton;
    }
}
