package com.example.android.yugiohcardmarket;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by termitavee on 14/01/17.
 */

public class ForbiddenFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_forbidden, container, false);
        //cuando se va a crear, coger lista de listas (nombre+id)


    }
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //Cuando ya está

    }

    //@Override
    public void onListItemClick(ListView l, View v, int position, long id){

    }




    private void detailsList(int id){

    }


}

