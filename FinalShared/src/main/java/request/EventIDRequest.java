package request;

/**
 * Represents a request object to return the single Event object with the specified ID.
 */

public class EventIDRequest {

    String authID;
    String eventID;

    /**
     * Empty constructor
     */
    public EventIDRequest() {
    }

    public String getAuthID() {
        return authID;
    }

    public void setAuthID(String authID) {
        this.authID = authID;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }
}
