package com.example.parker.familymapclient.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.parker.familymapclient.R;
import com.example.parker.familymapclient.adapters.ExpandableListViewAdapterEvents;
import com.example.parker.familymapclient.adapters.ExpandableListViewAdapterPersons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import data.Singleton;
import model.Event;
import model.Person;

public class PersonActivity extends AppCompatActivity {

    /**
     * singleton instance
     */
    Singleton mSingleton;

    /**
     * member first name text view
     */
    TextView mFirstNameTV;
    /**
     * member last name text view
     */
    TextView mLastNameTV;
    /**
     * member gender texst view
     */
    TextView mGenderTV;

    /**
     * expandable list adapter member variable for events
     */
    ExpandableListViewAdapterEvents listAdapterEvents;
    /**
     * expandable list adapter for persons
     */
    ExpandableListViewAdapterPersons listAdapterFamily;
    /**
     * expandable list view actual view for events
     */
    ExpandableListView listViewEvents;
    /**
     * expandable list view actual view for persons
     */
    ExpandableListView listViewFamily;

    /**
     * on create method for person activity.
     * saved instance state never implemented.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        //get singleton
        mSingleton = Singleton.getInstance();

        //POPULATE PERSON ACTIVITY UPPER INFORMATION
        Person currentPerson = mSingleton.getSelectedPerson();
        mFirstNameTV = (TextView)findViewById(R.id.person_activity_first_name);
        mFirstNameTV.setText(currentPerson.getFirstName());
        mLastNameTV = (TextView)findViewById(R.id.person_activity_last_name);
        mLastNameTV.setText(currentPerson.getLastName());
        mGenderTV = (TextView)findViewById(R.id.person_activity_gender);
        mGenderTV.setText(currentPerson.getGender());



        //POPULATE EVENTS DROP DOWN
        listViewEvents = (ExpandableListView) findViewById(R.id.person_activity_events_list_view);
        List<String> listHeadersEvents;
        final HashMap<String, List<Event>> listChildrenEvents;
        listHeadersEvents = new ArrayList();
        listChildrenEvents = new HashMap<String, List<Event>>();
        populateListParametersEvents(listHeadersEvents, listChildrenEvents);

        listAdapterEvents = new ExpandableListViewAdapterEvents(this , listHeadersEvents, listChildrenEvents);
        listViewEvents.setAdapter(listAdapterEvents);
        listViewEvents.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {

                Event clickedEvent = listChildrenEvents.get("Life Events").get(i1);
                mSingleton.setClickedEventFromPersonActivity(clickedEvent);
                Intent intent = new Intent(PersonActivity.this, EventActivity.class);
                startActivity(intent);

                return false;
            }
        });



        //POPULATE FAMILY DROP DOWN
        listViewFamily = (ExpandableListView) findViewById(R.id.person_activity_family_list_view);
        List<String> listHeaderFamily;
        final HashMap<String, List<Person>> listChildrenFamily;
        listHeaderFamily = new ArrayList();
        listChildrenFamily = new HashMap<String, List<Person>>();
        populateListParametersFamily(listHeaderFamily, listChildrenFamily);

        listAdapterFamily = new ExpandableListViewAdapterPersons(this , listHeaderFamily, listChildrenFamily);
        listViewFamily.setAdapter(listAdapterFamily);



        //set expandable list onclick listeners
        listViewFamily.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {

                //get the clicked child...
                //i is going to be 0 for both
                //i1 is the child so get that from the map.
                Person clickedChild = listChildrenFamily.get("Family").get(i1);

                //start a new person activity
                //activate the person activity.
                //set the new selected person
                mSingleton.setSelectedPerson(clickedChild);
                Intent intent = new Intent(PersonActivity.this, PersonActivity.class);
                startActivity(intent);

                return false;
            }
        });

    }

    /**
     * populates the parameters from the person activity data
     * into the expandable list view input parameters
     * FOR EVENTS
     * @param listHeaders
     * @param listChildren
     */
    private void populateListParametersEvents(List<String> listHeaders, HashMap<String, List<Event>> listChildren){

        listHeaders.add("Life Events");
        //get events pertaining to the selected person
        ArrayList<Event> list1 = mSingleton.getEventsFromSelectedPerson();
        list1 = mSingleton.orderEventsByDate(list1);

        listChildren.put("Life Events", list1);

    }

    /**
     * * populates the parameters from the person activity data
     * into the expandable list view input parameters
     * FOR FAMILY
     * @param listHeaders
     * @param listChildren
     */
    private void populateListParametersFamily(List<String> listHeaders, HashMap<String, List<Person>> listChildren){

        listHeaders.add("Family");

        //get events pertaining to the selected person
        ArrayList<Person> list1 = mSingleton.getFamilyFromSelectedPerson();

        listChildren.put("Family", list1);

    }

    /**
     * creates options menu..
     * short menu for person activity
     * with just up button
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
     * return to map fragment and clear stack.
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
