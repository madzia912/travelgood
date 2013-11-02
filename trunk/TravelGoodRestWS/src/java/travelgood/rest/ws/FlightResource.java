package travelgood.rest.ws;

import java.text.ParseException;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import travelgood.model.FlightHelper;
import travelgood.utils.DateUtils;
import travelgood.utils.model.Flights;

/**
 *
 * @author Bartosz Grzegorz Cichecki
 */
@Path("/flight")
public class FlightResource {

    private FlightHelper flightHelper = new FlightHelper();

    @GET
    @Path("/{from}/{to}/{date}")
    @Produces(MediaType.APPLICATION_XML)
    public Response getFlights(@PathParam("from") String from, @PathParam("to") String to, @PathParam("date") String date, @Context final HttpServletResponse response) {
        try {
            Flights flights = flightHelper.getFlights(from, to, DateUtils.stringToDate(date));
            return Response.status(Response.Status.OK).entity(flights).build();
        } catch (ParseException ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
