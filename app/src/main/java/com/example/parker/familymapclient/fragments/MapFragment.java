package com.example.parker.familymapclient.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.parker.familymapclient.activities.PersonActivity;
import com.example.parker.familymapclient.R;
import com.google.android.gms.maps.model.PolylineOptions;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import data.Filter;
import data.Settings;
import data.Singleton;
import model.Event;
import model.Person;


public class MapFragment extends Fragment {


    /**
     * member private view
     */
    private View view;

    /**
     * member map view
     */
    MapView mMapView;
    /**
     * member map to be accessed within class
     */
    private GoogleMap mMap;
    /**
     * member singelton instance
     */
    Singleton mSingleton;
    /**
     * member filter instance to be accessed within class
     */
    Filter mFilter;

    /**
     * member settings instance to be accessed within class
     */
    Settings mSettings;

    //now gotta update the mapviewfragment
    /**
     * member text view for name event
     */
    TextView mNameEventTV;
    /**
     * member text view for event info
     */
    TextView mInfoEventTV;
    /**
     * member imagive view
     */
    ImageView mImageView;

    /**
     * map to map markers to their events
     */
    private HashMap <Marker, Event> markerEventMap = new HashMap();

    //lines between events
    /**
     * poly lines for life and family and spouse lines
     */
    private List<Polyline> lifeLines;
    private List<Polyline> famLines;
    private List<Polyline> spouseLines;


    /**
     * on create method for map fragment
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    /**
     * on create view for map fragment.
     * draws polylines based on settings
     * markers placed based on filter
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //initialize polylines
        lifeLines = new ArrayList<>();
        famLines = new ArrayList<>();
        spouseLines = new ArrayList<>();

        //set singleton
        mSingleton = Singleton.getInstance();
        mSettings = Settings.getInstance();
        mFilter = Filter.getInstance();
        //mFilter.initialize();
        mFilter.filterEvents();

        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_map, container, false);

        mMapView = (MapView) view.findViewById(R.id.mapview);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }


        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                setMapType();


                System.out.print("\n\nI made it to OnMapReadyCallback!! \n\n");

                //ArrayList<Event> events = new ArrayList<Event>(mSingleton.getEventIDEventMap().values());
                addMarkers(mFilter.getFilteredEvents());


                //now gotta update the mapviewfragment
                mNameEventTV = (TextView)view.findViewById(R.id.mapBottomEventName);
                mInfoEventTV = (TextView)view.findViewById(R.id.mapBottomEventInfo);
                mImageView = (ImageView)view.findViewById(R.id.image_view_map_fragment_event);

                //on click listener for marker
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {

                        //get the event corresponding to the marker
                        Event markerEvent = markerEventMap.get(marker);

                        //get the person associated with the event
                        Person person = mSingleton.getPersonIDPersonMap().get(markerEvent.getPersonID());

                        mSingleton.setSelectedPerson(person);

                        //need to get the person from the eventID

                        String setTextName = person.getFirstName() + " " + person.getLastName();
                        mNameEventTV.setText(setTextName);
                        String setTextInfo = markerEvent.getEventType() + " " + markerEvent.getCity() + ", " + markerEvent.getCountry();
                        if(markerEvent.getYear() != null)
                            setTextInfo = setTextInfo + " | " + markerEvent.getYear();
                        mInfoEventTV.setText(setTextInfo);

                        //Drawable genderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_male).sizeDp(40);
                        //mImageView.setImageDrawable(genderIcon);
                        if(person.getGender().equals(("m")))
                        {
                            mImageView.setImageResource(R.drawable.human_male);
                        }
                        else
                        {
                            mImageView.setImageResource(R.drawable.human_female);
                        }

                        drawLines(markerEvent);

                        return false;
                    }
                });

                mNameEventTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //activate the person activity.
                        Intent intent = new Intent( getActivity() , PersonActivity.class);
                        startActivity(intent);

                    }
                });

                //move over to the correct event... and then set the event to null
                if(mSingleton.getClickedEventFromPersonActivity() != null)
                {
                    // Add a marker in Sydney and move the camera
                    LatLng eventLocation = new LatLng(Double.valueOf(mSingleton.getClickedEventFromPersonActivity().getLatitude()), Double.valueOf(mSingleton.getClickedEventFromPersonActivity().getLongitude()));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(eventLocation, 4), 2000, null);

                    //gotta set the text to the selected event information
                    Event event = mSingleton.getClickedEventFromPersonActivity();
                    Person person = mSingleton.getPersonIDPersonMap().get(event.getPersonID());

                    String setTextName = person.getFirstName() + " " + person.getLastName();
                    mNameEventTV.setText(setTextName);
                    String setTextInfo = event.getEventType() + " " + event.getCity() + ", " + event.getCountry();
                    if(event.getYear() != null)
                        setTextInfo = setTextInfo + " | " + event.getYear();

                    mInfoEventTV.setText(setTextInfo);

                    //Drawable genderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_male).sizeDp(40);
                    //mImageView.setImageDrawable(genderIcon);
                    if(person.getGender().equals(("m")))
                    {
                        mImageView.setImageResource(R.drawable.human_male);
                    }
                    else
                    {
                        mImageView.setImageResource(R.drawable.human_female);
                    }

                    drawLines(event);

                    mSingleton.setClickedEventFromPersonActivity(null);
                }
            }
        });

        return view;
    }

    /**
     * draws family lines on map fragment
     * google map
     * draws a line to the father and mother of the person
     * to their father and mother, to their father and mother's birth event
     * generational lines getting thinner as the increase a generation.
     * @param rootEvent
     * @param lineThickness
     */
    public void drawFamilyLines(Event rootEvent, Integer lineThickness)
    {
        if(mSettings.getFamilyTreeLinesOn())
        {
            Person selectedPerson = mSingleton.getPersonIDPersonMap().get(rootEvent.getPersonID());
            //find father
            //find birth event of father
            Event earliestEventFather = findEarliestEventFather(selectedPerson);
            Event earliestEventMother = findEarliestEventMother(selectedPerson);

            if(earliestEventFather != null)
            {
                //draw the actual lines
                PolylineOptions optionsFather = new PolylineOptions();
                optionsFather.add(new LatLng(Double.valueOf(rootEvent.getLatitude()), Double.valueOf(rootEvent.getLongitude())), new LatLng(Double.valueOf(earliestEventFather.getLatitude()), Double.valueOf(earliestEventFather.getLongitude())));
                optionsFather.width(lineThickness);

                switch(mSettings.getFamilyTreeLinesColor().toLowerCase())
                {
                    case "red":
                        optionsFather.color(Color.RED);
                        break;
                    case "green":
                        optionsFather.color(Color.GREEN);
                        break;
                    case "blue":
                        optionsFather.color(Color.BLUE);
                        break;
                }
                Polyline line = mMap.addPolyline(optionsFather);
                famLines.add(line);

                drawFamilyLines(earliestEventFather, lineThickness*3/4);

            }

            if(earliestEventMother != null)
            {
                //draw the actual lines
                PolylineOptions optionsMother = new PolylineOptions();
                optionsMother.add(new LatLng(Double.valueOf(rootEvent.getLatitude()), Double.valueOf(rootEvent.getLongitude())), new LatLng(Double.valueOf(earliestEventMother.getLatitude()), Double.valueOf(earliestEventMother.getLongitude())));
                optionsMother.width(lineThickness);

                switch(mSettings.getFamilyTreeLinesColor().toLowerCase())
                {
                    case "red":
                        optionsMother.color(Color.RED);
                        break;
                    case "green":
                        optionsMother.color(Color.GREEN);
                        break;
                    case "blue":
                        optionsMother.color(Color.BLUE);
                        break;
                }
                Polyline line = mMap.addPolyline(optionsMother);
                famLines.add(line);

                drawFamilyLines(earliestEventMother, lineThickness*3/4);
            }

        }
    }

