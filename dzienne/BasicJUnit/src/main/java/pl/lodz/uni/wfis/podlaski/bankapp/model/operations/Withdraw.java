package pl.lodz.uni.wfis.podlaski.bankapp.model.operations;


import pl.lodz.uni.wfis.podlaski.bankapp.model.Account;
import pl.lodz.uni.wfis.podlaski.bankapp.model.User;

/**
 * Created by Krzysztof Podlaski on 07.03.2018.
 */
public class Withdraw extends Payment{

    public Withdraw(User user, double ammount, String description, Account account) {
        super(user, ammount, description, account, OperationType.WITHDRAW);
    }
}
