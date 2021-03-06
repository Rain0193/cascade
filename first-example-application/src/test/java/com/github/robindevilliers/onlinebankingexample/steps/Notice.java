package com.github.robindevilliers.onlinebankingexample.steps;


import com.github.robindevilliers.cascade.annotations.*;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

import static com.github.robindevilliers.onlinebankingexample.Utilities.*;

@SuppressWarnings("all")
@Step(Challenge.class)
public interface Notice {

    public class AcceptOneNotice implements Notice {

        @Supplies
        private List<String> notices = new ArrayList<String>();

        @Demands
        public WebDriver webDriver;

        @Given
        public void given() {
            notices.add("Lorem ipsum dolor sit amet, consectetuer adipiscing elit.");
        }

        @Then
        public void then() {
            assertTextEquals(webDriver, "[test-text-notice]", notices.get(0));

            click(webDriver, "[test-cta-continue]");
            waitForPage(webDriver);
            assertElementDisplayed(webDriver, "[test-page-portfolio]");
        }
    }

    public class AcceptTwoNotices implements Notice {

        @Supplies
        private List<String> notices = new ArrayList<String>();

        @Demands
        private WebDriver webDriver;

        @Given
        public void given() {
            notices.add("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
            notices.add("Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.");
        }

        @Then
        public void then() {
            assertTextEquals(webDriver, "[test-text-notice]", notices.get(0));
            click(webDriver, "[test-cta-continue]");
            waitForPage(webDriver);

            assertTextEquals(webDriver, "[test-text-notice]", notices.get(1));
            click(webDriver, "[test-cta-continue]");
            waitForPage(webDriver);

            assertElementDisplayed(webDriver, "[test-page-portfolio]");
        }
    }
}
