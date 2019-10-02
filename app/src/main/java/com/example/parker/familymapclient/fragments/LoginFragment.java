package com.example.parker.familymapclient.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.parker.familymapclient.activities.MainActivity;
import com.example.parker.familymapclient.R;
import com.google.android.gms.maps.GoogleMapOptions;

import java.io.IOException;

import data.Filter;
import data.Singleton;
import proxy.*;
import request.EventRequest;
import request.LoginRequest;
import request.PersonIDRequest;
import request.PersonRequest;
import request.RegisterRequest;
import result.EventResult;
import result.LoginResult;
import result.PersonIDResult;
import result.PersonResult;
import result.RegisterResult;

/**
 * Created by Parker on 3/26/18.
 */

public class LoginFragment extends Fragment {

    /**
     * login request
     */
    private LoginRequest mLoginRequest;
    /**
     * login result
     */
    private LoginResult mLoginResult;
    /**
     * member register request
     */
    private RegisterRequest mRegisterRequest;
    /**
     * member register result
     */
    private RegisterResult  mRegisterResult;
    /**
     * member Host
     */
    private String mHost;
    /**
     * member port
     */
    private String mPort;
    /**
     * member hostET
     */
    private EditText mHostET;
    /**
     * member portET
     */
    private EditText mPortET;
    /**
     * member username
     */
    private EditText mUsername;
    /**
     * member password
     */
    private EditText mPassword;
    /**
     * member firstName
     */
    private EditText mFirstName;
    /**
     * member lastName
     */
    private EditText mLastName;
    /**
     * member email
     */
    private EditText mEmail;
    /**
     * member genderField
     */
    private RadioGroup mGenderField;
    /**
     * member progressbar
     */
    private ProgressBar spinner;


    /**
     * member register button
     */
    private Button mRegisterButton;
    /**
     * member loginButton
     */
    public static Button mLoginButton;

    /**
     * member Proxy
     */
    private Proxy proxy;
    /**
     * member singleton
     */
    private Singleton singleton;

    /**
     * member view
     */
    private View view;

    /**
     * on create method. starts the login fragment
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    /**
     * on create view. creates the login fragment view. sets the ontextchange listeners
     * for storing the variables.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        singleton = Singleton.getInstance();
        mLoginRequest = new LoginRequest();
        mRegisterRequest = new RegisterRequest();

        view = inflater.inflate(R.layout.login_fragment, container, false);

        spinner = view.findViewById(R.id.login_spinner);
        //get spinner
        enableSpinner(false);

        mHostET = (EditText) view.findViewById(R.id.server_host);
        mHostET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //nothing yet
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mHost = charSequence.toString();
                singleton.setHost(mHost);
                buttonChecker();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //nothing yet
            }
        });

        mPortET = (EditText) view.findViewById(R.id.server_port);
        mPortET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //nothing yet
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mPort = charSequence.toString();
                singleton.setPort(mPort);
                buttonChecker();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //nothing yet
            }
        });

        mUsername = (EditText) view.findViewById(R.id.username);
        mUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //nothing yet
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mLoginRequest.setUserName(charSequence.toString());
                mRegisterRequest.setUserName(charSequence.toString());
                buttonChecker();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //nothing yet
            }
        });

        mPassword = (EditText) view.findViewById(R.id.password);
        mPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //nothing yet
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mLoginRequest.setPassWord(charSequence.toString());
                mRegisterRequest.setPassWord(charSequence.toString());
                buttonChecker();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //nothing yet
            }
        });

        mFirstName = (EditText) view.findViewById(R.id.first_name);
        mFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //nothing yet
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mRegisterRequest.setFirstName(charSequence.toString());
                buttonChecker();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //nothing yet
            }
        });

        mLastName = (EditText) view.findViewById(R.id.last_name);
        mLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //nothing yet
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mRegisterRequest.setLastName(charSequence.toString());
                buttonChecker();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //nothing yet
            }
        });

        mEmail = (EditText) view.findViewById(R.id.email_address);
        mEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //nothing yet
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mRegisterRequest.setEmail(charSequence.toString());
                buttonChecker();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //nothing yet
            }
        });

        mGenderField = (RadioGroup) view.findViewById(R.id.gender);

        mRegisterButton = (Button)view.findViewById(R.id.register_button);
        mRegisterButton.setEnabled(false);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            System.out.print("Register Button Clicked");

            if(mGenderField.getCheckedRadioButtonId() == R.id.radio_male)
            {
                mRegisterRequest.setGender("m");
            }
            else if(mGenderField.getCheckedRadioButtonId() == R.id.radio_female)
            {
                mRegisterRequest.setGender("f");
            }

            //all i do in the login fragment is call the asynctask
            enableSpinner(true);
            new RegisterService().execute(mRegisterRequest);

            //probably want to stick in here until the singleton class is filled...

            //waitForData();

            }
        });

        mLoginButton = (Button)view.findViewById(R.id.login_button);
        mLoginButton.setEnabled(false);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            System.out.print("Login Button Clicked");

            enableSpinner(true);
            //all i do in the login fragment is call the asynctask.
            new LoginService().execute(mLoginRequest);

            //waitForData();

            }
        });

        mGenderField = (RadioGroup)view.findViewById(R.id.gender);
        mGenderField.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                buttonChecker();
            }
        });

        return view;
    }

    /**
     * checks if the correct fields are filled out for each button
     */
    public void buttonChecker()
    {
        if(checkLoginFields() == true)
        {
            mLoginButton.setEnabled(true);
        }
        else
        {
            mLoginButton.setEnabled(false);
        }
        if(checkRegisterFields() == true)
        {
            mRegisterButton.setEnabled(true);
        }
        else
        {
            mRegisterButton.setEnabled(false);
        }
    }

