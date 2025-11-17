package pl.lodz.uni.wfis.podlaski.bankapp.model.operations;


import pl.lodz.uni.wfis.podlaski.bankapp.model.Operation;
import pl.lodz.uni.wfis.podlaski.bankapp.model.User;

/**
 * Created by Krzysztof Podlaski on 07.03.2018.
 */
public class LogOperation extends Operation {
    protected LogOperation(User user, String description, OperationType operationType) {
        super(user, description, operationType);
    }
}
