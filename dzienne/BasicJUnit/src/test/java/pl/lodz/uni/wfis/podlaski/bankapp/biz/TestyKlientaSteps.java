package pl.lodz.uni.wfis.podlaski.bankapp.biz;

import io.cucumber.java.en.Given;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.mockito.Mockito;
import pl.lodz.uni.wfis.podlaski.bankapp.db.dao.DAO;
import pl.lodz.uni.wfis.podlaski.bankapp.model.Account;
import pl.lodz.uni.wfis.podlaski.bankapp.model.Operation;
import pl.lodz.uni.wfis.podlaski.bankapp.model.User;
import pl.lodz.uni.wfis.podlaski.bankapp.model.operations.Withdraw;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;



public class TestyKlientaSteps {
    List<User> users = new ArrayList<>();
    List<Account> accounts = new ArrayList<>();
    DAO dao = Mockito.mock(DAO.class);
    AuthenticationManager  auth = Mockito.mock(AuthenticationManager.class);
    BankHistory mockHistory = Mockito.mock(BankHistory.class);
    AccountManager target = null;
    boolean result = false;

    @Given("We have user {string} with id: {int}" )
    public void we_have_user_with_id(String username, int id) throws Exception{
        User user = new User();
        user.setName(username);
        user.setId(id);
        users.add(user);
        when(dao.findUserByName(username)).thenReturn(user);
    }

    @Given("{string} have account: {int} with: {double} pln")
    public void have_account_with_pln(String username, int accId, double amount) throws Exception{
        Account account = new Account();
        User user = dao.findUserByName(username);
        account.setOwner(user);
        account.setId(accId);
        account.setAmmount(amount);
        accounts.add(account);
        when(dao.findAccountById(accId)).thenReturn(account);
    }

    @Given("There is an account:{int} with {double} pln")
    public void there_is_an_account_with_pln(Integer accId, double amount) throws Exception{
        Account account = new Account();
        account.setId(accId);
        account.setAmmount(amount);
        accounts.add(account);
        when(dao.findAccountById(accId)).thenReturn(account);
    }

    @Given("Everything is authorised")
    public void everything_is_authorised() throws Exception{
       when(auth.canInvokeOperation(any(Withdraw.class), any(User.class))).thenReturn(true);
       when(dao.updateAccountState(any())).thenReturn(true);
    }

    private void createTarget() throws Exception{
        target = new AccountManager();
        InterestOperator mockInterestOperator = Mockito.mock(InterestOperator.class);
        target.dao = dao;
        target.history = mockHistory;
        target.auth = auth;
        //Co zrobić jak pole jest prywatne
        Field f = AccountManager.class.getDeclaredField("interestOperator");
        f.setAccessible(true);
        f.set(target, mockInterestOperator);
    }

    @When("{string} make transfer from acc: {int} to acc: {int} with amount: {double}")
    public void make_transfer_from_acc_to_acc_with_amount(String username, Integer srcAccId,
                    Integer dstAccId, double amount) throws Exception{
        if (target == null) createTarget();
        User user = dao.findUserByName(username);
        result = target.internalPayment(user,amount," ", srcAccId, dstAccId);
    }



    @Then("account:{int} value:{double} pln")
    public void account_value_pln(Integer accId, Double double1) throws Exception{
        // Write code here that turns the phrase above into concrete actions
        Account a = dao.findAccountById(accId);
        assertEquals(double1, a.getAmmount(),0.01);
    }

    @Then("All operations were successful")
    public void all_operations_were_successful() throws Exception{
        // Write code here that turns the phrase above into concrete actions
        assertTrue(result);
    }

}
