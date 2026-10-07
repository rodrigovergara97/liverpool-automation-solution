package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BuyNowPage extends BasePage {

    private final By itemUnitPrice = By.xpath("(//div[contains(@data-testid,'input-cart-item-wrapper-input-cart-item-price')]/span/span)[1]");
    private final By increaseProductQuantityButton = By.cssSelector("button[data-testid$='quantity-increase']");
    private final By decreaseProductQuantityButton = By.cssSelector("button[data-testid$='quantity-decrease']");
    private final By productTitle = By.cssSelector("span[data-testid$='ml-card-product-mybag']");
    private final By productQuantity = By.cssSelector("input[data-testid$='input-cart-item-wrapper-input-cart-item-quantity-input']");
    private final By totalPrice = By.xpath("(//div[contains(@data-testid,'input-cart-item-wrapper-input-cart-item-total')]/span/span)[1]");
    private final By originalItemUnitPrice = By.xpath("(//div[contains(@data-testid,'input-cart-item-wrapper-input-cart-item-price')]/span[contains(@data-testid,'original')]/span)[1]");
    private final By subtotalParagraph = By.xpath("//p[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'),'subtotal')]");
    private final By subtotalPrice = By.xpath("//p[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'),'subtotal')]/parent::div//div/span");

    public BuyNowPage(WebDriver driver) {
        super(driver);
    }

    public int getCurrentProductQuantity() {
        waitForElementToBePresent(productQuantity, 3);
        WebElement element = driver.findElement(productQuantity);
        return Integer.parseInt(Objects.requireNonNull(element.getAttribute("value")));
    }

    public boolean isSubtotalCorrect() {
        int currentProductQuantity = getCurrentProductQuantity();
        waitForElementToBePresent(subtotalParagraph, 1);
        WebElement subtotal = driver.findElement(subtotalParagraph);
        String text = getTextFromElement(subtotal);

        // Match one or more consecutive digits
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(text);

        if (matcher.find())
            return Integer.parseInt(matcher.group()) == currentProductQuantity;
        return false;
    }

    public double getPrice(String text) {

        if (text == null || text.trim().isEmpty()) {
            return 0.0;
        }

        String cleanText = text
                .replace(",", "")
                .trim();

        Pattern pattern =
                Pattern.compile("[-+]?\\d*\\.?\\d+");

        Matcher matcher =
                pattern.matcher(cleanText);

        if (matcher.find()) {
            return Double.parseDouble(matcher.group());
        }

        return 0.0;
    }


    public  double getOriginalItemPrice(){
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(originalItemUnitPrice));
            return getPrice(getTextFromElement(driver.findElement(originalItemUnitPrice)));
        }
        catch (Exception e){
            wait.until(ExpectedConditions.presenceOfElementLocated(itemUnitPrice));
            return getPrice(getTextFromElement(driver.findElement(itemUnitPrice)));
        }
    }


}
