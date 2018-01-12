package control;

import exchanges.Bittrex;

public class PriceManager {

    
    public static void initialize(){
        initExchanges();
    }
    
    
    
    private static void initExchanges(){
        Bittrex bittrex = new Bittrex();
        bittrex.updatePricePairs();
    }
    
    
    
}
