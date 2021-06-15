package com.neo;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.neo.steps.GithubPageSteps;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static io.qameta.allure.Allure.step;

class CheckIssueNumberAvailableOnPageTests {

    private static final String GITHUB_URL = "https://github.com";
    private static final String REPOSITORY_NAME = "allure-framework/allure2";
    private static final String ISSUE_NUMBER_ID = "#issue_1264";
    private static final int ISSUE_NUMBER = 1264;

    private GithubPageSteps step = new GithubPageSteps();

    @Test
    void pureSelenideTest() {
        SelenideLogger.addListener("allure", new AllureSelenide().screenshots(true));

        Selenide.open(GITHUB_URL);
        $(".header-search-input").setValue(REPOSITORY_NAME).pressEnter();
        $(By.linkText(REPOSITORY_NAME)).click();
        $(withText("Issues")).click();
        $(ISSUE_NUMBER_ID).shouldBe(visible);
    }

    @Severity(SeverityLevel.MINOR)
    @Description("Test with using lambda Steps")
    @Test
    void lambdaStepsTest() {
        step("Go to Github", (s) -> {
            s.parameter("url", GITHUB_URL);
            Selenide.open(GITHUB_URL);
        });

        step("Search for the repository: " + REPOSITORY_NAME, (s) -> {
            s.parameter("Repository name", REPOSITORY_NAME);
            $(".header-search-input").setValue(REPOSITORY_NAME).pressEnter();
        });

        step("Click on repository: " + REPOSITORY_NAME, (s) -> {
            s.parameter("Repository name", REPOSITORY_NAME);
            $(By.linkText(REPOSITORY_NAME)).click();
        });

        step("Go to the Issues section", () ->
                $(withText("Issues")).click()
        );

        step("Verify that issue with " + ISSUE_NUMBER_ID + " is visible",
                (s) -> {
                    s.parameter("issue number", ISSUE_NUMBER_ID);
                    $(ISSUE_NUMBER_ID).should(visible);
                });
    }

    @Severity(SeverityLevel.BLOCKER)
    @Description("Test with using PO Steps")
    @Test
    void annotationStepTest() {
        step.openUrl(GITHUB_URL);
        step.searchForRepository(REPOSITORY_NAME);
        step.clickOnRepository(REPOSITORY_NAME);
        step.goToIssuesSection();
        step.checkIssueWithNumberShouldBeVisible(ISSUE_NUMBER);
    }
}

