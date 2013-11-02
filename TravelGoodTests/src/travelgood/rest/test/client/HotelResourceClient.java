/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package travelgood.rest.test.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import travelgood.utils.model.Hotels;

/**
 * Jersey REST client generated for REST resource:HotelResource [/hotel]<br> USAGE:
 * <pre>
 *        HotelResourceClient client = new HotelResourceClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Bartosz Grzegorz Cichecki
 */
public class HotelResourceClient {

    private WebResource webResource;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/travelgood/rest";

    public HotelResourceClient() {
        com.sun.jersey.api.client.config.ClientConfig config = new com.sun.jersey.api.client.config.DefaultClientConfig();
        client = Client.create(config);
        webResource = client.resource(BASE_URI).path("hotel");
    }

    public Hotels getHotels(String city, String arrivalDate, String departureDate) throws UniformInterfaceException {
        WebResource resource = webResource;
        resource = resource.path(java.text.MessageFormat.format("{0}/{1}/{2}", new Object[]{city, arrivalDate, departureDate}));
        return resource.accept(javax.ws.rs.core.MediaType.APPLICATION_XML).get(Hotels.class);
    }

    public void close() {
        client.destroy();
    }
}
