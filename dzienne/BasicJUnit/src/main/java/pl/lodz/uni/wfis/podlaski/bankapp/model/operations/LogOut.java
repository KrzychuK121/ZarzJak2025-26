package pl.lodz.uni.wfis.podlaski.bankapp.model.operations;


import pl.lodz.uni.wfis.podlaski.bankapp.model.User;

/**
 * Created by Krzysztof Podlaski on 07.03.2018.
 */
public class LogOut extends LogOperation {
    public LogOut(User user, String description) {
        super(user, description, OperationType.LOG_OUT);
    }
}
