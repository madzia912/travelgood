package travelgood.bpel.test;

import dk.dtu.travelgood.GetFlightsRequest;
import dk.dtu.travelgood.GetFlightsResponse;
import dk.dtu.travelgood.GetHotelsRequest;
import dk.dtu.travelgood.GetHotelsResponse;
import static org.junit.Assert.*;
import org.junit.Test;
import travelgood.utils.DateUtils;

/**
 *
 * @author Jacob
 */
public class TravelGoodBPELTest {

    public TravelGoodBPELTest() {
    }

    @Test
    public void getFlights() {
        GetFlightsRequest request = new GetFlightsRequest();

        request.setFrom("ABC");
        request.setTo("CBA");
        request.setTakeOffDate(DateUtils.getXmlGregorianCalendar(2012, 10, 10));

        GetFlightsResponse response = getFlights(request);

        assertTrue(response.getFlights().getFlight().size() == 1);
    }

    @Test
    public void getHotels() {
        GetHotelsRequest request = new GetHotelsRequest();

        request.setCity("Paris");
        request.setArrivalDate(DateUtils.getXmlGregorianCalendar(2012, 10, 10));

        GetHotelsResponse response = getHotels(request);

        assertTrue(response.getHotels().getHotel().get(0).getId().equals("hotel1"));
    }

    private static GetFlightsResponse getFlights(dk.dtu.travelgood.GetFlightsRequest getFlightsRequestPart) {
        dk.dtu.travelgood.TravelGoodService service = new dk.dtu.travelgood.TravelGoodService();
        dk.dtu.travelgood.TravelGoodPortType port = service.getTravelGoodPortTypeBindingPort();
        return port.getFlights(getFlightsRequestPart);
    }

    private static GetHotelsResponse getHotels(dk.dtu.travelgood.GetHotelsRequest getHotelsRequestPart) {
        dk.dtu.travelgood.TravelGoodService service = new dk.dtu.travelgood.TravelGoodService();
        dk.dtu.travelgood.TravelGoodPortType port = service.getTravelGoodPortTypeBindingPort();
        return port.getHotels(getHotelsRequestPart);
    }
}
