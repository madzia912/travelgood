package travelgood.rest.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import travelgood.utils.model.Flight;
import travelgood.utils.model.Flights;
import travelgood.utils.model.Hotel;
import travelgood.utils.model.Hotels;
import travelgood.utils.model.Itinerary;
import travelgood.utils.model.BookingState;
import travelgood.utils.model.rest.NextStep;

/**
 * @author Bartosz Grzegorz Cichecki
 */
public class NextStepsUtils {

    public static Collection<NextStep> getNextStepsForItinerary(HttpServletRequest request, Itinerary itinerary) {
        String address = getRestAddress(request);

        List<NextStep> nextSteps = new ArrayList<NextStep>();

        // I now that repeating code is bad, but repetitions below are so that code looks cleaner
        if (BookingState.IN_PROGRESS.equals(itinerary.getBookingState())) {
            nextSteps.add(new NextStep(NextStep.NextStepAction.GET, address, itinerary.getBookingNumber()));
            nextSteps.add(new NextStep(NextStep.NextStepAction.POST, address, itinerary.getBookingNumber(), "book"));
            nextSteps.add(new NextStep(NextStep.NextStepAction.DELETE, address, itinerary.getBookingNumber()));
            nextSteps.add(new NextStep(NextStep.NextStepAction.GET, address, itinerary.getBookingNumber(), "flight"));
            nextSteps.add(new NextStep(NextStep.NextStepAction.GET, address, itinerary.getBookingNumber(), "hotel"));
        }

        if (BookingState.BOOKED.equals(itinerary.getBookingState())) {
            nextSteps.add(new NextStep(NextStep.NextStepAction.GET, address, itinerary.getBookingNumber()));
            nextSteps.add(new NextStep(NextStep.NextStepAction.DELETE, address, itinerary.getBookingNumber()));
        }

        //If the flight has been cancelled then there are no steps
        //if (BookingState.CANCELLED.equals(itinerary.getItineraryState())) {
        //}

        return nextSteps;
    }

    public static Collection<NextStep> getNextStepsForGetFlights(HttpServletRequest request, String itineraryBookingNumber, Flights flights) {
        String address = getRestAddress(request);

        List<NextStep> nextSteps = new ArrayList<NextStep>();
        nextSteps.add(new NextStep(NextStep.NextStepAction.GET, address, itineraryBookingNumber));
        nextSteps.add(new NextStep(NextStep.NextStepAction.POST, address, itineraryBookingNumber, "book"));
        nextSteps.add(new NextStep(NextStep.NextStepAction.DELETE, address, itineraryBookingNumber));
        nextSteps.add(new NextStep(NextStep.NextStepAction.GET, address, itineraryBookingNumber, "flight"));
        nextSteps.add(new NextStep(NextStep.NextStepAction.GET, address, itineraryBookingNumber, "hotel"));

        for (Flight f : flights.getFlights()) {
            nextSteps.add(new NextStep(NextStep.NextStepAction.POST, address, itineraryBookingNumber, "flight", f.getBookingNumber()));
        }

        return nextSteps;
    }

    public static Collection<NextStep> getNextStepsForGetHotels(HttpServletRequest request, String itineraryBookingNumber, Hotels hotels) {
        String address = getRestAddress(request);

        List<NextStep> nextSteps = new ArrayList<NextStep>();
        nextSteps.add(new NextStep(NextStep.NextStepAction.GET, address, itineraryBookingNumber));
        nextSteps.add(new NextStep(NextStep.NextStepAction.POST, address, itineraryBookingNumber, "book"));
        nextSteps.add(new NextStep(NextStep.NextStepAction.DELETE, address, itineraryBookingNumber));
        nextSteps.add(new NextStep(NextStep.NextStepAction.GET, address, itineraryBookingNumber, "flight"));
        nextSteps.add(new NextStep(NextStep.NextStepAction.GET, address, itineraryBookingNumber, "hotel"));

        for (Hotel h : hotels.getHotels()) {
            nextSteps.add(new NextStep(NextStep.NextStepAction.POST, address, itineraryBookingNumber, "hotel", h.getBookingNumber()));
        }

        return nextSteps;
    }

