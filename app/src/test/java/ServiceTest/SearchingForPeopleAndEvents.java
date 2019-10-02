package ServiceTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.* ;


import data.Filter;
import data.Singleton;
import model.Event;
import model.Person;
import proxy.Proxy;
import model.User;
import request.*;
import result.*;

/**
 * Created by Parker on 2/25/18.
 */

public class SearchingForPeopleAndEvents {

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


        //initialize filter
        filter = Filter.getInstance();
        filter.initialize();
        filter.filterEvents();

        //after data is stored...
        singleton.setSelectedPerson(singleton.getUserPerson());

        Person father = filter.findFather(singleton.getUserPerson());
        Person mother = filter.findMother(singleton.getUserPerson());

        //find the grandparents
        Person grandfatherFather = filter.findFather(father);
        Person grandmotherFather = filter.findMother(father);
        Person grandfatherMother = filter.findFather(mother);
        Person grandmotherMother = filter.findMother(mother);

        //map the id of the person to the person
        assertEquals(grandfatherFather, singleton.getPersonIDPersonMap().get(grandfatherFather.getPersonID()));
        assertEquals(grandmotherFather, singleton.getPersonIDPersonMap().get(grandmotherFather.getPersonID()));
        assertEquals(grandfatherMother, singleton.getPersonIDPersonMap().get(grandfatherMother.getPersonID()));
        assertEquals(grandmotherMother, singleton.getPersonIDPersonMap().get(grandmotherMother.getPersonID()));

        //get the events for the person
        ArrayList<Event> eventsGrandfatherFather = filter.getFilteredEventsFromPerson(grandfatherFather);
        ArrayList<Event> eventsGrandmotherFather = filter.getFilteredEventsFromPerson(grandmotherFather);
        ArrayList<Event> eventsGrandfatherMother = filter.getFilteredEventsFromPerson(grandfatherMother);
        ArrayList<Event> eventsGrandmotherMother = filter.getFilteredEventsFromPerson(grandmotherMother);

        //check the amount of events for each person
        assertEquals(eventsGrandfatherFather.size(),2);
        assertEquals(eventsGrandmotherFather.size(),2);
        assertEquals(eventsGrandfatherMother.size(),2);
        assertEquals(eventsGrandmotherMother.size(),2);


    }





}
