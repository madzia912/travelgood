package niceview.model;

import bank.unsecure.ws.CreditCardFaultMessage;
import bank.utils.BankUtils;
import com.rits.cloning.Cloner;
import dk.dtu.niceview.BookHotelFault;
import dk.dtu.niceview.BookHotelFault_Exception;
import dk.dtu.niceview.BookHotelResponse;
import dk.dtu.niceview.CancelHotelFault;
import dk.dtu.niceview.CancelHotelFault_Exception;
import dk.dtu.niceview.CancelHotelResponse;
import dk.dtu.niceview.GetHotelsResponse;
import dk.dtu.travelgood.commons.CreditCardType;
import dk.dtu.travelgood.commons.HotelType;
import dk.dtu.travelgood.commons.HotelsType;
import java.util.List;
import java.util.Map;
import javax.xml.datatype.XMLGregorianCalendar;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Magdalena Furman
 */
public class HotelsHelper {

    public GetHotelsResponse getHotels(String bookingNumber, String city, XMLGregorianCalendar arrival, XMLGregorianCalendar departure) {
        List<HotelType> hotels = HotelsHolder.getInstance().getHotels();
        HotelsType hotelsList = new HotelsType();

        for (HotelType ht : hotels) {
            if (StringUtils.equals(bookingNumber, ht.getBookingNumber()) || StringUtils.equals(city, ht.getAddress().getCity())) {
                Cloner cloner = new Cloner();
                HotelType htClone = cloner.deepClone(ht);
                
                htClone.setArrivalDate(arrival);
                htClone.setDepartureDate(departure);
                hotelsList.getHotel().add(htClone);
            }
        }
        GetHotelsResponse ghr = new GetHotelsResponse();
        ghr.setHotels(hotelsList);
        return ghr;
    }

    public BookHotelResponse bookHotel(String bookingNumber, CreditCardType creditCard) throws BookHotelFault_Exception {
        HotelType hotel = HotelsHolder.getInstance().getHotelByBookingNumber(bookingNumber);
        if (hotel == null) {
            BookHotelFault fault = new BookHotelFault();
            fault.setReason("Does not exist.");
            fault.setBookingNumber(bookingNumber);
            throw new BookHotelFault_Exception("Could not book hotel " + bookingNumber + ": does not exist.", fault);
        }

        if (hotel.isCreditCardGuarantee()) {
            try {
                BankUtils.validateCreditCard(hotel.getPrice(), creditCard.getName(), creditCard.getExpMonth(), creditCard.getExpYear(), creditCard.getNumber());
            } catch (CreditCardFaultMessage e) {
                System.out.println("SYSOUT: " + e.getMessage() + "\n" + e.getFaultInfo().getMessage());
                BookHotelFault fault = new BookHotelFault();
                fault.setReason(e.getFaultInfo().getMessage());
                fault.setBookingNumber(bookingNumber);
                throw new BookHotelFault_Exception("Could not book hotel " + bookingNumber + ": invalid credit card.", fault);
            } catch (Exception e) {
                System.out.println("SYSOUT: " + e.getMessage() + "\n" + e.getMessage());
                BookHotelFault fault = new BookHotelFault();
                fault.setReason(e.getMessage());
                fault.setBookingNumber(bookingNumber);
                throw new BookHotelFault_Exception("Could not book hotel " + bookingNumber + ": unknown exception.", fault);
            }
        }

        Map<String, HotelType> reservations = HotelsHolder.getInstance().getReservations();
        reservations.put(hotel.getBookingNumber(), hotel);

        BookHotelResponse response = new BookHotelResponse();
        response.setBooked(true);
        return response;
    }

    public CancelHotelResponse cancelHotel(String bookingNumber) throws CancelHotelFault_Exception {
        Map<String, HotelType> reservations = HotelsHolder.getInstance().getReservations();
        if (reservations.containsKey(bookingNumber)) {

            //Special case for cancelling fail
            if (StringUtils.equals("bookingNr4", bookingNumber)) {
                CancelHotelFault fault = new CancelHotelFault();
                fault.setReason("You are not allowed to cancel this booking!");
                fault.setBookingNumber(bookingNumber);
                throw new CancelHotelFault_Exception("Could not remove reservation " + bookingNumber + ": not allowed.", fault);
            }

            reservations.remove(bookingNumber);
        } else {
            CancelHotelFault fault = new CancelHotelFault();
            fault.setReason("Does not exist.");
            fault.setBookingNumber(bookingNumber);
            throw new CancelHotelFault_Exception("Could not remove reservation " + bookingNumber + ": does not exist.", fault);
        }

        CancelHotelResponse response = new CancelHotelResponse();
        response.setCanceled(true);
        return response;
    }
}
