package com.planittesting.cloud.jupiter;

import com.planittesting.cloud.jupiter.context.AbstractUITest;
import com.planittesting.cloud.jupiter.data.component.Alert;
import com.planittesting.cloud.jupiter.data.component.Input;
import com.planittesting.cloud.jupiter.data.component.NavigationBar;
import com.planittesting.cloud.jupiter.data.page.CartPage;
import com.planittesting.cloud.jupiter.data.page.ContactPage;
import com.planittesting.cloud.jupiter.data.page.ShopPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import static java.lang.Integer.parseInt;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;

public class TestSuite extends AbstractUITest {

    @BeforeMethod
    public void init() {
        openHomePage();
    }

    @Test
    public void testCase1() {
        driver.clickOn(NavigationBar.CONTACT);

        driver.clickOn(ContactPage.BUTTON_SUBMIT);

        String alertError = driver.getElementText(Alert.Error.BODY);
        softly.assertTrue(alertError.contains("We welcome your feedback"));
        softly.assertTrue(alertError.contains("- but we won't get it unless you complete the form correctly."));
        softly.assertAll();

        List<String> inputErrors = driver.getElementsText(Input.ERROR_MESSAGE);
        assertThat(inputErrors, hasItems("Forename is required", "Email is required", "Message is required"));

        driver.sendKeys(Input.FORENAME, "John");
        driver.sendKeys(Input.EMAIL, "john@doe.com");
        driver.sendKeys(Input.MESSAGE, "Message");

        String alertInfo = driver.getElementText(Alert.Info.BODY);
        softly.assertTrue(alertInfo.contains("We welcome your feedback"));
        softly.assertTrue(alertInfo.contains("- tell it how it is."));
        softly.assertAll();
    }

    @DataProvider(name = "users")
    public Object[][] users() {
        return new Object[][]{
                {"a", "a@b.cd", "a"},
                {"Z z", "Z.z@z.com", "Z"},
                {"A z z", "A.z.z@bc.def", "A z z "},
                {"1!@#$%^&~**()<>|/]{0", "test.email@gmail.com", "1!@#$%^&~**()<>|/]{0"},
                {"My name is long it's just long it's what they call me it's a long name",
                        "myemail.is.unreasonably.long@at.a.very.long.domain.com",
                        "This is a very long message which should be decided with stakeholders based on \n" +
                                "what the actual requirements are"},
        };
    }

    @Test(dataProvider = "users")
    public void testCase2(String forename, String email, String message) {
        driver.clickOn(NavigationBar.CONTACT);

        driver.sendKeys(Input.FORENAME, forename);
        driver.sendKeys(Input.EMAIL, email);
        driver.sendKeys(Input.MESSAGE, message);

        driver.clickOn(ContactPage.BUTTON_SUBMIT);

        String alertInfo = driver.getElementText(Alert.Info.BODY);
        softly.assertTrue(alertInfo.contains("We welcome your feedback"));
        softly.assertTrue(alertInfo.contains("- tell it how it is."));
        softly.assertAll();
    }

    @Test
    public void testCase3() {
        driver.clickOn(NavigationBar.SHOP);

        driver.clickOn(ShopPage.Item.buyButtonFor("Funny Cow"));
        driver.clickOn(ShopPage.Item.buyButtonFor("Funny Cow"));
        driver.clickOn(ShopPage.Item.buyButtonFor("Fluffy Bunny"));

        driver.clickOn(NavigationBar.CART);

        softly.assertTrue(parseInt(
                driver.getElementText(CartPage.ITEMS_TOTAL_COUNT)) == 3);

        softly.assertTrue(parseInt(
                driver.getAttribute(
                        CartPage.Item.quantityOf("Funny Cow"), "value")) == 2);

        softly.assertTrue(parseInt(
                driver.getAttribute(
                        CartPage.Item.quantityOf("Fluffy Bunny"), "value")) == 2);

    }
}
