package pages;

import org.openqa.selenium.*;

import java.lang.ref.PhantomReference;
import java.util.List;

public class CategoryPage extends BasePage {

    // Locators
    private final By levelNavCategory = By.xpath(
            "(//div[contains(@data-testid,'filter-level-nav-category-title-category-title-CAT')])[1]"
    );

    private final By seeAllLink = By.cssSelector(
            "a[data-testid$='clp-page-link']"
    );

    private final By currentPageInput = By.cssSelector(
            "input[data-testid$='page-pagination-input']"
    );

    private final By productList = By.cssSelector(
            "div[id$='page-card-product-list']"
    );

    private final By cardProduct = By.cssSelector(
            "div[id$='page-card-product-list'] a[data-testid$='card-card-link']"
    );

    // Dynamic locators
    private final String categoryItem = """
            (//*[contains(@data-testid,'filter-level-nav-slots')]
            /a[contains(@data-testid,'filter-level-nav-slot')
            and contains(translate(text(),
            'ABCDEFGHIJKLMNOPQRSTUVWXYZ',
            'abcdefghijklmnopqrstuvwxyz'), '%s')])[1]
            """;

    private final String filterCategory = """
            //button[@data-testid='button-dropdown-filter']
            /span[contains(translate(text(),
            'ABCDEFGHIJKLMNOPQRSTUVWXYZ',
            'abcdefghijklmnopqrstuvwxyz'),'%s')]
            """;

    private final String filterCategoryCheckBox = """
            (//button[@data-testid='button-dropdown-filter']
            /span[contains(translate(text(),
            'ABCDEFGHIJKLMNOPQRSTUVWXYZ',
            'abcdefghijklmnopqrstuvwxyz'),'%s')]
            /ancestor::div[@class='border-b']
            //div[contains(@data-testid,'checkbox-group')]
            //*[contains(translate(text(),
            'ABCDEFGHIJKLMNOPQRSTUVWXYZ',
            'abcdefghijklmnopqrstuvwxyz'),'%s')])[1]
            """;

    private final String searchBar = """
            //input[contains(translate(@placeholder,
            'ABCDEFGHIJKLMNOPQRSTUVWXYZ',
            'abcdefghijklmnopqrstuvwxyz'),'%s')]
            """;

    private final String brandFilterApplied =
            "button[data-testid$='%s']";


    public CategoryPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Selects a category from the category navigation.
     *
     * @param category category name to select
     */
    public void clickCategory(String category) {
        robustClick(By.xpath(String.format(categoryItem, category)));
    }

    /**
     * Clicks the "See All" link.
     */
    public void clickSeeAll() {
        robustClick(seeAllLink);
    }

    /**
     * Checks whether the product list is displayed.
     *
     * @return true if the product list is visible
     */
    public boolean isProductListDisplayed() {
        return isElementDisplayed(productList);
    }

    /**
     * Checks whether a specific filter is displayed.
     *
     * @param filter filter name
     * @return true if the filter is visible
     */
    public boolean isFilterDisplayed(String filter) {
        return isElementDisplayed(
                By.xpath(String.format(filterCategory, filter))
        );
    }

    /**
     * Returns the current root-level category name.
     *
     * @return root-level category name
     */
    public String getRootLevelCategory() {
        waitForElementToBePresent(levelNavCategory, 2);
        return getTextFromElement(driver.findElement(levelNavCategory));
    }

    /**
     * Selects a product from the current page by its zero-based index.
     *
     * @param index product index
     */
    public void selectItemByNumberInPage(int index) {
        if (!isProductListDisplayed()) {
            return;
        }

        List<WebElement> products = driver.findElements(cardProduct);

        if (index >= 0 && index < products.size()) {
            robustClick(products.get(index));
        }
    }

