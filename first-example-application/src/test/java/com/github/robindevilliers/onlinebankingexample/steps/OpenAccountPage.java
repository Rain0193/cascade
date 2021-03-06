package com.github.robindevilliers.onlinebankingexample.steps;


import com.github.robindevilliers.cascade.annotations.*;
import com.github.robindevilliers.onlinebankingexample.Utilities;
import com.github.robindevilliers.onlinebankingexample.domain.Transaction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.github.robindevilliers.cascade.conditions.Predicate;
import com.github.robindevilliers.onlinebankingexample.domain.Account;

import java.util.List;

import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
import static com.github.robindevilliers.cascade.conditions.Predicates.or;
import static com.github.robindevilliers.cascade.conditions.Predicates.withStep;

@SuppressWarnings("all")
@Step(Portfolio.class)
@ReEntrantTerminator(1)
public interface OpenAccountPage {

    public class OpenCurrentAccount implements OpenAccountPage {
        @OnlyRunWith
        Predicate predicate = or(
                withStep(Portfolio.CurrentAndSaverAccounts.class),
                withStep(Portfolio.CurrentAccountOnly.class),
                withStep(Portfolio.AllAccounts.class)
        );

        @Demands
        private WebDriver webDriver;

        @Demands
        private List<Account> accounts;

        private Account currentAccount;

