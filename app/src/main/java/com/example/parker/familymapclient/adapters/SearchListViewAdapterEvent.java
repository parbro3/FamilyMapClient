package com.example.parker.familymapclient.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.parker.familymapclient.R;

import java.util.ArrayList;

import data.Singleton;
import model.Event;
import model.Person;

/**
 * Created by Parker on 4/15/18.
 */

public class SearchListViewAdapterEvent extends BaseAdapter {

    /**
     * instance of data singleton
     */
    Singleton mSingleton = Singleton.getInstance();
    /**
     * instance of context to be used in class
     */
    private Context mContext;
    /**
     * events to be populated in list view
     */
    private ArrayList<Event> mEvents;

    data.Filter mFilter = new data.Filter().getInstance();

    public SearchListViewAdapterEvent(Context context, ArrayList<Event> events) {
        mContext = context;
        mEvents = events;
    }

    @Override
    public int getCount() {
        return mEvents.size();
    }

    @Override
    public Object getItem(int i) {
        return mEvents.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    /**
     * view from Base List View Adapter Interface.
     * Easy way to do a list view and update the text
     * dynamically.
     * @param i
     * @param view
     * @param viewGroup
     * @return
     */
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.events_list_item, null);

        //set image view to Person
        ImageView imageView = (ImageView)view.findViewById(R.id.event_image_view);
        imageView.setImageResource(R.drawable.map_marker);

        TextView namePersonTV = (TextView) view.findViewById(R.id.events_list_person_name);
        Person person = mSingleton.getPersonIDPersonMap().get(mEvents.get(i).getPersonID());
        String personName = person.getFirstName() + " " + person.getLastName();
        namePersonTV.setText(personName);

        TextView eventTitleTV = (TextView) view.findViewById(R.id.events_list_title);
        String eventInfo = mEvents.get(i).getEventType() + ": " + mEvents.get(i).getCity() + ", " + mEvents.get(i).getCountry() + " | ";
        if(mEvents.get(i).getYear() != null)
            eventInfo = eventInfo + mEvents.get(i).getYear();
        eventTitleTV.setText(eventInfo);

        LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.event_item_linear_layout);

        return view;
    }
}
