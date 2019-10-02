package com.example.parker.familymapclient.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.parker.familymapclient.R;
import com.example.parker.familymapclient.adapters.FilterListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import data.Filter;
import data.Singleton;

/**
 * Filter Activity. Allows user to filter out different events on the map fragment
 */
public class FilterActivity extends AppCompatActivity {

    /**
     * instance of singleton
     */
    Singleton singleton;
    /**
     * instance of filter
     */
    Filter filter;

    /**
     * on create. doesn't use saveinstancestate. populates filter with adapter
     * and data.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        singleton = Singleton.getInstance();
        filter = Filter.getInstance();

        List<String> listViewRows = new ArrayList<String>(singleton.getEventTypes());
        listViewRows.add("Father's Side");
        listViewRows.add("Mother's Side");
        listViewRows.add("Male Events");
        listViewRows.add("Female Events");

        //probably change the event types to a map so it can map to the current settings.. or else
        //have the settings take it from the singleton as a map. that would be fine.

        HashMap<String,Boolean> eventDetails = new HashMap<String, Boolean>(filter.getEventMapSwitches());
        eventDetails.put("Father's Side", filter.getFathersSide());
        eventDetails.put("Mother's Side", filter.getMothersSide());
        eventDetails.put("Male Events", filter.getMale());
        eventDetails.put("Female Events",filter.getFemale());


        ListView filterSwitchList = (ListView)findViewById(R.id.filter_list_view);

        FilterListAdapter filterListAdapter = new FilterListAdapter(this, listViewRows, eventDetails);
        filterSwitchList.setAdapter(filterListAdapter);


        //k now i gotta update the
        
    }


    /**
     * menu is the simple up button menu
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
     * returns to map fragment when up button clicked (with filtered events)
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        filter.filterEvents();

        Intent goHome = new Intent(this, MainActivity.class);
        goHome.putExtra("MapFragment",true);
        goHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        goHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(goHome);

        return false;
    }

}
