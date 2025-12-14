package biz;

import db.dao.DAO;
import model.Account;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class AccountManagerTest {

    AccountManager target;
    DAO daoMock;
    AuthenticationManager authMock;
    BankHistory histMock;
    InterestOperator interestMock;

    @BeforeEach
    void testSetup() throws NoSuchFieldException, IllegalAccessException {
        target = new AccountManager();
        daoMock = Mockito.mock(DAO.class);
        authMock = Mockito.mock(AuthenticationManager.class);
        histMock = Mockito.mock(BankHistory.class);
        interestMock = Mockito.mock(InterestOperator.class);
        target.dao = daoMock;
        target.auth = authMock;
        target.history = histMock;
        //for private field
        //target.interestOperator = interestMock;
        Field interestField = target.getClass().getDeclaredField("interestOperator");
        interestField.setAccessible(true);
        interestField.set(target,interestMock);
    }

    //Wpłata na konto 100zł, powinno się udać wszystko
    @Test
    void paymentIn() throws SQLException {
        User user = new User();
        user.setId(12);
        user.setName("Eleonora");
        when(daoMock.findUserByName("Eleonora")).thenReturn(user);
        Account acc = new Account();
        acc.setId(1);
        acc.setOwner(user);
        acc.setAmmount(1234.5);
        when(daoMock.findAccountById(1)).thenReturn(acc);
        //System.out.println(daoMock.findAccountById(3));
        //System.out.println(daoMock.findAccountById(1));
        when(daoMock.updateAccountState(any())).thenReturn(true);
        boolean sucess = target.paymentIn(user,100," ",1);
        assertTrue(sucess);
        assertEquals(1334.5, acc.getAmmount(),.01);
        verify(daoMock, times(1)).updateAccountState(any());
        verify(daoMock, times(1)).updateAccountState(acc);
    }

    @Test
    void paymentInNegativeAmount() throws SQLException {
        User user = new User();
        user.setId(12);
        user.setName("Eleonora");
        when(daoMock.findUserByName("Eleonora")).thenReturn(user);
        Account acc = new Account();
        acc.setId(1);
        acc.setOwner(user);
        acc.setAmmount(1234.5);
        when(daoMock.findAccountById(1)).thenReturn(acc);
        //System.out.println(daoMock.findAccountById(3));
        //System.out.println(daoMock.findAccountById(1));
        when(daoMock.updateAccountState(any())).thenReturn(true);
        boolean sucess = target.paymentIn(user,-100," ",1);
        assertEquals(1234.5, acc.getAmmount(),.01);
        assertFalse(sucess);
        verify(daoMock, never()).updateAccountState(any());
    }

    // Test co jeżli daoMock zwróci wyjątek
    // test co jest updateAccount zwróci fałsz
    // Wypłata kwoty większej niz jest na koncie
}