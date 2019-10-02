package request;

/**
 * Represents a request object to populates the server's database
 * with generated data for the specified user name
 */

public class FillRequest {

    int generations;

    String userName;

    /**
     * Empty constructor
     */
    public FillRequest() {}

    public int getGenerations() {
        return generations;
    }

    public void setGenerations(int generations) {
        this.generations = generations;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean checkValues()
    {
        if(getGenerations() >= 0 && getUserName()!=null)
            return true;
        return false;
    }
}
