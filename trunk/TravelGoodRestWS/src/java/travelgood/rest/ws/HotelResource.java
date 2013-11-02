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
import travelgood.model.HotelHelper;
import travelgood.utils.DateUtils;
import travelgood.utils.model.Hotels;

/**
 *
 * @author Bartosz Grzegorz Cichecki
 */
@Path("/hotel")
public class HotelResource {

    private HotelHelper hotelHelper = new HotelHelper();

    @GET
    @Path("/{city}/{arrivalDate}/{departureDate}")
    @Produces(MediaType.APPLICATION_XML)
    public Response getHotels(@PathParam("city") String city, @PathParam("arrivalDate") String arrivalDate, @PathParam("departureDate") String departureDate, @Context final HttpServletResponse response) {
        try {
            Hotels hotels = hotelHelper.getHotels(city, DateUtils.stringToDate(arrivalDate), DateUtils.stringToDate(departureDate));
            return Response.status(Response.Status.OK).entity(hotels).build();
        } catch (ParseException ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
