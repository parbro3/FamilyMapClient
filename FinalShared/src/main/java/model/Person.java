package model;

import java.util.UUID;

/**
 * Represents a Model class Person in memory
 * Only getters and setters because it's a shared dataholder object
 */

public class Person {

    /**
     * Person's owner/descendant
     */
    String descendant;
    /**
     * Person's first name
     */
    String firstName;
    /**
     * Person's last name
     */
    String lastName;
    /**
     * Person's gender (m or f)
     */
    String gender;
    /**
     * Person's father's ID
     */
    String father;
    /**
     * Person's mother's ID
     */
    String mother;
    /**
     * Person's spouse's ID
     */
    String spouse;

    String personID;

    /**
     * Empty constructor to be accessed by Gson
     */
    public Person(){}

    //personID needs to be generated.. doesn't include father, mother, spouse id because they are optional
    public Person(String descendant, String firstName, String lastName, String gender)
    {
        this.descendant = descendant;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.setPersonID(this.generateID());
    }

    //with already created personID.. doesn't include father, mother, spouse id because they are optional
    public Person(String descendant, String firstName, String lastName, String gender, String personID)
    {
        this.descendant = descendant;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.setPersonID(personID);
    }

    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
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

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getMother() {
        return mother;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public String getSpouse() {
        return spouse;
    }

    public void setSpouse(String spouse) {
        this.spouse = spouse;
    }

    public String generateID()
    {
        return UUID.randomUUID().toString();
    }

    public String getPersonID() { return personID; }

    public void setPersonID(String ID) {
        this.personID = ID;
    }
}
