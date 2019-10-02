package data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.Event;
import model.Person;

/**
 * Created by Parker on 4/15/18.
 */

public class Filter {

    private static Filter filter_instance = null;
    private Singleton singleton;

    private ArrayList<Person> entireFamily;
    private ArrayList<Person> fatherSidePeople;
    private ArrayList<Person> motherSidePeople;
    private ArrayList<Person> malesPeople;
    private ArrayList<Person> femalesPeople;
    private ArrayList<Event> filteredEvents;

    private ArrayList<Event> allEvents;
    private ArrayList<Event> fatherSideEvents;
    private ArrayList<Event> motherSideEvents;
    private ArrayList<Event> maleEvents;
    private ArrayList<Event> femaleEvents;


    //dynamic switches from UI???
    //i can get the event types from the singleton so that's good...
    //maybe create a map.. string to boolean.. yeah that sounds good.
    private Map<String, Boolean> eventMapSwitches;
    private Map<String, ArrayList<Event>> eventTypesMapEvents;

    //static switches from UI
    private Boolean fathersSide;
    private Boolean mothersSide;
    private Boolean male;
    private Boolean female;

    public Filter()
    {
        singleton = Singleton.getInstance();
        singleton.updateEventTypes();
    }

    public static Filter getInstance()
    {
        if(filter_instance == null)
            filter_instance = new Filter();
        return filter_instance;
    }

    //called after singleton is populated by login or resync

    /**
     * initializes data from server
     */
    public void initialize()
    {
        fathersSide = true;
        mothersSide = true;
        male = true;
        female = true;

        //initialize and populate arrays and maps
        initializePopulateArrays();
        initializePopulateMaps();

        //THIS IS STILL INITIALIZING...
        //HAVEN'T DONE ANY FILTERING YET...
        populateEventGroupsFromPeopleGroups();
    }

    /**
     * get filtered events from the person
     * @param person
     * @return list of events
     */
    public ArrayList<Event> getFilteredEventsFromPerson(Person person)
    {
        if(person == null)
        {
            return null;
        }

        ArrayList<Event> selectedPersonEvents = new ArrayList();
        //parse through all the events and check for the personID...
        for(Event event : getFilteredEvents())
        {
            if(event.getPersonID().equals(person.getPersonID()))
            {
                selectedPersonEvents.add(event);
            }
        }
        return selectedPersonEvents;
    }

    //update based on filter switches
    //should we pass all the test and then add to the filtered events?
    //or delete them as we go?? or how does that work??
    //probably have to go the delete route

    /**
     * filters events based on filter booleans.
     */
    public void filterEvents()
    {
        //initialize filtered events to all events
        filteredEvents = new ArrayList<Event>(allEvents);

        //filter through map switches
        for(String eventType: eventMapSwitches.keySet())
        {
            //hopefully this works lol
            if(eventMapSwitches.get(eventType) == false)
            {
                filteredEvents.removeAll(eventTypesMapEvents.get(eventType));
            }
        }

        //remove the other groups
        if(fathersSide == false)
        {
            filteredEvents.removeAll(fatherSideEvents);
        }
        if(mothersSide == false)
        {
            filteredEvents.removeAll(motherSideEvents);
        }
        if(male == false)
        {
            filteredEvents.removeAll(maleEvents);
        }
        if(female == false)
        {
            filteredEvents.removeAll(femaleEvents);
        }
    }

    /**
     * initializes member arrays
     */
    public void initializePopulateArrays()
    {
        //SORTED PEOPLE
        entireFamily = new ArrayList<Person>(singleton.getPersonIDPersonMap().values());
        fatherSidePeople = new ArrayList<>();
        motherSidePeople = new ArrayList<>();
        malesPeople = new ArrayList<>();
        femalesPeople = new ArrayList<>();

        allEvents = new ArrayList<Event>(singleton.getEventIDEventMap().values());
        fatherSideEvents = new ArrayList<>();
        motherSideEvents = new ArrayList<>();
        maleEvents = new ArrayList<>();
        femaleEvents = new ArrayList<>();

        //split father and mother
        groupFatherMother(singleton.getUserPerson());
        //organize male and female
        groupMaleFemale();
    }

