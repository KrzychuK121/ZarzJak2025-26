package biz;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CucumberSteps {

    @Given("We have user {string} with id: {int}")
    public void we_have_user_with_id(String string, Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Given("{string} have account: {int} with: {int} pln")
    public void have_account_with_pln(String string, Integer int1, Integer int2) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Given("There is an account:{int} with {int} pln")
    public void there_is_an_account_with_pln(Integer int1, Integer int2) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Given("Everything is authorised")
    public void everything_is_authorised() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @When("{string} make transfer from acc: {int} to acc: {int} with amount: {int}")
    public void make_transfer_from_acc_to_acc_with_amount(String string, Integer int1, Integer int2, Integer int3) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("account:{int} value:{double} pln")
    public void account_value_pln(Integer int1, Double double1) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("All operations were successful")
    public void all_operations_were_successful() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
}
