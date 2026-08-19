package com.aria.cucumber;

import com.aria.pages.InventoryPage;
import com.aria.pages.LoginPage;
import com.aria.utils.PageManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

/*
 LoginSteps — connects the Gherkin steps in login.feature to the actual Selenium code
 Each method here corresponds to one line in the feature file (Given, When, Then)
 */
public class LoginSteps {

    //I N S T A N C E  V A R I A B L E S

    // TestContext — gives access to the WebDriver created by Hooks
    // PicoContainer injects the SAME TestContext that Hooks received so both classes share the same browser session
    private final TestContext context;

    // PageManager — creates and caches Page Objects on demand
    // receives the driver from context so pages use the same browser
    private final PageManager pages;

    // C O N S T R U C T O R  — PicoContainer calls this automatically
    // injects the same TestContext that was injected into Hooks
    // this is why both classes share the same driver (thread-safe)
    public LoginSteps(TestContext context){

        this.context = context; //context.getDriver() -> gets the driver that Hooks created in @Before
        this.pages = new PageManager(context.getDriver());//PageManager uses it to create LoginPage, and other pages
    }

    //M E T H O D S
    /*
    Scenario: Successful login with valid credentials
    Given the user is on the login page
    When the user logs in with "standard_user" and "secret_sauce"
    Then the inventory page should be displayed

     */
    @Given("the user is on the login page")
    public void the_user_is_on_the_login_page(){
        Assert.assertEquals(context.getDriver().getTitle(), "Swag Labs");
    }

    @When("the user logs in with {string} and {string}")
    public void the_user_logs_in(String username, String password){
        pages.getPage(LoginPage.class).login(username,password);
    }
    @Then("the inventory page should be displayed")
    public void the_inventory_page_should_be_displayed(){
        Assert.assertTrue(pages.getPage(InventoryPage.class).inventoryIsDisplayed());
    }
    @Then("an error message {string} should be displayed")
    public void an_error_message_should_be_displayed(String expectedMessage){
        Assert.assertEquals(pages.getPage(LoginPage.class).getErrorMessage(), expectedMessage);
    }
}