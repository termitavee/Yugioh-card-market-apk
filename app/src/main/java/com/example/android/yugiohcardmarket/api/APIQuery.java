package com.example.android.yugiohcardmarket.api;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;

/**
 * Created by termitavee on 06/03/17.
 */

public class APIQuery {

    private String apiURL;
    private JSONObject mData;
    private Context mParent;
    private boolean mbuscar;

    public static boolean BUSCAR = true;
    public static boolean MIRAR = false;

    public APIQuery(Context parent, boolean buscar) {
        mData = new JSONObject();
        mParent = parent;
        mbuscar = buscar;
    }

    //utilities for the class

    public void prepareUrl(String stringUrl) {

        String dfUrl = "https://www.mkmapi.eu/ws/v1.1/output.json";
        try {
            if (mbuscar)
                apiURL = dfUrl + "/products/" + rawurlencode(stringUrl) + "/3/4/false";
            else
                apiURL = dfUrl + "/product/" + rawurlencode(stringUrl);
        } catch (Exception e) {
        }
    }

    public String prepareAuth() {

        try {
            //prepara datos
            String realm = apiURL;
            String oauth_version = "1.0";
            String oauth_signature_method = APIData.signatureMethod;
            String oauth_timestamp = "" + System.currentTimeMillis() / 1000;
            String oauth_consumer_key = APIData.appToken;
            String oauth_nonce = "" + System.currentTimeMillis();
            String oauth_token = "";


            //crea param
            String encodedRequestURL = rawurlencode(apiURL);
            String baseString = "GET&" + encodedRequestURL + "&";

            String paramString = "oauth_consumer_key=" + rawurlencode(oauth_consumer_key) + "&" +
                    "oauth_nonce=" + rawurlencode(oauth_nonce) + "&" +
                    "oauth_signature_method=" + rawurlencode(oauth_signature_method) + "&" +
                    "oauth_timestamp=" + rawurlencode(oauth_timestamp) + "&" +
                    "oauth_token=" + rawurlencode(oauth_token) + "&" +
                    "oauth_version=" + rawurlencode(oauth_version);

            baseString = baseString + rawurlencode(paramString);

            //encripta
            String signingKey = rawurlencode(APIData.appSecret) +
                    "&" + rawurlencode(APIData.accessTokenSecret);
            Mac mac = Mac.getInstance("HmacSHA1");
            SecretKeySpec secret = new SecretKeySpec(signingKey.getBytes(), mac.getAlgorithm());
            mac.init(secret);
            byte[] digest = mac.doFinal(baseString.getBytes());
            //String oauth_signature = DatatypeConverter.printBase64Binary(digest);    //Base64.encode(digest) ;
            String oauth_signature = Base64.encodeToString(digest, Base64.NO_WRAP);

            String authorizationProperty = "OAuth " +
                    "realm=\"" + realm + "\", " +
                    "oauth_version=\"" + oauth_version + "\", " +
                    "oauth_timestamp=\"" + oauth_timestamp + "\", " +
                    "oauth_nonce=\"" + oauth_nonce + "\", " +
                    "oauth_consumer_key=\"" + oauth_consumer_key + "\", " +
                    "oauth_token=\"" + oauth_token + "\", " +
                    "oauth_signature_method=\"" + oauth_signature_method + "\", " +
                    "oauth_signature=\"" + oauth_signature + "\"";

            return authorizationProperty;


        } catch (Exception e) {
            Log.e("prepareAuth", "Error haciendo la autorizacion" + e.getMessage());
        }
        return "";
    }


    public static JSONObject readFromStream(InputStream inputStream) throws IOException, JSONException {

        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return new JSONObject(output.toString());
    }

    private String rawurlencode(String str) throws UnsupportedEncodingException {
        return URLEncoder.encode(str, "UTF-8");
    }

    public JSONObject doInBackground(String... urls) {
        // Don't perform the request if there are no URLs, or the first URL is null.

        if (mbuscar) {
            if (urls.length < 1 || urls[0] == null || urls[0].equals("")) {
                return null;
            } else {
                prepareUrl(urls[0]);
            }
        } else {
            prepareUrl(urls[0]);
        }

        String authorizationProperty;
        JSONObject response = null;

        try {

            URL url = new URL(apiURL);


            HttpsURLConnection urlConnection = null;
            InputStream inputStream;

            //prepare and make connection

            urlConnection = (HttpsURLConnection) url.openConnection();
            authorizationProperty = prepareAuth();
            urlConnection.addRequestProperty("Authorization", authorizationProperty);
            urlConnection.setRequestMethod("GET");


            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {

                inputStream = urlConnection.getInputStream();
                response = APIQuery.readFromStream(inputStream);
            } else {
                Log.e("Connection", "Error " + urlConnection.getResponseCode() + ": " + urlConnection.getResponseMessage());
            }


        } catch (IOException ex) {
        } catch (JSONException ex) {
        } catch (Exception ex) {
            Log.e("Connection", "error: que no es io ni json");
            Log.e("Mensaje", "" + ex.getMessage());
            ex.printStackTrace();
        }
        return response;
    }

}
