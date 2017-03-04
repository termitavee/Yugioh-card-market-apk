package com.example.android.yugiohcardmarket.item;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by termitavee on 21/01/17.
 */

public class Card {

    private int id;
    private String name;
    private String image;
    private String rarity;
    private String expansion;
    private double priceLow;
    private double priceTrend;
    private String web;

    public Card(int cid, String cName, String cImage, String cRarity, String cExpansion, double cPriceLow, double cpriceTrend, String cweb) {
        id = cid;
        name = cName;
        image = cImage;
        rarity = cRarity;
        expansion = cExpansion;
        priceLow = cPriceLow;
        priceTrend = cpriceTrend;
        web = cweb;

    }

    public Card(JSONObject json) throws JSONException {
        Log.i("Card", "json=" + json);
        id = json.getInt("idProduct");

        name = json.getJSONObject("name").getJSONObject("4").getString("productName");

        image = json.getString("image");

        rarity = json.getString("rarity");

        expansion = json.getString("expansion");

        priceLow = Double.parseDouble(json.getJSONObject("priceGuide").getString("LOWEX"));

        priceTrend = Double.parseDouble(json.getJSONObject("priceGuide").getString("TREND"));

        web = json.getString("website");
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getRarity() {
        return rarity;
    }

    public String getExpansion() {
        return expansion;
    }

    public double getPriceLow() {
        return priceLow;
    }

    public double getPriceTrend() {
        return priceTrend;
    }

    public String getWeb() {
        return web;
    }



    /*
{
  "idProduct": 255812,
  "idMetaproduct": 104382,
  "idGame": 3,
  "countReprints": "3",
  "name": {
    "1": {
      "idLanguage": 1,
      "languageName": "English",
      "productName": "Ultimate Ancient Gear Golem"
    },
    "2": {
      "idLanguage": 2,
      "languageName": "French",
      "productName": "Golem Rouages Ancients Ultime"
    },
    "3": {
      "idLanguage": 3,
      "languageName": "German",
      "productName": "Ultimativer Antiker Antriebsgolem"
    },
    "4": {
      "idLanguage": 4,
      "languageName": "Spanish",
      "productName": "Golem de Mecanismo Antiguo Definitivo"
    },
    "5": {
      "idLanguage": 5,
      "languageName": "Italian",
      "productName": "Golem-Ingranaggio Antico Finale"
    }
  },
  "website": "\\/Products\\/Singles\\/Duel+Terminal+3\\/Ultimate+Ancient+Gear+Golem",
  "image": ".\\/img\\/items\\/5\\/DT03\\/ultimate_ancient_gear_golem.jpg",
  "category": {
    "idCategory": 5,
    "categoryName": "Yugioh Single"
  },
  "priceGuide": {
    "SELL": 8.95,
    "LOW": 10.99,
    "LOWEX": 10.99,
    "LOWFOIL": 0,
    "AVG": 11.47,
    "TREND": 10.31
  },
  "expansion": "Duel Terminal 3",
  "expIcon": 216,
  "number": "033",
  "rarity": "Common"
}

*/
}
