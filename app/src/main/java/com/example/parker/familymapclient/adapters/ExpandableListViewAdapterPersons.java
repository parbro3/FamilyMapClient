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
import model.Person;

/**
 * Expandable List View for Persons adapter. takes in a map and populates the list
 * based on the map.
 * Has ability to do multiple expandable lists.
 * did not implement this. just does one.
 */

public class ExpandableListViewAdapterPersons extends BaseExpandableListAdapter {

    /**
     * Context
     */
    private Context context;
    /**
     * titles for expandable lists
     */
    private List<String> expandableListTitle;
    /**
     * details mapping the titles to the details
     */
    private HashMap<String, List<Person> > expandableListDetail;
    /**
     * instance of the singleton
     */
    Singleton singleton = new Singleton().getInstance();

    public ExpandableListViewAdapterPersons(Context context, List<String> expandableListTitle, HashMap<String, List<Person>> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }

    /**
     * gets group count
     * @return
     */
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
     * implemented from BaseListViewAdapter.
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
            view = inflater.inflate(R.layout.persons_list_group, null);
        }

        TextView lblListHeader = (TextView)view.findViewById(R.id.family_list_title);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(String.valueOf(expandableListTitle.get(groupPosition)));

        return view;
    }

    /**
     * implemented BaseListViewAdapter
     * @param listPosition
     * @param expandedListPosition
     * @param b
     * @param view
     * @param viewGroup
     * @return
     */
    @Override
    public View getChildView(int listPosition, int expandedListPosition, boolean b, View view, ViewGroup viewGroup) {

        final Person person = (Person) getChild(listPosition, expandedListPosition);

        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.persons_list_item, null);
        }

        TextView childView = view.findViewById(R.id.family_list_person_name);
        String childText = person.getFirstName() + " " + person.getLastName();
        //String childText = "just a text";
        childView.setText(childText);

        ImageView imageView = (ImageView)view.findViewById(R.id.person_image_view);
        if(person.getGender().equals("f"))
        {
            imageView.setImageResource(R.drawable.human_female);
        }
        else
        {
            imageView.setImageResource(R.drawable.human_male);
        }


        TextView familyPerson = (TextView) view.findViewById(R.id.family_list_relationship_type);

        if(person.getPersonID().equals(singleton.getSelectedPerson().getSpouse()))
            familyPerson.setText("Spouse");
        else if(person.getPersonID().equals(singleton.getSelectedPerson().getFather()))
            familyPerson.setText("Father");
        else if(person.getPersonID().equals(singleton.getSelectedPerson().getMother()))
            familyPerson.setText("Mother");

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
