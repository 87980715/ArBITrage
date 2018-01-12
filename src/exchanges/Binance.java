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

public class Binance extends Exchange {
    // Thread that automatically calls updatePricePairs;
    private UpdateThread updater;

    public Binance() {
        name = "Binance";
    }

    @Override
    public CoinPair getPricePair(Coin quote, Coin base) {
        return marketData.get(quote, base);
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
        // /api/v1/ticker/24hr
        try {
            System.out.println("Updating: " + name);
            // reset the data
            marketData = HashBasedTable.create();

            // pull the market data from Binance's REST API
            String data = IOUtils.toString(
                    new URL("https://api.binance.com/api/v1/ticker/24hr"),
                    Charset.forName("UTF-8"));
            System.out.println(data);
            JSONArray arr = new JSONArray(data);
            // parse the JSONArray
            for (int n = 0; n < arr.length(); n++) {
                // extract the useful data from the JSON output
                JSONObject obj = arr.getJSONObject(n);
                String pair = obj.getString("symbol");

                Double volume = obj.getDouble("volume");
                Double price = obj.getDouble("lastPrice");

                // create coin and coinpair objects to represent this set
                int len = pair.length();

                String quoteTic = pair.substring(0, len - 3);
                String baseTic = pair.substring(len - 3, len);

                // Handle case for USDT
                if (baseTic.equals("SDT")) {
                    quoteTic = pair.substring(0, len - 4);
                    baseTic = pair.substring(len - 4, len);
                }
                // System.out.println(quoteTic + "-" + baseTic);

                if (Coin.isSupported(quoteTic) && Coin.isSupported(baseTic)) {
                    // pull the correct coin objects from the map
                    Coin base = Coin.get(baseTic);
                    Coin quote = Coin.get(quoteTic);
                    assert (base != quote);
                    CoinPair coinPair = new CoinPair(quote, base, price, volume, this);
                    // add data to the list
                    marketData.put(quote, base, coinPair);
                }
            }
            System.out.println("Successfully updated: Binance");

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
