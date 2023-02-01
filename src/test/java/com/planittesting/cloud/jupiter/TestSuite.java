package com.planittesting.cloud.jupiter;

import com.planittesting.cloud.jupiter.context.AbstractUITest;
import com.planittesting.cloud.jupiter.data.component.Alert;
import com.planittesting.cloud.jupiter.data.component.Input;
import com.planittesting.cloud.jupiter.data.component.NavigationBar;
import com.planittesting.cloud.jupiter.data.page.ContactPage;
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

        driver.sendKeys(Input.FORENAME, "ØJohn");
        driver.sendKeys(Input.EMAIL, "john@doe.com");
        driver.sendKeys(Input.MESSAGE, "!");

        String alertInfo = driver.getElementText(Alert.Info.BODY);
        softly.assertTrue(alertInfo.contains("We welcome your feedback"));
        softly.assertTrue(alertInfo.contains("- tell it how it is."));
        softly.assertAll();
    }
}
