package com.example.android.yugiohcardmarket;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.example.android.yugiohcardmarket.item.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by termitavee on 03/03/17.
 */

public class SearchLoader extends AsyncTaskLoader<List<Card>>{
    public SearchLoader(Context context) {
        super(context);
    }
    @Override
    public List<Card> loadInBackground() {
        List<Card> list = new ArrayList<Card>();

        //TODO hacer
        return list;
    }
}
