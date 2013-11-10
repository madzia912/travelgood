package travelgood.utils.model;

import java.util.Date;

/**
 *
 * @author Bartosz Grzegorz Cichecki
 */
public class Hotel {

    protected String id;
    protected String bookingNumber;
    protected String name;
    protected Address address;
    protected String provider;
    protected int price;
    protected boolean creditCardGuarantee;
    protected BookingState bookingState;
    protected Date arrivalDate;
    protected Date departureDate;

    public Hotel() {
    }

    public Hotel(String id, String bookingNumber, String name, Address address, String provider, int price, boolean creditCardGuarantee, BookingState bookingState) {
        this.id = id;
        this.bookingNumber = bookingNumber;
        this.name = name;
        this.address = address;
        this.provider = provider;
        this.price = price;
        this.creditCardGuarantee = creditCardGuarantee;
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

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address value) {
        this.address = value;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String value) {
        this.provider = value;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int value) {
        this.price = value;
    }

    public boolean isCreditCardGuarantee() {
        return creditCardGuarantee;
    }

    public void setCreditCardGuarantee(boolean value) {
        this.creditCardGuarantee = value;
    }

    public BookingState getBookingState() {
        return bookingState;
    }

    public void setBookingState(BookingState bookingState) {
        this.bookingState = bookingState;
    }
    
    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }
}
