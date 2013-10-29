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
 * @author Bartosz Grzegorz Cichecki
 */
@WebService(serviceName = "lameDuckService", portName = "lameDuckPort", endpointInterface = "dk.dtu.lameduck.LameDuckPortType", targetNamespace = "urn:lameduck.dtu.dk", wsdlLocation = "WEB-INF/wsdl/LameDuckWS/lameDuck.wsdl")
public class LameDuckWS {

    public GetFlightsResponse getFlights(GetFlightsRequest request) {
        FlightsHelper helper = new FlightsHelper();
        FlightsType flightsType = helper.getFlights(request.getFrom(), request.getTo(), request.getDateFrom(), request.getDateTo());
        GetFlightsResponse response = new GetFlightsResponse();
        response.setFlights(flightsType);
        return response;
    }

    public BookFlightResponse bookFlight(BookFlightRequest request) throws BookFlightFault_Exception {
        FlightsHelper helper = new FlightsHelper();
        String reservationId = helper.bookFlight(request.getFlightId());
        BookFlightResponse response = new BookFlightResponse();
        response.setReservationId(reservationId);
        return response;
    }

    public CancelReservationResponse cancelReservation(CancelReservationRequest request) throws CancelReservationFault_Exception {
        FlightsHelper helper = new FlightsHelper();
        String status = helper.cancelFlight(request.getReservationId());
        CancelReservationResponse response = new CancelReservationResponse();
        response.setStatus(status);
        return response;
    }
    
}
