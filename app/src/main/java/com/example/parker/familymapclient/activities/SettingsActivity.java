package com.example.parker.familymapclient.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.parker.familymapclient.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import data.Filter;
import data.Settings;
import data.Singleton;
import proxy.Proxy;
import request.EventRequest;
import request.PersonRequest;
import result.EventResult;
import result.PersonResult;

public class SettingsActivity extends AppCompatActivity {

    /**
     * Logout Linear Layout to be selected
     */
    LinearLayout mLogout;
    /**
     * Resync Linear Layout to be selected
     */
    LinearLayout mResync;
    /**
     * spinner to change color of life lines
     */
    Spinner lifeStoryLinesSpinner;
    /**
     * switch to turn on life story lines
     */
    Switch lifeStoryLinesSwitch;
    /**
     * spinner to change color of family lines
     */
    Spinner familyTreeLinesSpinner;
    /**
     * switch to turn on family life lines
     */
    Switch familyTreeLinesSwitch;
    /**
     * spinner to change color of spouse lines
     */
    Spinner spouseLinesSpinner;
    /**
     * switch to turn on spouse lines
     */
    Switch spouseLinesSwitch;
    /**
     * type of map to change to satelite, hybrid, etc.
     */
    Spinner mapType;

    /**
     * instance of singelton data
     */
    Singleton singleton;
    /**
     * instance of itself to be static.
     */
    Settings settings;

    /**
     * on create method for settings activity.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        singleton = Singleton.getInstance();
        settings = Settings.getInstance();


        //HAVE TO SET ALL THE DATA FROM THE CURRENT SETTINGS
        final List<String> storyLinesSpinnerColors =  new ArrayList<>();
        storyLinesSpinnerColors.add("Red");
        storyLinesSpinnerColors.add("Green");
        storyLinesSpinnerColors.add("Blue");

        ArrayAdapter<String> linesSpinnerAdaptor = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, storyLinesSpinnerColors);


        final List<String> mapTypeOptions =  new ArrayList<>();
        mapTypeOptions.add("Normal");
        mapTypeOptions.add("Hybrid");
        mapTypeOptions.add("Satellite");
        mapTypeOptions.add("Terrain");

        ArrayAdapter<String> mapTypeSpinnerAdaptor = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, mapTypeOptions);

        lifeStoryLinesSpinner = findViewById(R.id.spinner_life_story_lines);
        lifeStoryLinesSpinner.setAdapter(linesSpinnerAdaptor);
        lifeStoryLinesSpinner.setSelection(storyLinesSpinnerColors.indexOf(settings.getLifeStoryLinesColor()));
        lifeStoryLinesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                settings.setLifeStoryLinesColor(storyLinesSpinnerColors.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        lifeStoryLinesSwitch = findViewById(R.id.switch_life_story_lines);
        lifeStoryLinesSwitch.setChecked(settings.getLifeStoryLinesOn());
        lifeStoryLinesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                settings.setLifeStoryLinesOn(b);
            }
        });

        familyTreeLinesSpinner = findViewById(R.id.spinner_family_tree_lines);
        familyTreeLinesSpinner.setAdapter(linesSpinnerAdaptor);
        familyTreeLinesSpinner.setSelection(storyLinesSpinnerColors.indexOf(settings.getFamilyTreeLinesColor()));
        familyTreeLinesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                settings.setFamilyTreeLinesColor(storyLinesSpinnerColors.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        familyTreeLinesSwitch = findViewById(R.id.switch_family_tree_lines);
        familyTreeLinesSwitch.setChecked(settings.getFamilyTreeLinesOn());
        familyTreeLinesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                settings.setFamilyTreeLinesOn(b);
            }
        });

        spouseLinesSpinner = findViewById(R.id.spinner_spouse_lines);
        spouseLinesSpinner.setAdapter(linesSpinnerAdaptor);
        spouseLinesSpinner.setSelection(storyLinesSpinnerColors.indexOf(settings.getSpouseLinesColor()));
        spouseLinesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                settings.setSpouseLinesColor(storyLinesSpinnerColors.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spouseLinesSwitch = findViewById(R.id.switch_spouse_lines);
        spouseLinesSwitch.setChecked(settings.getSpoueLinesOn());
        spouseLinesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                settings.setSpoueLinesOn(b);
            }
        });

        mapType = findViewById(R.id.spinner_map_type);
        mapType.setAdapter(mapTypeSpinnerAdaptor);
        mapType.setSelection(mapTypeOptions.indexOf(settings.getMapType()));
        mapType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                settings.setMapType(mapTypeOptions.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        mLogout = (LinearLayout)findViewById(R.id.logout_text_button);
        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goHome = new Intent(SettingsActivity.this, MainActivity.class);
                goHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                goHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(goHome);
            }
        });


        //LITERALLY NO IDEA HOW TO DO THIS.... THE ASYNC TASK ISN'T FINISHING I THINK IDK.
        mResync = (LinearLayout)findViewById(R.id.resync_text_button);
        mResync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //clear cached data
                singleton.clearCache();

                //execute async task resync
                new Resync().execute();
            }
        });
    }

    /**
     * on create options menu for settings.
     * just up button.
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

    /**
     * resync async task to restore data from server.
     */
    public class Resync extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {

            System.out.print("doInBackground entered\n");
            try
            {
                Proxy proxy = new Proxy();

                //set up person request
                PersonRequest familyRequest = new PersonRequest();
                familyRequest.setAuthID(singleton.getAuthID());

                //get family
                PersonResult familyResult = proxy.getFamily(familyRequest);

                //set up event request
                EventRequest eventRequest = new EventRequest();
                eventRequest.setAuthID(singleton.getAuthID());

                //get family events
                EventResult eventResult = proxy.getEvents(eventRequest);

                singleton.addToSingleton(familyResult);
                singleton.addToSingleton(eventResult);

                //reinitialize filter
                Filter filter = Filter.getInstance();
                filter.initialize();
                filter.filterEvents();
            }
            catch(IOException e)
            {
                System.out.print(e.getMessage());
                return false;
            }
            return true;
        }


        @Override
        protected void onPostExecute(Boolean executed)
        {
            //enableSpinner(false);
            System.out.print("onPostExecute entered\n");

            //somehow do something with the result...
            //gotta check if it passed.
            //if it did... gather data from the server.
            if(executed)
            {
                Toast.makeText(SettingsActivity.this , "Resync Successful!" , Toast.LENGTH_SHORT).show();

                //start new mapfragment activity
                Intent goHome = new Intent(SettingsActivity.this, MainActivity.class);
                goHome.putExtra("MapFragment",true);
                goHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                goHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(goHome);
            }
            else
            {
                Toast.makeText(SettingsActivity.this , "Resync Failed!" , Toast.LENGTH_SHORT).show();
            }

        }

    }

}
