package pages;

import org.openqa.selenium.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Objects;

public class LoginPage extends BasePage {

    // Login form elements
    private final By userName = By.id("username");
    private final By password = By.id("password");
    private final By submitButton = By.cssSelector(
            "button[type='submit'][data-action-button-primary='true']"
    );
    private final By primaryForm = By.cssSelector(
            "form[data-form-primary='true']"
    );

    // Verification and alternative login method
    private final By verificationCode = By.id("code");
    private final By tryOtherMethodButton = By.xpath(
            "//button[contains(translate(text(), " +
                    "'ABCDEFGHIJKLMNOPQRSTUVWXYZ', " +
                    "'abcdefghijklmnopqrstuvwxyz'), 'probar otro')]"
    );
    private final By tryOtherMethodEmail = By.xpath(
            "//form[contains(@class,'ulp-action-form-email')]"
    );

    // Gmail login elements
    private final By gmailSignInButton = By.xpath(
            "(//span[@class='button__content' and text()='Sign in'])[1]"
    );
    private final By gmailEmailInput = By.id("identifierId");
    private final By gmailPasswordInput = By.cssSelector(
            "input[type='password']"
    );


    public LoginPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Enters the password in the login form.
     *
     * @param passwordValue password to enter
     */
    public void enterPassword(String passwordValue) {
        sendKeysWithAction(password, passwordValue);
    }

    /**
     * Enters the username/email in the login form.
     *
     * @param username username or email to enter
     */
    public void enterUserName(String username) {
        sendKeysWithAction(userName, username);
    }

    /**
     * Submits the login form.
     */
    public void clickLogin() {
        robustClick(submitButton);
    }

    /**
     * Logs in using the provided credentials.
     *
     * @param email user email
     * @param passwordValue user password
     */
    public void loginWithCredentials(String email, String passwordValue) {
        waitForElementToBePresent(primaryForm, 3);

        enterUserName(email);
        enterPassword(passwordValue);
        clickLogin();
    }

    /**
     * Checks whether the password field is empty.
     *
     * @return true if the password field has no value
     */
    public boolean isPasswordEmpty() {
        return isInputEmpty(password);
    }

    /**
     * Checks whether the username field is empty.
     *
     * @return true if the username field has no value
     */
    public boolean isUserNameEmpty() {
        return isInputEmpty(userName);
    }

    /**
     * Checks whether the password field is highlighted as invalid.
     *
     * @return true if the field contains the expected error border color
     */
    public boolean isPasswordHighlighted() {
        return hasErrorBorder(password);
    }

    /**
     * Checks whether the username field is highlighted as invalid.
     *
     * @return true if the field contains the expected error border color
     */
    public boolean isUserNameHighlighted() {
        return hasErrorBorder(userName);
    }

    /**
     * Checks whether all required elements of the login form are displayed.
     *
     * @return true if the complete login form is visible
     */
    public boolean isFormDisplayed() {
        return isElementDisplayed(primaryForm)
                && isElementDisplayed(userName)
                && isElementDisplayed(password)
                && isElementDisplayed(submitButton);
    }

    /**
     * Checks whether the verification code field is displayed.
     *
     * @return true if the verification code field is visible
     */
    public boolean isVerificationCodeDisplayed() {
        return isElementDisplayed(verificationCode);
    }

    /**
     * Opens the alternative login methods and selects email verification.
     *
     * @param email user email
     * @param passwordValue user password
     */
    public void loginWithAnotherMethodEmail(
            String email,
            String passwordValue) {

        if (!isVerificationCodeDisplayed()) {
            return;
        }

        robustClick(tryOtherMethodButton);

        waitForElementToBePresent(tryOtherMethodEmail, 3);

        robustClick(tryOtherMethodEmail);
    }

    /**
     * Checks whether an input field is empty.
     *
     * @param locator input locator
     * @return true if the input is empty
     */
    private boolean isInputEmpty(By locator) {
        waitForElementToBePresent(locator, 1);

        WebElement element = driver.findElement(locator);
        String value = element.getAttribute("value");

        return Objects.requireNonNull(value).trim().isEmpty();
    }

    /**
     * Checks whether an input has the expected error border color.
     *
     * @param locator input locator
     * @return true if the input has the error border
     */
    private boolean hasErrorBorder(By locator) {
        waitForElementToBePresent(locator, 1);

        return driver.findElement(locator)
                .getCssValue("border-color")
                .contains("10098");
    }
}

