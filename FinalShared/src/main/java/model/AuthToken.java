package model;

import java.util.UUID;

/**
 * Represents a Model class Authorization Token in memory
 * Only getters and setters because it's a shared dataholder object
 */

public class AuthToken {

    /**
     * username that the authtoken belongs to
     */
    String userName;

    String authTokenID;

    /**
     * Empty constructor to be accessed by Gson
     */
    public AuthToken(){}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String generateID()
    {
        return UUID.randomUUID().toString();
    }

    public String getAuthTokenID() { return authTokenID; }

    public void setAuthTokenID(String ID) {
        this.authTokenID = ID;
    }
}
