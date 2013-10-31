package niceview.model;

import dk.dtu.travelgood.commons.AddressType;
import dk.dtu.travelgood.commons.HotelType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

/**
 * Holds all available hotels and reservations..
 *
 * @author Magdalena Furman
 */
public class HotelsHolder {

    private List<HotelType> hotels = new ArrayList<HotelType>();
    private static HotelsHolder INSTANCE = new HotelsHolder();
    private Map<String, HotelType> reservations = new HashMap<String, HotelType>();
    private HotelsHolder() {
        init();
    }

    public static HotelsHolder getInstance() {
        return INSTANCE;
    }

    public List<HotelType> getHotels() {
        return hotels;
    }
    
    public HotelType getHotelByBookingNumber(String nr){
        for(HotelType ht : hotels){
            if(StringUtils.equals(ht.getBookingNumber(), nr)){
                return ht;
            }
        }
        return null;
    }
    
    public Map<String, HotelType> getReservations(){
        return reservations;
    }
    
    private void init()
    {
        // Sample hotels, remember to modify test when modify sample hotels.
        HotelType ht = new HotelType();
        ht.setId("hotel1");
        ht.setBookingNumber("bookingNr1");
        ht.setName("Hilton");
        ht.setPrice(500);
        ht.setCreditCardGuarantee(true);
        ht.setProvider("Provider1");
        AddressType at = new AddressType();
        at.setCity("Paris");
        at.setStreet("Rue de lEtoile");
        at.setZipCode("75017");
        ht.setAddress(at);
        hotels.add(ht);
        
        ht = new HotelType();
        ht.setId("hotel2");
        ht.setBookingNumber("bookingNr2");
        ht.setName("Tivoli");
        ht.setPrice(200);
        ht.setCreditCardGuarantee(true);
        ht.setProvider("Provider2");
        at = new AddressType();
        at.setCity("Copenhagen");
        at.setStreet("Arni Magnussons Gade");
        at.setZipCode("1577");
        ht.setAddress(at);
        hotels.add(ht);
        
        ht = new HotelType();
        ht.setId("hotel3");
        ht.setBookingNumber("bookingNr3");
        ht.setName("Hotel No Name");
        ht.setPrice(20);
        ht.setCreditCardGuarantee(false);
        ht.setProvider("Provider3");
        at = new AddressType();
        at.setCity("London");
        at.setStreet("Baker Street");
        at.setZipCode("123");
        ht.setAddress(at);
        hotels.add(ht);
    }
}
