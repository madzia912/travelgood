package travelgood.rest.ws;

import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lameduck.client.CancelReservationFault_Exception;
import niceview.client.BookHotelFault_Exception;
import niceview.client.CancelHotelFault_Exception;
import travelgood.model.FlightHelper;
import travelgood.model.HotelHelper;
import travelgood.model.ItineraryException;
import travelgood.model.ItineraryHelper;
import travelgood.rest.utils.NextStepsUtils;
import travelgood.utils.DateUtils;
import travelgood.utils.model.CreditCard;
import travelgood.utils.model.Flights;
import travelgood.utils.model.Hotels;
import travelgood.utils.model.Itinerary;
import travelgood.utils.model.rest.BookFlightResponse;
import travelgood.utils.model.rest.BookHotelResponse;
import travelgood.utils.model.rest.BookItineraryResponse;
import travelgood.utils.model.rest.CancelItineraryResponse;
import travelgood.utils.model.rest.CreateItineraryResponse;
import travelgood.utils.model.rest.DeleteFlightResponse;
import travelgood.utils.model.rest.DeleteHotelResponse;
import travelgood.utils.model.rest.GetFlightsResponse;
import travelgood.utils.model.rest.GetHotelsResponse;
import travelgood.utils.model.rest.GetItineraryResponse;

/**
 *
 * @author Bartosz Grzegorz Cichecki
 */
@Path("/itinerary")
public class ItineraryResource {

    private ItineraryHelper itineraryHelper = new ItineraryHelper();
    private FlightHelper flightHelper = new FlightHelper();
    private HotelHelper hotelHelper = new HotelHelper();

