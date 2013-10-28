package travelgood.utils.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents an itinerary consisting of zero or more flights and hotel bookings.
 * 
 * @author Bartosz Cichecki
 */
public class Itinerary {
    
    private String id;
    private List<Flight> flights;
    private List<Hotel> hotels;
    private int finalPrice;
    private PaymentStatus paymentStatus;
    private ItineraryState itineraryState;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Flight> getFlights() {
        if (flights == null) {
            flights = new ArrayList<Flight>();
        }
        return flights;
    }

    public List<Hotel> getHotels() {
        if (hotels == null) {
            hotels = new ArrayList<Hotel>();
        }
        return hotels;
    }

    public int getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(int finalPrice) {
        this.finalPrice = finalPrice;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public ItineraryState getItineraryState() {
        return itineraryState;
    }

    public void setItineraryState(ItineraryState itineraryState) {
        this.itineraryState = itineraryState;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
        hash = 59 * hash + Objects.hashCode(this.flights);
        hash = 59 * hash + Objects.hashCode(this.hotels);
        hash = 59 * hash + this.finalPrice;
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
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.flights, other.flights)) {
            return false;
        }
        if (!Objects.equals(this.hotels, other.hotels)) {
            return false;
        }
        if (this.finalPrice != other.finalPrice) {
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

    @Override
    public String toString() {
        return "Itinerary{" + "id=" + id + ", flights=" + flights + ", hotels=" + hotels + ", finalPrice=" + finalPrice + ", paymentStatus=" + paymentStatus + ", itineraryState=" + itineraryState + '}';
    }
    
}
