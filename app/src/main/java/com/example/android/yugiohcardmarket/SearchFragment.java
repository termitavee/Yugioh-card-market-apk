package com.example.android.yugiohcardmarket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.yugiohcardmarket.api.APIQuery;
import com.example.android.yugiohcardmarket.item.Card;
import com.example.android.yugiohcardmarket.item.CardAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.provider.AlarmClock.EXTRA_MESSAGE;
import static com.example.android.yugiohcardmarket.api.APIQuery.BUSCAR;


/**
 * Created by termitavee on 16/01/17.
 */

public class SearchFragment extends Fragment {


    private List<Card> cardsFound;
    private View loadingIndicator;
    private EditText searchField;
    private CardAdapter mAdapter;
    private TextView mEmptyStateTextView;
    ListView cardsListView ;
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

        loadingIndicator =  getView().findViewById(R.id.loading_indicator);
        hideLoading();
        getView().findViewById(R.id.loading_indicator).setVisibility(View.GONE);

        mAdapter = new CardAdapter(getContext(), new ArrayList<Card>());

        cardsFound = new ArrayList<>();

        cardsListView = (ListView) getActivity().findViewById(R.id.search_list);
        cardsListView.setAdapter(mAdapter);
        //earthquakeListView.setEmptyView(TextView);



        mEmptyStateTextView = (TextView) getView().findViewById(R.id.empty_view);

        cardsListView.setEmptyView(mEmptyStateTextView);



        cardsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Somehow Find the current earthquake that was clicked on
                Card currentCard= cardsFound.get(position);

                // Create a new intent to view the earthquake URI
                Intent cardDetails = new Intent(getActivity(), CardDetails.class);
                cardDetails.putExtra(EXTRA_MESSAGE, "");

                // Send the intent to launch a new activity
                startActivity(cardDetails);

            }
        });
    }

    public void refreshList(JSONObject mData) {

        //TODO manejar datos y listarlos
        cardsFound.clear();
        mAdapter.clear();
        try {
            hideLoading();
            // Extract the JSONArray associated with the key called "features",
            // which represents a list of features (or earthquakes).
            JSONArray cardsArray = mData.getJSONArray("product");
            Log.i("refreshList","cardsArray="+cardsArray);
            for (int i = 0; i < cardsArray.length(); i++) {

                cardsFound.add(new Card(cardsArray.getJSONObject(i)));
            }
            loadingIndicator.setVisibility(View.GONE);


        }catch (JSONException e) {
            Log.e("refreshList", "Problem parsing the JSON results");
            Log.e("refreshList", e.getMessage());
        }catch (Exception e) {
            // TODO poner ventana de no se encuantra nada
            Log.e("refreshList", "Problem");
            Log.e("refreshList", e.getMessage());
        }
        mAdapter.addAll(cardsFound);
        mAdapter.notifyDataSetChanged();
        Log.i("refreshList","cardsFound.size()="+cardsFound.size());
        hideLoading();


    }


    public void searchAction() {
        //todo get text and search in the API
        String text = searchField.getText().toString();
        Log.i("searchAction", "texto \"" + text + "\"");
        asyncSearch = new APIQuery(this,asyncSearch.BUSCAR);
        showLoading();
        asyncSearch.execute(text);


    }
    public void hideLoading(){
        //loadingIndicator.setVisibility(View.VISIBLE);
        getView().findViewById(R.id.loading_indicator).setVisibility(View.GONE);
    }

    public void showLoading(){
        //loadingIndicator.setVisibility(View.GONE);
        getView().findViewById(R.id.loading_indicator).setVisibility(View.VISIBLE);
    }


}
