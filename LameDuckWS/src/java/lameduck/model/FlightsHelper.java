package lameduck.model;

import dk.dtu.lameduck.BookFlightFault;
import dk.dtu.lameduck.BookFlightFault_Exception;
import dk.dtu.lameduck.BookFlightResponse;
import dk.dtu.lameduck.CancelReservationFault;
import dk.dtu.lameduck.CancelReservationFault_Exception;
import dk.dtu.lameduck.CancelReservationResponse;
import dk.dtu.travelgood.commons.FlightType;
import dk.dtu.travelgood.commons.FlightsType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Bartosz Grzegorz Cichecki
 */
public class FlightsHelper {

    public FlightsType getFlights(String from, String to, long dateFrom, long dateTo) {
        List<FlightType> flights = FlightsHolder.getInstance().getFlights();
        List<FlightType> sortedFlights = new ArrayList<FlightType>();
        
        for (FlightType ft : flights) {
            boolean flightMatches = true;
            
            if (ft.getTimestamp() >= dateFrom && ft.getTimestamp() <= dateTo) {
                flightMatches &= true;
            }
            if (StringUtils.isNotBlank(from)) {
                flightMatches &= StringUtils.equals(from, ft.getFrom());
            }
            if (StringUtils.isNotBlank(to)) {
                flightMatches &= StringUtils.equals(to, ft.getTo());
            }
            
            if (flightMatches) {
                sortedFlights.add(ft);
            }
        }            
        
        FlightsType ft = new FlightsType();
        ft.getFlight().addAll(sortedFlights);
        return ft;
    }

    public BookFlightResponse bookFlight(String flightId, int places) throws BookFlightFault_Exception {
        FlightType flight = FlightsHolder.getInstance().getFlightById(flightId);
        if (flight == null) {
            BookFlightFault fault = new BookFlightFault();
            fault.setReason("Does not exist.");
            fault.setFlightId(flightId);
            throw new BookFlightFault_Exception("Could not book flight " + flightId + ": does not exist.", fault);
        }
        if (flight.getPlacesLeft() < places) {
            BookFlightFault fault = new BookFlightFault();
            fault.setReason("No places left.");
            fault.setFlightId(flightId);
            throw new BookFlightFault_Exception("Could not book flight " + flightId + ": no places left.", fault);
        }
        
        String reservationId = "flt" + System.nanoTime();
        
        Map<String, ReservationData> reservations = FlightsHolder.getInstance().getReservations();
        flight.setPlacesLeft(flight.getPlacesLeft() - places);
        reservations.put(reservationId, new ReservationData(flight, places));
        
        BookFlightResponse response = new BookFlightResponse();
        response.setReservationId(reservationId);
        response.setPrice(places * flight.getPrice());
        
        return response;
    }

    public CancelReservationResponse cancelFlight(String reservationId) throws CancelReservationFault_Exception {
        Map<String, ReservationData> reservations = FlightsHolder.getInstance().getReservations();
        if (reservations.containsKey(reservationId)) {
            ReservationData reservationData = reservations.remove(reservationId);
            reservationData.getFlight().setPlacesLeft(reservationData.getFlight().getPlacesLeft() + reservationData.getPlacesReserved());
        } else {
            CancelReservationFault fault = new CancelReservationFault();
            fault.setReason("Does not exist.");
            fault.setReservationId(reservationId);
            throw new CancelReservationFault_Exception("Could not remove reservation " + reservationId + ": does not exist.", fault);
        }
        
        CancelReservationResponse response = new CancelReservationResponse();
        response.setStatus("Cancelled");
        
        return response;
    }
}
