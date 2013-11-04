package travelgood.model;

/**
 *
 * @author Bartosz Grzegorz Cichecki
 */
public class ItineraryException extends Exception {

    private String reason;
    private String bookingNumber;

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

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setBookingNumber(String bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    public String getBookingNumber() {
        return bookingNumber;
    }

    public ItineraryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
