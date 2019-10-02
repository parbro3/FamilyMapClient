package result;

/**
 * Represents the Load Result object with the appropriate message
 * depending on the outcome of the Load service.
 */
public class LoadResult implements Result {

    /**
     * LoadResult's success message
     */
    String message;

    /**
     * Empty constructor
     */
    public LoadResult() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
