package proxy;

import java.net.URL;

import data.Singleton;
import request.LoginRequest;
import request.*;
import result.*;
import json.Encoder;

import java.io.*;
import java.net.*;

/**
 * Connection to server. Independent of all other classes. No other classes access the server except
 * the Proxy. Deals with registering a user, logging in a user, getting a family from a user,
 * and getting events from a user. These tasks are synchronous tasks, but are implemented by
 * asynchronous tasks within the UI.
 */
public class Proxy {

    public Proxy(){
        System.setProperty("sun.net.client.defaultConnectTimeout", "2000");
    }

    /**
     * Logs in a user given the login request. returns a login result. Contacts the server given the credentials
     * from the Login Fragment.
     * @param request
     * @return login result
     * @throws IOException
     */
    public LoginResult login(LoginRequest request) throws IOException
    {

        Singleton singleton = Singleton.getInstance();
        LoginResult result = null;

        URL url = new URL("http://" + singleton.getHost() + ":" + singleton.getPort() + "/user/login");
        //get contents for host and port from UI

        HttpURLConnection http = (HttpURLConnection)url.openConnection();

        //specify post
        http.setRequestMethod("POST");
        //set request body
        http.setDoOutput(true);

        //don't add an auth token because you don't need one...

        http.addRequestProperty("Accept","application/json");

        Encoder encoder = new Encoder();
        String reqData = encoder.encode(request);

        http.connect();

        OutputStream reqBody = http.getOutputStream();
        encoder.writeString(reqData, reqBody);

        reqBody.close();

        if(http.getResponseCode() == HttpURLConnection.HTTP_OK)
        {
            System.out.print("Login Success\n");
            String responseString = encoder.readString(http.getInputStream());
            System.out.print("Response from server: " + responseString + "\n");
            result = (LoginResult)encoder.decode(responseString, LoginResult.class);

            System.out.print("result message: " + result.getMessage());
        }
        else
        {
            System.out.print("Error in Register\n");
            System.out.print("Error: " + http.getResponseCode() + " - " + http.getErrorStream().read());
            System.out.print(http.getErrorStream().toString());
        }

        return result;
    }

    //login and register extend AsyncTask<LoginReq, Void , LoginResult >
    //asynchronous method

    /**
     * registers a user given a register request. Contacts the server given the credentials
     * from the Login Fragment.
     * @param request
     * @return
     * @throws IOException
     */
    public RegisterResult register(RegisterRequest request) throws IOException
    {
        Singleton singleton = Singleton.getInstance();
        RegisterResult result = null;

        //request.setGender("f");

        URL url = new URL("http://" + singleton.getHost() + ":" + singleton.getPort() + "/user/register");
        //get contents for host and port from UI

        HttpURLConnection http = (HttpURLConnection)url.openConnection();

        //specify post
        http.setRequestMethod("POST");
        //set request body
        http.setDoOutput(true);

        //don't add an auth token because you don't need one...

        http.addRequestProperty("Accept","application/json");

        Encoder encoder = new Encoder();
        String reqData = encoder.encode(request);

        http.connect();

        OutputStream reqBody = http.getOutputStream();
        encoder.writeString(reqData, reqBody);

        reqBody.close();

        if(http.getResponseCode() == HttpURLConnection.HTTP_OK)
        {
            System.out.print("Register Success\n");
            String responseString = encoder.readString(http.getInputStream());
            System.out.print("Response from server: " + responseString + "\n");
            result = (RegisterResult)encoder.decode(responseString, RegisterResult.class);

            System.out.print("result message: " + result.getMessage());
        }
        else
        {
            System.out.print("Error in Register\n");
            System.out.print("Error: " + http.getResponseCode() + " - " + http.getErrorStream().read());
            System.out.print(http.getErrorStream().toString());
        }

        return result;
    }

    //login and register extend AsyncTask<LoginReq, Void , LoginResult >
    //asynchronous method

