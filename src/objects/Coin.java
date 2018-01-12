package objects;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Coin {

    // maps each ticker to a coin name
    private static final Map<String, String> tickerMap;

    // get the Coin object associated with this ticker
    // this assures that there is only 1 instance of each coin in the program
    private static final Map<String, Coin> coinMap;

    // initialize ALL supported coins
    static {
        Map<String, String> tMap = new HashMap<String, String>();
        Map<String, Coin> cMap = new HashMap<String, Coin>();

        // MOST IMPORTANT
        tMap.put("USD", "US Dollar");
        tMap.put("USDT", "US Dollar Tether");

        tMap.put("BTC", "Bitcoin");
        tMap.put("ETH", "Ethereum");
        tMap.put("BCH", "Bitcoin Cash");
        tMap.put("BCC", "Bitcoin Cash");
        tMap.put("MIOTA", "IOTA");
        tMap.put("XRP", "Ripple");
        tMap.put("LTC", "Litecoin");
        tMap.put("DASH", "Dash");
        tMap.put("BTG", "Bitcoin Gold");
        tMap.put("XEM", "NEM");
        tMap.put("ADA", "Cardano");
        tMap.put("ETC", "Ethereum Classic");
        tMap.put("XLM", "Stellar Lumens");
        tMap.put("NEO", "NEO");
        tMap.put("EOS", "EOS");
        tMap.put("PPT", "Populous");
        tMap.put("OMG", "OmiseGO");
        tMap.put("ARK", "Ark");
        tMap.put("VTC", "Vertcoin");
        tMap.put("SALT", "SALT");
        tickerMap = Collections.unmodifiableMap(tMap);

        for (String ticker : tickerMap.keySet()) {
            cMap.put(ticker, new Coin(ticker));
        }
        coinMap = Collections.unmodifiableMap(cMap);
    }

    public String ticker;
    public String name;

    /* Private constructor */
    private Coin(String ticker) {
        this.ticker = ticker;
        this.name = tickerMap.get(ticker);
    }
    
    public static Coin get(String ticker){
        assert(coinMap.containsValue(ticker));
        return coinMap.get(ticker);
    }
    
    public static boolean isSupported(String ticker) {
        return coinMap.containsKey(ticker);
    }

    public String toString() {
        return name + " (" + ticker + ")";
    }

    // Overriding equals() to compare two Coin objects
    @Override 
    public boolean equals(Object o) {
        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }
        /* Check if o is an instance of Coin or not */
        if (!(o instanceof Coin)) {
            return false;
        }
        // typecast o to Complex so that we can compare data members
        Coin c = (Coin) o;
        // Compare the data members and return accordingly
        // Compare NAME because two equal coins can have different tickers
        return name.equals(c.name);
    }
}
