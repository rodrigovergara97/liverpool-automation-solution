package tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.DriverManager;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    protected void setUp(){
        DriverManager.setDriver("chrome");
    }

    @AfterMethod
    protected void tearDown(){
        DriverManager.unload();
    }

    @Test(testName = "",groups = {""},dependsOnGroups = {},dependsOnMethods = {},
            timeOut = 44,invocationCount = 2,
    enabled = true,expectedExceptions = {ArithmeticException.class})
    public void mockTest(){
        System.out.println("after test");
        int result = 5/0;
    }
}
