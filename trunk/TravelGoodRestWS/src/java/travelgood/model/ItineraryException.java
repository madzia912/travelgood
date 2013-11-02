package travelgood.model;

/**
 *
 * @author Bartosz Grzegorz Cichecki
 */
public class ItineraryException extends Exception {

    public ItineraryException() {
    }

    public ItineraryException(String message) {
        super(message);
    }

    public ItineraryException(String message, Throwable cause) {
        super(message, cause);
    }

    public ItineraryException(Throwable cause) {
        super(cause);
    }

    public ItineraryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
