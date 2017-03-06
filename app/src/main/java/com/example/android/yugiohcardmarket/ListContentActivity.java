package com.example.android.yugiohcardmarket;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.yugiohcardmarket.database.DBQuery;
import com.example.android.yugiohcardmarket.item.Card;
import com.example.android.yugiohcardmarket.item.CardAdapter;

import java.util.ArrayList;

/**
 * Created by termitavee on 21/01/17.
 */

public class ListContentActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    int listID;
    private CardAdapter madapter;
    private DBQuery database;
    private ListView mlistView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("ListContentActivity", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_content);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle info = getIntent().getExtras();


        listID = (int) info.get("listID");
        database = new DBQuery(getBaseContext(), this);
        ArrayList lists = database.getAllCards(listID);

        madapter = new CardAdapter(ListContentActivity.this, lists);
        mlistView = (ListView) findViewById(R.id.list_content);
        mlistView.setAdapter(madapter);

        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Card item = madapter.getItem(position);


                Intent cardDetails = new Intent(ListContentActivity.this, CardActivity.class);

                cardDetails.putExtra("listID", listID);
                cardDetails.putExtra("cardID", item.getId());
                cardDetails.putExtra("img", item.getImage());
                cardDetails.putExtra("name", item.getName());
                cardDetails.putExtra("rarity", item.getRarity());
                cardDetails.putExtra("expansion", item.getExpansion());
                cardDetails.putExtra("priceLow", item.getPriceLow());
                cardDetails.putExtra("priceTrend", item.getPriceTrend());
                cardDetails.putExtra("web", item.getWeb());
                cardDetails.putExtra("menu", "true");

                Log.i("setOnItemClickListener", "id=" + item.getId());
                // Send the intent to launch a new activity
                startActivity(cardDetails);

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View popup = LayoutInflater.from(getBaseContext()).inflate(R.layout.remove_list_popup, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ListContentActivity.this);
                alertDialogBuilder.setView(popup);
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                new DBQuery(getBaseContext(), ListContentActivity.this).deleteList(listID);
                                Toast.makeText(ListContentActivity.this, "Lista borrada", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                alertDialogBuilder.create().show();

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
