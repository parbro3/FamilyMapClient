package request;

/**
 * Represents a request object to clear the database
 */


public class LoginRequest {

    /**
     * LoginRequest's username
     */
    String userName;
    /**
     * LoginRequest's password
     */
    String password;

    /**
     * Empty constructor
     */
    public LoginRequest() {
    }

    public Boolean checkValues()
    {
        return (getPassWord()!= null && getUserName()!=null);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return password;
    }

    public void setPassWord(String passWord) {
        this.password = passWord;
    }
}
