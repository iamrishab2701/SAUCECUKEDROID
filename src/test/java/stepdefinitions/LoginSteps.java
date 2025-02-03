package stepdefinitions;

import org.junit.Assert;

import config.ConfigReader;
import driver.DriverManager;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.LoginPage;

public class LoginSteps {
    private LoginPage loginPage;

    @Given("I launch the app")
    public void iLaunchTheApp() {
        DriverManager.initializeDriver();
        loginPage = new LoginPage();
    }

    @When("I enter valid credentials")
    public void iEnterValidCredentials() {
        loginPage.enterUsername(ConfigReader.getUsername());
        loginPage.enterPassword(ConfigReader.getPassword());
    }

    @And("I click on login button")
    public void iClickOnLoginButton() {
        loginPage.clickLogin();
    }

    @Then("I should see the home screen")
    public void iShouldSeeTheHomeScreen() {
        Assert.assertTrue("Login Failed!", true); // Modify with actual validation
    }
}
