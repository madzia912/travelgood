package travelgood.rest.test;

import junit.framework.Assert;
import org.junit.Test;
import travelgood.rest.test.client.FlightResourceClient;
import travelgood.utils.DateUtils;
import travelgood.utils.model.Flights;

/**
 *
 * @author Bartosz Grzegorz Cichecki
 */
public class TravelGoodTest {

    // Remove later just dummy test
    @Test
    public void testGetFlights() {
        FlightResourceClient client = new FlightResourceClient();
        Flights flights = client.getFlights("ABC", "CBA", DateUtils.dateToString(DateUtils.getDate(2012, 10, 10)));
        Assert.assertTrue(flights != null);
        Assert.assertTrue(flights.getFlights() != null);
        Assert.assertTrue(flights.getFlights().size() == 1);
    }
}
