package pl.lodz.uni.wfis.podlaski.bankapp.biz;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.lodz.uni.wfis.podlaski.bankapp.db.dao.DAO;
import pl.lodz.uni.wfis.podlaski.bankapp.model.Account;
import pl.lodz.uni.wfis.podlaski.bankapp.model.User;


import java.lang.reflect.Field;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AccountManagerTest {
    AccountManager target;
    DAO mockDao;
    BankHistory mockHistory;
    AuthenticationManager mockAuth;
    InterestOperator mockInterestOperator;
    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        target = new AccountManager();
        mockDao =  Mockito.mock(DAO.class);
        mockHistory = Mockito.mock(BankHistory.class);
        mockAuth = Mockito.mock(AuthenticationManager.class);
        mockInterestOperator = Mockito.mock(InterestOperator.class);
        target.dao = mockDao;
        target.history = mockHistory;
        target.auth = mockAuth;
        //Co zrobić jak pole jest prywatne
        Field f = AccountManager.class.getDeclaredField("interestOperator");
        f.setAccessible(true);
        f.set(target, mockInterestOperator);

    }

    @AfterEach
    void tearDown() {
        target = null;
    }

    @Test
    void paymentIn() throws SQLException {
        User user = new User();
        //Przypadek testowy, założenia
        //Istnieje konto o id=1, na tym koncie mamy 100zł, Konto należy do Użytkownika Janek o id 3;
        //Operacje na accout też są poprawne
        //Baza danych działa wszystkie update się udają
        user.setName("Janek");
        user.setId(3);
        Account mockAccount = Mockito.mock(Account.class);
        when(mockAccount.getOwner()).thenReturn(user);
        when(mockAccount.getAmmount()).thenReturn(100.0);
        when(mockAccount.income(125.0)).thenReturn(true);
        when(mockDao.findAccountById(1)).thenReturn(mockAccount);
        when(mockDao.updateAccountState(any())).thenReturn(true);
        Account account = mockDao.findAccountById(1);
        //Przypadek testowy, testowana akcja: Janek wpłaca na swoje konto 125 zł

        Boolean result = target.paymentIn(user,125,"Wpłata na konto",1);
        //Oczekiwania: result true, stan konta po wpłacie 225
        assertTrue(result);
        //Sprawdzenie czy konto zmieniło swój stan, powinno być wykonanie w testach klasy Account
        //assertEquals(225.0, account.getAmmount());
        //Weryfikujemy czy paymentIn próbowało zmienić stan konta operacją income
        verify(mockAccount, atLeastOnce()).income(125.0);
        //Weryfikujemy, ile updatów na bazie danych? czy upadty tylko na niezbędnych kontach, .....
    }
    @Test
    void paymentIn2() throws SQLException {
        User user = new User();
        //Przypadek testowy, założenia
        //Istnieje konto o id=1, na tym koncie mamy 100zł, Konto należy do Użytkownika Janek o id 3;
        //Operacje na accout nie są poprawne
        //Baza danych działa wszystkie update się udają
        user.setName("Janek");
        user.setId(3);
        Account mockAccount = Mockito.mock(Account.class);
        when(mockAccount.getOwner()).thenReturn(user);
        when(mockAccount.getAmmount()).thenReturn(100.0);
        when(mockAccount.income(125.0)).thenReturn(false);
        when(mockDao.findAccountById(1)).thenReturn(mockAccount);
        when(mockDao.updateAccountState(any())).thenReturn(true);
        Account account = mockDao.findAccountById(1);
        //Przypadek testowy, testowana akcja: Janek wpłaca na swoje konto 125 zł

        Boolean result = target.paymentIn(user,125,"Wpłata na konto",1);
        //Oczekiwania: result false, stan konta po wpłacie 100
        assertFalse(result);
        //Sprawdzenie czy konto zmieniło swój stan, powinno być wykonanie w testach klasy Account
        //assertEquals(225.0, account.getAmmount());
        //Weryfikujemy czy paymentIn próbowało zmienić stan konta operacją income
        verify(mockAccount, atLeastOnce()).income(125.0);
        //Weryfikujemy, ile updatów na bazie danych? czy upadty tylko na niezbędnych kontach, .....
    }
}