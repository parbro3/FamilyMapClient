package result;

/**
 * Represents the Register Result object with the appropriate message and data
 * depending on the outcome of the Register service.
 */

public class RegisterResult implements Result{

    /**
     * RegisterResult's error message
     */
    String message;
    /**
     * RegisterResult's authorization token
     */
    String authToken;
    /**
     * RegisterResult's person id (UUID)
     */
    String personID;
    /**
     * RegisterResult's username
     */
    String userName;

    /**
     * Empty constructor
     */
    public RegisterResult() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
