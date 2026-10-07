package tests;

import org.openqa.selenium.WindowType;
import org.testng.Assert;
import org.testng.IHookable;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import pages.*;
import utils.DriverManager;

public class ApiTest extends BaseTest {

    @BeforeClass
    public void setUp() {
        DriverManager.setDriver("chrome");
        driver = DriverManager.getDriver();
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        categoryPage = new CategoryPage(driver);
        productPage = new ProductPage(driver);
         buyNowPage =
                new BuyNowPage(driver);
    }
    @Test(description = "login exitoso ",groups = {"functional","regression"},
            testName = "login exitoso")
    public void login(){
        homePage.navigateTo();
        homePage.clickLoginButton();
        loginPage.loginWithCredentials("rodrigovergara87@gmail.com","Rodrigo202211..");
        loginPage.loginWithAnotherMethodEmail("rodrigovergara87@gmail.com","Rodrigo202211..");
        Assert.assertTrue(homePage.isLoginSuccessful(),"");
    }

    @Test(description = "Flujo e2e positivo",groups = {"functional","regression"},
            testName = "prueba funcional 1",dependsOnMethods = {"login"})
    public void genericTest() throws InterruptedException {
        /*
        homePage.clickCategoriesButton();
        Assert.assertTrue(homePage.isCategoriesMenuPresent(),"is not displayed");
        homePage.clickCategory("hombre");
        Assert.assertTrue(categoryPage.getRootLevelCategory().equalsIgnoreCase("hombre"),"");
        categoryPage.clickCategory("zapatos");
        categoryPage.clickCategory("mocasines");
        categoryPage.selectCategoryFilter("color","azul claro");
        categoryPage.selectCategoryFilter("talla","22");
        categoryPage.selectItemByNumberInPage(0);
        productPage.addProductToBag();
        Assert.assertTrue(productPage.isSuccessMessageDisplayed(),"no se agrego producto");
        homePage.clickCategoriesButton();
        homePage.clickCategory("belleza");
        categoryPage.clickCategory("perfumes");
        categoryPage.clickCategory("perfumes hombre");
        categoryPage.selectCategoryFilter("fragancia","floral");
        categoryPage.selectItemByNumberInPage(6);
        int currentBag = Integer.parseInt(homePage.getCurrentBagQuantity());
        System.out.println("Current bag quantity 1"+currentBag);
        productPage.increaseProductQuantity();
        int productsToBeAdded = productPage.getCurrentProductQuantity();
        productPage.addProductToBag();
        System.out.println("product quantity"+productsToBeAdded);
        Assert.assertTrue(productPage.isSuccessMessageDisplayed(),"no se agrego product");
        int finalProducts = currentBag + productsToBeAdded;
        System.out.println("Current bag quantity 2"+Integer.parseInt(homePage.getCurrentBagQuantity()));
        Thread.sleep(100000);
        Assert.assertTrue(Integer.parseInt(homePage.getCurrentBagQuantity())==finalProducts,"no son iguales");
        homePage.clickCategoriesButton();
        homePage.clickCategory("electr");
        categoryPage.clickCategory("computa");
        categoryPage.clickCategory("computadoras");
        categoryPage.filterByBrand("lenovo");
        categoryPage.selectItemByNumberInPage(1);
        currentBag = Integer.parseInt(homePage.getCurrentBagQuantity());
        productPage.increaseProductQuantity();
        productPage.increaseProductQuantity();
        productsToBeAdded = productPage.getCurrentProductQuantity();
        productPage.addProductToBag();
        productPage.dontAddWarranty();
        finalProducts = currentBag + productsToBeAdded;
        //.getCurrentBagQuantity());
        //Assert.assertTrue(Integer.parseInt(homePage.getCurrentBagQuantity())==finalProducts,"no son iguales");
        productPage.clickBuyNow();
        BuyNowPage buyNow = new BuyNowPage(driver);
        System.out.println(buyNow.getOriginalItemPrice());
*/

    }
    @Test(description = "Flujo e2e positivo",groups = {"functional","regression"},
            testName = "prueba funcional 1",dependsOnMethods = {"login"})
    public void addMultipleProductsAndValidateShoppingBag() throws InterruptedException {

        // ============================================================
        // PRODUCT 1 - MEN'S LOAFERS
        // ============================================================

        homePage.clickCategoriesButton();

        Assert.assertTrue(
                homePage.isCategoriesMenuPresent(),
                "Categories menu is not displayed"
        );

        homePage.clickCategory("hombre");

        Assert.assertEquals(
                categoryPage.getRootLevelCategory(),
                "Hombre",
                "Root category is incorrect"
        );

        categoryPage.clickCategory("zapatos");
        categoryPage.clickCategory("mocasines");

        categoryPage.selectCategoryFilter(
                "color",
                "azul claro"
        );

        categoryPage.selectCategoryFilter(
                "talla",
                "22"
        );

        categoryPage.selectItemByNumberInPage(0);


        // Capture bag state before adding the product
        int bagBeforeFirstProduct =
                homePage.getCurrentBagQuantity();

        System.out.println(
                "Bag before first product: "
                        + bagBeforeFirstProduct
        );


        // Add product
        productPage.addProductToBag();


        // Wait until the header counter changes
        homePage.waitForBagQuantityChange(
                bagBeforeFirstProduct
        );


        // Read updated bag quantity
        int bagAfterFirstProduct =
                homePage.getCurrentBagQuantity();

        System.out.println(
                "Bag after first product: "
                        + bagAfterFirstProduct
        );


        Assert.assertTrue(
                bagAfterFirstProduct > bagBeforeFirstProduct,
                "First product was not reflected in the shopping bag. "
                        + "Before: "
                        + bagBeforeFirstProduct
                        + " | After: "
                        + bagAfterFirstProduct
        );


        // ============================================================
        // PRODUCT 2 - MEN'S PERFUME
        // ============================================================

        homePage.clickCategoriesButton();

        homePage.clickCategory("belleza");

        categoryPage.clickCategory("perfumes");
        categoryPage.clickCategory("perfumes hombre");

        categoryPage.selectCategoryFilter(
                "fragancia",
                "floral"
        );

        categoryPage.selectItemByNumberInPage(6);


        // Capture bag state before adding second product
        int bagBeforeSecondProduct =
                homePage.getCurrentBagQuantity();

        System.out.println(
                "Bag before second product: "
                        + bagBeforeSecondProduct
        );


        // Increase product quantity
        productPage.increaseProductQuantity();

        int secondProductQuantity =
                productPage.getCurrentProductQuantity();

        System.out.println(
                "Second product quantity: "
                        + secondProductQuantity
        );


        // Add second product
        productPage.addProductToBag();


        // Wait for header cart to update
        homePage.waitForBagQuantityChange(
                bagBeforeSecondProduct
        );


        int bagAfterSecondProduct =
                homePage.getCurrentBagQuantity();

        System.out.println(
                "Bag after second product: "
                        + bagAfterSecondProduct
        );


        Assert.assertTrue(
                bagAfterSecondProduct > bagBeforeSecondProduct,
                "Second product was not reflected in the shopping bag. "
                        + "Before: "
                        + bagBeforeSecondProduct
                        + " | After: "
                        + bagAfterSecondProduct
        );


        // ============================================================
        // PRODUCT 3 - LENOVO COMPUTER
        // ============================================================

        homePage.clickCategoriesButton();

        homePage.clickCategory("electr");

        categoryPage.clickCategory("computa");
        categoryPage.clickCategory("computadoras");

        categoryPage.filterByBrand("lenovo");

        categoryPage.selectItemByNumberInPage(4);


        // Capture bag state before adding third product
        int bagBeforeThirdProduct =
                homePage.getCurrentBagQuantity();

        System.out.println(
                "Bag before third product: "
                        + bagBeforeThirdProduct
        );


        // Increase quantity twice
        productPage.increaseProductQuantity();
        productPage.increaseProductQuantity();

        int thirdProductQuantity =
                productPage.getCurrentProductQuantity();

        System.out.println(
                "Third product quantity: "
                        + thirdProductQuantity
        );


        // Add third product
        productPage.addProductToBag();


        // Warranty modal is optional
        productPage.dontAddWarranty();


        // Wait for header cart to update
        homePage.waitForBagQuantityChange(
                bagBeforeThirdProduct
        );


        int bagAfterThirdProduct =
                homePage.getCurrentBagQuantity();

        System.out.println(
                "Bag after third product: "
                        + bagAfterThirdProduct
        );


        Assert.assertTrue(
                bagAfterThirdProduct > bagBeforeThirdProduct,
                "Third product was not reflected in the shopping bag. "
                        + "Before: "
                        + bagBeforeThirdProduct
                        + " | After: "
                        + bagAfterThirdProduct
        );


        // ============================================================
        // BUY NOW
        // ============================================================

        productPage.clickBuyNow();
        Thread.sleep(10000);
        System.out.println(
                "Original item price: "
                        + buyNowPage.getOriginalItemPrice()
        );

    }


}
