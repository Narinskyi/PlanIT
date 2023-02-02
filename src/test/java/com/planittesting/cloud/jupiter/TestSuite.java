package com.planittesting.cloud.jupiter;

import com.planittesting.cloud.jupiter.context.AbstractUITest;
import com.planittesting.cloud.jupiter.data.component.Alert;
import com.planittesting.cloud.jupiter.data.component.Input;
import com.planittesting.cloud.jupiter.data.component.NavigationBar;
import com.planittesting.cloud.jupiter.data.page.ContactPage;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;

public class TestSuite extends AbstractUITest {

    @Test
    public void testCase1() {
        openHomePage();

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
        return new Object[][] {
                { "a", "a@b.cd", "a"},
                { "Z z", "Z.z@z.com", "Z"},
                { "A z z", "A.z.z@bc.def", "A z z "},
                { "1!@#$%^&~**()<>|/]{0", "test.email@gmail.com", "1!@#$%^&~**()<>|/]{0"},
                { "My name is long it's just long it's what they call me it's a long name",
                        "myemail.is.unreasonably.long@at.a.very.long.domain.com",
                        "This is a very long message which should be decided with stakeholders based on \n" +
                                "what the actual requirements are"},
        };
    }

    @Test(dataProvider = "users")
    public void testCase2(String forename, String email, String message) {
        openHomePage();

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


}
