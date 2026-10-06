package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.WebDriverUtil;

import java.awt.event.WindowAdapter;

public  class BasePage extends WebDriverUtil {

    private final By bagQuantity  = By.cssSelector("div[data-testid$='-header-shopping-cart-header-cart-quantity']");


    public BasePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);
    }

    protected String getCurrentBagQuantity(){
        try {
            waitForElementToBePresent(bagQuantity,2);
            WebElement bagElement = driver.findElement(bagQuantity);
            return getTextFromElement(bagElement).trim().isEmpty()? "0" :getTextFromElement(bagElement);
        }
        catch (TimeoutException e){
            return "0";
        }

    }

    protected void clickToBag(){
        robustClick(bagQuantity);
    }






}
