package travelgood.rest;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import javax.ws.rs.core.MultivaluedMap;
import travelgood.utils.model.CreditCard;
import travelgood.utils.model.rest.GetFlightsResponse;
import travelgood.utils.model.rest.GetHotelsResponse;
import travelgood.utils.model.rest.GetItineraryResponse;

/**
 * Jersey REST client generated for REST resource:ItineraryResource [/itinerary]<br> USAGE:
 * <pre>
 *        TravelGoodRestClient client = new TravelGoodRestClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Bartosz Grzegorz Cichecki
 */
public class TravelGoodRestClient {

    private WebResource webResource;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/travelgood/rest";

    public TravelGoodRestClient() {
        com.sun.jersey.api.client.config.ClientConfig config = new com.sun.jersey.api.client.config.DefaultClientConfig();
        client = Client.create(config);
        webResource = client.resource(BASE_URI).path("itinerary");
    }

    public GetHotelsResponse getHotels(String bookingNumber, String city, String arrivalDate, String departureDate) throws UniformInterfaceException {
        WebResource resource = webResource;
        resource = resource.path(java.text.MessageFormat.format("{0}/hotel/{1}/{2}/{3}", new Object[]{bookingNumber, city, arrivalDate, departureDate}));
        return resource.accept(javax.ws.rs.core.MediaType.APPLICATION_XML).get(GetHotelsResponse.class);
    }

    // Because of limitations of java.net.HttpURLConnection DELETE request cannot transmit entity, so credit card data is send in url.
    // http://bugs.sun.com/view_bug.do?bug_id=7157360
    public ClientResponse cancelItinerary(CreditCard cc, String bookingNumber) throws UniformInterfaceException {
        MultivaluedMap<String, String> params = new MultivaluedMapImpl();
        params.putSingle("name", cc.getName());
        params.putSingle("month", Integer.toString(cc.getExpMonth()));
        params.putSingle("year", Integer.toString(cc.getExpYear()));
        params.putSingle("number", cc.getNumber());
        return webResource.path(java.text.MessageFormat.format("{0}", new Object[]{bookingNumber})).queryParams(params).delete(ClientResponse.class);
    }

    public ClientResponse createItinerary(String userId) throws UniformInterfaceException {
        return webResource.path(java.text.MessageFormat.format("{0}", new Object[]{userId})).put(ClientResponse.class);
    }

    public ClientResponse addHotel(String bookingNumber, String hotelBookingNumber) throws UniformInterfaceException {
        return webResource.path(java.text.MessageFormat.format("{0}/hotel/{1}", new Object[]{bookingNumber, hotelBookingNumber})).post(ClientResponse.class);
    }

    public ClientResponse bookItinerary(Object requestEntity, String bookingNumber) throws UniformInterfaceException {
        return webResource.path(java.text.MessageFormat.format("{0}/book", new Object[]{bookingNumber})).type(javax.ws.rs.core.MediaType.APPLICATION_XML).post(ClientResponse.class, requestEntity);
    }

    public GetFlightsResponse getFlights(String bookingNumber, String from, String to, String date) throws UniformInterfaceException {
        WebResource resource = webResource;
        resource = resource.path(java.text.MessageFormat.format("{0}/flight/{1}/{2}/{3}", new Object[]{bookingNumber, from, to, date}));
        return resource.accept(javax.ws.rs.core.MediaType.APPLICATION_XML).get(GetFlightsResponse.class);
    }

    public ClientResponse addFlight(String bookingNumber, String flightBookingNumber) throws UniformInterfaceException {
        return webResource.path(java.text.MessageFormat.format("{0}/flight/{1}", new Object[]{bookingNumber, flightBookingNumber})).post(ClientResponse.class);
    }

    public GetItineraryResponse getItinerary(String bookingNumber) throws UniformInterfaceException {
        WebResource resource = webResource;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{bookingNumber}));
        return resource.accept(javax.ws.rs.core.MediaType.APPLICATION_XML).get(GetItineraryResponse.class);
    }

    public void close() {
        client.destroy();
    }
}
