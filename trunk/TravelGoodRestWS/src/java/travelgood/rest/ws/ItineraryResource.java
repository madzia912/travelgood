package travelgood.rest.ws;

import java.util.logging.Level;
import java.util.logging.Logger;
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
import travelgood.model.ItineraryException;
import travelgood.model.ItineraryHelper;
import travelgood.utils.model.CreditCard;
import travelgood.utils.model.Itinerary;

/**
 *
 * @author Bartosz Grzegorz Cichecki
 */
@Path("/itinerary")
public class ItineraryResource {

    private ItineraryHelper itineraryHelper = new ItineraryHelper();

    @PUT
    @Path("/{userId}")
    public Response createItinerary(@PathParam("userId") String userId, @Context final HttpServletResponse response) {
        String bookingNumber = itineraryHelper.createItinerary(userId);
        return Response.status(Response.Status.OK).entity(bookingNumber).build();
    }

    @GET
    @Path("/{bookingNumber}")
    @Produces(MediaType.APPLICATION_XML)
    public Response getItinerary(@PathParam("bookingNumber") String bookingNumber, @Context final HttpServletResponse response) {
        try {
            Itinerary itinerary = itineraryHelper.getItinerary(bookingNumber);
            return Response.status(Response.Status.OK).entity(itinerary).build();
        } catch (ItineraryException ex) {
            Logger.getLogger(ItineraryResource.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("/{bookingNumber}/book")
    @Consumes(MediaType.APPLICATION_XML)
    public Response bookItinerary(@PathParam("bookingNumber") String bookingNumber, CreditCard creditCard, @Context final HttpServletResponse response) {
        try {
            boolean booked = itineraryHelper.bookItinerary(bookingNumber, creditCard);
            return Response.status(Response.Status.OK).entity(booked).build();
        } catch (ItineraryException ex) {
            Logger.getLogger(ItineraryResource.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }

    @DELETE
    @Path("/{bookingNumber}")
    @Consumes(MediaType.APPLICATION_XML)
    public Response cancelItinerary(@PathParam("bookingNumber") String bookingNumber, CreditCard creditCard, @Context final HttpServletResponse response) {
        try {
            boolean booked = itineraryHelper.cancelItinerary(bookingNumber, creditCard);
            return Response.status(Response.Status.OK).entity(booked).build();
        } catch (ItineraryException ex) {
            Logger.getLogger(ItineraryResource.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }

    @POST
    @Path("/{bookingNumber}/flight/{flightBookingNumber}")
    public Response addFlight(@PathParam("bookingNumber") String bookingNumber, @PathParam("flightBookingNumber") String flightBookingNumber, @Context final HttpServletResponse response) {
        try {
            boolean booked = itineraryHelper.addFlight(bookingNumber, flightBookingNumber);
            return Response.status(Response.Status.OK).entity(booked).build();
        } catch (ItineraryException ex) {
            Logger.getLogger(ItineraryResource.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }

    @DELETE
    @Path("/{bookingNumber}/flight/{flightBookingNumber}")
    public Response deleteFlight(@PathParam("bookingNumber") String bookingNumber, @PathParam("flightBookingNumber") String flightBookingNumber, @Context final HttpServletResponse response) {
        try {
            boolean booked = itineraryHelper.deleteFlight(bookingNumber, flightBookingNumber);
            return Response.status(Response.Status.OK).entity(booked).build();
        } catch (ItineraryException ex) {
            Logger.getLogger(ItineraryResource.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }

    @POST
    @Path("/{bookingNumber}/hotel/{hotelBookingNumber}")
    public Response addHotel(@PathParam("bookingNumber") String bookingNumber, @PathParam("hotelBookingNumber") String hotelBookingNumber, @Context final HttpServletResponse response) {
        try {
            boolean booked = itineraryHelper.addHotel(bookingNumber, hotelBookingNumber);
            return Response.status(Response.Status.OK).entity(booked).build();
        } catch (ItineraryException ex) {
            Logger.getLogger(ItineraryResource.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }

    @DELETE
    @Path("/{bookingNumber}/hotel/{hotelBookingNumber}")
    public Response deleteHotel(@PathParam("bookingNumber") String bookingNumber, @PathParam("hotelBookingNumber") String hotelBookingNumber, @Context final HttpServletResponse response) {
        try {
            boolean booked = itineraryHelper.deleteHotel(bookingNumber, hotelBookingNumber);
            return Response.status(Response.Status.OK).entity(booked).build();
        } catch (ItineraryException ex) {
            Logger.getLogger(ItineraryResource.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }
}
