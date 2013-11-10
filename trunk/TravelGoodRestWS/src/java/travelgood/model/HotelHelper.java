package travelgood.model;

import java.util.Date;
import java.util.List;
import travelgood.utils.DateUtils;
import travelgood.utils.model.Address;
import travelgood.utils.model.Hotel;
import travelgood.utils.model.Hotels;

/**
 *
 * @author Bartosz Grzegorz Cichecki
 */
public class HotelHelper {

    private niceview.client.NiceViewPortType port = new niceview.client.NiceViewService().getNiceViewPortTypeBindingPort();

    public Hotels getHotels(String city, Date arrivalDate, Date departureDate) {
        niceview.client.GetHotelsRequest hotelsRequest = new niceview.client.GetHotelsRequest();
        hotelsRequest.setCity(city);
        hotelsRequest.setArrivalDate(DateUtils.toXmlGregorianCalendar(arrivalDate));
        hotelsRequest.setDepartureDate(DateUtils.toXmlGregorianCalendar(departureDate));

        niceview.client.GetHotelsResponse hotelsResponse = port.getHotels(hotelsRequest);

        List<niceview.client.HotelType> hotels = hotelsResponse.getHotels().getHotel();

        Hotels result = new Hotels();

        for (niceview.client.HotelType hotelType : hotels) {
            result.getHotels().add(convertHotelType(hotelType));
        }

        return result;
    }

    private Hotel convertHotelType(niceview.client.HotelType hotelType) {
        Hotel h = new Hotel();
        h.setBookingNumber(hotelType.getBookingNumber());
        h.setAddress(convertAddressType(hotelType.getAddress()));
        h.setCreditCardGuarantee(hotelType.isCreditCardGuarantee());
        h.setId(hotelType.getId());
        h.setName(hotelType.getName());
        h.setPrice(hotelType.getPrice());
        h.setProvider(hotelType.getProvider());
        h.setArrivalDate(DateUtils.toDate(hotelType.getArrivalDate()));
        h.setDepartureDate(DateUtils.toDate(hotelType.getDepartureDate()));
        return h;
    }

    private Address convertAddressType(niceview.client.AddressType addressType) {
        Address a = new Address();
        a.setCity(addressType.getCity());
        a.setStreet(addressType.getStreet());
        a.setZipCode(addressType.getZipCode());
        return a;
    }
}
