package com.example.parker.familymapclient.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.parker.familymapclient.R;

import java.util.HashMap;
import java.util.List;

import data.Singleton;
import model.Event;

/**
 * Expandable List View for Events adapter. takes in a map and populates the list
 * based on the map.
 * Has ability to do multiple expandable lists.
 * did not implement this. just does one.
 */

public class ExpandableListViewAdapterEvents extends BaseExpandableListAdapter {

    /**
     * Context
     */
    private Context context;
    /**
     * titles of the expandable lists
     */
    private List<String> expandableListTitle;
    /**
     * maps mapping the titles to the data
     */
    private HashMap<String, List<Event> > expandableListDetail;
    /**
     * instance of singleton
     */
    Singleton singleton = new Singleton().getInstance();

    public ExpandableListViewAdapterEvents(Context context, List<String> expandableListTitle, HashMap<String, List<Event>> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }

    @Override
    public int getGroupCount() {
        return expandableListTitle.size();
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition)).size();
    }

    @Override
    public Object getGroup(int i) {
        return this.expandableListDetail.get(i);
    }

    @Override
    public Object getChild(int listPosition, int expandableListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition)).get(expandableListPosition);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * gets the group view. implemented from BaseExpandableListAdapter
     * @param groupPosition
     * @param b
     * @param view
     * @param viewGroup
     * @return
     */
    @Override
    public View getGroupView(int groupPosition, boolean b, View view, ViewGroup viewGroup) {

        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.events_list_group, null);
        }

        TextView lblListHeader = (TextView)view.findViewById(R.id.events_list_title);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(String.valueOf(expandableListTitle.get(groupPosition)));

        return view;
    }

    /**
     * Gets the child view. Implemented from BaseExpandableListAdapter
     * @param listPosition
     * @param expandedListPosition
     * @param b
     * @param view
     * @param viewGroup
     * @return
     */
    @Override
    public View getChildView(int listPosition, int expandedListPosition, boolean b, View view, ViewGroup viewGroup) {

        final Event event = (Event) getChild(listPosition, expandedListPosition);

        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.events_list_item, null);
        }

        TextView childView = view.findViewById(R.id.events_list_title);
        String childText = event.getEventType() + ": " + event.getCity() + ", " + event.getCountry() + " | ";
        if(event.getYear() != null)
            childText = childText + event.getYear();
        //String childText = "just a text";
        childView.setText(childText);

        ImageView imageView = (ImageView)view.findViewById(R.id.event_image_view);
        imageView.setImageResource(R.drawable.map_marker);

        TextView eventPerson = (TextView) view.findViewById(R.id.events_list_person_name);
        eventPerson.setText(singleton.getSelectedPerson().getFirstName() + " " + singleton.getSelectedPerson().getLastName());

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
