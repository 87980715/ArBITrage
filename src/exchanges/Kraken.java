package exchanges;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.common.collect.HashBasedTable;

import objects.Coin;
import objects.CoinPair;

public class Kraken extends Exchange {

    private static final String PAIRS = "BCHXBT,XBTUSD,XRPUSD,XRPXBT";
    
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
        //BCHXBT,%20XBTUSD
        try {
            //reset the data
            marketData = HashBasedTable.create();
            
            //https://api.kraken.com/0/public/Ticker?pair=BCHXBT,XBTUSD,XRPUSD,XRPXBT
            //pull the market data from Kraken's REST API
            JSONObject json = (JSONObject) new JSONObject(IOUtils.toString(
                    new URL("https://api.kraken.com/0/public/Ticker?pair=" + PAIRS),
                    Charset.forName("UTF-8"))).get("result");
            System.out.println(json);
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
            System.out.println("Successfully updated: Kraken");
            
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
