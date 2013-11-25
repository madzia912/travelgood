package lameduck.ws;

import dk.dtu.lameduck.BookFlightFault_Exception;
import dk.dtu.lameduck.BookFlightRequest;
import dk.dtu.lameduck.BookFlightResponse;
import dk.dtu.lameduck.CancelReservationFault_Exception;
import dk.dtu.lameduck.CancelReservationRequest;
import dk.dtu.lameduck.CancelReservationResponse;
import dk.dtu.lameduck.GetFlightsRequest;
import dk.dtu.lameduck.GetFlightsResponse;
import dk.dtu.travelgood.commons.FlightsType;
import javax.jws.WebService;
import lameduck.model.FlightsHelper;

/**
 * @author Stanislav Skuratovich
 */
@WebService(serviceName = "lameDuckService", portName = "lameDuckPort", endpointInterface = "dk.dtu.lameduck.LameDuckPortType", targetNamespace = "urn:lameduck.dtu.dk", wsdlLocation = "WEB-INF/wsdl/LameDuckWS/lameDuck.wsdl")
public class LameDuckWS {

    private FlightsHelper fh = new FlightsHelper();

    public GetFlightsResponse getFlights(GetFlightsRequest request) {
        FlightsType flightsType = fh.getFlights(request.getBookingNumber(), request.getFrom(), request.getTo(), request.getTakeOffDate());
        GetFlightsResponse response = new GetFlightsResponse();
        response.setFlights(flightsType);
        return response;
    }

    public BookFlightResponse bookFlight(BookFlightRequest request) throws BookFlightFault_Exception {
        return fh.bookFlight(request.getBookingNumber(), request.getCreditCard());
    }

    public CancelReservationResponse cancelReservation(CancelReservationRequest request) throws CancelReservationFault_Exception {
        return fh.cancelFlight(request.getBookingNumber(), request.getCreditCard(), request.getPrice());
    }
}
