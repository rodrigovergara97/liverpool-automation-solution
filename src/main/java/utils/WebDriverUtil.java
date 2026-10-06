package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class WebDriverUtil {

    protected  WebDriver driver;
    protected WebDriverWait wait;
    protected Actions actions;
    protected String browser;

    public WebDriverUtil(WebDriver driver){
        this.driver = driver;
        this.actions = new Actions(driver);
    }

    protected boolean isElementDisplayed(By locator) {
        try {
            // Wait until the element is visible on the DOM and scree}
            wait = new WebDriverWait(this.driver,Duration.ofSeconds(10));
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            System.out.println("element:"+element);
            return element.isDisplayed();
        } catch (Exception e) {
            // Catches TimeoutException, NoSuchElementException, etc.
            return false;
        }
    }

    protected String getTextFromElement(WebElement element){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String text = element.getText().trim();
        return text.isEmpty() ? (String) js.executeScript("return arguments[0].textContent;", element):text;
    }


    protected void waitForElementToBeVisible(WebElement element, long seconds){
        wait = new WebDriverWait(this.driver,Duration.ofSeconds(seconds));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected void waitForElementToBeClickable(WebElement element, long seconds){
        wait = new WebDriverWait(this.driver,Duration.ofSeconds(seconds));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected void waitForElementToBeClickable(By locator, long seconds){
        wait = new WebDriverWait(this.driver,Duration.ofSeconds(seconds));
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void waitForElementToBePresent(By locator, long seconds){
        wait = new WebDriverWait(this.driver,Duration.ofSeconds(seconds));
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    protected void robustClick(By locator) {
        wait = new WebDriverWait(this.driver,Duration.ofSeconds(5));
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        robustClick(element);
    }

    protected void robustClick(WebElement element) {
        try {
            // 1. Wait until element is clickable
            wait = new WebDriverWait(this.driver,Duration.ofSeconds(5));
            wait.until(ExpectedConditions.elementToBeClickable(element));

            // 2. Scroll element into the center of the viewport
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'center'});", element);

            // 3. Attempt standard Selenium click
            element.click();
        } catch (TimeoutException | ElementClickInterceptedException | StaleElementReferenceException e) {
            // 4. Fallback to JavaScript click if intercepted or stale
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    protected void sendKeysWithAction(By locator,String text){
        wait = new WebDriverWait(this.driver,Duration.ofSeconds(5));
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        sendKeysWithAction(element,text);
    }

    protected void sendKeysWithAction(WebElement element,String text){
        actions.moveToElement(element).click().sendKeys(text).build().perform();
    }

    protected void reloadPage(){
        driver.navigate().refresh();
    }


}
