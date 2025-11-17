package pl.lodz.uni.wfis.podlaski.bankapp.db.dao;


import pl.lodz.uni.wfis.podlaski.bankapp.model.Account;
import pl.lodz.uni.wfis.podlaski.bankapp.model.Operation;
import pl.lodz.uni.wfis.podlaski.bankapp.model.Password;
import pl.lodz.uni.wfis.podlaski.bankapp.model.User;

import java.sql.SQLException;

/**
 * Created by Krzysztof Podlaski on 04.03.2018.
 */
public interface DAO {
    User findUserByName(String userName) throws SQLException;
    Password findPasswordForUser(User user) throws SQLException;
    Account findAccountById(int accountId) throws SQLException;
    boolean updateAccountState(Account account) throws SQLException;
    boolean setUserPassword(User user, String passwd, String oldPass) throws SQLException;
    void close() throws SQLException;

    void logOperation(Operation operation, boolean success) throws SQLException;
}
