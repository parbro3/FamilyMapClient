package model;

import java.util.UUID;

/**
 * Represents a Model class User in memory
 * Only getters and setters because it's a shared dataholder object
 */

public class User {

    /**
     * User's username
     */
    private String userName;
    /**
     * User's password
     */
    private String password;
    /**
     * User's email
     */
    private String email;
    /**
     * User's first name
     */
    private String firstName;
    /**
     * User's last name
     */
    private String lastName;
    /**
     * User's gender (m or f)
     */
    private String gender;
    /**
     * User's ID (UUID)
     */
    private String personID;


    /**
     * Empty constructor to be accessed by Gson
     */
    public User() {}

    public User(String userName, String password, String email, String firstName, String lastName, String gender) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.setPersonID(this.generateID());
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String generateID()
    {
        return UUID.randomUUID().toString();
    }

    public String getPersonID() { return personID; }

    public void setPersonID(String ID) {
        this.personID = ID;
    }

    /*
    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

*/

}
