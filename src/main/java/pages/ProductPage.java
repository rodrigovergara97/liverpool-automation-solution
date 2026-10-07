package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.*;


import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ProductPage extends BasePage {

    // Product quantity
    private final By increaseProductQuantityButton =
            By.cssSelector("button[data-testid$='quantity-increase']");

    private final By decreaseProductQuantityButton =
            By.cssSelector("button[data-testid$='quantity-decrease']");

    private final By currentProductQuantity =
            By.cssSelector("input[data-testid$='configurator-quantity-input']");

    // Product actions
    private final By buyNowButton =
            By.cssSelector("button[data-testid$='buy-now-button']");

    private final By addToBagButton =
            By.cssSelector("button[data-testid$='add-to-bag-button']");

    // Product information
    private final By itemTitle =
            By.tagName("h1");

    private final By brandLink =
            By.cssSelector("button[data-testid$='brand-link']");

    // Product validation messages
    private final By errorItemMessage =
            By.xpath(
                    "//*[contains(text()," +
                            "'Elige las características del artículo para agregar a la bolsa.')]"
            );

    private final String successItemMessage =
            "//*[contains(text(),'Agregaste %s artículo(s) a tu bolsa.')]";

    // Warranty modal
    private final By warrantyModal =
            By.cssSelector(
                    "div[data-testid$='warranty-modal-modal-guarantee-modal']"
            );

    private final By dontAddWarrantyModalButton =
            By.cssSelector(
                    "div[data-testid$='warranty-modal-modal-guarantee-modal'] " +
                            "button[data-testid$='modal-guarantee-modal-footer-secondary-button']"
            );

    private final By addWarrantyModalButton =
            By.cssSelector(
                    "div[data-testid$='warranty-modal-modal-guarantee-modal'] " +
                            "button[data-testid$='warranty-modal-modal-guarantee-modal-footer-primary-button']"
            );

    // Product option selectors
    private final By buttonUndefinedSelector =
            By.cssSelector(
                    "button[data-testid$='selection-button-simple-picker-undefined-button-selection']"
            );

    private final By buttonUndefinedSelectorFirstOption =
            By.cssSelector(
                    "button[data-testid$='selection-button-simple-picker-undefined-option-picker-sheet-simple-picker-btn-0']"
            );

    private final By firstOptionValue =
            By.cssSelector(
                    "button[data-testid$='selection-button-simple-picker-undefined-option-picker-sheet-simple-picker-btn-0'] span"
            );

    private final By firstOptionRadio =
            By.cssSelector(
                    "div[role='radiogroup'][data-testid^='ml-radio-group-size-picker'] " +
                            "input:first-of-type"
            );

    private final By radioGroup =
            By.cssSelector(
                    "div[role='radiogroup'][data-testid^='ml-radio-group-size-picker']"
            );

    private final By detailsButton =
            By.xpath(
                    "//details[contains(@data-testid,'configurator-size-picker')]"
            );

    // Stores the total quantity added for each product.
    private final Map<String, Integer> productsQuantity = new HashMap<>();


    public ProductPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Increases the product quantity by one.
     */
    public void increaseProductQuantity() {
        robustClick(increaseProductQuantityButton);
    }

    /**
     * Decreases the product quantity by one.
     */
    public void decreaseProductQuantity() {
        robustClick(decreaseProductQuantityButton);
    }

    /**
     * Clicks the "Buy Now" button.
     */
    public void clickBuyNow() {
        robustClick(buyNowButton);
    }

    /**
     * Adds the current product to the shopping bag.
     *
     * If mandatory product options have not been selected,
     * the first available option is selected and the product
     * is added again.
     */
    public void addProductToBag() {

        robustClick(addToBagButton);

        if (!isErrorMessageDisplayed()) {
            return;
        }

        selectFirstOptionOfUndefinedMandatory();

        robustClick(addToBagButton);

        if (isErrorMessageDisplayed()) {
            addProductToBag();
        }
    }

    /**
     * Returns the product title.
     *
     * @return product title
     */
    public String getItemTitle() {
        return getTextFromElement(
                driver.findElement(itemTitle)
        );
    }

    /**
     * Returns the currently selected product quantity.
     *
     * @return current product quantity
     */
    public int getCurrentProductQuantity() {
        waitForElementToBePresent(currentProductQuantity, 3);

        String value = driver.findElement(currentProductQuantity)
                .getAttribute("value");

        return Integer.parseInt(
                Objects.requireNonNull(value)
        );
    }

    /**
     * Checks whether the product option validation message is displayed.
     *
     * @return true if the error message is visible
     */
    public boolean isErrorMessageDisplayed() {
        return isElementDisplayed(errorItemMessage);
    }

    /**
     * Checks whether the success message is displayed after adding
     * a product to the shopping bag.
     *
     * The quantity is also stored in the product quantity map.
     *
     * @return true if the success message is visible
     */
    public boolean isSuccessMessageDisplayed() {
        try {
            int quantity = getCurrentProductQuantity();

            By successMessage = By.xpath(
                    String.format(successItemMessage, quantity)
            );

            System.out.println("success message *"+String.format(successItemMessage, quantity));

            waitForElementToBePresent(successMessage, 3);

            addProductToBagMap(getItemTitle());

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Adds the current product quantity to the total quantity
     * stored for the product.
     *
     * @param title product title
     */
    private void addProductToBagMap(String title) {
        int currentQuantity = getCurrentProductQuantity();

        productsQuantity.merge(
                title,
                currentQuantity,
                Integer::sum
        );
    }

    /**
     * Checks whether the warranty modal is displayed.
     *
     * @return true if the warranty modal is visible
     */
    public boolean isWarrantyModalDisplayed() {
        if (!isElementDisplayed(warrantyModal)) {
            return false;
        }

        WebElement modal = driver.findElement(warrantyModal);

        return "fixed".equalsIgnoreCase(
                modal.getCssValue("position").trim()
        );
    }

    /**
     * Closes the warranty modal without adding a warranty.
     */
    public void dontAddWarranty() {

        if (!isElementDisplayed(warrantyModal)) {
            return;
        }

        robustClick(dontAddWarrantyModalButton);
    }

    /**
     * Selects the first available option for mandatory product
     * configurations.
     *
     * Some products use a simple picker while others use a
     * radio-button based configuration.
     */
    public void selectFirstOptionOfUndefinedMandatory() {
        try {
            selectSimplePickerOptions();
        } catch (NoSuchElementException e) {
            selectRadioGroupOptions();
        } catch (StaleElementReferenceException e) {
            // Retry once because the product configuration may
            // have been refreshed in the DOM.
            selectFirstOptionOfUndefinedMandatory();
        }
    }

    /**
     * Selects the first option from simple product pickers.
     */
    private void selectSimplePickerOptions() {
        WebDriverWait optionWait =
                new WebDriverWait(driver, Duration.ofSeconds(5));

        for (WebElement option : driver.findElements(buttonUndefinedSelector)) {

            robustClick(option);
            robustClick(buttonUndefinedSelectorFirstOption);

            String selectedValue = getTextFromElement(
                    driver.findElement(firstOptionValue)
            );

            WebElement currentValue = option.findElement(
                    By.xpath("(/div/p)[2]")
            );

            optionWait.until(
                    ExpectedConditions.textToBePresentInElement(
                            currentValue,
                            selectedValue
                    )
            );
        }
    }

    /**
     * Selects the first available option from a radio-button
     * based product configuration.
     */
    private void selectRadioGroupOptions() {
        WebDriverWait optionWait =
                new WebDriverWait(driver, Duration.ofSeconds(5));

        for (WebElement option : driver.findElements(detailsButton)) {

            robustClick(option);

            if (!isElementDisplayed(radioGroup)) {
                continue;
            }

            WebElement firstRadio =
                    driver.findElement(firstOptionRadio);

            robustClick(firstRadio);

            String selectedValue =
                    firstRadio.getAttribute("value");

            WebElement currentValue = option.findElement(
                    By.xpath("(/div/p)[2]")
            );

            optionWait.until(
                    ExpectedConditions.textToBePresentInElement(
                            currentValue,
                            selectedValue
                    )
            );
        }
    }
}

