package request;

/**
 * Represents a request object to return
 * ALL family members of the current user.
 * The current user is determined from the provided auth token.
 */


public class PersonRequest {

    String authID;

    /**
     * Empty constructor
     */
    public PersonRequest() {

    }

    public String getAuthID() {
        return authID;
    }

    public void setAuthID(String authID) {
        this.authID = authID;
    }
}
