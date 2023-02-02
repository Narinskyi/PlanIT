package com.planittesting.cloud.jupiter;

import com.planittesting.cloud.jupiter.context.AbstractUITest;
import com.planittesting.cloud.jupiter.data.component.Alert;
import com.planittesting.cloud.jupiter.data.component.Input;
import com.planittesting.cloud.jupiter.data.component.NavigationBar;
import com.planittesting.cloud.jupiter.data.model.Item;
import com.planittesting.cloud.jupiter.data.page.CartPage;
import com.planittesting.cloud.jupiter.data.page.ContactPage;
import com.planittesting.cloud.jupiter.data.page.ShopPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
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

    @DataProvider(name = "items")
    public Object[][] items() {
        return new Object[][]{
                {new Item("Funny Cow", 2), new Item("Fluffy Bunny", 1)},
                {new Item("Stuffed Frog", 2), new Item("Fluffy Bunny", 2), new Item("Valentine Bear", 3)}
        };
    }

    //After having implemented test case 4 I realized that test case 3 is redundant
    @Test(dataProvider = "items")
    public void testCase3_4(Item... items) {
        driver.clickOn(NavigationBar.SHOP);

        //Collect items prices and click buy
        for (Item item : items) {
            item.setPrice(driver.getElementText(
                    ShopPage.Item.priceOf(item.getName())));

            for (int i = 0; i < item.getQuantity(); i++) {
                driver.clickOn(ShopPage.Item.buyButtonFor(item.getName()));
            }
        }

        driver.clickOn(NavigationBar.CART);

        //Verify total count of items
        assertThat(parseInt(
                        driver.getElementText(CartPage.ITEMS_TOTAL_COUNT)),
                equalTo(
                        Arrays.stream(items).map(Item::getQuantity).mapToInt(Integer::intValue).sum()));

        //For each item verify quantity, price, subtotal
        for (Item item : items) {
            assertThat(parseInt(
                            driver.getAttribute(
                                    CartPage.Item.quantityOf(item.getName()), "value")),
                    equalTo(
                            item.getQuantity()));

            assertThat(
                    driver.getElementText(
                            CartPage.Item.priceOf(item.getName())),
                    equalTo(item.getPriceString()));

            assertThat(
                    driver.getElementText(
                            CartPage.Item.subtotalOf(item.getName())),
                    equalTo(item.getSubtotalString()));
        }

        //Verify total price of all items
        assertThat(parseDouble(
                        driver.getElementText(CartPage.ITEMS_TOTAL_PRICE).replaceAll("Total: ", "")),
                equalTo(
                        Arrays.stream(items).map(Item::getSubtotalValue).mapToDouble(Double::doubleValue).sum()));
    }
}