    public static Collection<NextStep> getNextStepsForBookFlight(HttpServletRequest request, String itineraryBookingNumber, String flightBookingNumber) {
        String address = getRestAddress(request);

        List<NextStep> nextSteps = new ArrayList<NextStep>();
        nextSteps.add(new NextStep(NextStep.NextStepAction.GET, address, itineraryBookingNumber));
        nextSteps.add(new NextStep(NextStep.NextStepAction.POST, address, itineraryBookingNumber, "book"));
        nextSteps.add(new NextStep(NextStep.NextStepAction.DELETE, address, itineraryBookingNumber));
        nextSteps.add(new NextStep(NextStep.NextStepAction.GET, address, itineraryBookingNumber, "flight"));
        nextSteps.add(new NextStep(NextStep.NextStepAction.GET, address, itineraryBookingNumber, "hotel"));

        nextSteps.add(new NextStep(NextStep.NextStepAction.DELETE, address, itineraryBookingNumber, "flight", flightBookingNumber));

        return nextSteps;
    }

    public static Collection<NextStep> getNextStepsForBookHotel(HttpServletRequest request, String itineraryBookingNumber, String hotelBookingNumber) {
        String address = getRestAddress(request);

        List<NextStep> nextSteps = new ArrayList<NextStep>();
        nextSteps.add(new NextStep(NextStep.NextStepAction.GET, address, itineraryBookingNumber));
        nextSteps.add(new NextStep(NextStep.NextStepAction.POST, address, itineraryBookingNumber, "book"));
        nextSteps.add(new NextStep(NextStep.NextStepAction.DELETE, address, itineraryBookingNumber));
        nextSteps.add(new NextStep(NextStep.NextStepAction.GET, address, itineraryBookingNumber, "flight"));
        nextSteps.add(new NextStep(NextStep.NextStepAction.GET, address, itineraryBookingNumber, "hotel"));

        nextSteps.add(new NextStep(NextStep.NextStepAction.DELETE, address, itineraryBookingNumber, "hotel", hotelBookingNumber));

        return nextSteps;
    }

    public static Collection<NextStep> getNextStepsForDeleteFlight(HttpServletRequest request, String itineraryBookingNumber, String flightBookingNumber) {
        String address = getRestAddress(request);

        List<NextStep> nextSteps = new ArrayList<NextStep>();
        nextSteps.add(new NextStep(NextStep.NextStepAction.GET, address, itineraryBookingNumber));
        nextSteps.add(new NextStep(NextStep.NextStepAction.POST, address, itineraryBookingNumber, "book"));
        nextSteps.add(new NextStep(NextStep.NextStepAction.DELETE, address, itineraryBookingNumber));
        nextSteps.add(new NextStep(NextStep.NextStepAction.GET, address, itineraryBookingNumber, "flight"));
        nextSteps.add(new NextStep(NextStep.NextStepAction.GET, address, itineraryBookingNumber, "hotel"));

        return nextSteps;
    }

    public static Collection<NextStep> getNextStepsForDeleteHotel(HttpServletRequest request, String itineraryBookingNumber, String flightBookingNumber) {
        String address = getRestAddress(request);

        List<NextStep> nextSteps = new ArrayList<NextStep>();
        nextSteps.add(new NextStep(NextStep.NextStepAction.GET, address, itineraryBookingNumber));
        nextSteps.add(new NextStep(NextStep.NextStepAction.POST, address, itineraryBookingNumber, "book"));
        nextSteps.add(new NextStep(NextStep.NextStepAction.DELETE, address, itineraryBookingNumber));
        nextSteps.add(new NextStep(NextStep.NextStepAction.GET, address, itineraryBookingNumber, "flight"));
        nextSteps.add(new NextStep(NextStep.NextStepAction.GET, address, itineraryBookingNumber, "hotel"));

        return nextSteps;
    }

    private static String getRestAddress(HttpServletRequest request) {
        return StringUtils.substringBefore(request.getRequestURL().toString(), request.getRequestURI()) + "/travelgood/rest/itinerary";
    }
}
