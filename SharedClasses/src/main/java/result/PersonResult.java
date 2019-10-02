package result;
import model.Person;

/**
 * Represents the Person Result object with the appropriate message
 * depending on the outcome of the Person service.
 */

public class PersonResult  implements Result{

    /**
     * PersonResult's error message
     */
    String message;

    /**
     * Array of Person objects
     */
    Person[] data;

    public PersonResult() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Person[] getData() {
        return data;
    }

    public void setData(Person[] data) {
        this.data = data;
    }
}
