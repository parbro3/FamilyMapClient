package data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.Event;
import model.Person;
import result.EventResult;
import result.PersonIDResult;
import result.PersonResult;

/**
 * Singleton class used to store most all original unmanipulated data from the server.
 * This makes it easier to go from activity to activity without losing data.
 */

public class Singleton {

    /**
     * Instance to be accessed anywhere in the code because it is static
     */
    private static Singleton singleton_instance = null;

    /**
     * host stored from login fragment
     */
    private String host;
    /**
     * port stored from login fragment
     */
    private String port;
    /**
     * auth id stored when login or register
     */
    private String authID;

    /**
     * User person stored upon login or register
     */
    private Person userPerson;
    /**
     * selected person changed when selected event changed
     */
    private Person selectedPerson;
    /**
     * clicked event stored when clicked from the person activity
     */
    private Event clickedEventFromPersonActivity;

    /**
     * Instance of the filter to be accessed by some methods
     */
    private Filter mFilter;

    /**
     * event types stored for sorting
     */
    private List<String> eventTypes = new ArrayList<>();

    /**
     * map stored mapping person ids to persons. for easy access without contacting server
     */
    //map of personID to person
    private HashMap<String, Person> personIDPersonMap;

    //map of eventID to event
    /**
     * map stored for mapping eventid to event. for easy access without contacting server
     */
    private HashMap<String, Event> eventIDEventMap;

    public Singleton() {}

    /**
     * Get instance called to return the static instance anywhere in the program.
     * @return
     */
    public static Singleton getInstance()
    {
        if(singleton_instance == null)
            singleton_instance = new Singleton();
        return singleton_instance;
    }

    /**
     * ordered events needed for specs. Person Activity and Filter Activity events
     * need to be sorted.
     * @param inputList
     * @return
     */
    public ArrayList<Event> orderEventsByDate(List<Event> inputList)
    {
        ArrayList<Event> toBeSorted = new ArrayList<>(inputList);
        ArrayList<Event> sorted = new ArrayList<>();

        ArrayList<Event> birthEvent = new ArrayList<>();
        ArrayList<Event> deathEvent = new ArrayList<>();


        //first check for birth event
        for(Event event: toBeSorted)
        {
            if(event.getEventType().toLowerCase().equals("birth"))
            {
                birthEvent.add(event);
                toBeSorted.remove(event);
                break;
            }
        }


        for(Event event: toBeSorted)
        {
            if(event.getEventType().toLowerCase().equals("death"))
            {
                deathEvent.add(event);
                toBeSorted.remove(event);
                break;
            }
        }

        ArrayList<Event> notDatedEvents = new ArrayList<>();

        while(toBeSorted.size() > 0)
        {
            Event earliestEvent = toBeSorted.get(0);
            for(Event event: toBeSorted)
            {
                if(event.getYear() != null && earliestEvent.getYear() != null)
                {
                    if(event.getYear().equals("") || earliestEvent.getYear().equals(""))
                    {
                        notDatedEvents.add(event);
                        toBeSorted.remove(event);
                    }
                    else
                    {
                        if(Integer.valueOf(earliestEvent.getYear()) > Integer.valueOf(event.getYear()))
                        {
                            earliestEvent = event;
                        }
                    }
                }
                else
                {
                    notDatedEvents.add(event);
                    toBeSorted.remove(event);
                }
            }
            sorted.add(earliestEvent);
            toBeSorted.remove(earliestEvent);
        }

        //add the birth
        if(birthEvent.size() > 0)
            sorted.add(0, birthEvent.get(0));

        //add death event
        if(deathEvent.size() > 0)
            sorted.add(sorted.size(), deathEvent.get(0));

        return sorted;
    }

    /**
     * Updates event types
     */
    public void updateEventTypes()
    {
        List<String> typesEvents = new ArrayList<>();
        for(Event event: eventIDEventMap.values())
        {
            if(!typesEvents.contains(event.getEventType()))
            {
                typesEvents.add(event.getEventType());
            }
        }
        setEventTypes(typesEvents);
    }

    /**
     * Clears cache when the logout button is selected
     */
    public void clearCache()
    {
        setClickedEventFromPersonActivity(null);
        setSelectedPerson(null);
        setEventIDEventMap(null);
        setPersonIDPersonMap(null);
    }

    /**
     * gets event types (getter)
     * @return
     */
    public List<String> getEventTypes() {
        sortEventTypes();
        return eventTypes;
    }

