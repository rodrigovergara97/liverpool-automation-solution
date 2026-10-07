package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.WebDriverUtil;

import java.awt.event.WindowAdapter;


import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;



public class BasePage extends WebDriverUtil {

    /*
     * Shopping bag quantity displayed in the global header.
     */
    private final By bagQuantity = By.cssSelector(
            "div[data-testid$='-header-shopping-cart-header-cart-quantity']"
    );

    public BasePage(WebDriver driver) {
        super(driver);
    }

    /**
     * Gets the current quantity displayed in the shopping bag.
     *
     * The method returns 0 when the counter is not displayed
     * or when the counter does not contain a numeric value.
     *
     * @return current shopping bag quantity
     */
    protected int getBagQuantity() {

        try {
            WebElement bagElement =
                    driver.findElement(bagQuantity);

            String text =
                    getTextFromElement(bagElement);

            String quantity =
                    text.replaceAll("[^0-9]", "").trim();

            return quantity.isEmpty()
                    ? 0
                    : Integer.parseInt(quantity);

        } catch (NoSuchElementException | NumberFormatException e) {
            return 0;
        }
    }

    /**
     * Waits until the shopping bag quantity changes from
     * the value captured before adding the product.
     *
     * This is intentionally based on a change rather than
     * an expected calculated quantity because the website
     * may display the number of products/SKUs instead of
     * the total number of units.
     *
     * @param previousQuantity quantity before adding the product
     */
    protected void waitForBagQuantityChange(int previousQuantity) {

        try {

            wait.until(driver ->
                    getBagQuantity() != previousQuantity
            );

        } catch (TimeoutException e) {

            int currentQuantity = getBagQuantity();

            throw new TimeoutException(
                    "Shopping bag quantity was not updated. "
                            + "Expected a value different from: "
                            + previousQuantity
                            + ", but current value is: "
                            + currentQuantity
            );
        }
    }

    /**
     * Waits until the shopping bag reaches an exact quantity.
     *
     * Use this method only when the application behavior
     * has been confirmed to represent total product units.
     *
     * @param expectedQuantity expected quantity
     */
    protected void waitForBagQuantity(int expectedQuantity) {

        try {

            wait.until(driver ->
                    getBagQuantity() == expectedQuantity
            );

        } catch (TimeoutException e) {

            int currentQuantity = getBagQuantity();

            throw new TimeoutException(
                    "Shopping bag quantity did not reach the expected value. "
                            + "Expected: "
                            + expectedQuantity
                            + " | Actual: "
                            + currentQuantity
            );
        }
    }

    /**
     * Opens the shopping bag.
     */
    protected void clickToBag() {
        robustClick(bagQuantity);
    }
}


