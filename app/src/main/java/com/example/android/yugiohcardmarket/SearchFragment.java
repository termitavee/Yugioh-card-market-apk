package com.example.android.yugiohcardmarket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.yugiohcardmarket.api.APIQuery;
import com.example.android.yugiohcardmarket.item.Card;
import com.example.android.yugiohcardmarket.item.CardAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by termitavee on 16/01/17.
 */

public class SearchFragment extends Fragment {


    private List<Card> cardsFound;
    private View loadingIndicator;
    private EditText searchField;
    private CardAdapter mAdapter;
    private TextView mEmptyStateTextView;
    ListView cardsListView;
    APIQuery asyncSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        Log.d("SearchFragment", " onViewCreated");

        Button search = (Button) getView().findViewById(R.id.search_button);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchAction();

            }
        });
        searchField = ((EditText) getView().findViewById(R.id.search_field));

        loadingIndicator = getView().findViewById(R.id.loading_indicator);
        hideLoading();
        getView().findViewById(R.id.loading_indicator).setVisibility(View.GONE);

        mAdapter = new CardAdapter(getContext(), new ArrayList<Card>());
        mEmptyStateTextView = (TextView) getView().findViewById(R.id.empty_view);
        cardsFound = new ArrayList<>();

        searchField.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                        (i == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    searchAction();
                    return true;
                }
                return false;
            }
        });
        cardsListView = (ListView) getActivity().findViewById(R.id.search_list);
        cardsListView.setAdapter(mAdapter);

        cardsListView.setEmptyView(mEmptyStateTextView);


        cardsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Somehow Find the current earthquake that was clicked on
                Card currentCard = cardsFound.get(position);

                // Create a new intent to view the earthquake URI
                Intent cardDetails = new Intent(getActivity(), CardDetails.class);

                cardDetails.putExtra("cardID", currentCard.getId());
                cardDetails.putExtra("img", currentCard.getImage());
                cardDetails.putExtra("name", currentCard.getName());
                cardDetails.putExtra("rarity", currentCard.getRarity());
                cardDetails.putExtra("expansion", currentCard.getExpansion());
                cardDetails.putExtra("priceLow", currentCard.getPriceLow());
                cardDetails.putExtra("priceTrend", currentCard.getPriceTrend());
                cardDetails.putExtra("web", currentCard.getWeb());

                Log.i("setOnItemClickListener", "id=" + currentCard.getId());
                // Send the intent to launch a new activity
                mAdapter.clear();
                startActivity(cardDetails);

            }
        });
    }

    public void refreshList(JSONObject mData) {

        //manejar datos y listarlos
        cardsFound.clear();
        mAdapter.clear();
        try {
            hideLoading();
            // Extract the JSONArray associated with the key called "features",
            // which represents a list of features (or earthquakes).
            JSONArray cardsArray = mData.getJSONArray("product");
            for (int i = 0; i < cardsArray.length(); i++) {

                cardsFound.add(new Card(cardsArray.getJSONObject(i)));
            }
            loadingIndicator.setVisibility(View.GONE);


        } catch (JSONException e) {
            Log.e("refreshList","Error parsing json "+e.getMessage());
        }
        mAdapter.addAll(cardsFound);
        mAdapter.notifyDataSetChanged();
        if (mAdapter.isEmpty())
            Toast.makeText(getActivity(), "No se ha encontrado nada", Toast.LENGTH_SHORT).show();
        Log.i("refreshList", "cardsFound.size()=" + cardsFound.size());
        hideLoading();


    }


    public void searchAction() {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        String text = searchField.getText().toString();
        Log.i("searchAction", "texto \"" + text + "\"");
        if (!text.equals("")) {
            asyncSearch = new APIQuery(this, asyncSearch.BUSCAR);
            showLoading();
            asyncSearch.execute(text);
        }

    }

    public void hideLoading() {
        //loadingIndicator.setVisibility(View.VISIBLE);
        getView().findViewById(R.id.loading_indicator).setVisibility(View.GONE);
    }

    public void showLoading() {
        //loadingIndicator.setVisibility(View.GONE);
        getView().findViewById(R.id.loading_indicator).setVisibility(View.VISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (cardsFound.size() > 0)
            mAdapter.addAll(cardsFound);
    }
}
