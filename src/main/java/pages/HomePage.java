package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage extends BasePage{

    private final By categoriesButton = By.cssSelector("button[data-testid$='header-button-category']");
    private final By headerMenuCategories = By.xpath("//div[contains(@data-testid,'header-menu-categories') and @role='dialog']//*[contains(@data-testid,'logo-side-menu')]");
    private final By mainPageHeader = By.cssSelector("[data-testid$='header']");
    private final By loginButton = By.cssSelector("button[data-testid$='header-menu-dropdown-button']");

    private String categoryItem = "//div[contains(@data-testid,'header-menu-categories') and @role='dialog']//span[contains(@data-testid,'header-menu-categories-menu-category-item--label') and contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '%s')]";
    private final String URL = "https://www.liverpool.com.mx/tienda/home";

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void clickCategoriesButton(){
        robustClick(categoriesButton);
    }

    public void clickLoginButton(){
        robustClick(loginButton);
    }

    public CategoryPage clickCategory(String category){
        if(isCategoriesMenuDisplayed()) {
            String finalCategoryLocator = String.format(categoryItem, category);
            System.out.println("category to navigate:" + finalCategoryLocator);
            robustClick(By.xpath(finalCategoryLocator));
            CategoryPage categoryPage = new CategoryPage(driver);
            return categoryPage;
        }
        return null;
    }

    public boolean isCategoriesMenuDisplayed(){
       return isElementDisplayed(headerMenuCategories);
    }

    public boolean isCategoriesMenuPresent(){
        try {
            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(headerMenuCategories));
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    public boolean isMainHeaderDisplayed(){
        return isElementDisplayed(mainPageHeader);
    }

    public void navigateTo(){
        driver.get(URL);
    }

    public void navigateToCategory(String category){
        clickCategoriesButton();
        clickCategory(category);
    }

    public String getCurrentBagQuantity(){
       return super.getCurrentBagQuantity();
    }

    public void clickBag(){
         super.clickToBag();
    }




}