    /**
     * Get the person given the person ID from the user. Contacts the server given the credentials
     * from the result of the login or register result.
     * @param request
     * @return PersonIDResult
     * @throws IOException
     */
    public PersonIDResult getPersonByID(PersonIDRequest request) throws IOException
    {
        PersonIDResult result = null;

        Singleton singleton = Singleton.getInstance();

        URL url = new URL("http://" + singleton.getHost() + ":" + singleton.getPort() + "/person/" + request.getPersonID());
        //get contents for host and port from UI

        HttpURLConnection http = (HttpURLConnection)url.openConnection();

        //set request body
        //http.setDoOutput(true);

        //don't add an auth token because you don't need one...

        http.addRequestProperty("Accept","application/json");

        //specify post or get
        http.setRequestMethod("GET");

        http.addRequestProperty("Authorization", request.getAuthID());

        Encoder encoder = new Encoder();
        //String reqData = encoder.encode(request);

        http.connect();

        //OutputStream reqBody = http.getOutputStream();
        //encoder.writeString(reqData, reqBody);

        //reqBody.close();

        if(http.getResponseCode() == HttpURLConnection.HTTP_OK)
        {
            System.out.print("Register Success\n");
            String responseString = encoder.readString(http.getInputStream());
            System.out.print("Response from server: " + responseString + "\n");
            result = (PersonIDResult) encoder.decode(responseString, PersonIDResult.class);

            System.out.print("result message: " + result.getMessage());
        }
        else
        {
            System.out.print("Error in Register\n");
            System.out.print("Error: " + http.getResponseCode() + " - " + http.getErrorStream().read());
            System.out.print(http.getErrorStream().toString());
        }

        return result;
    }


    /**
     * Gets the family from the given user. Given user is in the PersonRequest. Contacts the
     * server given the credentials from the PersonID Result
     * @param request
     * @return PersonResult (Family of the User)
     * @throws IOException
     */
    public PersonResult getFamily(PersonRequest request) throws IOException
    {
        PersonResult result = null;

        Singleton singleton = Singleton.getInstance();

        URL url = new URL("http://" + singleton.getHost() + ":" + singleton.getPort() + "/person/");
        //get contents for host and port from UI

        HttpURLConnection http = (HttpURLConnection)url.openConnection();

        //set request body
        //http.setDoOutput(true);

        //don't add an auth token because you don't need one...

        http.addRequestProperty("Accept","application/json");

        //specify post or get
        http.setRequestMethod("GET");

        http.addRequestProperty("Authorization", request.getAuthID());

        Encoder encoder = new Encoder();
        //String reqData = encoder.encode(request);

        http.connect();

        //OutputStream reqBody = http.getOutputStream();
        //encoder.writeString(reqData, reqBody);

        //reqBody.close();

        if(http.getResponseCode() == HttpURLConnection.HTTP_OK)
        {
            System.out.print("PersonFamily Success\n");
            String responseString = encoder.readString(http.getInputStream());
            System.out.print("Response from server: " + responseString + "\n");
            result = (PersonResult) encoder.decode(responseString, PersonResult.class);

            System.out.print("result message: " + result.getMessage());
        }
        else
        {
            System.out.print("Error in Register\n");
            System.out.print("Error: " + http.getResponseCode() + " - " + http.getErrorStream().read());
            System.out.print(http.getErrorStream().toString());
        }

        return result;
    }

    /**
     * Gets the events from the family from the given user. Given user is in the PersonRequest. Contacts the
     * server given the credentials from the PersonID Result
     * @param request
     * @return
     * @throws IOException
     */
    public EventResult getEvents(EventRequest request) throws IOException
    {
        EventResult result = null;

        Singleton singleton = Singleton.getInstance();

        URL url = new URL("http://" + singleton.getHost() + ":" + singleton.getPort() + "/event/");
        //get contents for host and port from UI

        HttpURLConnection http = (HttpURLConnection)url.openConnection();

        //set request body
        //http.setDoOutput(true);

        //don't add an auth token because you don't need one...

        http.addRequestProperty("Accept","application/json");

        //specify post or get
        http.setRequestMethod("GET");

        http.addRequestProperty("Authorization", request.getAuthID());

        Encoder encoder = new Encoder();
        //String reqData = encoder.encode(request);

        http.connect();

        //OutputStream reqBody = http.getOutputStream();
        //encoder.writeString(reqData, reqBody);

        //reqBody.close();

        if(http.getResponseCode() == HttpURLConnection.HTTP_OK)
        {
            System.out.print("EventsFamily Success\n");
            String responseString = encoder.readString(http.getInputStream());
            System.out.print("Response from server: " + responseString + "\n");
            result = (EventResult) encoder.decode(responseString, EventResult.class);

            System.out.print("result message: " + result.getMessage());
        }
        else
        {
            System.out.print("Error in Register\n");
            System.out.print("Error: " + http.getResponseCode() + " - " + http.getErrorStream().read());
            System.out.print(http.getErrorStream().toString());
        }

        return result;
    }

}