    /**
     * finds the earliest event of a person's father
     * @param root
     * @return
     */
    public Event findEarliestEventFather(Person root)
    {
        //get father.
        Person father = mFilter.findFather(root);
        if(father == null)
        {
            return null;
        }

        ArrayList<Event> fatherEvents = mFilter.getFilteredEventsFromPerson(father);

        if(fatherEvents.size() == 0)
        {
            return null;
        }
        fatherEvents = mSingleton.orderEventsByDate(fatherEvents);

        //search for birth event type first...
        for(Event event: fatherEvents)
        {
            if(event.getEventType().toLowerCase().equals("birth"))
            {
                return event;
            }
        }

        return fatherEvents.get(0);
    }

    /**
     * finds the earliest mother of a person
     * @param root
     * @return
     */
    public Event findEarliestEventMother(Person root)
    {
        //get father.
        Person mother = mFilter.findMother(root);
        if(mother == null)
        {
            return null;
        }

        ArrayList<Event> motherEvents = mFilter.getFilteredEventsFromPerson(mother);

        if(motherEvents.size() == 0)
        {
            return null;
        }
        motherEvents = mSingleton.orderEventsByDate(motherEvents);

        //search for birth event type first...
        for(Event event: motherEvents)
        {
            if(event.getEventType().toLowerCase().equals("birth"))
            {
                return event;
            }
        }

        return motherEvents.get(0);
    }

    /**
     * finds the earliest spouse of the person
     *
     * @param root
     * @return
     */
    public Event findEarliestEventSpouse(Person root)
    {
        //get father.
        Person spouse = mFilter.findSpouse(root);
        if(spouse == null)
        {
            return null;
        }

        ArrayList<Event> motherEvents = mFilter.getFilteredEventsFromPerson(spouse);

        if(motherEvents.size() == 0)
        {
            return null;
        }
        motherEvents = mSingleton.orderEventsByDate(motherEvents);

        //search for birth event type first...
        for(Event event: motherEvents)
        {
            if(event.getEventType().toLowerCase().equals("birth"))
            {
                return event;
            }
        }

        return motherEvents.get(0);
    }


