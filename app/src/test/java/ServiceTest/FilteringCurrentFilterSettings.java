package ServiceTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.* ;


import data.Filter;
import data.Singleton;
import proxy.Proxy;
import model.User;
import request.*;
import result.*;

/**
 * Created by Parker on 2/25/18.
 */

public class FilteringCurrentFilterSettings {

    Proxy proxy;

    private User user;
    private User user2;
    private LoginRequest request;
    private LoginResult result;
    private PersonIDRequest pIDRequest;
    private PersonIDResult pIDResult;
    private PersonRequest familyRequest;
    private PersonResult familyResult;
    private EventRequest eventRequest;
    private EventResult eventResult;
    Singleton singleton;
    Filter filter;

    @Before
    public void setUp(){

        proxy = new Proxy();
        user = new User();
        request = new LoginRequest();
        result = new LoginResult();
        pIDRequest = new PersonIDRequest();
        pIDResult = new PersonIDResult();
        familyRequest = new PersonRequest();
        familyResult = new PersonResult();
        eventRequest = new EventRequest();
        eventResult = new EventResult();

    }

    @After
    public void tearDown(){
        return;
    }

    @Test
    public void ProxyPersonFamilyEventstest() {

        try
        {
            singleton = singleton.getInstance();
            singleton.setPort("8080");
            singleton.setHost("127.0.0.1");

            request.setPassWord("parker");
            request.setUserName("sheila");

            result = proxy.login(request);

            pIDRequest.setAuthID(result.getAuthToken());
            pIDRequest.setPersonID(result.getPersonID());

            pIDResult = proxy.getPersonByID(pIDRequest);

            familyRequest.setAuthID(result.getAuthToken());
            familyResult = proxy.getFamily(familyRequest);

            eventRequest.setAuthID(result.getAuthToken());
            eventResult = proxy.getEvents(eventRequest);

            //ALL DATA STORED

        }
        catch(Exception e)
        {
            System.out.print("Test Error: " + e.getMessage());
        }

        assertNotNull(result.getAuthToken());
        assertNotNull(pIDResult.getFirstName());
        assertEquals(familyResult.getData().length, 8);
        assertEquals(eventResult.getData().length,16);


        //TEST ADDING DATA

        //store data in singleton
        singleton.addToSingleton(pIDResult);
        singleton.addToSingleton(familyResult);
        singleton.addToSingleton(eventResult);

        assertNotNull(singleton.getEventIDEventMap());
        assertNotNull(singleton.getPersonIDPersonMap());
        assertNotNull(singleton.getUserPerson());


        //SETTING FILTER OPTIONS
        filter = filter.getInstance();
        filter.initialize();
        filter.setFathersSide(false);
        filter.setFemale(false);


        //FILTERING
        filter.filterEvents();

        assertEquals(filter.getFilteredEvents().size(), 1);

        filter.initialize();
        filter.setMothersSide(false);
        filter.setMale(false);
        filter.filterEvents();

        assertEquals(filter.getFilteredEvents().size(), 5);

        filter.initialize();
        filter.setMothersSide(false);

        assertEquals(filter.getFilteredEvents().size(), 5);

        System.out.print("test");

    }





}
