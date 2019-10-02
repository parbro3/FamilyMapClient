package result;

import model.Event;

/**
 * Represents the Event Result object with the appropriate message
 * depending on the outcome of the Event service.
 */
public class EventResult implements Result {

    /**
     * EventResult's error message
     */
    String message;

    Event[] data;


    /**
     * Empty constructor
     */
    public EventResult() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Event[] getData() {
        return data;
    }

    public void setData(Event[] data) {
        this.data = data;
    }
}
