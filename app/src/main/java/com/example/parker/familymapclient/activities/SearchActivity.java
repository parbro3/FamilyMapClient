package com.example.parker.familymapclient.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.parker.familymapclient.R;
import com.example.parker.familymapclient.adapters.SearchListViewAdapterEvent;
import com.example.parker.familymapclient.adapters.SearchListViewAdapterPerson;

import java.util.ArrayList;

import data.Filter;
import data.Singleton;
import model.Event;
import model.Person;

public class SearchActivity extends AppCompatActivity {

    /**
     * member variable edittext search
     */
    EditText mEditTextSearch;
    /**
     * search text used throughout class
     */
    String searchText;
    /**
     * instance of filter
     */
    data.Filter mFilter;
    /**
     * instance of data singleton
     */
    Singleton mSingleton;

    /**
     * onCreate method for search activity
     * saved instance state not implemented
     * data populated into searchlistviewadapterEvent
     * and searchlistviewadapterPerson
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mFilter = Filter.getInstance();
        mSingleton = Singleton.getInstance();


        //need to search all people
        //and filtered events

        //for whatever is in the edit text
        mEditTextSearch = findViewById(R.id.search_edit_text);
        mEditTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchText = charSequence.toString().toLowerCase();

                //execute the search
                searchData(searchText);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

    }

    /**
     * searches persons and events
     * @param text
     */
    public void searchData(String text)
    {
        searchPersons();
        searchEvents();
    }

    /**
     * search persons is executed to search and populate
     * searchListViewAdapterPerson
     * updated and reflected in SearchActivity in real time
     * upon text changed.
     */
    public void searchPersons()
    {
        ListView searchPersonList = (ListView)findViewById(R.id.search_list_view_persons);

        ArrayList<Person> persons = mFilter.getEntireFamily();
        final ArrayList<Person> personSearch = new ArrayList<>();

        //if(searchText.toString().length() > 1) {
            for (Person person : persons) {
                if (person.getFirstName().toLowerCase().contains(searchText)) {
                    personSearch.add(person);
                } else if (person.getLastName().toLowerCase().contains(searchText))
                {
                    personSearch.add(person);
                }
            }
            SearchListViewAdapterPerson searchListViewAdapterPerson = new SearchListViewAdapterPerson(this, personSearch);
            searchPersonList.setAdapter(searchListViewAdapterPerson);

            searchPersonList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    Person clickedPerson = personSearch.get(i);
                    mSingleton.setSelectedPerson(clickedPerson);
                    Intent intent = new Intent(SearchActivity.this, PersonActivity.class);
                    startActivity(intent);
                }
            });
        //}

    }

    /**
     * search events is executed to search and populate
     * searchListViewAdapterEvent
     * updated and reflected in SearchActivity in real time
     * upon text changed.
     */
    public void searchEvents()
    {
        ListView searchEventList = (ListView)findViewById(R.id.search_list_view_events);

        ArrayList<Event> allEvents = mFilter.getFilteredEvents();
        final ArrayList<Event> eventsSearch = new ArrayList<>();

        //if(searchText.toString().length() > 1) {
            for (Event event : allEvents) {
                Person person = mSingleton.getPersonIDPersonMap().get(event.getPersonID());

                if (event.getCountry().toLowerCase().contains(searchText)) {
                    eventsSearch.add(event);
                } else if (event.getCity().toLowerCase().contains(searchText)) {
                    eventsSearch.add(event);
                } else if (event.getEventType().toLowerCase().contains(searchText)) {
                    eventsSearch.add(event);
                } else if (event.getYear().toLowerCase().contains(searchText)) {
                    eventsSearch.add(event);
                } else if (person.getFirstName().toLowerCase().contains(searchText)) {
                    eventsSearch.add(event);
                } else if (person.getLastName().toLowerCase().contains(searchText)) {
                    eventsSearch.add(event);
                }
            }
            SearchListViewAdapterEvent searchListViewAdapterEvent = new SearchListViewAdapterEvent(this, eventsSearch);
            searchEventList.setAdapter(searchListViewAdapterEvent);

            searchEventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    Event clickedEvent = eventsSearch.get(i);
                    mSingleton.setClickedEventFromPersonActivity(clickedEvent);
                    Intent intent = new Intent(SearchActivity.this, EventActivity.class);
                    startActivity(intent);
                }
            });
        //}
    }

    /**
     * options menu created only up button
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
     * upon up button being selected
     * returns to map fragment and clears activity stack
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
