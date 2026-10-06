package tests;

import org.testng.Assert;
import org.testng.IHookable;
import org.testng.Reporter;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import pages.BasePage;
import pages.CategoryPage;
import pages.HomePage;
import pages.ProductPage;
import utils.DriverManager;

public class ApiTest extends BaseTest {

    @Test(description = "Flujo e2e positivo",groups = {"functional","regression"},
            testName = "prueba funcional 1")
    public void genericTest() throws InterruptedException {
        driver = DriverManager.getDriver();
        HomePage homePage = new HomePage(driver);
        homePage.navigateTo();
        homePage.clickCategoriesButton();
        Assert.assertTrue(homePage.isCategoriesMenuPresent(),"is not displayed");
        CategoryPage categoryPage = homePage.clickCategory("hombre");
        Assert.assertTrue(categoryPage.getRootLevelCategory().equalsIgnoreCase("hombre"),"");
        categoryPage.clickCategory("zapatos");
        categoryPage.clickCategory("mocasines");
        categoryPage.selectCategoryFilter("color","azul claro");
        categoryPage.selectCategoryFilter("talla","24");
        homePage.clickCategoriesButton();
        homePage.clickCategory("belleza");
        categoryPage.clickCategory("perfumes");
        categoryPage.clickCategory("perfumes hombre");
        categoryPage.selectCategoryFilter("fragancia","floral");
        categoryPage.selectItemByNumberInPage(6);
        ProductPage product = new ProductPage(driver);
        int currentBag = Integer.parseInt(homePage.getCurrentBagQuantity());
        product.increaseProductQuantity();
        int productsToBeAdded = product.getCurrentProductQuantity();
        product.addProductToBag();
        Assert.assertTrue(product.isSuccessMessageDisplayed());
        int finalProducts = currentBag + productsToBeAdded;

        Assert.assertTrue(Integer.parseInt(homePage.getCurrentBagQuantity())==finalProducts,"no son iguales");
        homePage.clickCategoriesButton();
        homePage.clickCategory("electr");
        categoryPage.clickCategory("computa");
        categoryPage.clickCategory("computadoras");
        categoryPage.filterByBrand("lenovo");
        categoryPage.selectItemByNumberInPage(1);
        currentBag = Integer.parseInt(homePage.getCurrentBagQuantity());
        product.increaseProductQuantity();
        product.increaseProductQuantity();
        productsToBeAdded = product.getCurrentProductQuantity();
        product.addProductToBag();
        product.dontAddWarranty();
        finalProducts = currentBag + productsToBeAdded;
        Assert.assertTrue(Integer.parseInt(homePage.getCurrentBagQuantity())==finalProducts,"no son iguales");


    }

}
