package pl.lodz.uni.wfis.podlaski.bankapp.model.exceptions;

/**
 * Created by Krzysztof Podlaski on 04.03.2018.
 */
public class UserUnnkownOrBadPasswordException extends Exception {
    public UserUnnkownOrBadPasswordException(String msg){
        super(msg);
    }
}
