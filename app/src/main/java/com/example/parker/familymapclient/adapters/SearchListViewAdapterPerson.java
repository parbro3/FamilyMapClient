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
import model.Person;

/**
 * Created by Parker on 4/15/18.
 */

public class SearchListViewAdapterPerson extends BaseAdapter {

    /**
     * instance of data singleton
     */
    Singleton mSingleton = Singleton.getInstance();
    /**
     * instance of context to be used in clas
     */
    private Context mContext;
    /**
     * all persons to be input into list
     */
    private ArrayList<Person> mPersons;

    /**
     * instance of filter never used.
     */
    data.Filter mFilter = new data.Filter().getInstance();

    public SearchListViewAdapterPerson(Context context, ArrayList<Person> persons) {
        mContext = context;
        mPersons = persons;
    }

    @Override
    public int getCount() {
        return mPersons.size();
    }

    @Override
    public Object getItem(int i) {
        return mPersons.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    /**
     * view that updates text views and image views dynamically based on gender
     * and information and details.
     * @param i
     * @param view
     * @param viewGroup
     * @return
     */
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.persons_list_item, null);


        TextView namePersonTV = (TextView) view.findViewById(R.id.family_list_person_name);
        String personName = mPersons.get(i).getFirstName() + " " + mPersons.get(i).getLastName();
        namePersonTV.setText(personName);

        ImageView imageView = (ImageView)view.findViewById(R.id.person_image_view);

        //TextView eventTitleTV = (TextView) view.findViewById(R.id.family_list_relationship_type);

        //change the image view source to a male or female...
        if(mPersons.get(i).getGender().equals("m"))
        {
            imageView.setImageResource(R.drawable.human_male);
        }
        else
        {
            imageView.setImageResource(R.drawable.human_female);
        }

        LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.event_item_linear_layout);

        return view;
    }
}
