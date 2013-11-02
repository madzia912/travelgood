package travelgood.utils.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bartosz Grzegorz Cichecki
 */
public class Itinerary {

    protected String id;
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
}
