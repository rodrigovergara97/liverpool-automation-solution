package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    // ============================================================
    // HEADER
    // ============================================================

    private final By categoriesButton =
            By.cssSelector(
                    "button[data-testid$='header-button-category']"
            );

    private final By headerMenuCategories =
            By.xpath(
                    "//div[contains(@data-testid,'header-menu-categories') " +
                            "and @role='dialog']" +
                            "//*[contains(@data-testid,'logo-side-menu')]"
            );

    private final By mainPageHeader =
            By.cssSelector("[data-testid$='header']");

    // ============================================================
    // LOGIN
    // ============================================================

    private final By loginButton =
            By.cssSelector(
                    "button[data-testid$='header-menu-dropdown-button']"
            );

    private final By profileButtonText =
            By.xpath(
                    "(//button[contains(@data-testid," +
                            "'header-menu-dropdown-button')]//span)[1]"
            );

    // ============================================================
    // CATEGORY MENU
    // ============================================================

    private final String categoryItem =
            "//div[contains(@data-testid,'header-menu-categories') " +
                    "and @role='dialog']" +
                    "//span[contains(@data-testid," +
                    "'header-menu-categories-menu-category-item--label') " +
                    "and contains(translate(text(), " +
                    "'ABCDEFGHIJKLMNOPQRSTUVWXYZ', " +
                    "'abcdefghijklmnopqrstuvwxyz'), '%s')]";

    private static final String URL =
            "https://www.liverpool.com.mx/tienda/home";

    public HomePage(WebDriver driver) {
        super(driver);
    }

    // ============================================================
    // HEADER / CATEGORIES
    // ============================================================

    /**
     * Opens the categories menu.
     */
    public void clickCategoriesButton() {
        robustClick(categoriesButton);
    }

    /**
     * Checks whether the categories menu is visible.
     *
     * @return true when the categories menu is displayed
     */
    public boolean isCategoriesMenuDisplayed() {
        return isElementDisplayed(headerMenuCategories);
    }

    /**
     * Waits for the categories menu to be present in the DOM.
     *
     * @return true when the menu is present
     */
    public boolean isCategoriesMenuPresent() {

        try {
            waitForElementToBePresent(
                    headerMenuCategories,
                    10
            );

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Selects a category from the categories menu.
     *
     * @param category category name
     * @return CategoryPage representing the selected category
     */
    public CategoryPage clickCategory(String category) {

        if (!isCategoriesMenuDisplayed()) {
            return null;
        }

        String categoryLocator =
                String.format(categoryItem, category);

        robustClick(By.xpath(categoryLocator));

        return new CategoryPage(driver);
    }

    /**
     * Opens the category menu and navigates to the requested category.
     *
     * @param category category to select
     */
    public void navigateToCategory(String category) {
        clickCategoriesButton();
        clickCategory(category);
    }

    /**
     * Checks whether the main header is displayed.
     *
     * @return true when the header is visible
     */
    public boolean isMainHeaderDisplayed() {
        return isElementDisplayed(mainPageHeader);
    }

    // ============================================================
    // LOGIN
    // ============================================================

    /**
     * Opens the login menu.
     */
    public void clickLoginButton() {
        robustClick(loginButton);
    }

    /**
     * Checks whether the user is logged in.
     *
     * @return true when the profile does not display "iniciar"
     */
    public boolean isLoginSuccessful() {

        waitForElementToBePresent(
                loginButton,
                2
        );

        String currentLogin =
                getTextFromElement(
                        driver.findElement(profileButtonText)
                );

        return !currentLogin
                .trim()
                .toLowerCase()
                .contains("iniciar");
    }

    // ============================================================
    // NAVIGATION
    // ============================================================

    /**
     * Navigates to the Liverpool home page.
     */
    public void navigateTo() {
        driver.get(URL);
    }

    // ============================================================
    // SHOPPING BAG
    // ============================================================

    /**
     * Returns the current shopping bag quantity.
     *
     * @return current bag quantity
     */
    public int getCurrentBagQuantity() {
        return getBagQuantity();
    }

    /**
     * Waits until the shopping bag quantity changes.
     * <p>
     * This should be used after adding a product to the bag.
     *
     * @param previousQuantity quantity before adding the product
     */
    public void waitForBagQuantityChange(int previousQuantity) {
        super.waitForBagQuantityChange(previousQuantity);
    }

    /**
     * Waits until the shopping bag reaches an exact quantity.
     *
     * @param expectedQuantity expected bag quantity
     */
    public void waitForBagQuantity(int expectedQuantity) {
        super.waitForBagQuantity(expectedQuantity);
    }

    /**
     * Opens the shopping bag.
     */
    public void clickBag() {
        clickToBag();
    }
}
