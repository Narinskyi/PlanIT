package com.example.base;

import com.example.constants.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;

import java.time.Duration;

public abstract class Test {

    private final static WebDriver DRIVER;

    static {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");

        DRIVER = new ChromeDriver();
    }

    @AfterClass(alwaysRun = true)
    public static void quit() {
        DRIVER.quit();
    }

    public static void open(String url) {
        DRIVER.get(url);
    }

    private static WebElement find_visible(By locator) {
        return new WebDriverWait(DRIVER, Duration.ofSeconds(Constants.WEB_DRIVER_WAIT_IN_SEC))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static void clickOn(By locator) {
        find_visible(locator).click();
    }

    public static String getTextOf(By locator) {
        return DRIVER.findElement(locator).getText();
    }

    public static String getAttributeOf(By locator, String attributeName) {
        return DRIVER.findElement(locator).getAttribute(attributeName);
    }
}