        @Given
        public void given() {

            for (Account account : accounts) {
                if ("Premium Current Account".equals(account.getName())) {
                    currentAccount = account;
                    break;
                }
            }

            currentAccount.getTransactions().add(new Transaction("30 Aug 2014", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor", "DEBIT", "4543"));
            currentAccount.getTransactions().add(new Transaction("27 Aug 2014", "In enim justo, rhoncus ut,", "CREDIT", "3432"));
            currentAccount.getTransactions().add(new Transaction("14 Aug 2014", "Curabitur ullamcorper ultricies nisi.", "DEBIT", "221"));
            currentAccount.getTransactions().add(new Transaction("12 Aug 2014", "Sed consequat, leo eget bibend", "DEBIT", "4342"));
            currentAccount.getTransactions().add(new Transaction("10 Aug 2014", "Duis leo.", "CREDIT", "5432"));

        }

        @When
        public void when() {
            Utilities.click(webDriver, String.format("[test-row-%s] [test-button-account-details]", currentAccount.getNumber()));
            Utilities.waitForPage(webDriver);
        }

        @Then
        public void then() {
            Utilities.assertTextEquals(webDriver, "[test-field-account-name]", "Premium Current Account");

            Utilities.assertAccountListRow(webDriver, "0", "30 Aug 2014", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor", "£ 45.43", "", "£ 400.00");
            Utilities.assertAccountListRow(webDriver, "1", "27 Aug 2014", "In enim justo, rhoncus ut,", "", "£ 34.32", "£ 445.43");
            Utilities.assertAccountListRow(webDriver, "2", "14 Aug 2014", "Curabitur ullamcorper ultricies nisi.", "£ 2.21", "", "£ 411.11");
            Utilities.assertAccountListRow(webDriver, "3", "12 Aug 2014", "Sed consequat, leo eget bibend", "£ 43.42", "", "£ 413.32");
            Utilities.assertAccountListRow(webDriver, "4", "10 Aug 2014", "Duis leo.", "", "£ 54.32", "£ 456.74");
        }
    }

    public class OpenSaverAccount implements OpenAccountPage {

        @OnlyRunWith
        Predicate predicate = or(
                withStep(Portfolio.CurrentAndSaverAccounts.class),
                withStep(Portfolio.AllAccounts.class)
        );

        @Demands
        private WebDriver webDriver;

        @Demands
        private List<Account> accounts;

        private Account currentAccount;

        @Given
        public void given() {

            for (Account account : accounts) {
                if ("Easy Saver Account".equals(account.getName())) {
                    currentAccount = account;
                    break;
                }
            }

            currentAccount.getTransactions().add(new Transaction("30 Aug 2014", "Nulla consequat massa quis enim", "DEBIT", "1500"));
            currentAccount.getTransactions().add(new Transaction("27 Aug 2014", "Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu", "CREDIT", "1500"));
            currentAccount.getTransactions().add(new Transaction("14 Aug 2014", "In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo", "DEBIT", "3000"));
            currentAccount.getTransactions().add(new Transaction("12 Aug 2014", "Nullam dictum felis eu pede mollis pretium", "DEBIT", "2000"));
            currentAccount.getTransactions().add(new Transaction("10 Aug 2014", "Integer tincidunt", "CREDIT", "1000"));
        }

        @When
        public void when() {
            Utilities.click(webDriver, format("[test-row-%s] [test-button-account-details]", currentAccount.getNumber()));
            Utilities.waitForPage(webDriver);
        }

        @Then
        public void then() {
            Utilities.assertTextEquals(webDriver, "[test-field-account-name]", "Easy Saver Account");

            Utilities.assertAccountListRow(webDriver, "0", "30 Aug 2014", "Nulla consequat massa quis enim", "£ 15.00", "", "£ 100.00");
            Utilities.assertAccountListRow(webDriver, "1", "27 Aug 2014", "Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu", "", "£ 15.00", "£ 115.00");
            Utilities.assertAccountListRow(webDriver, "2", "14 Aug 2014", "In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo", "£ 30.00", "", "£ 100.00");
            Utilities.assertAccountListRow(webDriver, "3", "12 Aug 2014", "Nullam dictum felis eu pede mollis pretium", "£ 20.00", "", "£ 130.00");
            Utilities.assertAccountListRow(webDriver, "4", "10 Aug 2014", "Integer tincidunt", "", "£ 10.00", "£ 150.00");
        }
    }

    public class OpenMortgageAccount implements OpenAccountPage {

        @OnlyRunWith
        Predicate predicate = or(
                withStep(Portfolio.MortgageAccountOnly.class),
                withStep(Portfolio.AllAccounts.class)
        );

        @Demands
        private WebDriver webDriver;

        @Demands
        private List<Account> accounts;

        private Account currentAccount;

        @Given
        public void given() {

            for (Account account : accounts) {
                if ("Fancy Mortgage".equals(account.getName())) {
                    currentAccount = account;
                    break;
                }
            }

            currentAccount.getTransactions().add(new Transaction("30 Aug 2014", "Nulla consequat massa quis enim", "DEBIT", "1500"));
            currentAccount.getTransactions().add(new Transaction("27 Aug 2014", "Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu", "CREDIT", "1500"));
            currentAccount.getTransactions().add(new Transaction("14 Aug 2014", "In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo", "DEBIT", "3000"));
            currentAccount.getTransactions().add(new Transaction("12 Aug 2014", "Nullam dictum felis eu pede mollis pretium", "DEBIT", "2000"));
            currentAccount.getTransactions().add(new Transaction("10 Aug 2014", "Integer tincidunt", "CREDIT", "1000"));
        }

        @When
        public void when() {
            Utilities.click(webDriver, format("[test-row-%s] [test-button-account-details]", currentAccount.getNumber()));
            Utilities.waitForPage(webDriver);
        }

        @Then
        public void then() {
            assertEquals("Fancy Mortgage", webDriver.findElement(By.cssSelector("[test-field-account-name]")).getText());

            Utilities.assertAccountListRow(webDriver, "0", "30 Aug 2014", "Nulla consequat massa quis enim", "£ 15.00", "", "£ (154,987.00)");
            Utilities.assertAccountListRow(webDriver, "1", "27 Aug 2014", "Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu", "", "£ 15.00", "£ (154,972.00)");
            Utilities.assertAccountListRow(webDriver, "2", "14 Aug 2014", "In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo", "£ 30.00", "", "£ (154,987.00)");
            Utilities.assertAccountListRow(webDriver, "3", "12 Aug 2014", "Nullam dictum felis eu pede mollis pretium", "£ 20.00", "", "£ (154,957.00)");
            Utilities.assertAccountListRow(webDriver, "4", "10 Aug 2014", "Integer tincidunt", "", "£ 10.00", "£ (154,937.00)");
        }
    }
}
