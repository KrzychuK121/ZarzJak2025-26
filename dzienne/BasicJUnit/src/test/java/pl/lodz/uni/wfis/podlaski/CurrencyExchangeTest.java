package pl.lodz.uni.wfis.podlaski;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CurrencyExchangeTest {

    private CurrencyExchange testObject;
    @BeforeEach
    void setUp() {
        testObject = new CurrencyExchange();
    }

    @AfterEach
    void tearDown() {
        testObject = null;
    }

    @Test
    void changeFromUsdToPln() throws Exception {
        //GIVEN -- istnieje obiek testowany
        //WHEN -- uruchamiamy change("usd","pl",100)
        double val = testObject.change("usd", "pln", 100);
        //THEN -- oczekiwany wynik 100*3.63
        assertEquals(100*3.63, val, 0.01, "Wrong value");
    }

    @Test
    void changeFromUsdToUsd() throws Exception {
        //GIVEN -- istnieje obiek testowany
        //WHEN -- uruchamiamy change("usd","pl",100)
        double val = testObject.change("usd", "usd", 100);
        //THEN -- oczekiwany wynik 100
        assertEquals(100, val, 0.01, "Wrong value");
    }

    @Test
    void changeFromUsdToUnknownCurrency() throws Exception {
        //GIVEN -- istnieje obiek testowany
        //WHEN -- uruchamiamy change("usd","walutaXXX",100)
        //THEn -- oczekiwany wyjątek Exception
        assertThrows(Exception.class,
                () -> testObject.change("usd", "walutaXXX", 100));
    }

    //DOROBIĆ OBSŁUGĘ Z PLIKU CSV
}