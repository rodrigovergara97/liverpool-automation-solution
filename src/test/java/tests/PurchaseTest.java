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

public class PurchaseTest extends BaseTest {

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
    public void addMultipleProductsAndValidateShoppingBag() throws InterruptedException {


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
        productPage.addProductToBag(true);


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


        homePage.clickCategoriesButton();

        homePage.clickCategory("hombre");

        categoryPage.clickCategory("zapatos");
        categoryPage.clickCategory("botas");

        categoryPage.selectCategoryFilter(
                "color",
                "caf"
        );



        categoryPage.selectItemByNumberInPage(0);
        productPage.increaseProductQuantity();
        productPage.selectFirstSizePicker();




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
        productPage.addProductToBag(true);


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



        homePage.clickCategoriesButton();
        homePage.clickCategory("electr");
        categoryPage.clickCategory("tv");
        categoryPage.clickCategory("pantallas");
        categoryPage.filterByBrand("sony");
        categoryPage.selectItemByNumberInPage(4);
        productPage.addProductToBag(true);

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
        productPage.addProductToBag(false);


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


        productPage.clickBuyNow();
        System.out.println(
                "Original item price: "
                        + buyNowPage.getOriginalItemPrice()
        );

    }


    @Test(description = "Flujo agregar a bolsa producto sin sesion iniciada",groups = {"negative","regression"},
            testName = "prueba negativa producto sin todos detalles mandatorios incluidos")
    public void addProductWithOutActiveSession() throws InterruptedException {
        homePage.navigateTo();
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

        categoryPage.selectItemByNumberInPage(1);

        productPage.selectFirstSizePicker();

        // Add product
        productPage.addProductToBag(false);
        productPage.clickBuyNow();
        Assert.assertTrue(loginPage.isFormDisplayed(),"form is not being displayed when trying to add product to bag without an active session");
    }

    @Test(description = "Flujo agregar a bolsa producto que requiere mas detalles",groups = {"negative","regression"},
            testName = "prueba negativa producto sin todos detalles mandatorios incluidos",dependsOnMethods = {"login"})
    public void addProductWithOutMandatorySpecs() throws InterruptedException {

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




        // Add product
        productPage.addProductToBag(false);





        Assert.assertTrue(productPage.isErrorMessageDisplayed(),"Error message is not being displayed for product that requires additional details");
    }


    @Test(description = "Flujo agregar a bolsa producto que requiere mas detalles",groups = {"edge","regression"},
            testName = "prueba negativa producto sin todos detalles mandatorios incluidos",dependsOnMethods = {"login"})
    public void buyNowCurrentProductMantainsAfterRefresh() throws InterruptedException {

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

        categoryPage.selectItemByNumberInPage(0);
        // Add product
        productPage.addProductToBag(true);

        productPage.clickBuyNow();
        double originalItemPrice = buyNowPage.getOriginalItemPrice();
        int getCurrentProductQuantity = buyNowPage.getCurrentProductQuantity();
        driver.navigate().refresh();

        Assert.assertEquals(originalItemPrice, buyNowPage.getOriginalItemPrice(), "Product's price doesnt remain the same after refresh");
        Assert.assertEquals(getCurrentProductQuantity, buyNowPage.getCurrentProductQuantity(), "Product's quantity doesnt remain  the same after refresh");

    }

    @Test(description = "Flujo agregar cantidad mayor al producto dispobile",groups = {"edge","regression"},
            testName = "prueba negativa producto sin todos detalles mandatorios incluidos",dependsOnMethods = {"login"})
    public void addMoreThanMaximumAvaialbleFromProduct() throws InterruptedException {

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

        homePage.clickCategoriesButton();

        homePage.clickCategory("electr");

        categoryPage.clickCategory("computa");
        categoryPage.clickCategory("computadoras");

        categoryPage.filterByBrand("lenovo");

        categoryPage.selectItemByNumberInPage(4);


        // Add product
        productPage.setProductQuantity(100);
        productPage.addProductToBag(false);
        Assert.assertFalse(productPage.isSuccessMessageDisplayed());

    }




}
