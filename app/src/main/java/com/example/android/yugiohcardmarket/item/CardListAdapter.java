package com.example.android.yugiohcardmarket.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.yugiohcardmarket.R;

import java.util.ArrayList;

/**
 * Created by termitavee on 24/02/17.
 */

public class CardListAdapter extends ArrayAdapter<CardList> {

    public CardListAdapter(Context context, ArrayList<CardList> lists) {
        super(context, 0, lists);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position

        View listsView = convertView;

        if (listsView == null) {
            listsView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        CardList currentList = getItem(position);

        TextView listIdView = (TextView) listsView.findViewById(R.id.listID);
        int id = currentList.getId();
        listIdView.setText(""+id);

        TextView listNameView = (TextView) listsView.findViewById(R.id.list_name);
        String name = currentList.getNombre();
        listNameView.setText(name);

        /*TextView listCountView = (TextView) listsView.findViewById(R.id.list_cards_count);
        int count = currentList.getSize();
        listCountView.setText(""+count);*/


        return listsView;
    }


}
