package pages;

import org.openqa.selenium.*;

import java.lang.ref.PhantomReference;
import java.util.List;

public class CategoryPage extends BasePage{

    private final By categoriesContainer = By.cssSelector("div[@data-testid$='filter-level-nav-slots']");
    private final By levelNavCategory = By.xpath("(//div[contains(@data-testid,'filter-level-nav-category-title-category-title-CAT')])[1]");
    private final By seeAllLink = By.cssSelector("a[@data-testid$='clp-page-link']");
    private final By currentPageInput = By.cssSelector("input[data-testid$='page-pagination-input']");
    private final By productList = By.cssSelector("div[id$='page-card-product-list']");
    private final By cardProduct = By.cssSelector("div[id$='page-card-product-list'] a[data-testid$='card-card-link']");

    private String categoryItem = "(//*[contains(@data-testid,'filter-level-nav-slots')]/a[contains(@data-testid,'filter-level-nav-slot') and contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '%s')])[1]";
    private String filterCategory ="//button[@data-testid='button-dropdown-filter']/span[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'),'%s')]";
    private String filterCategoryCheckBox ="(//button[@data-testid='button-dropdown-filter']/span[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'),'%s')]/ancestor::div[@class='border-b']//div[contains(@data-testid,'checkbox-group')]//*[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'),'%s')])[1]";

    private String searchBar = "//input[contains(translate(@placeholder,'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'),'%s')]";
    private String brandFilterApplied ="button[data-testid$='%s']";


    public CategoryPage(WebDriver driver) {
        super(driver);
    }

    public void clickCategory(String category){
        String finalCategoryXpath = String.format(categoryItem,category);
        robustClick(By.xpath(finalCategoryXpath));
    }

    public void clickSeeAll(){
        robustClick(seeAllLink);
    }

    public boolean isProductListDisplayed(){
        return isElementDisplayed(productList);
    }

    public boolean isFilterDisplayed(String filter){

        return isElementDisplayed(By.xpath(String.format(filterCategory,filter)));
    }



    public String getRootLevelCategory(){
        waitForElementToBePresent(levelNavCategory,2);
        WebElement element = driver.findElement(levelNavCategory);
        return getTextFromElement(element);
    }

    public void selectItemByNumberInPage(int index){
        if(isProductListDisplayed()){
            List<WebElement> products = driver.findElements(cardProduct);
            robustClick(products.get(index));
        }
    }

    public void selectCategoryFilter(String filter,String value){
        if(isFilterDisplayed(filter)){
            try {
                String parentString = "/parent::*";
                WebElement filterElement = driver.findElement(By.xpath(String.format(filterCategory,filter)));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", filterElement);;

                WebElement checkBox = driver.findElement(By.xpath(String.format(filterCategoryCheckBox,filter,value)));
                System.out.println("checkbox value"+String.format(filterCategoryCheckBox,filter,value));
                WebElement checkBoxFinal = driver.findElement(By.xpath(generateResultInputStringForCategory(filter,String.format(filterCategoryCheckBox,filter,value))));
                //checkBox.click();
                System.out.println("checkbox color:"+checkBoxFinal.getCssValue("color"));
                if (!checkBoxFinal.getCssValue("color").contains("rgba(225, 0, 152, 1)")){
                    checkBox = driver.findElement(By.xpath(String.format(filterCategoryCheckBox,filter,value)));
                    checkBox.click();
                    selectCategoryFilter( filter, value);
                }
            }
            catch (StaleElementReferenceException | NoSuchElementException e){
                selectCategoryFilter(filter,value);
            }

        }
    }

    public String generateResultInputStringForCategory(String category,String xpath){
        String parentString = "/parent::*";
        String finalString = "";
        String suffix ="//input/parent::span";

        if (category.toLowerCase().contains("color")){
            finalString += xpath + parentString.repeat(2)+suffix;
        }
        else {
            finalString += xpath + parentString.repeat(3)+suffix;
        }

        return finalString;
    }

    public void navigateToPage (String page){
        if(!page.equals(getCurrentProductsPage())){
            waitForElementToBePresent(currentPageInput,3);
            WebElement currentPage = driver.findElement(currentPageInput);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'center'});", currentPage);
            actions.click(currentPage)
                    .keyDown(Keys.CONTROL)
                    .sendKeys("a")
                    .keyUp(Keys.CONTROL)
                    .sendKeys(Keys.DELETE)
                    .sendKeys(page)
                    .build()
                    .perform();

        }
    }


    public String getCurrentProductsPage(){
        waitForElementToBePresent(currentPageInput,2);
        WebElement currentPage = driver.findElement(currentPageInput);
        return currentPage.getAttribute("value");
    }

    public void filterByBrand(String brand){
        String brandSearchBar = String.format(searchBar,"buscar marca");
        waitForElementToBePresent(By.xpath(brandSearchBar),3);
        WebElement searchBar = driver.findElement(By.xpath(brandSearchBar));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'center'});", searchBar);
        sendKeysWithAction(searchBar,brand);
        try {
            WebElement checkBox = driver.findElement(By.xpath(String.format(filterCategoryCheckBox,"marcas",brand)));
            String brandText = getTextFromElement(checkBox);
            actions.moveToElement(checkBox).click().build().perform();
            waitForElementToBePresent(By.cssSelector(String.format(brandFilterApplied,brandText)),1);
        }
        catch (NoSuchElementException | TimeoutException e){
            filterByBrand(brand);
        }

    }




}
