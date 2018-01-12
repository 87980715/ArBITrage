package exchanges;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import objects.Coin;
import objects.CoinPair;

/* This class is responsible for initializing and managing the exchange objects that connect to each exchange's API */
public class ExchangeManager {

    public ArrayList<Exchange> exchanges;

    /* Handle updates from here */

    public ExchangeManager() {
        Bittrex bittrex = new Bittrex();
        Binance binance = new Binance();
        Kraken kraken = new Kraken();

        exchanges = new ArrayList<Exchange>();
        exchanges.add(bittrex);
        exchanges.add(binance);
        exchanges.add(kraken);

        update();
        analyzeCoin(Coin.get("XRP"));
    }

    // TODO: REDO COINPAIR STRUCTURING (keep volume/price data in EXCHANGE

    public void analyzeExchanges(Exchange exA, Exchange exB) {
        Map<Coin, Map<Coin, CoinPair>> rowsA = exA.marketData.rowMap();
        Map<Coin, CoinPair> colA;
        for (Coin rowKey : rowsA.keySet()) {
            colA = rowsA.get(rowKey);
            for (Coin colKey : colA.keySet()) {
                CoinPair cpA = colA.get(colKey);
                CoinPair cpB = exB.marketData.get(rowKey, colKey);
                if (cpA.equals(cpB)) {
                 // This is the difference in # of QUOTE for 1 unit of Base
                    double diff = cpA.ratio - cpB.ratio;
                    Exchange exBuy; // Where trade opens
                    Exchange exSell; // Where trade closes
                    if (diff > 0) {
                        exBuy = exB;
                        exSell = exA;
                    } else {
                        exBuy = exA;
                        exSell = exB;
                    }
                    diff = Math.abs(diff);
                    System.out.println(exA.name + " vs. " + exB.name + ": " + cpA.pairString()
                            + " is cheaper by " + diff + " on " + exBuy.name);

                    // To compute profit percentage, we take the DIFF / (Price of QUOTE in
                    // BASE on EXPENSIVE EXCHANGE) / (Price of BASE = 1) * 100
                    double profit = diff / cpA.ratio * 100; // INCOMPLETE IMPLEMENTATION
                    System.out.println("Profit: " + profit + "%\n");
                }
            }
        }
    }

    /* Update all the exchanges */
    public void update() {
        for (Exchange e : exchanges) {
            e.updatePricePairs();
        }
    }
    
    /* Analyzes all bases to a given Quote currency of one exchange to another */
    public void analyzeCoin(Coin quote) {
        List<CoinPair> routesA = exchanges.get(0).routes(quote);
        List<CoinPair> routesB = exchanges.get(1).routes(quote);
        
        for(CoinPair cpA: routesA) {
            for(CoinPair cpB: routesB) {
                if(cpA.equals(cpB)) {
                    double diff = Math.abs(cpA.ratio - cpB.ratio);
                    System.out.println(cpA.parent.name + " vs. " + cpB.parent.name + ": " + cpA.pairString()
                    + " is cheaper by " + diff);
                    double profit = Math.abs(diff) / Math.min(cpA.ratio, cpB.ratio) * 100;
                    System.out.println("Profit: " + profit + "%\n");
                }
            }
        }
        
    }
}
