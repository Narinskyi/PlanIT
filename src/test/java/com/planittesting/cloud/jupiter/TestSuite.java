package com.planittesting.cloud.jupiter;

import com.planittesting.cloud.jupiter.context.AbstractUITest;
import com.planittesting.cloud.jupiter.data.component.NavigationBar;
import org.testng.annotations.Test;

public class TestSuite extends AbstractUITest {

    @Test
    public void testCase1() {
        openHomePage();

        driver.clickOn(NavigationBar.CONTACT);
    }
}
