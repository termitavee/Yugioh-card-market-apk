package com.example.android.yugiohcardmarket;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.android.yugiohcardmarket.database.DBQuery;
import com.example.android.yugiohcardmarket.item.CardList;
import com.example.android.yugiohcardmarket.item.CardListAdapter;

import java.util.ArrayList;

/**
 * Created by termitavee on 14/01/17.
 */

public class ListsFragment extends Fragment {
    private FloatingActionButton fab;
    private CardListAdapter madapter;
    private ListView mlistView;
    private DBQuery database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lists, container, false);

    }

    public void onViewCreated(View view, Bundle savedInstanceState) {

        mlistView = ((ListView) getView().findViewById(R.id.lists));
        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {


                CardList item = (CardList) mlistView.getItemAtPosition(position);


                Intent listDetails = new Intent(getActivity(), ListContentActivity.class);
                listDetails.putExtra("listID", item.getId());
                Log.i("onItemClick", "id=" + item.getId());

                // Send the intent to launch a new activity
                startActivity(listDetails);

            }
        });
        database = new DBQuery(getActivity().getBaseContext(), getActivity());


        fab = (FloatingActionButton) getView().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create pet provider

                //input
                View popup = LayoutInflater.from(getContext()).inflate(R.layout.new_list_popup, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setView(popup);
                final EditText userInput = (EditText) popup.findViewById(R.id.popup_input);
                userInput.requestFocus();
                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result
                                // edit text
                                String name = userInput.getText().toString();

                                database.open();
                                //peticion a la base de datos, meter nueva lista
                                CardList item = database.createList(name);

                                madapter.add(item);

                                database.close();

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                alertDialogBuilder.create().show();

            }
        });

        //peticion a la base de datos y meterlos en listView

        ArrayList<CardList> lists = database.getAllLists();
        Log.i("ListsFragment", "onViewCreated");
        Log.i("onViewCreated", "lists.size()=" + lists.size());

        madapter = new CardListAdapter(getContext(), lists);
        mlistView.setAdapter(madapter);

        madapter.notifyDataSetChanged();

    }


    @Override
    public void onResume() {
        super.onResume();
        madapter.notifyDataSetChanged();
        //getActivity().getWindow().getDecorView().findViewById(android.R.id.list).invalidate();
        //madapter.notifyDataSetInvalidated();
        //mlistView.setAdapter(madapter);
    }

}