    /**
     * enables the spinner when the login or register button is selected
     * @param enable
     */
    public void enableSpinner(Boolean enable)
    {
        if(enable == true)
            spinner.setVisibility(View.VISIBLE);
        else
            spinner.setVisibility(View.GONE);
    }

    /**
     * checks to make sure the login fields are filled out
     * @return
     */
    public Boolean checkLoginFields()
    {
        if(!(mUsername.getText().toString().equals("")))
        {
            if(!(mPassword.getText().toString().equals("")))
            {
                if(!(mPortET.getText().toString().equals("")))
                {
                    if(!(mHostET.getText().toString().equals("")))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * checks to make sure the registerfields are filled out.
     * @return
     */
    public Boolean checkRegisterFields()
    {
        if(!(mUsername.getText().toString().equals("")))
        {
            if(!(mPassword.getText().toString().equals("")))
            {
                if(!(mPortET.getText().toString().equals("")))
                {
                    if(!(mHostET.getText().toString().equals("")))
                    {
                        if(!(mEmail.getText().toString().equals("")))
                        {
                            if(!(mFirstName.getText().toString().equals("")))
                            {
                                if(!(mLastName.getText().toString().equals("")))
                                {

                                    int test = mGenderField.getCheckedRadioButtonId();
                                    if(test != -1)
                                    {
                                        return true;
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * activates the map fragment
     */
    public void activateMapFragment()
    {

        GoogleMapOptions options = new GoogleMapOptions();
        MapFragment mapFragment = new MapFragment();

        ((MainActivity)getActivity()).changeFragment(mapFragment);

    }

    /**
     * async task that registers the user. Uses the proxy.
     */
    //inner asynchronous class
    public class RegisterService extends AsyncTask<RegisterRequest, Void, RegisterResult> {

        @Override
        protected RegisterResult doInBackground(RegisterRequest... request) {

            //start login spinner
            //enableSpinner(true);

            System.out.print("doInBackground entered\n");
            RegisterResult result = null;
            try
            {
                Proxy proxy = new Proxy();
                result = proxy.register(request[0]);
            }
            catch(IOException e)
            {
                System.out.print(e.getMessage());
            }

            return result;
        }


        @Override
        protected void onPostExecute(RegisterResult result)
        {
            System.out.print("onPostExecute entered\n");

            //stop the loading thing
            //enableSpinner(false);

            if(result.getPersonID() != null)
            {
                singleton.setAuthID(result.getAuthToken());

                PersonIDRequest personIDRequest = new PersonIDRequest();
                personIDRequest.setAuthID(result.getAuthToken());
                personIDRequest.setPersonID(result.getPersonID());
                //start an async task to get the data...
                new PersonIDService().execute(personIDRequest);
            }
            else
            {
                Toast.makeText(getActivity() , result.getMessage() , Toast.LENGTH_SHORT).show();
            }

        }

    }


    /**
     * async task that logs in the user. uses the Proxy.
     */
    //inner asynchronous class
    public class LoginService extends AsyncTask<LoginRequest, Void, LoginResult> {

        @Override
        protected LoginResult doInBackground(LoginRequest... request) {

            System.out.print("doInBackground entered\n");
            LoginResult result = null;
            try
            {
                Proxy proxy = new Proxy();
                result = proxy.login(request[0]);
            }
            catch(IOException e)
            {
                System.out.print(e.getMessage());
            }

            return result;
        }


        @Override
        protected void onPostExecute(LoginResult result)
        {
            //enableSpinner(false);
            System.out.print("onPostExecute entered\n");

            //somehow do something with the result...
            //gotta check if it passed.
            //if it did... gather data from the server.
            if(result.getPersonID() != null)
            {
                //set the authID real quick for future use
                singleton.setAuthID(result.getAuthToken());

                //make a personID request to get the person from the database
                PersonIDRequest personIDRequest = new PersonIDRequest();
                personIDRequest.setAuthID(result.getAuthToken());
                personIDRequest.setPersonID(result.getPersonID());

                //start an async task to get the person...
                new PersonIDService().execute(personIDRequest);
            }
            else
            {
                Toast.makeText(getActivity() , result.getMessage() , Toast.LENGTH_SHORT).show();
            }

        }

    }

    //inner asynchronous class

    /**
     * Gets a Person from the PersonID. Specifically gets the userPerson from the User
     * credentials. Asynchronous task that is called by the UI after the LoginFragment.
     */
    public class PersonIDService extends AsyncTask<PersonIDRequest, Void, PersonIDResult> {

        @Override
        protected PersonIDResult doInBackground(PersonIDRequest... request) {

            System.out.print("doInBackground entered\n");
            PersonIDResult result = null;
            try
            {
                Proxy proxy = new Proxy();
                result = proxy.getPersonByID(request[0]);
            }
            catch(IOException e)
            {
                System.out.print(e.getMessage());
            }

            return result;
        }


        @Override
        protected void onPostExecute(PersonIDResult result)
        {
            System.out.print("onPostExecute entered\n");

            //populate the singleton class i assume...
            //and make a toast on the screen with the information.
            String toastString = "Welcome " + result.getFirstName() + " " + result.getLastName() + "!";

            Toast.makeText(getActivity() , toastString , Toast.LENGTH_SHORT).show();

            //Toast toast = Toast.makeText(LoginFragment.this, , Toast.LENGTH_LONG);

            singleton.addToSingleton(result);

            //probably need to request more data honestly.. all the person events and people

            PersonRequest personRequest = new PersonRequest();
            personRequest.setAuthID(singleton.getAuthID());
            new PersonService().execute(personRequest);

        }

    }


    //inner asynchronous class

    /**
     * Inner asynchronous class. Gets the Family from the person request. Implements the proxy class.
     * person request comes from the personID result asynchronous task onPostExecute.
     */
    public class PersonService extends AsyncTask<PersonRequest, Void, PersonResult> {

        @Override
        protected PersonResult doInBackground(PersonRequest... request) {

            System.out.print("doInBackground entered\n");
            PersonResult result = null;
            try
            {
                Proxy proxy = new Proxy();
                result = proxy.getFamily(request[0]);
            }
            catch(IOException e)
            {
                System.out.print(e.getMessage());
            }

            return result;
        }


        @Override
        protected void onPostExecute(PersonResult result)
        {
            System.out.print("onPostExecute entered\n");

            //somehow do something with the result...
            //gotta check if it passed.
            //if it did... gather data from the server.
            if(result.getData() != null)
            {
                //store the person data
                singleton.addToSingleton(result);

                EventRequest eventRequest = new EventRequest();
                eventRequest.setAuthID(singleton.getAuthID());
                //start an async task to get the data...
                new EventService().execute(eventRequest);
            }
            else
            {
                Toast.makeText(getActivity() , result.getMessage() , Toast.LENGTH_SHORT).show();
            }
        }
    }

    //inner asynchronous class

    /**
     * Inner asynchronous class. Gets the Events from the person request. Implements the proxy class.
     * person request comes from the personID result asynchronous task onPostExecute.
     */
    public class EventService extends AsyncTask<EventRequest, Void, EventResult> {

        @Override
        protected EventResult doInBackground(EventRequest... request) {

            System.out.print("doInBackground entered\n");
            EventResult result = null;
            try
            {
                Proxy proxy = new Proxy();
                result = proxy.getEvents(request[0]);
            }
            catch(IOException e)
            {
                System.out.print(e.getMessage());
            }

            return result;
        }


        @Override
        protected void onPostExecute(EventResult result)
        {
            System.out.print("onPostExecute entered\n");

            //somehow do something with the result...
            //gotta check if it passed.
            //if it did... gather data from the server.
            if(result.getData() != null)
            {
                enableSpinner(false);
                singleton.addToSingleton(result);

                //initialize the filter stuff
                Filter filter = Filter.getInstance();
                filter.initialize();
                filter.filterEvents();

                activateMapFragment();
            }
            else
            {
                Toast.makeText(getActivity() , result.getMessage() , Toast.LENGTH_SHORT).show();
            }

        }

    }


}