    /**
     * draws the spouse lines on the map fragment
     * google maps
     * draws a line connecting a person to their spouse's birth event.
     * @param rootEvent
     */
    public void drawSpouseLines(Event rootEvent)
    {
        if(mSettings.getSpoueLinesOn())
        {
            Person selectedPerson = mSingleton.getPersonIDPersonMap().get(rootEvent.getPersonID());
            Event earliestSpouseEvent = findEarliestEventSpouse(selectedPerson);

            if(earliestSpouseEvent != null)
            {
                //draw the actual lines
                PolylineOptions options = new PolylineOptions();
                options.add(new LatLng(Double.valueOf(rootEvent.getLatitude()), Double.valueOf(rootEvent.getLongitude())), new LatLng(Double.valueOf(earliestSpouseEvent.getLatitude()), Double.valueOf(earliestSpouseEvent.getLongitude())));
                options.width(15);

                switch(mSettings.getSpouseLinesColor().toLowerCase())
                {
                    case "red":
                        options.color(Color.RED);
                        break;
                    case "green":
                        options.color(Color.GREEN);
                        break;
                    case "blue":
                        options.color(Color.BLUE);
                        break;
                }
                Polyline line = mMap.addPolyline(options);
                spouseLines.add(line);
            }

        }
    }

    //lines drawn to connect events in chronological order

    /**
     * draw life lines on the map fragment
     * google map
     * draws a line from the birth to the other
     * events to the death event of a person.
     * @param rootEvent
     */
    public void drawLifeLines(Event rootEvent)
    {
        if(mSettings.getLifeStoryLinesOn())
        {
            Person selectedPerson = mSingleton.getPersonIDPersonMap().get(rootEvent.getPersonID());
            ArrayList<Event> filteredEvents = mFilter.getFilteredEventsFromPerson(selectedPerson);
            filteredEvents = mSingleton.orderEventsByDate(filteredEvents);

            for(int i = 0; i < filteredEvents.size()-1; i++)
            {
                Event event1 = filteredEvents.get(i);
                Event event2 = filteredEvents.get(i+1);

                //draw the actual lines
                PolylineOptions options = new PolylineOptions();
                options.add(new LatLng(Double.valueOf(event1.getLatitude()), Double.valueOf(event1.getLongitude())), new LatLng(Double.valueOf(event2.getLatitude()), Double.valueOf(event2.getLongitude())));
                options.width(15);

                switch(mSettings.getLifeStoryLinesColor().toLowerCase())
                {
                    case "red":
                        options.color(Color.RED);
                        break;
                    case "green":
                        options.color(Color.GREEN);
                        break;
                    case "blue":
                        options.color(Color.BLUE);
                        break;
                }
                Polyline line = mMap.addPolyline(options);
                lifeLines.add(line);
            }
        }
    }

    /**
     * clears all current polylines on the map fragment
     */
    public void clearLines()
    {
        for(Polyline line: lifeLines)
        {
            line.remove();
        }
        for(Polyline line: famLines)
        {
            line.remove();
        }
        for(Polyline line: spouseLines)
        {
            line.remove();
        }
    }

    /*

     Polyline line = map.addPolyline(new PolylineOptions()
     .add(new LatLng(51.5, -0.1), new LatLng(40.7, -74.0))
     .width(5)
     .color(Color.RED));

     */

    /**
     * draws all family spouse and life lines
     * @param event
     */
    public void drawLines(Event event)
    {
        clearLines();
        drawFamilyLines(event, 40);
        drawSpouseLines(event);
        drawLifeLines(event);
    }

    /**
     * sets the map type from the settings singleton
     */
    public void setMapType()
    {
        switch(Settings.getInstance().getMapType())
        {
            case "Normal":
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case "Hybrid":
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case "Satellite":
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case "Terrain":
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
        }
    }

    /**
     * adds markers to the map fragment
     * from the filtered events
     * @param events
     */
    public void addMarkers(ArrayList<Event> events){

        for(Event event: events)
        {
            LatLng location = new LatLng(Double.valueOf(event.getLatitude()), Double.valueOf(event.getLongitude()));
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(location).title(event.getCity());


            //gotta get the event type... somehow do a hue or whatever. 1-160
            int multiplier = 160/mSingleton.getEventTypes().size();

            int index = mSingleton.getEventTypes().indexOf(event.getEventType());
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(multiplier*index));

            //add the marker/event to a hashmap for clicking on the marker to get the event
            Marker marker = mMap.addMarker(markerOptions);
            markerEventMap.put(marker, event);

        }

    }

    /**
     * creates the menu for the map fragment. if the map fragment
     * is implemented by the event activity, a different menu is selected.
     * @param menu
     * @param inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        Bundle bundle = this.getArguments();
        //this has to be an if statement... to know if it's being called from the event
        //or main activity...
        if(bundle != null)
        {

        }
        else
        {
            inflater.inflate(R.menu.menu_main, menu);
        }

    }

}
