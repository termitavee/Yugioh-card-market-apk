package com.example.android.yugiohcardmarket.item;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.yugiohcardmarket.R;

import java.util.List;


/**
 * Created by termitavee on 20/02/17.
 */

public class CardAdapter extends ArrayAdapter<Card> {
    public CardAdapter(Context context, List<Card> cards) {
        super(context, 0, cards);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.card_item, parent, false);
        }

        Card currentCard = getItem(position);

        TextView idView = (TextView) listItemView.findViewById(R.id.cardID);
        String id = String.valueOf(currentCard.getId());
        idView.setText(id);
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image);


        Drawable drawable = getContext().getResources().getDrawable(R.drawable.undefined_card);
        imageView.setBackground(drawable);

        TextView cardNameView = (TextView) listItemView.findViewById(R.id.card_name);
        String name = currentCard.getName();
        cardNameView.setText(name);

        TextView expansionView = (TextView) listItemView.findViewById(R.id.expansion_name);
        String expansion = currentCard.getExpansion();
        expansionView.setText(expansion);

        TextView rarezaView = (TextView) listItemView.findViewById(R.id.rareza);
        String rareza = currentCard.getRarity();
        rarezaView.setText(rareza);

        TextView priceLowView = (TextView) listItemView.findViewById(R.id.price);
        String priceLow = String.valueOf(currentCard.getPriceLow())+"€";
        priceLowView.setText(priceLow);



        return listItemView;
    }



}
