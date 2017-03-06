package com.example.android.yugiohcardmarket;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.android.yugiohcardmarket.api.APIQuery2;
import com.example.android.yugiohcardmarket.item.Card;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by termitavee on 03/03/17.
 */

public class SearchLoader extends AsyncTaskLoader<List<Card>>{
    protected List<Card> mLastDataList = null;
    private String text;
    private APIQuery2 asyncSearch;
    private Context context;
    private SearchFragment parent;

    public SearchLoader(Context context, Bundle bundle, SearchFragment parent) {
        super(context);
        if(bundle!=null)
        this.text=bundle.getString("texto","");
        else
            this.text="";
        this.parent=parent;
        this.context = context;
    }

    @Override
    public List<Card> loadInBackground() {
        JSONObject json;
        Log.i("SearchLoader","loadInBackground, text="+text);
        //TODO creo que aquí llama a la api

        if (!text.equals("")) {
            asyncSearch = new APIQuery2(context, asyncSearch.BUSCAR);
            //parent.showLoading();
            json= asyncSearch.doInBackground(text);

        }
        else
            json = new JSONObject();
        return jsonToList(json);
    }



    @Override
    protected void onStopLoading() {
        // Attempt to cancel the current load task if possible.
        cancelLoad();
    }

    @Override
    protected void onReset() {

        super.onReset();

        // Ensure the loader is stopped

        onStopLoading();

        if (mLastDataList != null && mLastDataList.size() > 0) {

            mLastDataList.clear();

        }

        mLastDataList = null;

    }

    private List<Card> jsonToList(JSONObject json) {
        List<Card> cardsFound = new ArrayList<>();
        try {
            JSONArray cardsArray = json.getJSONArray("product");
            for (int i = 0; i < cardsArray.length(); i++) {
                try {
                cardsFound.add(new Card(cardsArray.getJSONObject(i)));
                }catch(Exception e){}
            }
        }catch(Exception e){}

        Log.i("SearchLoader","loadInBackground, finished");

        Log.i("SearchLoader","loadInBackground, found "+cardsFound.size()+" Cards");
        return cardsFound;
    }


}
