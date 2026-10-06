package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Objects;

public class ProductPage extends BasePage{

    private final By increaseProductQuantityButton = By.cssSelector("button[data-testid$='quantity-increase']");
    private final By decreaseProductQuantityButton = By.cssSelector("button[data-testid$='quantity-decrease']");
    private final By buyNowButton = By.cssSelector("button[data-testid$='buy-now-button']");
    private final By addToBagButton  = By.cssSelector("button[data-testid$='add-to-bag-button']");
    private final By brandLink =By.cssSelector("button[data-testid$='brand-link']");
    private final By itemTitle = By.tagName("h1");
    private final By currentProductQuantity =By.cssSelector("input[data-testid$='configurator-quantity-input']");
    private final By errorItemMessage = By.xpath("//*[contains(text(),'Elige las características del artículo para agregar a la bolsa.')]");
    private final By warrantyModal = By.cssSelector("div[data-testid$='warranty-modal-modal-guarantee-modal']");
    private final By dontAddWarrantyModalButton = By.cssSelector("div[data-testid$='warranty-modal-modal-guarantee-modal'] button[data-testid$='modal-guarantee-modal-footer-secondary-button']");
    private final By addWarrantyModalButton = By.cssSelector("div[data-testid$='warranty-modal-modal-guarantee-modal'] button[data-testid$='warranty-modal-modal-guarantee-modal-footer-primary-button']");


    private String successItemMessage = "//*[contains(text(),'Agregaste %s artículo(s) a tu bolsa.')]";


    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public void increaseProductQuantity(){
        robustClick(increaseProductQuantityButton);
    }
    public void decreaseProductQuantity(){
        robustClick(decreaseProductQuantityButton);
    }

    public void clickBuyNow(){
        robustClick(buyNowButton);
    }

    public void addProductToBag(){
        robustClick(addToBagButton);
    }

    public String getItemTitle(){
        WebElement element = driver.findElement(itemTitle);
        return getTextFromElement(element);
    }

    public int getCurrentProductQuantity(){
        waitForElementToBePresent(currentProductQuantity,3);
        WebElement element = driver.findElement(currentProductQuantity);
        return Integer.parseInt(Objects.requireNonNull(element.getAttribute("value")));
    }
    public boolean isErrorMessageDisplayed(){
        wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(errorItemMessage));
            return true;
        }
        catch (Exception e){
            return false;
        }

    }

    public boolean isSuccessMessageDisplayed(){
        wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        try {
            String value = String.valueOf(getCurrentProductQuantity());
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(successItemMessage,value))));
            return true;
        }
        catch (Exception e){
            return false;
        }

    }

    public boolean isWarrantyModalDisplayed(){
        waitForElementToBePresent(warrantyModal,3);
        WebElement modal = driver.findElement(warrantyModal);
        return modal.getCssValue("position").trim().equalsIgnoreCase("fixed");
    }

    public void dontAddWarranty(){
        if(isWarrantyModalDisplayed()){
            robustClick(dontAddWarrantyModalButton);
        }
    }
}
