package com.example.android.yugiohcardmarket;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.yugiohcardmarket.database.DBQuery;
import com.example.android.yugiohcardmarket.item.Card;
import com.example.android.yugiohcardmarket.item.CardAdapter;
import com.example.android.yugiohcardmarket.item.CardDetails;

import java.util.ArrayList;

/**
 * Created by termitavee on 21/01/17.
 */

public class ListContentActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    int listID;
    private CardAdapter madapter;
    private DBQuery database;
    private ListView mlistView;

    private Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_content);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle info = getIntent().getExtras();


        listID = (int) info.get("listID");
        database = new DBQuery(getBaseContext(), this);
        ArrayList lists = database.getAllCards(listID);

        madapter = new CardAdapter(ListContentActivity.this, lists);
        mlistView = (ListView) findViewById(R.id.list_content);
        if (lists != null) {
            mlistView.setAdapter(madapter);
            mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                     @Override
                     public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                         Card item = madapter.getItem(position);



                         Intent cardDetails = new Intent(getParent(), CardDetails.class);

                         cardDetails.putExtra("cardID", item.getId());
                         cardDetails.putExtra("img", item.getImage());
                         cardDetails.putExtra("name", item.getName());
                         cardDetails.putExtra("rarity", item.getRarity());
                         cardDetails.putExtra("expansion", item.getExpansion());
                         cardDetails.putExtra("price", item.getPrice());
                         cardDetails.putExtra("menu", "true");

                         Log.i("setOnItemClickListener", "id=" + item.getId());
                         // Send the intent to launch a new activity
                         startActivity(cardDetails);

                     }
                 }
            );
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        mMenu = menu;
        getMenuInflater().inflate(R.menu.list_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //switch
        if(item.getItemId()==R.id.action_del) {
            new DBQuery(getBaseContext(), this).deleteList(listID);

            finish();
//TODO notify list fragment to update
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        //Log.d("Main activity", id + " onOptionsItemSelected");

        //launchView(item.getItemId());
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
