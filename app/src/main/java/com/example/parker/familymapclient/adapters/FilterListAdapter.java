package com.example.parker.familymapclient.adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.Switch;
import android.widget.TextView;

import com.example.parker.familymapclient.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Parker on 4/15/18.
 */

public class FilterListAdapter implements ListAdapter {

    private Context mContext;
    private List<String> mEventTypes;
    private HashMap<String, Boolean> mEventTypeDetail;

    data.Filter mFilter = new data.Filter().getInstance();

    public FilterListAdapter(Context context, List<String> eventTypes, HashMap<String, Boolean> eventTypeDetail) {
        mContext = context;
        mEventTypes = eventTypes;
        mEventTypeDetail = eventTypeDetail;
    }


    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int i) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return mEventTypeDetail.size();
    }

    @Override
    public Object getItem(int i) {
        return this.mEventTypeDetail.get(this.mEventTypes.get(i));
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        String title = mEventTypes.get(i);


        LayoutInflater layoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.filter_item, null);

        TextView eventTitleTV = (TextView) view.findViewById(R.id.title_filter_row);
        eventTitleTV.setText(title);

        TextView eventDescription = (TextView) view.findViewById(R.id.description_filter_row);
        String description = title + " description";
        eventDescription.setText(description);


        Switch switchRow = (Switch)view.findViewById(R.id.switch_filter_row);
        switchRow.setChecked(mEventTypeDetail.get(title));
        switchRow.setTag(title);

        switchRow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                String eventType = (String)compoundButton.getTag();
                //mEventTypes contains all of them
                switch(eventType)
                {
                    case "Father's Side":
                        mFilter.setFathersSide(b);
                        break;
                    case "Mother's Side":
                        mFilter.setMothersSide(b);
                        break;
                    case "Male Events":
                        mFilter.setMale(b);
                        break;
                    case "Female Events":
                        mFilter.setFemale(b);
                        break;
                    default:
                        //check the rest of the map
                        mFilter.getEventMapSwitches().put(eventType,b);
                        break;
                }

            }
        });


        return view;
    }

    @Override
    public int getItemViewType(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return mEventTypes.size();
    }

    @Override
    public boolean isEmpty() {
        if(mEventTypes.size() == 0)
        {
            return true;
        }
        else return false;
    }
}
