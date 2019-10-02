package result;

/**
 * Represents the EventID Result object with the appropriate message and data
 * depending on the outcome of the EventID service.
 */

public class EventIDResult implements Result {

    /**
     * EventIDResult's descendant
     */
    String descendant;
    /**
     * EventIDResult's event ID (UUID)
     */
    String eventID;
    /**
     * EventIDResult's person ID (UUID)
     */
    String personID;
    /**
     * EventIDResult's location latitude
     */
    String latitude;
    /**
     * EventIDResult's location longitude
     */
    String longitude;
    /**
     * EventIDResult's location country
     */
    String country;
    /**
     * EventIDResult's location city
     */
    String city;
    /**
     * EventIDResult's eventType
     */
    String eventType;
    /**
     * EventIDResult's year
     */
    String year;
    /**
     * EventIDResult's error message
     */
    String message;


    /**
     * Empty constructor
     */
    public EventIDResult() {
    }

    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String errorMessage) {
        this.message = errorMessage;
    }
}
