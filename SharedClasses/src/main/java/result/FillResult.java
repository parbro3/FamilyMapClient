package result;

/**
 * Represents the Fill Result object with the appropriate message
 * depending on the outcome of the Fill service.
 */

public class FillResult implements Result {


    /**
     * FillResult's success message
     */
    String message;

    /**
     * Empty constructor
     */
    public FillResult() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