    /**
     * initializes and populates maps
     */
    public void initializePopulateMaps()
    {
        eventMapSwitches = new HashMap<>();
        eventTypesMapEvents = new HashMap<>();

        //initialize string to boolean map for switches
        for(String event: singleton.getEventTypes())
        {
            //every switch should be true when initialized
            eventMapSwitches.put(event,true);
        }

        //initialize eventType to eventList map
        //parse through entire event list

        for(Event event: allEvents)
        {
            //if the event type is new
            if(!(eventTypesMapEvents.containsKey(event.getEventType())))
            {
                ArrayList<Event> tempEventList = new ArrayList<Event>();
                tempEventList.add(event);
                eventTypesMapEvents.put(event.getEventType(),tempEventList);
            }
            else //add the event... make sure it adds.
            {
                eventTypesMapEvents.get(event.getEventType()).add(event);
            }
        }
    }

    /**
     * separating function to populate events
     */
    public void populateEventGroupsFromPeopleGroups()
    {
        populateEventsFromFamilyList(fatherSidePeople, fatherSideEvents);
        populateEventsFromFamilyList(motherSidePeople, motherSideEvents);
        populateEventsFromFamilyList(malesPeople, maleEvents);
        populateEventsFromFamilyList(femalesPeople, femaleEvents);
    }

    /**
     * I don't even remember what this does
     * @param people
     * @param events
     */
    public void populateEventsFromFamilyList(ArrayList<Person> people, ArrayList<Event> events)
    {
        for(Person person: people)
        {
            for(Event event : allEvents)
            {
                if(event.getPersonID().equals(person.getPersonID()))
                {
                    events.add(event);
                }
            }
        }
    }

    /**
     * filters the males and females
     */
    public void groupMaleFemale()
    {
        for(Person person: entireFamily)
        {
            if(person.getGender().equals("f"))
            {
                femalesPeople.add(person);
            }
            else if(person.getGender().equals("m"))
            {
                malesPeople.add(person);
            }
        }
    }

    /**
     * filters the father side and the mother side
     * @param userPerson
     */
    public void groupFatherMother(Person userPerson)
    {
        //find the father and mother first
        Person mother = findMother(userPerson);
        Person father = findFather(userPerson);

        //base case
        if(mother == null && father == null)
            return;

        fatherSidePeople.add(father);
        motherSidePeople.add(mother);

        groupFatherMother(father);
        groupFatherMother(mother);
    }

    /**
     * returns the mother of a child
     * @param child
     * @return
     */
    public Person findMother(Person child)
    {
        for(Person person: entireFamily)
        {
            if(child.getMother() != null && person.getPersonID() != null)
            {
                if(child.getMother().equals(person.getPersonID()))
                {
                    return person;
                }
            }
        }
        return null;
    }

    /**
     * returns the spous fo a person
     * @param person1
     * @return
     */
    public Person findSpouse(Person person1)
    {
        for(Person person: entireFamily)
        {
            if(person1.getSpouse() != null && person.getPersonID() != null)
            {
                if(person1.getSpouse().equals(person.getPersonID()))
                {
                    return person;
                }
            }
        }
        return null;
    }

    /**
     * returns the father of a person
     * @param child
     * @return
     */
    public Person findFather(Person child)
    {
        for(Person person: entireFamily)
        {
            if(child.getFather() != null && person.getPersonID() != null)
            {
                if(child.getFather().equals(person.getPersonID()))
                {
                    return person;
                }
            }
        }
        return null;
    }


    //GETTERS AND SETTERS

    public static Filter getFilter_instance() {
        return filter_instance;
    }

    public static void setFilter_instance(Filter filter_instance) {
        Filter.filter_instance = filter_instance;
    }

    public Singleton getSingleton() {
        return singleton;
    }

