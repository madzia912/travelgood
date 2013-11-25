package lameduck.model;

import bank.unsecure.ws.CreditCardFaultMessage;
import bank.utils.BankUtils;
import dk.dtu.lameduck.BookFlightFault;
import dk.dtu.lameduck.BookFlightFault_Exception;
import dk.dtu.lameduck.BookFlightResponse;
import dk.dtu.lameduck.CancelReservationFault;
import dk.dtu.lameduck.CancelReservationFault_Exception;
import dk.dtu.lameduck.CancelReservationResponse;
import dk.dtu.travelgood.commons.CreditCardType;
import dk.dtu.travelgood.commons.FlightType;
import dk.dtu.travelgood.commons.FlightsType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.XMLGregorianCalendar;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Stanislav Skuratovich
 */
public class FlightsHelper {

    private final static String ACCOUNT_NAME = "LameDuck";
    private final static String ACCOUNT_NUMBER = "50208812";

    public FlightsType getFlights(String bookingNumber, String from, String to, XMLGregorianCalendar date) {
        List<FlightType> flights = FlightsHolder.getInstance().getFlights();
        List<FlightType> sortedFlights = new ArrayList<FlightType>();

        fixDate(date);

        for (FlightType ft : flights) {
            boolean flightMatches = true;

            if (StringUtils.isNotBlank(bookingNumber)) {
                flightMatches &= StringUtils.equals(bookingNumber, ft.getBookingNumber());
            }
            if (date != null) {
                flightMatches &= ft.getLiftOffDate().compare(date) == DatatypeConstants.EQUAL;
            }
            if (StringUtils.isNotBlank(from)) {
                flightMatches &= StringUtils.equals(from, ft.getFrom());
            }
            if (StringUtils.isNotBlank(to)) {
                flightMatches &= StringUtils.equals(to, ft.getTo());
            }

            if (flightMatches) {
                sortedFlights.add(ft);
            }
        }

        FlightsType ft = new FlightsType();
        ft.getFlight().addAll(sortedFlights);
        return ft;
    }

    public BookFlightResponse bookFlight(String bookingNumber, CreditCardType creditCard) throws BookFlightFault_Exception {
        FlightType flight = FlightsHolder.getInstance().getFlightByBookingNumber(bookingNumber);
        if (flight == null) {
            BookFlightFault fault = new BookFlightFault();
            fault.setReason("Does not exist.");
            fault.setBookingNumber(bookingNumber);
            throw new BookFlightFault_Exception("Could not book flight " + bookingNumber + ": does not exist.", fault);
        }

        try {
            BankUtils.chargeCreditCard(flight.getPrice(), creditCard.getName(), creditCard.getExpMonth(), creditCard.getExpYear(), creditCard.getNumber(), ACCOUNT_NAME, ACCOUNT_NUMBER);
        } catch (CreditCardFaultMessage e) {
            System.out.println("SYSOUT: " + e.getMessage() + "\n" + e.getFaultInfo().getMessage());
            BookFlightFault fault = new BookFlightFault();
            fault.setReason(e.getFaultInfo().getMessage());
            fault.setBookingNumber(bookingNumber);
            throw new BookFlightFault_Exception("Could not book flight " + bookingNumber + ": payment fail.", fault);
        } catch (Exception e) {
            System.out.println("SYSOUT: " + e.getMessage() + "\n" + e.getMessage());
            BookFlightFault fault = new BookFlightFault();
            fault.setReason(e.getMessage());
            fault.setBookingNumber(bookingNumber);
            throw new BookFlightFault_Exception("Could not book flight " + bookingNumber + ": unknown exception.", fault);
        }

        Map<String, FlightType> reservations = FlightsHolder.getInstance().getReservations();
        reservations.put(flight.getBookingNumber(), flight);

        BookFlightResponse response = new BookFlightResponse();
        response.setBooked(true);
        return response;
    }

    public CancelReservationResponse cancelFlight(String bookingNumber, CreditCardType creditCard, int price) throws CancelReservationFault_Exception {
        Map<String, FlightType> reservations = FlightsHolder.getInstance().getReservations();
        if (reservations.containsKey(bookingNumber)) {
            reservations.remove(bookingNumber);
        } else {
            CancelReservationFault fault = new CancelReservationFault();
            fault.setReason("Does not exist.");
            fault.setBookingNumber(bookingNumber);
            throw new CancelReservationFault_Exception("Could not remove reservation " + bookingNumber + ": does not exist.", fault);
        }


        try {
            BankUtils.refundCreditCard(price, creditCard.getName(), creditCard.getExpMonth(), creditCard.getExpYear(), creditCard.getNumber(), ACCOUNT_NAME, ACCOUNT_NUMBER);
        } catch (CreditCardFaultMessage e) {
            System.out.println("SYSOUT: " + e.getMessage() + "\n" + e.getFaultInfo().getMessage());
            CancelReservationFault fault = new CancelReservationFault();
            fault.setReason(e.getFaultInfo().getMessage());
            fault.setBookingNumber(bookingNumber);
            throw new CancelReservationFault_Exception("Could not cancel flight " + bookingNumber + ": refund fail.", fault);
        }  catch (Exception e) {
            System.out.println("SYSOUT: " + e.getMessage() + "\n" + e.getMessage());
            CancelReservationFault fault = new CancelReservationFault();
            fault.setReason(e.getMessage());
            fault.setBookingNumber(bookingNumber);
            throw new CancelReservationFault_Exception("Could not cancel flight " + bookingNumber + ": unknown exception.", fault);
        }

        CancelReservationResponse response = new CancelReservationResponse();
        response.setCanceled(true);
        return response;
    }

    private void fixDate(XMLGregorianCalendar date) {
        if (date == null) {
            return;
        }
        date.setHour(DatatypeConstants.FIELD_UNDEFINED);
        date.setMinute(DatatypeConstants.FIELD_UNDEFINED);
        date.setSecond(DatatypeConstants.FIELD_UNDEFINED);
        date.setMillisecond(DatatypeConstants.FIELD_UNDEFINED);
        date.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
    }
}
