package com.example.android.yugiohcardmarket.item;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.yugiohcardmarket.R;
import com.example.android.yugiohcardmarket.database.DBQuery;

import java.util.ArrayList;

import static android.R.attr.id;
import static android.widget.AbsListView.CHOICE_MODE_MULTIPLE;

/**
 * Created by termitavee on 22/01/17.
 */

public class CardDetails extends AppCompatActivity {
    int CardId;
    String image;
    String name;
    String rarity;
    String expansion;
    double price;
    DBQuery database;
    ListView popUpListView;
    int[] selectedItems;
    boolean hasMenu;
    private Menu mMenu;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_details);

        Bundle info = getIntent().getExtras();

        CardId = (int) info.get("cardID");
        image = (String) info.get("img");
        name = (String) info.get("name");
        rarity = (String) info.get("rarity");
        expansion = (String) info.get("expansion");
        price = (double) info.get("price");
        try {
            String a = (String) info.get("menu");
            if (a.equals("true"))
                hasMenu = true;
            else
                hasMenu = false;
        } catch (Exception e) {
            hasMenu = false;
        }

        Log.i("CardDetails", "creating id=" + id);

// poner las cosas en su sitio

        imageView = (ImageView) findViewById(R.id.image);

        new ImageGetter(imageView).execute(image);

        TextView cardNameView = (TextView) findViewById(R.id.card_name);
        cardNameView.setText(name);

        TextView expansionView = (TextView) findViewById(R.id.expansion_name);

        expansionView.setText(expansion);

        TextView rarezaView = (TextView) findViewById(R.id.rareza);
        rarezaView.setText(rarity);

        TextView priceView = (TextView) findViewById(R.id.price);

        priceView.setText(String.valueOf(price) + "€");

        database = new DBQuery(getBaseContext(), this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (hasMenu) {
            //TODO change icon  to ic_remove and add listener

            //setBackground(Drawable);
            fab.hide();
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //ask confirmation

                    //delete from relation
                }
            });
        } else {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //ventana emergente para añadir a una de las listas existentes

                    ArrayList<CardList> lists = database.getAllLists();
                    selectedItems = new int[lists.size()];
                    CardListAdapter mListAdapter = new CardListAdapter(getBaseContext(), lists);

                    View popup = LayoutInflater.from(getBaseContext()).inflate(R.layout.add_card_popup, null);

                    //TODO a popup poner List<CardList> lists en TextView lists
                    popUpListView = ((ListView) popup.findViewById(R.id.existing_lists));
                    popUpListView.setAdapter(mListAdapter);
                    popUpListView.setChoiceMode(CHOICE_MODE_MULTIPLE);
                    popUpListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            if (selectedItems[position] == 0) {

                                selectedItems[position] = ((CardList) popUpListView.getItemAtPosition(position)).getId();
                                view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                            } else {
                                selectedItems[position] = 0;
                                view.setBackground(null);
                            }

                            Log.i("CardDetails", "selectedItems " + ((TextView) view.findViewById(R.id.listID)).getText());

                            String a = "";
                            for (int i = 0; i < selectedItems.length; i++) {
                                a += selectedItems[i] + ",";
                            }

                            //selectedItems[position] = 1;

                        }
                    });

                    launchPopup(popup);

                }
            });
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        mMenu = menu;
        if (hasMenu)
            getMenuInflater().inflate(R.menu.card_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.action_del:
                //TODO borrar la carta de esta lsita
                break;
            case R.id.action_add:
                View popup = LayoutInflater.from(getBaseContext()).inflate(R.layout.add_card_popup, null);
                launchPopup(popup);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void launchPopup(View popup) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CardDetails.this);
        alertDialogBuilder.setView(popup);


        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        //Crear objeto card para meterlo en la base de datos
                        Card card = new Card(CardId, name, image, rarity, expansion, price);

                        database.open();

                        for (int i = 0; i < selectedItems.length; i++) {
                            //TODO get id from item
                            if (selectedItems[i] != 0)
                                if (database.insertCard(card, selectedItems[i]))
                                    Toast.makeText(getApplicationContext(), "Carta añadida", Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(getApplicationContext(), "No se ha podido añadir", Toast.LENGTH_SHORT).show();

                        }

                        database.close();

                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        alertDialogBuilder.create().show();
    }

}
