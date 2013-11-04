package travelgood.utils.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bartosz Grzegorz Cichecki
 */
public class Itinerary {

    protected String id;
    protected String bookingNumber;
    protected int finalPrice;
    protected List<Flight> flights;
    protected List<Hotel> hotels;
    protected PaymentStatus paymentStatus;
    protected ItineraryState itineraryState;

    public String getId() {
        return id;
    }

    public void setId(String value) {
        this.id = value;
    }

    public String getBookingNumber() {
        return bookingNumber;
    }

    public void setBookingNumber(String bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    public int getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(int value) {
        this.finalPrice = value;
    }

    public List<Hotel> getHotels() {
        if (hotels == null) {
            hotels = new ArrayList<Hotel>();
        }
        return this.hotels;
    }

    public List<Flight> getFlights() {
        if (flights == null) {
            flights = new ArrayList<Flight>();
        }
        return this.flights;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus value) {
        this.paymentStatus = value;
    }

    public ItineraryState getItineraryState() {
        return itineraryState;
    }

    public void setItineraryState(ItineraryState value) {
        this.itineraryState = value;
    }
    
    public boolean isBooked() {
        return itineraryState.BOOKED.equals(this.itineraryState);
    }
    
    public boolean isCancelled() {
        return itineraryState.CANCELLED.equals(this.itineraryState);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 59 * hash + (this.bookingNumber != null ? this.bookingNumber.hashCode() : 0);
        hash = 59 * hash + this.finalPrice;
        hash = 59 * hash + (this.flights != null ? this.flights.hashCode() : 0);
        hash = 59 * hash + (this.hotels != null ? this.hotels.hashCode() : 0);
        hash = 59 * hash + (this.paymentStatus != null ? this.paymentStatus.hashCode() : 0);
        hash = 59 * hash + (this.itineraryState != null ? this.itineraryState.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Itinerary other = (Itinerary) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        if ((this.bookingNumber == null) ? (other.bookingNumber != null) : !this.bookingNumber.equals(other.bookingNumber)) {
            return false;
        }
        if (this.finalPrice != other.finalPrice) {
            return false;
        }
        if (this.flights != other.flights && (this.flights == null || !this.flights.equals(other.flights))) {
            return false;
        }
        if (this.hotels != other.hotels && (this.hotels == null || !this.hotels.equals(other.hotels))) {
            return false;
        }
        if (this.paymentStatus != other.paymentStatus) {
            return false;
        }
        if (this.itineraryState != other.itineraryState) {
            return false;
        }
        return true;
    }
    
}
