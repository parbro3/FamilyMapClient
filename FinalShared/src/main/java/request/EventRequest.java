package request;

/**
 * Represents a request object to returns ALL events for ALL family members
 * of the current user. The current user is determined from the provided auth token.
 */

public class EventRequest {

    String authID;

    /**
     * Empty constructor
     */
    public EventRequest() {
    }

    public String getAuthID() {
        return authID;
    }

    public void setAuthID(String authID) {
        this.authID = authID;
    }
}
