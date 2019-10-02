package request;

import model.Event;
import model.Person;
import model.User;

/**
 * Represents a request object to Clears all data from
 * the database (just like the /clear API), and then loads
 * the posted user, person, and event data into the database.
 */


public class LoadRequest {

    /**
     * LoadRequest's users
     */
    User[] users;
    /**
     * LoadRequest's persons
     */
    Person[] persons;
    /**
     * LoadRequest's events
     */
    Event[] events;

    /**
     * Empty constructor
     */
    public LoadRequest() {}

    public Boolean checkValues()
    {
        return (getUsers()!= null && getEvents()!=null && getPersons()!= null);
    }

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public Person[] getPersons() {
        return persons;
    }

    public void setPersons(Person[] persons) {
        this.persons = persons;
    }

    public Event[] getEvents() {
        return events;
    }

    public void setEvents(Event[] events) {
        this.events = events;
    }
}
