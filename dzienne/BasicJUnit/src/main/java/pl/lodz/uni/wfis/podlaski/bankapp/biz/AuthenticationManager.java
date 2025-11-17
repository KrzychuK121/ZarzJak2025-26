package pl.lodz.uni.wfis.podlaski.bankapp.biz;





import pl.lodz.uni.wfis.podlaski.bankapp.db.dao.DAO;
import pl.lodz.uni.wfis.podlaski.bankapp.model.Operation;
import pl.lodz.uni.wfis.podlaski.bankapp.model.Password;
import pl.lodz.uni.wfis.podlaski.bankapp.model.User;
import pl.lodz.uni.wfis.podlaski.bankapp.model.exceptions.UserUnnkownOrBadPasswordException;
import pl.lodz.uni.wfis.podlaski.bankapp.model.operations.OperationType;
import pl.lodz.uni.wfis.podlaski.bankapp.model.operations.Withdraw;

import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Base64;

/**
 * Created by Krzysztof Podlaski on 04.03.2018.
 */
public class AuthenticationManager {
    private DAO dao;
    private BankHistory history;

    public AuthenticationManager(DAO dao, BankHistory bankHistory){
        this.dao=dao;
        this.history=bankHistory;
    }

    public User logIn(String userName, char[] password) throws UserUnnkownOrBadPasswordException, SQLException {
        User user = dao.findUserByName(userName);
        if (user==null) {
            history.logLoginFailure(null,"Zła nazwa użytkownika "+userName);
            throw new UserUnnkownOrBadPasswordException("Bad Password");
        }
        Password paswd = dao.findPasswordForUser(user);
        if (checkPassword(paswd,password)) {
            history.logLoginSuccess(user);
            return user;
        }
        else {
            history.logLoginFailure(user,"Bad Password");
            throw new UserUnnkownOrBadPasswordException("Bad Password");
        }
    }

    public boolean logOut(User user) throws SQLException {
        history.logLogOut(user);
        return true;
    }

    private boolean checkPassword(Password passwd, char[] password) {
        String hashedPassword = hashPassword(password);
        return passwd.getPasswd().equals(hashedPassword);
    }

    public static String hashPassword(char[] pass){
        String result=null;
        CharBuffer cBuffer = CharBuffer.wrap(pass);
        byte[] bpass = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            bpass = Charset.forName("UTF-8").encode(cBuffer).array();
            byte[] encodedhash = digest.digest(bpass);
            result = new String(Base64.getEncoder().encode(encodedhash));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        finally{
            cBuffer.clear();
            if (bpass!=null) Arrays.fill(bpass, (byte) 0x0);
            if (pass!=null) Arrays.fill(pass, 'a');
        }
        return result;
    }

    public boolean canInvokeOperation(Operation operation, User user) {
        if (user.getRole().getName().equals("Admin")) return true;
        if (operation.getType() == OperationType.PAYMENT_IN) return true;
        if (operation.getType() == OperationType.WITHDRAW) {
            Withdraw op = (Withdraw) operation;
            return user.getId() == op.getUser().getId();
        }
        return false;
    }


}
