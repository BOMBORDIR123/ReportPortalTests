package com.example.ReportPortal.ReportPortalTests.utils;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.epam.reportportal.junit5.ReportPortalExtension;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Базовый класс для UI и API тестов.
 * Настраивает WebDriver, Selenide и интеграцию с Allure/ReportPortal.
 */
@ExtendWith({ReportPortalExtension.class})
abstract public class BaseTest {

    @BeforeAll
    static void globalSetUp() {
        WebDriverManager.chromedriver().driverVersion("139.0.7258.68").setup();

        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.pageLoadTimeout = 60000;
        Configuration.pageLoadStrategy = PageLoadStrategy.EAGER.toString();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-infobars");
        options.addArguments("--start-maximized");
        Configuration.browserCapabilities.setCapability("goog:chromeOptions", options);

        SelenideLogger.addListener("AllureSelenide",
                new AllureSelenide()
                        .screenshots(true)
                        .savePageSource(true)
        );
    }

/*    @BeforeEach
    public void init() {

    }*/

    @AfterEach
    public void tearDown() {
        Selenide.closeWebDriver();
    }
}
