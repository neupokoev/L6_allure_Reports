package com.neo.steps;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class GithubPageSteps {

    @Step("Go to the {url}")
    public void openUrl(String url) {
        Selenide.open(url);
    }

    @Step("Search for the repository {repositoryName}")
    public void searchForRepository(String repositoryName) {
        $(".header-search-input").val(repositoryName).pressEnter();
    }

    @Step("Click on repository {repositoryName}")
    public void clickOnRepository(String repositoryName) {
        $(By.linkText(repositoryName)).click();
    }

    @Step("Go to the Issues section")
    public void goToIssuesSection() {
        $(withText("Issues")).click();
    }

    @Step("Verify that issue with #issue_{issueNumber} is visible")
    public void checkIssueWithNumberShouldBeVisible(int issueNumber) {
        $(String.format("#issue_%s", issueNumber)).shouldBe(visible);
    }
}
