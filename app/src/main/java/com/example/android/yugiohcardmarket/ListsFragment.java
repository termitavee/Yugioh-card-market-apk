package com.example.android.yugiohcardmarket;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.android.yugiohcardmarket.database.DBContentProvider;
import com.example.android.yugiohcardmarket.database.DBQuery;
import com.example.android.yugiohcardmarket.item.CardList;

import java.util.List;

/**
 * Created by termitavee on 14/01/17.
 */

public class ListsFragment extends Fragment {
    private FloatingActionButton fab;
    private ListView mlistView;
    private DBQuery database;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lists, container, false);

    }
    public void onViewCreated(View view, Bundle savedInstanceState) {

        mlistView = ((ListView) getView().findViewById(R.id.lists));
        database = new DBQuery(getActivity().getBaseContext(),getActivity());




        FloatingActionButton fab = (FloatingActionButton) getView().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create pet provider
                database.open();
                //TODO peticion a la base de datos, meter nueva lista

                //TODO añadir a listView


                database.close();
            }
        });

        //TODO peticion a la base de datos y meterlos en listView
        Uri uri = Uri.parse(DBContentProvider.CONTENT_LISTS_TYPE);
        getActivity().getContentResolver();


        database.open();
        List<CardList> lists = database.getLists();

        ArrayAdapter<CardList> adapter = new ArrayAdapter<CardList>(
                getActivity().getApplicationContext(),
                android.R.layout.simple_list_item_1, lists);
        mlistView.setAdapter(adapter);
        database.close();
    }

    //@Override
    public void onListItemClick(ListView l, View v, int position, long id){
        //TODO abrir list content
    }




    private void detailsList(int id){
        //TODO no recuerdo que
    }


}
