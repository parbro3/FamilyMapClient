package com.example.parker.familymapclient.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.parker.familymapclient.fragments.MapFragment;
import com.example.parker.familymapclient.R;

/**
 * Event Activity. Zooms in on a location when an event is selected.
 * Uses the map fragment.
 */
public class EventActivity extends AppCompatActivity {

    /**
     * instance of the fragment manager
     */
    private FragmentManager mFragmentManager;

    /**
     * Doesn't use save instance state. creates the view.
     * Creates the map fragment and adds it to the fragManager.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        mFragmentManager = this.getSupportFragmentManager();
        Fragment mapFragment = mFragmentManager.findFragmentById(R.id.fragment_container_event);

        //pass the event intent into the fragment
        //because the menus are different
        if (mapFragment == null)
        {
            mapFragment = new MapFragment();
            Bundle bundle = new Bundle();
            bundle.putBoolean("EventMenu",true);
            mapFragment.setArguments(bundle);
            mFragmentManager.beginTransaction().add(R.id.fragment_container_event, mapFragment).commit();
        }

    }

    /**
     * Creates the menu. Uses the smaller menu "menu_event_activity"
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_event_activity, menu);
        return true;
    }

    /**
     * Upon selection of the up button, returns to the map fragment and restarts the stack.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        Intent goHome = new Intent(this, MainActivity.class);
        goHome.putExtra("MapFragment",true);
        goHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        goHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(goHome);

        return false;
    }

}

/*
//this is how to get back to the main activity and kill the stack of activities

Intent goHome = new Intent(getContext(), HomeActivity.class);
goHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
goHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
startActivity(goHome);
 */

/*
getSupportActionBar().show();
 */