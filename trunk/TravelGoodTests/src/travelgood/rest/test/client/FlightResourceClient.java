package travelgood.rest.test.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import travelgood.utils.model.Flights;

/**
 * Jersey REST client generated for REST resource:FlightResource [/flight]<br> USAGE:
 * <pre>
 *        FlightResourceClient client = new FlightResourceClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Bartosz Grzegorz Cichecki
 */
public class FlightResourceClient {

    private WebResource webResource;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/travelgood/rest";

    public FlightResourceClient() {
        com.sun.jersey.api.client.config.ClientConfig config = new com.sun.jersey.api.client.config.DefaultClientConfig();
        client = Client.create(config);
        webResource = client.resource(BASE_URI).path("flight");
    }

    public Flights getFlights(String from, String to, String date) throws UniformInterfaceException {
        WebResource resource = webResource;
        resource = resource.path(java.text.MessageFormat.format("{0}/{1}/{2}", new Object[]{from, to, date}));
        return resource.accept(javax.ws.rs.core.MediaType.APPLICATION_XML).get(Flights.class);
    }

    public void close() {
        client.destroy();
    }
}
