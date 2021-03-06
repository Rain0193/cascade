package com.github.robindevilliers.welcometohell.steps;

import com.github.robindevilliers.cascade.annotations.*;
import com.github.robindevilliers.welcometohell.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertEquals;

@Step(DateOfDeathQuestion.class)
@Narrative("Payment question.")
public interface SelectPayment {
    @Narrative("Enter devilish dollars as payment and go to the vip section page.")
    class DevilishDollars implements SelectPayment {
        @Demands
        private WebDriver webDriver;

        @When
        public void when() {
            Utilities.selectOption(webDriver, "input", "DEVILISH-DOLLARS");

            webDriver.findElement(By.cssSelector("button[type=submit]")).click();
            Utilities.waitForPage(webDriver);
        }

        @Then
        public void then() {
            assertEquals("Welcome to Hell | VIP Section", webDriver.getTitle());
        }
    }

    @Narrative("Enter heavenly half pennies as payment and go to the appeal page.")
    class HeavenlyHalfPennies implements SelectPayment {
        @Demands
        private WebDriver webDriver;

        @When
        public void when() {
            Utilities.selectOption(webDriver, "input", "HEAVENLY-HALF-PENNIES");

            webDriver.findElement(By.cssSelector("button[type=submit]")).click();
            Utilities.waitForPage(webDriver);
        }

        @Then
        public void then() {
            assertEquals("Welcome to Hell | Appeal", webDriver.getTitle());
        }
    }

    @Narrative("Enter earthly fiat as payment and go to the appeal page.")
    class EarthlyFiat implements SelectPayment {
        @Demands
        private WebDriver webDriver;

        @When
        public void when() {
            Utilities.selectOption(webDriver, "input", "EARTHLY-FIAT");

            webDriver.findElement(By.cssSelector("button[type=submit]")).click();
            Utilities.waitForPage(webDriver);
        }

        @Then
        public void then() {
            assertEquals("Welcome to Hell | Appeal", webDriver.getTitle());
        }
    }

    @Narrative("Enter gold as payment and go to the appeal page.")
    class Gold implements SelectPayment {
        @Demands
        private WebDriver webDriver;

        @When
        public void when() {
            Utilities.selectOption(webDriver, "input", "GOLD");

            webDriver.findElement(By.cssSelector("button[type=submit]")).click();
            Utilities.waitForPage(webDriver);
        }

        @Then
        public void then() {
            assertEquals("Welcome to Hell | Appeal", webDriver.getTitle());
        }
    }

    @Narrative("Enter nothing as payment and go to the appeal page.")
    class Nothing implements SelectPayment {
        @Demands
        private WebDriver webDriver;

        @When
        public void when() {
            Utilities.selectOption(webDriver, "input", "NOTHING");

            webDriver.findElement(By.cssSelector("button[type=submit]")).click();
            Utilities.waitForPage(webDriver);
        }

        @Then
        public void then() {
            assertEquals("Welcome to Hell | Appeal", webDriver.getTitle());
        }
    }
}
