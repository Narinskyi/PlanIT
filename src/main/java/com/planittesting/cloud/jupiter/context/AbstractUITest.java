package com.planittesting.cloud.jupiter.context;

import com.planittesting.cloud.jupiter.driver.Driver;
import com.planittesting.cloud.jupiter.listeners.TestNGExecutionListener;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Listeners;
import org.testng.asserts.SoftAssert;

@Listeners({TestNGExecutionListener.class})
public abstract class AbstractUITest {

    protected SoftAssert softly = new SoftAssert();

    protected Driver driver;

    public AbstractUITest() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
        this.driver = new Driver(new ChromeDriver());
    }

    @AfterClass(alwaysRun = true)
    public void quitDriver() {
        driver.quit();
    }

    protected void openHomePage() {
        driver.get("https://jupiter.cloud.planittesting.com/");
    }
}

