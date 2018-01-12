package exchanges;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Table;

import objects.Coin;
import objects.CoinPair;

public abstract class Exchange {
    
    //Table of coinpair objects to represent to current state of the market
    //Keys: Quote, Base
    //marketData.row(quote): Map of <Base, CoinPair>
    //marketData.column(base): Map of <Quote, CoinPair>
    protected Table<Coin, Coin, CoinPair> marketData;
  
    protected String name;
    
    public abstract CoinPair getPricePair(Coin quote, Coin base);
    
    public abstract int buy(CoinPair pair);
    
    public abstract int sell(CoinPair pair);
    
    public abstract int withdraw(Coin coin, String addr);
    
    public abstract int updatePricePairs();
    
    public List<CoinPair> routes(Coin quote){
        Map<Coin, CoinPair> bases = marketData.row(quote);
        List<CoinPair> routes = new ArrayList<CoinPair>();
        for(CoinPair cp: bases.values()) {
            routes.add(cp);
        }
        return routes;
    }
    
}
