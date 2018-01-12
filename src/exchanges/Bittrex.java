package exchanges;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import objects.Coin;
import objects.CoinPair;

public class Bittrex extends Exchange {

    
    // Thread that automatically calls updatePricePairs;
    //private UpdateThread updater;
    
    public Bittrex() {
       // updater = new UpdateThread();
        name = "Bittrex";
    }

    @Override
    public CoinPair getPricePair(Coin quote, Coin base) {
        return marketData.get(quote, base);
    }

    @Override
    public int buy(CoinPair pair) {
        //for marketData.
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
        try {
            //reset the data
            marketData = HashBasedTable.create();
            
            //pull the market data from Bittrex's REST API
            JSONObject json = new JSONObject(IOUtils.toString(
                    new URL("https://bittrex.com/api/v1.1/public/getmarketsummaries"),
                    Charset.forName("UTF-8")));
            JSONArray arr = (JSONArray) json.get("result");

            // parse the JSONArray
            for (int n = 0; n < arr.length(); n++) {
                // extract the useful data from the JSON output
                JSONObject obj = arr.getJSONObject(n);
                String pair = obj.getString("MarketName");
                Double volume = obj.getDouble("Volume");
                Double price = obj.getDouble("Last");

                // create coin and coinpair objects to represent this set
                String[] coinNames = pair.split("-");
                if (Coin.isSupported(coinNames[0])
                        && Coin.isSupported(coinNames[1])) {
                    // pull the correct coin objects from the map
                    Coin base = Coin.get(coinNames[0]);
                    Coin quote = Coin.get(coinNames[1]);
                    assert(base != quote);
                    CoinPair coinPair = new CoinPair(quote, base, price, volume, this);
                    // add data to the list
                    marketData.put(quote, base, coinPair);
                }
            }
            System.out.println("Successfully updated: Bittrex");
            
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
