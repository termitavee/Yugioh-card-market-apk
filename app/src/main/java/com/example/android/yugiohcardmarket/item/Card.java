package com.example.android.yugiohcardmarket.item;

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
    private double price;

    public Card() {
        id = -1;
        name = null;
        image = null;
        rarity = null;
        expansion = null;
        price = -1;
    }

    public Card(int cid, String cName, String cImage, String cRarity, String cExpansion, double cPrice) {
        id = cid;
        name = cName;
        image = cImage;
        rarity = cRarity;
        expansion = cExpansion;
        price = cPrice;
    }

    public Card(JSONObject json)throws JSONException{
        /*TODO coger
            website
            expIcon

         */
        id = json.getInt("idProduct");

        name = json.getJSONObject("name").getJSONObject("4").getString("productName");

        image = json.getString("image");

        rarity = json.getString("rarity");

        expansion= json.getString("expansion");

        price = Double.parseDouble(json.getJSONObject("priceGuide").getString("AVG"));

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

    public double getPrice() {
        return price;
    }



    /*
{
    product: {
        idProduct:                          // Product ID
        idMetaproduct:                      // Metaproduct ID
        countReprints:                      // Number of similar products bundled by the metaproduct
        name: [                             // Name entity for each supported language
        {
            idLanguage:                 // Language ID
            languageName:               // Language's name in English
            productName:                // Product's name in given language
        }
        ]
        category: {                         // Category entity the product belongs to
            idCategory:                     // Category ID
            categoryName:                   // Category's name
        }
        priceGuide: {                       // Price guide entity '''(ATTN: not returned for expansion requests)'''
            SELL:                           // Average price of articles ever sold of this product
            LOW:                            // Current lowest non-foil price (all conditions)
            LOWEX+:                         // Current lowest non-foil price (condition EX and better)
            LOWFOIL:                        // Current lowest foil price
            AVG:                            // Current average non-foil price of all available articles of this product
        }
        website:                            // URL to the product (relative to MKM's base URL)
        image:                              // Path to the product's image
        expansion:                          // Expansion's name
        expIcon:                            // Index of the expansion icon
        number:                             // Number of product within the expansion (where applicable)
        rarity:                             // Rarity of product (where applicable)
        reprint: [                          // Reprint entities for each similar product bundled by the metaproduct
        {
            idProduct:                  // Product ID
            expansion:                  // Expansion's name
            expIcon:                    // Index of the expansion icon
        }
        ]
        countArticles:                      // Number of available articles of this product
        countFoils:                         // Number of available articles in foil of this products"
    }
}
*/
}
