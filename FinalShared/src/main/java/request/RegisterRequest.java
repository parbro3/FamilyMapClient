package request;

/**
 * Represents a request object to creates a new user account, generates 4
 * generations of ancestor data for the new user, logs the user in,
 * and returns an auth token.
 */


public class RegisterRequest implements Request{

    /**
     * RegisterRequest's username
     */
    String userName;
    /**
     * RegisterRequest's password
     */
    String password;
    /**
     * RegisterRequest's email
     */
    String email;
    /**
     * RegisterRequest's first name
     */
    String firstName;
    /**
     * RegisterRequest's last name
     */
    String lastName;
    /**
     * RegisterRequest's gender
     */
    String gender;


    /**
     * Empty constructor
     */
    public RegisterRequest() {}

    public Boolean checkValues()
    {
        return(getUserName()!= null && getEmail()!=null && getFirstName()!= null &&
                getGender()!= null && getLastName()!= null && getPassWord()!= null);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
