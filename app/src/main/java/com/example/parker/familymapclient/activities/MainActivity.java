package com.example.parker.familymapclient.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.parker.familymapclient.R;
import com.example.parker.familymapclient.fragments.LoginFragment;
import com.example.parker.familymapclient.fragments.MapFragment;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

/**
 * Main activity... two fragments... map fragment and login fragment.
 * Login fragment changes to map fragment upon authentication
 */
public class MainActivity extends AppCompatActivity {

    /**
     * member fragment manager
     */
    private FragmentManager mFragmentManager;

    /**
     * on create method to create the main activity.
     * makes the login and map fragment.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Iconify.with(new FontAwesomeModule());

        mFragmentManager = this.getSupportFragmentManager();
        Fragment fragment = mFragmentManager.findFragmentById(R.id.fragment_container);


        Bundle bundle = getIntent().getExtras();
        if(bundle == null)
        {
            if (fragment == null)
            {
                fragment = new LoginFragment();
                Bundle bundleresync = new Bundle();
                bundleresync.putBoolean("notresync",true);
                fragment.setArguments(bundleresync);
                mFragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit();
            }
        }
        else
        {
            if(bundle.getBoolean("MapFragment") == true)
            {
                //make a map fragment
                fragment = new MapFragment();
                mFragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit();
            }
        }

    }

    //only called once... from the login fragment...
    //so settings should only be initialized here.
    //never reinitialized

    /**
     * changes fragment for the fragment container.
     * @param fragment
     */
    public void changeFragment(Fragment fragment)
    {
        data.Settings.getInstance().initialize();
        mFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

    /**
     * Menu method to call new activities
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        System.out.print("Got to the options menu selected!");

        switch(item.getItemId()){
            case R.id.menu_item_filter:
                Intent intentFilter = new Intent(MainActivity.this, FilterActivity.class);
                startActivity(intentFilter);
                break;
            case R.id.menu_item_search:
                Intent searchIntent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(searchIntent);
                break;
            case R.id.menu_item_settings:
                Intent intentSettings = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intentSettings);
                break;
        }

        return false;
    }



}