    /**
     * sorts event types based on certain criteria. birth first. death last. dates sorted.
     */
    public void sortEventTypes()
    {
        ArrayList<String> oldList = new ArrayList<>(eventTypes);
        ArrayList<String> newList = new ArrayList<>();

        for(String type: oldList)
        {
            if((!type.equals("birth")) && (!type.equals("death")))
            {
                newList.add(type);
            }
        }

        for(String type: oldList)
        {
            if(type.equals("birth"))
            {
                newList.add(0, type);
            }
            if(type.equals(("death")))
            {
                newList.add(newList.size(), type);
            }
        }
        setEventTypes(newList);
    }

    public void setEventTypes(List<String> eventTypes) {
        this.eventTypes = eventTypes;
    }

    public Event getClickedEventFromPersonActivity() {
        return clickedEventFromPersonActivity;
    }

    public void setClickedEventFromPersonActivity(Event clickedEventFromPersonActivity) {
        this.clickedEventFromPersonActivity = clickedEventFromPersonActivity;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public Person getUserPerson() {
        return userPerson;
    }

    public void setUserPerson(Person userPerson) {
        this.userPerson = userPerson;
    }

    public String getAuthID() {
        return authID;
    }

    public void setAuthID(String authID) {
        this.authID = authID;
    }

    public HashMap<String, Person> getPersonIDPersonMap() {
        return personIDPersonMap;
    }

    public void setPersonIDPersonMap(HashMap<String, Person> personIDPersonMap) {
        this.personIDPersonMap = personIDPersonMap;
    }

    public HashMap<String, Event> getEventIDEventMap() {
        return eventIDEventMap;
    }

    public void setEventIDEventMap(HashMap<String, Event> eventIDEventMap) {
        this.eventIDEventMap = eventIDEventMap;
    }

    public Person getSelectedPerson() {
        return selectedPerson;
    }

    public void setSelectedPerson(Person selectedPerson) {
        this.selectedPerson = selectedPerson;
    }

    /**
     * Given the selected person in the singleton, get the events.
     * @return
     */
    public ArrayList<Event> getEventsFromSelectedPerson()
    {
        mFilter = Filter.getInstance();
        if(getSelectedPerson() == null)
        {
            return null;
        }

        ArrayList<Event> selectedPersonEvents = new ArrayList();
        //parse through all the events and check for the personID...
        for(Event event : mFilter.getFilteredEvents())
        {
            if(event.getPersonID().equals(getSelectedPerson().getPersonID()))
            {
                selectedPersonEvents.add(event);
            }
        }
        return selectedPersonEvents;
    }

    /**
     * returns the family from selected person
     * @return
     */
    public ArrayList<Person> getFamilyFromSelectedPerson()
    {
        if(getSelectedPerson() == null)
            return null;

        ArrayList<Person> selectedPersonPersons = new ArrayList();

        for(Person person : getPersonIDPersonMap().values())
        {
            if(getSelectedPerson().getSpouse()!=null && getSelectedPerson().getSpouse().equals(person.getPersonID()))
            {
                selectedPersonPersons.add(person);
            }
            else if(getSelectedPerson().getFather()!=null &&getSelectedPerson().getFather().equals(person.getPersonID()))
            {
                selectedPersonPersons.add(person);
            }
            else if(getSelectedPerson().getMother()!=null &&getSelectedPerson().getMother().equals(person.getPersonID()))
            {
                selectedPersonPersons.add(person);
            }
        }
        return selectedPersonPersons;
    }

    /**
     * adds the result object to the singleton from what was returned
     * from the proxy.
     * @param object
     */
    public void addToSingleton(Object object)
    {
        if(object.getClass().equals(PersonIDResult.class))
        {
            PersonIDResult result = (PersonIDResult)object;
            Person person = new Person();
            person.setSpouse(result.getSpouseID());
            person.setMother(result.getMotherID());
            person.setFather(result.getFatherID());
            person.setGender(result.getGender());
            person.setPersonID(result.getPersonID());
            person.setLastName(result.getLastName());
            person.setFirstName(result.getFirstName());
            person.setDescendant(result.getDescendant());

            //store person
            setUserPerson(person);
        }
        else if(object.getClass().equals(PersonResult.class))
        {
            PersonResult result = (PersonResult)object;

            Person[] persons = result.getData();

            HashMap<String, Person> map = new HashMap<>();
            for(Person person : persons)
            {
                map.put(person.getPersonID(), person);
            }
            setPersonIDPersonMap(map);
        }
        else if(object.getClass().equals(EventResult.class))
        {
            EventResult result = (EventResult)object;

            Event[] events = result.getData();

            HashMap<String, Event> map = new HashMap<>();
            for(Event event : events)
            {
                map.put(event.getEventID(), event);
            }
            setEventIDEventMap(map);
        }

    }

}
