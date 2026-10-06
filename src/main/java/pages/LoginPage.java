package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Objects;

public class LoginPage extends BasePage{

    private final By userName = By.id("username");
    private final By password = By.id("password");
    private final By submitButton = By.cssSelector("button[type='submit'][data-action-button-primary='true']");
    private final By primaryForm = By.cssSelector("form[data-form-primary='true']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void enterPassword(String pwd){
        sendKeysWithAction(password,pwd);
    }

    public void enterUserName(String user){
        sendKeysWithAction(userName,user);
    }

    public void clickLogin(){
        robustClick(submitButton);
    }

    public boolean isPasswordEmpty(){
        waitForElementToBePresent(password,1);
        WebElement element = driver.findElement(password);
        return Objects.requireNonNull(element.getAttribute("value")).trim().isEmpty();
    }

    public boolean isUserNameEmpty(){
        waitForElementToBePresent(userName,1);
        WebElement element = driver.findElement(userName);
        return Objects.requireNonNull(element.getAttribute("value")).trim().isEmpty();
    }

    public boolean isPassWordHighLighted(){
        waitForElementToBePresent(password,1);
        WebElement element = driver.findElement(password);
       return element.getCssValue("border-color").contains("10098");
    }

    public boolean isUserNameHighLighted(){
        waitForElementToBePresent(userName,1);
        WebElement element = driver.findElement(userName);
        return element.getCssValue("border-color").contains("10098");
    }

    public boolean isFormDisplayed(){
        return isElementDisplayed(primaryForm) && isElementDisplayed(userName) && isElementDisplayed(userName) && isElementDisplayed(submitButton);
    }


}
