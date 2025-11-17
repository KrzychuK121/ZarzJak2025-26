package pl.lodz.uni.wfis.podlaski.bankapp.biz;

import pl.lodz.uni.wfis.podlaski.bankapp.db.dao.DAO;
import pl.lodz.uni.wfis.podlaski.bankapp.model.Account;
import pl.lodz.uni.wfis.podlaski.bankapp.model.Operation;
import pl.lodz.uni.wfis.podlaski.bankapp.model.User;
import pl.lodz.uni.wfis.podlaski.bankapp.model.operations.Interest;

import java.sql.SQLException;

/**
 * Created by Krzysztof Podlaski on 07.03.2018.
 */
public class InterestOperator {
    protected DAO dao;
    protected AccountManager accountManager;
    protected BankHistory bankHistory;
    private double interestFactor =.2;

    public InterestOperator (DAO dao, AccountManager am){
        this.dao=dao;
        accountManager = am;
    }

    public void countInterestForAccount(Account account) throws SQLException {
        double ammount = account.getAmmount();
        double interest = ammount*interestFactor;
        User user = dao.findUserByName("InterestOperator");
        String desc = "Interest ...";
        boolean success = accountManager.paymentIn(user,interest,desc,account.getId());
        Operation operation=new Interest(user,interest,desc,account);
        bankHistory.logOperation(operation,success);
    }


}