    @PUT
    @Path("/{userId}")
    public Response createItinerary(@PathParam("userId") String userId, @Context HttpServletRequest request, @Context final HttpServletResponse response) {
        Itinerary itinerary = itineraryHelper.createItinerary(userId);

        CreateItineraryResponse result = new CreateItineraryResponse();
        result.setBookingNumber(itinerary.getBookingNumber());
        result.getNextStepUrls().addAll(NextStepsUtils.getNextStepsForItinerary(request, itinerary));
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @GET
    @Path("/{bookingNumber}")
    @Produces(MediaType.APPLICATION_XML)
    public Response getItinerary(@PathParam("bookingNumber") String itineraryBookingNumber, @Context HttpServletRequest request, @Context final HttpServletResponse response) {
        try {
            Itinerary itinerary = itineraryHelper.getItinerary(itineraryBookingNumber);

            GetItineraryResponse result = new GetItineraryResponse();
            result.setItinerary(itinerary);
            result.getNextStepUrls().addAll(NextStepsUtils.getNextStepsForItinerary(request, itinerary));
            return Response.status(Response.Status.OK).entity(result).build();
        } catch (ItineraryException ex) {
            Logger.getLogger(ItineraryResource.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("/{bookingNumber}/book")
    @Consumes(MediaType.APPLICATION_XML)
    public Response bookItinerary(@PathParam("bookingNumber") String itineraryBookingNumber, CreditCard creditCard, @Context HttpServletRequest request, @Context final HttpServletResponse response) throws lameduck.client.BookFlightFault_Exception, niceview.client.BookHotelFault_Exception {
        try {
            Itinerary itinerary = itineraryHelper.bookItinerary(itineraryBookingNumber, creditCard);

            BookItineraryResponse result = new BookItineraryResponse();
            result.setBooked(itinerary.isBooked());
            result.getNextStepUrls().addAll(NextStepsUtils.getNextStepsForItinerary(request, itinerary));
            return Response.status(Response.Status.OK).entity(result).build();
        } catch (ItineraryException ex) {
            Logger.getLogger(ItineraryResource.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        } catch (lameduck.client.CancelReservationFault_Exception ex) {
            Logger.getLogger(ItineraryResource.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        } catch (niceview.client.CancelHotelFault_Exception ex) {
            Logger.getLogger(ItineraryResource.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }

    @DELETE
    @Path("/{bookingNumber}")
    @Consumes(MediaType.APPLICATION_XML)
    public Response cancelItinerary(@PathParam("bookingNumber") String itineraryBookingNumber, CreditCard creditCard, @Context HttpServletRequest request, @Context final HttpServletResponse response) throws lameduck.client.CancelReservationFault_Exception, niceview.client.CancelHotelFault_Exception {
        try {
            Itinerary itinerary = itineraryHelper.cancelItinerary(itineraryBookingNumber, creditCard);

            CancelItineraryResponse result = new CancelItineraryResponse();
            result.setCancelled(itinerary.isCancelled());
            result.getNextStepUrls().addAll(NextStepsUtils.getNextStepsForItinerary(request, itinerary));
            return Response.status(Response.Status.OK).entity(result).build();
        } catch (ItineraryException ex) {
            Logger.getLogger(ItineraryResource.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }

    @GET
    @Path("/{bookingNumber}/flight/{from}/{to}/{date}")
    @Produces(MediaType.APPLICATION_XML)
    public Response getFlights(@PathParam("bookingNumber") String itineraryBookingNumber, @PathParam("from") String from, @PathParam("to") String to, @PathParam("date") String date, @Context HttpServletRequest request, @Context final HttpServletResponse response) {
        try {
            Flights flights = flightHelper.getFlights(from, to, DateUtils.stringToDate(date));

            GetFlightsResponse result = new GetFlightsResponse();
            result.setFlights(flights);
            result.getNextStepUrls().addAll(NextStepsUtils.getNextStepsForGetFlights(request, itineraryBookingNumber, flights));
            return Response.status(Response.Status.OK).entity(result).build();
        } catch (ParseException ex) {
            Logger.getLogger(ItineraryResource.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Path("/{bookingNumber}/flight/{flightBookingNumber}")
    public Response addFlight(@PathParam("bookingNumber") String itineraryBookingNumber, @PathParam("flightBookingNumber") String flightBookingNumber, @Context HttpServletRequest request, @Context final HttpServletResponse response) {
        try {
            boolean booked = itineraryHelper.addFlight(itineraryBookingNumber, flightBookingNumber);

            BookFlightResponse result = new BookFlightResponse();
            result.setBooked(booked);
            result.getNextStepUrls().addAll(NextStepsUtils.getNextStepsForBookFlight(request, itineraryBookingNumber, flightBookingNumber));
            return Response.status(Response.Status.OK).entity(result).build();
        } catch (ItineraryException ex) {
            Logger.getLogger(ItineraryResource.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }

    @DELETE
    @Path("/{bookingNumber}/flight/{flightBookingNumber}")
    public Response deleteFlight(@PathParam("bookingNumber") String itineraryBookingNumber, @PathParam("flightBookingNumber") String flightBookingNumber, @Context HttpServletRequest request, @Context final HttpServletResponse response) {
        try {
            boolean deleted = itineraryHelper.deleteFlight(itineraryBookingNumber, flightBookingNumber);

            DeleteFlightResponse result = new DeleteFlightResponse();
            result.setDeleted(deleted);
            result.getNextStepUrls().addAll(NextStepsUtils.getNextStepsForDeleteFlight(request, itineraryBookingNumber, flightBookingNumber));
            return Response.status(Response.Status.OK).entity(result).build();
        } catch (ItineraryException ex) {
            Logger.getLogger(ItineraryResource.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }

    @GET
    @Path("/{bookingNumber}/hotel/{city}/{arrivalDate}/{departureDate}")
    @Produces(MediaType.APPLICATION_XML)
    public Response getHotels(@PathParam("bookingNumber") String itineraryBookingNumber, @PathParam("city") String city, @PathParam("arrivalDate") String arrivalDate, @PathParam("departureDate") String departureDate, @Context HttpServletRequest request, @Context final HttpServletResponse response) {
        try {
            Hotels hotels = hotelHelper.getHotels(city, DateUtils.stringToDate(arrivalDate), DateUtils.stringToDate(departureDate));

            GetHotelsResponse result = new GetHotelsResponse();
            result.setHotels(hotels);
            result.getNextStepUrls().addAll(NextStepsUtils.getNextStepsForGetHotels(request, itineraryBookingNumber, hotels));
            return Response.status(Response.Status.OK).entity(result).build();
        } catch (ParseException ex) {
            Logger.getLogger(ItineraryResource.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Path("/{bookingNumber}/hotel/{hotelBookingNumber}")
    public Response addHotel(@PathParam("bookingNumber") String itineraryBookingNumber, @PathParam("hotelBookingNumber") String hotelBookingNumber, @Context HttpServletRequest request, @Context final HttpServletResponse response) {
        try {
            boolean booked = itineraryHelper.addHotel(itineraryBookingNumber, hotelBookingNumber);

            BookHotelResponse result = new BookHotelResponse();
            result.setBooked(booked);
            result.getNextStepUrls().addAll(NextStepsUtils.getNextStepsForBookHotel(request, itineraryBookingNumber, hotelBookingNumber));
            return Response.status(Response.Status.OK).entity(result).build();
        } catch (ItineraryException ex) {
            Logger.getLogger(ItineraryResource.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }

    @DELETE
    @Path("/{bookingNumber}/hotel/{hotelBookingNumber}")
    public Response deleteHotel(@PathParam("bookingNumber") String itineraryBookingNumber, @PathParam("hotelBookingNumber") String hotelBookingNumber, @Context HttpServletRequest request, @Context final HttpServletResponse response) {
        try {
            boolean deleted = itineraryHelper.deleteHotel(itineraryBookingNumber, hotelBookingNumber);

            DeleteHotelResponse result = new DeleteHotelResponse();
            result.setDeleted(deleted);
            result.getNextStepUrls().addAll(NextStepsUtils.getNextStepsForDeleteHotel(request, itineraryBookingNumber, hotelBookingNumber));
            return Response.status(Response.Status.OK).entity(deleted).build();
        } catch (ItineraryException ex) {
            Logger.getLogger(ItineraryResource.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }
}
