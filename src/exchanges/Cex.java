package exchanges;

import objects.Coin;
import objects.CoinPair;

public class Cex extends Exchange {

    @Override
    public CoinPair getPricePair(Coin quote, Coin base) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int buy(CoinPair pair) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int sell(CoinPair pair) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int withdraw(Coin coin, String addr) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int updatePricePairs() {
        
        //https://cex.io/api/tickers/USD/EUR/RUB/BTC
        //TWO BASES: USD, BTC
        //https://cex.io/cex-api
        // TODO Auto-generated method stub
        return 0;
    }

}
