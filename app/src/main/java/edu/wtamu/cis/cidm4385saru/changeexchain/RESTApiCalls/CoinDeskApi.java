package edu.wtamu.cis.cidm4385saru.changeexchain.RESTApiCalls;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Currency;

import javax.net.ssl.HttpsURLConnection;

public class CoinDeskApi{

    private static String mCurrentPriceBaseUrl = "https://api.coindesk.com/v1/bpi/currentprice/";

    private static String TAG = "CoinDeskApi";

    public String fetchCurrentPrice(String currencyCode){

        String currentPrice = "";

        try{
            String url = Uri.parse(mCurrentPriceBaseUrl + currencyCode + ".json").toString();
            String jsonString = getUrlString(url);
            JSONObject jsonBody = new JSONObject(jsonString);
            currentPrice = parseJson(jsonBody, currencyCode);
            Log.i(TAG, "Fetched current price " + currentPrice);
        }catch(IOException e){
            Log.e(TAG, "Failed to fetch current price");
        }catch(JSONException je){
            Log.e(TAG, "Failed to parse jason ", je);
        }

        return currentPrice;
    }

    public byte[] getUrlBytes(String urlSpec) throws IOException{
        URL url = new URL(urlSpec);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

        try{
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if(connection.getResponseCode() != HttpsURLConnection.HTTP_OK){
                throw new IOException(connection.getResponseMessage() + ": with " + urlSpec);
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while((bytesRead = in.read(buffer)) > 0){
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        }finally {
            connection.disconnect();
        }
    }

    public String getUrlString(String urlSpec)throws IOException{
        return new String(getUrlBytes(urlSpec));
    }

    public String parseJson(JSONObject jsonBody, String currencyCode) throws IOException, JSONException{
        JSONObject bpi = jsonBody.getJSONObject("bpi");
        JSONObject priceJson = bpi.getJSONObject(currencyCode);
        double currentPriceDouble = priceJson.getDouble("rate_float");
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        String currentPrice = formatter.format(currentPriceDouble);
        Currency curr = Currency.getInstance(currencyCode);
        String symbol = curr.getSymbol();
        return curr.getSymbol() + " " + currentPrice;

    }
}
