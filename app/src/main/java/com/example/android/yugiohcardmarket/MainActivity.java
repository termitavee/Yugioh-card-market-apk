package com.example.android.yugiohcardmarket;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.add(R.id.lord_fragment, new SearchFragment());
        fragmentTransaction.commit();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        mMenu = menu;
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        launchView(item.getItemId());

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        //Log.d("Main activity", id + " onOptionsItemSelected");

        launchView(item.getItemId());
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean launchView(int op) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (op) {
            case (R.id.nav_search):
                transaction.replace(R.id.lord_fragment, new SearchFragment());
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case (R.id.nav_list):
                transaction.replace(R.id.lord_fragment, new ListsFragment());
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case (R.id.nav_web):
                Uri uri = Uri.parse("https://es.yugiohcardmarket.eu/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

                break;

        }


        return true;
    }

/*implements
        LoaderManager.LoaderCallbacks<Cursor>
    public static void getList(String id) {
        //TODO get all cards name, id and type from a id list

    }

    public boolean setList(String id) {
        //TODO create or override a list
        try{
            //TODO check if exist
            ContentValues values = new ContentValues();
            values.put(ListEntry.COLUMN_LIST_NAME, "Toto");
            values.put(ListEntry.COLUMN_LIST_CONTENT, "Terrier");
            getContentResolver().insert(ListEntry.CONTENT_URI, values);
        }catch(Exception e){
            return false;
        }
        return true;
    }

    public static void getAllLists() {
        //TODO get all list id and name
    }

    public static void getCard() {

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Update {@link PetCursorAdapter} with this new cursor containing updated pet data
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Callback called when the data needs to be deleted
        mCursorAdapter.swapCursor(null);
    }
*/
}
