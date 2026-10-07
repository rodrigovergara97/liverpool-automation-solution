package tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import utils.DriverManager;

import java.lang.reflect.Method;

public class BaseTest {

    protected WebDriver driver;
    protected HomePage homePage;
    protected LoginPage loginPage;
    protected CategoryPage categoryPage;
    protected ProductPage productPage;
    protected BuyNowPage buyNowPage;

    @AfterMethod
    protected void tearDown(Method method){
        if (!method.getName().equals("login"))
            DriverManager.unload();
    }


}
