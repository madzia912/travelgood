/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package niceview.model;

import bank.unsecure.ws.CreditCardFaultMessage;
import bank.utils.BankUtils;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author Magda
 */
public class HotelsHelper {
    private final static String ACCOUNT_NAME = "NiceView";
    private final static String ACCOUNT_NUMBER = "50308815";
    
    public GetHotelsResponse getHotels(String name, XMLGregorianCalendar arrival, XMLGregorianCalendar departure)
    {
        List<HotelType> hotels = HotelsHolder.getInstance().getHotels();
        HotelsType hotelsList = new HotelsType();

        for (HotelType ht : hotels) {
            if(ht.getName().equals(name)) {
                hotelsList.getHotel().add(ht);
            }
        }
        GetHotelsResponse ghr = new GetHotelsResponse();
        ghr.setHotels(hotelsList);
        ghr.setHotelReservationName(ACCOUNT_NAME);
        return ghr;
    }
    
    public BookHotelResponse bookHotel(String bookingNumber, CreditCardType creditCard) throws BookHotelFault_Exception
    {
        HotelType hotel = HotelsHolder.getInstance().getHotelByBookingNumber(bookingNumber);
        if (hotel == null) {
            BookHotelFault fault = new BookHotelFault();
            fault.setReason("Does not exist.");
            fault.setBookingNumber(bookingNumber);
            throw new BookHotelFault_Exception("Could not book hotel " + bookingNumber + ": does not exist.", fault);
        }
        
        if(hotel.isCreditCardGuarantee())
        {
            try
            {
                BankUtils.validateCreditCard(hotel.getPrice(), creditCard.getName(), Integer.parseInt(creditCard.getExpMonth()), Integer.parseInt(creditCard.getExpYear()), creditCard.getNumber());
            } catch (CreditCardFaultMessage e)
            {
                System.out.println("SYSOUT: " + e.getMessage() + "\n" + e.getFaultInfo().getMessage());
                BookHotelFault fault = new BookHotelFault();
                fault.setReason(e.getFaultInfo().getMessage());
                fault.setBookingNumber(bookingNumber);
                throw new BookHotelFault_Exception("Could not book hotel " + bookingNumber + ": invalid credit card.", fault);
            }
        }
        try {
            BankUtils.chargeCreditCard(hotel.getPrice(), creditCard.getName(), Integer.parseInt(creditCard.getExpMonth()), Integer.parseInt(creditCard.getExpYear()), creditCard.getNumber(), ACCOUNT_NAME, ACCOUNT_NUMBER);
        } catch (CreditCardFaultMessage e) {
            System.out.println("SYSOUT: " + e.getMessage() + "\n" + e.getFaultInfo().getMessage());
            BookHotelFault fault = new BookHotelFault();
            fault.setReason(e.getFaultInfo().getMessage());
            fault.setBookingNumber(bookingNumber);
            throw new BookHotelFault_Exception("Could not book hotel " + bookingNumber + ": payment fail.", fault);
        }

        Map<String, HotelType> reservations = HotelsHolder.getInstance().getReservations();
        reservations.put(hotel.getBookingNumber(), hotel);

        BookHotelResponse response = new BookHotelResponse();
        response.setBooked(true);
        return response;
    }
    
    public CancelHotelResponse cancelFlight(String bookingNumber) throws CancelHotelFault_Exception {
        Map<String, HotelType> reservations = HotelsHolder.getInstance().getReservations();
        if (reservations.containsKey(bookingNumber)) {
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
