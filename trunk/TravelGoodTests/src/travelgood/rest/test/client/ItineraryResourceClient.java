/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package travelgood.rest.test.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import travelgood.utils.model.CreditCard;
import travelgood.utils.model.Itinerary;

/**
 * Jersey REST client generated for REST resource:ItineraryResource [/itinerary]<br>
 *  USAGE:
 * <pre>
 *        ItineraryResourceClient client = new ItineraryResourceClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Bartosz Grzegorz Cichecki
 */
public class ItineraryResourceClient {
    private WebResource webResource;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/travelgood/rest";

    public ItineraryResourceClient() {
        com.sun.jersey.api.client.config.ClientConfig config = new com.sun.jersey.api.client.config.DefaultClientConfig();
        client = Client.create(config);
        webResource = client.resource(BASE_URI).path("itinerary");
    }

    public ClientResponse cancelItinerary(CreditCard requestEntity, String bookingNumber) throws UniformInterfaceException {
        return webResource.path(java.text.MessageFormat.format("{0}", new Object[]{bookingNumber})).type(javax.ws.rs.core.MediaType.APPLICATION_XML).delete(ClientResponse.class, requestEntity);
    }

    public ClientResponse createItinerary(String userId) throws UniformInterfaceException {
        return webResource.path(java.text.MessageFormat.format("{0}", new Object[]{userId})).put(ClientResponse.class);
    }

    public ClientResponse addHotel(String bookingNumber, String hotelBookingNumber) throws UniformInterfaceException {
        return webResource.path(java.text.MessageFormat.format("{0}/hotel/{1}", new Object[]{bookingNumber, hotelBookingNumber})).post(ClientResponse.class);
    }

    public ClientResponse bookItinerary(CreditCard requestEntity, String bookingNumber) throws UniformInterfaceException {
        return webResource.path(java.text.MessageFormat.format("{0}/book", new Object[]{bookingNumber})).type(javax.ws.rs.core.MediaType.APPLICATION_XML).post(ClientResponse.class, requestEntity);
    }

    public ClientResponse addFlight(String bookingNumber, String flightBookingNumber) throws UniformInterfaceException {
        return webResource.path(java.text.MessageFormat.format("{0}/flight/{1}", new Object[]{bookingNumber, flightBookingNumber})).post(ClientResponse.class);
    }

    public Itinerary getItinerary(String bookingNumber) throws UniformInterfaceException {
        WebResource resource = webResource;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{bookingNumber}));
        return resource.accept(javax.ws.rs.core.MediaType.APPLICATION_XML).get(Itinerary.class);
    }

    public ClientResponse deleteHotel(String bookingNumber, String hotelBookingNumber) throws UniformInterfaceException {
        return webResource.path(java.text.MessageFormat.format("{0}/hotel/{1}", new Object[]{bookingNumber, hotelBookingNumber})).delete(ClientResponse.class);
    }

    public ClientResponse deleteFlight(String bookingNumber, String flightBookingNumber) throws UniformInterfaceException {
        return webResource.path(java.text.MessageFormat.format("{0}/flight/{1}", new Object[]{bookingNumber, flightBookingNumber})).delete(ClientResponse.class);
    }

    public void close() {
        client.destroy();
    }
    
}
