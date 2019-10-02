package model;

import java.util.UUID;

/**
 * Represents a Model class Event in memory
 * Only getters and setters because it's a shared dataholder object
 */

public class Event {

    /**
     * Event's descendant/owner
     */
    private String descendant;
    /**
     * ID of the person the event belongs to
     */
    private String personID;
    /**
     * Event's location latitude
     */
    private String latitude;
    /**
     * Event's location longitude
     */
    private String longitude;
    /**
     * Event's location country
     */
    private String country;
    /**
     * Event's location city
     */
    private String city;
    /**
     * Type of event
     */
    private String eventType;
    /**
     * Year the event occurred
     */
    private String year;

    private String eventID;

    /**
     * Empty constructor to be accessed by Gson
     */

    public Event(){}

    public Event(String descendant, String personID, String latitude, String longitude, String country, String city, String eventType, String year) {
        this.descendant = descendant;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
        this.setEventID(this.generateID());
    }

    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String generateID()
    {
        return UUID.randomUUID().toString();
    }

    public String getEventID() { return eventID; }

    public void setEventID(String ID) {
        this.eventID = ID;
    }
}
