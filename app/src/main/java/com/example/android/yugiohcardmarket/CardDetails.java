package com.example.android.yugiohcardmarket;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by termitavee on 22/01/17.
 */

public class CardDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_details);

        Bundle info = getIntent().getExtras();

        String j = (String) info.get("name");


    }


}