    public void setSingleton(Singleton singleton) {
        this.singleton = singleton;
    }

    public ArrayList<Person> getEntireFamily() {
        return entireFamily;
    }

    public void setEntireFamily(ArrayList<Person> entireFamily) {
        this.entireFamily = entireFamily;
    }

    public ArrayList<Person> getFatherSidePeople() {
        return fatherSidePeople;
    }

    public void setFatherSidePeople(ArrayList<Person> fatherSidePeople) {
        this.fatherSidePeople = fatherSidePeople;
    }

    public ArrayList<Person> getMotherSidePeople() {
        return motherSidePeople;
    }

    public void setMotherSidePeople(ArrayList<Person> motherSidePeople) {
        this.motherSidePeople = motherSidePeople;
    }

    public ArrayList<Person> getMalesPeople() {
        return malesPeople;
    }

    public void setMalesPeople(ArrayList<Person> malesPeople) {
        this.malesPeople = malesPeople;
    }

    public ArrayList<Person> getFemalesPeople() {
        return femalesPeople;
    }

    public void setFemalesPeople(ArrayList<Person> femalesPeople) {
        this.femalesPeople = femalesPeople;
    }

    public ArrayList<Event> getFilteredEvents() {
        return filteredEvents;
    }

    public void setFilteredEvents(ArrayList<Event> filteredEvents) {
        this.filteredEvents = filteredEvents;
    }

    public ArrayList<Event> getAllEvents() {
        return allEvents;
    }

    public void setAllEvents(ArrayList<Event> allEvents) {
        this.allEvents = allEvents;
    }

    public ArrayList<Event> getFatherSideEvents() {
        return fatherSideEvents;
    }

    public void setFatherSideEvents(ArrayList<Event> fatherSideEvents) {
        this.fatherSideEvents = fatherSideEvents;
    }

    public ArrayList<Event> getMotherSideEvents() {
        return motherSideEvents;
    }

    public void setMotherSideEvents(ArrayList<Event> motherSideEvents) {
        this.motherSideEvents = motherSideEvents;
    }

    public ArrayList<Event> getMaleEvents() {
        return maleEvents;
    }

    public void setMaleEvents(ArrayList<Event> maleEvents) {
        this.maleEvents = maleEvents;
    }

    public ArrayList<Event> getFemaleEvents() {
        return femaleEvents;
    }

    public void setFemaleEvents(ArrayList<Event> femaleEvents) {
        this.femaleEvents = femaleEvents;
    }

    public Map<String, Boolean> getEventMapSwitches() {
        return eventMapSwitches;
    }

    public void setEventMapSwitches(Map<String, Boolean> eventMapSwitches) {
        this.eventMapSwitches = eventMapSwitches;
    }

    public Map<String, ArrayList<Event>> getEventTypesMapEvents() {
        return eventTypesMapEvents;
    }

    public void setEventTypesMapEvents(Map<String, ArrayList<Event>> eventTypesMapEvents) {
        this.eventTypesMapEvents = eventTypesMapEvents;
    }

    public Boolean getFathersSide() {
        return fathersSide;
    }

    public void setFathersSide(Boolean fathersSide) {
        this.fathersSide = fathersSide;
    }

    public Boolean getMothersSide() {
        return mothersSide;
    }

    public void setMothersSide(Boolean mothersSide) {
        this.mothersSide = mothersSide;
    }

    public Boolean getMale() {
        return male;
    }

    public void setMale(Boolean male) {
        this.male = male;
    }

    public Boolean getFemale() {
        return female;
    }

    public void setFemale(Boolean female) {
        this.female = female;
    }


    ///hmmmmmm
    // should i organize it into a tree first??

    //and then i can easily get the father's side??
    //and the mother's side??

    //soooo if i was just parsing through a list of persons...
    //i could get the user person...
    //and then check for their father and mother
    //and add them to the proper lists..
    //and then keep doing that for each new person
    //until it can't be done anymore
    //and then check at the end for the size of the list.
    //easy

}
