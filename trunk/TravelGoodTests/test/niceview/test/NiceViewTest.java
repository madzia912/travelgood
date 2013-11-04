package niceview.test;

import junit.framework.Assert;
import niceview.test.client.BookHotelFault_Exception;
import niceview.test.client.BookHotelRequest;
import niceview.test.client.BookHotelResponse;
import niceview.test.client.CancelHotelFault_Exception;
import niceview.test.client.CancelHotelRequest;
import niceview.test.client.CancelHotelResponse;
import niceview.test.client.CreditCardType;
import niceview.test.client.GetHotelsRequest;
import niceview.test.client.GetHotelsResponse;
import niceview.test.client.HotelType;
import niceview.test.client.NiceViewPortType;
import niceview.test.client.NiceViewService;
import org.junit.Test;
import travelgood.utils.DateUtils;

/**
 *
 * @author Magdalena Furman
 */
public class NiceViewTest {

    private NiceViewPortType port = new NiceViewService().getNiceViewPortTypeBindingPort();

    private NiceViewPortType getPort() {
        return port;
    }

    @Test
    public void testGetHotels() {
        GetHotelsRequest request = new GetHotelsRequest();
        request.setCity("Copenhagen");
        GetHotelsResponse response = getPort().getHotels(request);

        Assert.assertTrue(response != null && response.getHotels() != null && response.getHotels().getHotel() != null);
        Assert.assertTrue(response.getHotels().getHotel().size() > 0);
        Assert.assertEquals("hotel2", response.getHotels().getHotel().get(0).getId());
    }

    @Test
    public void testGetHotels2() {
        GetHotelsRequest request = new GetHotelsRequest();
        request.setCity("Copenhagen");
        request.setArrivalDate(DateUtils.getXmlGregorianCalendar(2013, 1, 1));
        request.setDepartureDate(DateUtils.getXmlGregorianCalendar(2013, 1, 10));

        GetHotelsResponse response = getPort().getHotels(request);

        Assert.assertTrue(response != null && response.getHotels() != null && response.getHotels().getHotel() != null);
        Assert.assertTrue(response.getHotels().getHotel().size() > 0);
        Assert.assertEquals("hotel2", response.getHotels().getHotel().get(0).getId());
    }

    @Test
    public void testBookCancelOK() throws BookHotelFault_Exception, CancelHotelFault_Exception {
        GetHotelsRequest request = new GetHotelsRequest();
        request.setCity("Copenhagen");
        request.setArrivalDate(DateUtils.getXmlGregorianCalendar(2013, 1, 1));
        request.setDepartureDate(DateUtils.getXmlGregorianCalendar(2013, 1, 10));

        GetHotelsResponse response = getPort().getHotels(request);

        HotelType hotel = response.getHotels().getHotel().get(0);

        CreditCardType creditCard = new CreditCardType();
        creditCard.setName("Anne Strandberg");
        creditCard.setExpMonth(5);
        creditCard.setExpYear(9);
        creditCard.setNumber("50408816");

        BookHotelRequest bookRequest = new BookHotelRequest();
        bookRequest.setBookingNumber(hotel.getBookingNumber());
        bookRequest.setCreditCard(creditCard);

        BookHotelResponse bookResponse = getPort().bookHotel(bookRequest);

        Assert.assertTrue(bookResponse.isBooked());

        CancelHotelRequest cancelRequest = new CancelHotelRequest();
        cancelRequest.setBookingNumber(hotel.getBookingNumber());

        CancelHotelResponse cancelResponse = getPort().cancelHotel(cancelRequest);

        Assert.assertTrue(cancelResponse.isCanceled());
    }

    @Test(expected = BookHotelFault_Exception.class)
    public void testFailBook() throws BookHotelFault_Exception {
        BookHotelRequest bookRequest = new BookHotelRequest();
        bookRequest.setBookingNumber("SOME_INVALID_AND_FAKE_AND_WRONG_HOTEL_ID");
        getPort().bookHotel(bookRequest);
    }

    @Test(expected = BookHotelFault_Exception.class)
    public void testFailBook2() throws BookHotelFault_Exception {
        BookHotelRequest bookRequest = new BookHotelRequest();
        bookRequest.setBookingNumber("hotel1");

        CreditCardType creditCard = new CreditCardType();
        creditCard.setName("Bech Camilla");
        creditCard.setExpMonth(7);
        creditCard.setExpYear(9);
        creditCard.setNumber("50408822");
        bookRequest.setCreditCard(creditCard);

        getPort().bookHotel(bookRequest);
    }

    @Test(expected = CancelHotelFault_Exception.class)
    public void testFailCancel() throws BookHotelFault_Exception, CancelHotelFault_Exception {
        CancelHotelRequest cancelRequest = new CancelHotelRequest();
        cancelRequest.setBookingNumber("SOME_INVALID_AND_FAKE_AND_WRONG_RESERVATION_ID");
        getPort().cancelHotel(cancelRequest);
    }
}
