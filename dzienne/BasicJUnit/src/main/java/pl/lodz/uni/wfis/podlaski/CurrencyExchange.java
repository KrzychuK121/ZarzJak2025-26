package pl.lodz.uni.wfis.podlaski;

import java.util.HashMap;

public class CurrencyExchange {

    private int getIdFromCurrecyName(String currecyName)
            throws Exception {
        if (currecyName.equals("usd")) {
            return  1;
        }
        if (currecyName.equals("pln")){
            return 2;
        }
        if (currecyName.equals("eur")){
            return 3;
        }
        throw new Exception("Nie mam takiej waluty");
    }

    double[][] rates = new double[4][4];

    private void initRates(){
        rates[1][1] = 1;
        rates[1][2] = 3.63;
        rates[2][1] = 1/3.63*.97;
        rates[2][2] = 1;
        rates[3][2] = 4.23;
        rates[3][3] = 1;
        rates[2][3] = 1/4.23*.97;
    }

    public CurrencyExchange(){
        initRates();
    }

    public double change(String from, String to, double value)
            throws Exception {
        int fromId  =  getIdFromCurrecyName(from);
        int toId  = getIdFromCurrecyName(to);
        //TODO get rate from some source
        double rate = rates[fromId][toId];
        return rate*value;
    }
}
