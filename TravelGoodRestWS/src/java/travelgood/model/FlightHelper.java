package travelgood.model;

import java.util.Date;
import java.util.List;
import travelgood.utils.DateUtils;
import travelgood.utils.model.Flight;
import travelgood.utils.model.Flights;

/**
 *
 * @author Magdalena Furman
 */
public class FlightHelper {

    private lameduck.client.LameDuckPortType port = new lameduck.client.LameDuckService().getLameDuckPort();
    
    public Flights getFlights(String from, String to, Date takeOffDate) {
        lameduck.client.GetFlightsRequest flightsRequest = new lameduck.client.GetFlightsRequest();
        flightsRequest.setFrom(from);
        flightsRequest.setTo(to);
        flightsRequest.setTakeOffDate(DateUtils.toXmlGregorianCalendar(takeOffDate));

        lameduck.client.GetFlightsResponse flightsResponse = port.getFlights(flightsRequest);

        List<lameduck.client.FlightType> flights = flightsResponse.getFlights().getFlight();

        Flights result = new Flights();

        for (lameduck.client.FlightType flightType : flights) {
            result.getFlights().add(convertFlightType(flightType));
        }

        return result;
    }
     private Flight convertFlightType(lameduck.client.FlightType flightType) {
        Flight f = new Flight();
        f.setBookingNumber(flightType.getBookingNumber());
        f.setCarrier(flightType.getCarrier());
        f.setFrom(flightType.getFrom());
        f.setId(flightType.getId());
        f.setLandingDate(DateUtils.toDate(flightType.getLandingDate()));
        f.setLiftOffDate(DateUtils.toDate(flightType.getLiftOffDate()));
        f.setPrice(flightType.getPrice());
        f.setTo(flightType.getTo());
        return f;
    }
}
