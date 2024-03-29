package travelgood.utils.model;

import java.util.Date;

/**
 *
 * @author Bartosz Grzegorz Cichecki
 */
public class Flight {

    protected String id;
    protected String bookingNumber;
    protected String carrier;
    protected String from;
    protected String to;
    protected Date liftOffDate;
    protected Date landingDate;
    protected int price;
    protected BookingState bookingState;

    public Flight() {
    }

    public Flight(String id, String bookingNumber, String carrier, String from, String to, Date liftOffDate, Date landingDate, int price, BookingState bookingState) {
        this.id = id;
        this.bookingNumber = bookingNumber;
        this.carrier = carrier;
        this.from = from;
        this.to = to;
        this.liftOffDate = liftOffDate;
        this.landingDate = landingDate;
        this.price = price;
        this.bookingState = bookingState;
    }

    public String getId() {
        return id;
    }

    public void setId(String value) {
        this.id = value;
    }

    public String getBookingNumber() {
        return bookingNumber;
    }

    public void setBookingNumber(String value) {
        this.bookingNumber = value;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String value) {
        this.carrier = value;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String value) {
        this.from = value;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String value) {
        this.to = value;
    }

    public Date getLiftOffDate() {
        return liftOffDate;
    }

    public void setLiftOffDate(Date value) {
        this.liftOffDate = value;
    }

    public Date getLandingDate() {
        return landingDate;
    }

    public void setLandingDate(Date value) {
        this.landingDate = value;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int value) {
        this.price = value;
    }
    
    public BookingState getBookingState() {
        return bookingState;
    }

    public void setBookingState(BookingState bookingState) {
        this.bookingState = bookingState;
    }
}
