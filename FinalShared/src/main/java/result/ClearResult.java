package result;

/**
 * Represents the Clear Result object with the appropriate message
 * depending on the outcome of the clear service.
 */

public class ClearResult implements Result {

    /**
     * ClearResult's success message
     */
    String message;


    /**
     * Empty constructor
     */
    public ClearResult() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String successMessage) {
        this.message = successMessage;
    }
}