    /**
     * Selects a value from a category filter.
     *
     * The method retries a limited number of times in case the DOM
     * changes and causes a stale element reference.
     *
     * @param filter filter name
     * @param value value to select
     */
    public void selectCategoryFilter(String filter, String value) {
        if (!isFilterDisplayed(filter)) {
            return;
        }

        By checkboxLocator = By.xpath(
                String.format(filterCategoryCheckBox, filter, value)
        );

        for (int attempt = 0; attempt < 3; attempt++) {
            try {
                WebElement filterElement = driver.findElement(
                        By.xpath(String.format(filterCategory, filter))
                );

                scrollToElement(filterElement);

                WebElement checkbox = driver.findElement(checkboxLocator);

                String inputLocator =
                        generateResultInputStringForCategory(
                                filter,
                                checkboxLocator.toString().replace("By.xpath: ", "")
                        );

                WebElement checkboxInput =
                        driver.findElement(By.xpath(inputLocator));

                if (!isCheckboxSelected(checkboxInput)) {
                    robustClick(checkbox);
                }

                return;

            } catch (StaleElementReferenceException | NoSuchElementException e) {
                // Retry because the filter DOM may have been refreshed.
            }
        }
    }

    /**
     * Builds the XPath used to locate the checkbox input associated
     * with a category filter.
     *
     * @param category filter category
     * @param xpath base checkbox XPath
     * @return XPath for the checkbox input
     */
    public String generateResultInputStringForCategory(
            String category,
            String xpath) {

        String suffix = "//input/parent::span";

        int parentLevels = category.toLowerCase().contains("color") ? 2 : 3;

        return xpath + "/parent::*".repeat(parentLevels) + suffix;
    }

    /**
     * Navigates to the requested product page.
     *
     * @param page page number
     */
    public void navigateToPage(String page) {
        if (page.equals(getCurrentProductsPage())) {
            return;
        }

        waitForElementToBePresent(currentPageInput, 3);

        WebElement currentPage = driver.findElement(currentPageInput);
        scrollToElement(currentPage);

        actions.click(currentPage)
                .keyDown(Keys.CONTROL)
                .sendKeys("a")
                .keyUp(Keys.CONTROL)
                .sendKeys(page)
                .sendKeys(Keys.ENTER)
                .build()
                .perform();
    }

    /**
     * Returns the current product page number.
     *
     * @return current page number
     */
    public String getCurrentProductsPage() {
        waitForElementToBePresent(currentPageInput, 2);
        return driver.findElement(currentPageInput).getAttribute("value");
    }

    /**
     * Filters products by brand using the brand search field.
     *
     * @param brand brand name
     */
    public void filterByBrand(String brand) {
        String brandSearchBar = String.format(searchBar, "buscar marca");
        By searchBarLocator = By.xpath(brandSearchBar);

        waitForElementToBePresent(searchBarLocator, 3);

        WebElement searchInput = driver.findElement(searchBarLocator);
        scrollToElement(searchInput);
        sendKeysWithAction(searchInput, brand);

        for (int attempt = 0; attempt < 3; attempt++) {
            try {
                By checkboxLocator = By.xpath(
                        String.format(filterCategoryCheckBox, "marcas", brand)
                );

                WebElement checkbox = driver.findElement(checkboxLocator);
                String brandText = getTextFromElement(checkbox);

                robustClick(checkbox);

                waitForElementToBePresent(
                        By.cssSelector(String.format(brandFilterApplied, brandText)),
                        1
                );

                return;

            } catch (NoSuchElementException | TimeoutException |
                     StaleElementReferenceException e) {
                // Retry because the filter may still be loading.
            }
        }
    }

    /**
     * Scrolls an element to the center of the viewport.
     *
     * @param element element to scroll to
     */
    private void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center', inline: 'center'});",
                element
        );
    }

    /**
     * Checks whether a checkbox is already selected.
     *
     * @param checkbox checkbox element
     * @return true if the checkbox is selected
     */
    private boolean isCheckboxSelected(WebElement checkbox) {
        return checkbox.getCssValue("color")
                .contains("rgba(225, 0, 152, 1)");
    }
}

