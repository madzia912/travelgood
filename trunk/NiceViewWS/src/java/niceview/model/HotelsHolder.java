package niceview.model;

import dk.dtu.travelgood.commons.HotelType;
import java.util.ArrayList;
import java.util.List;

/**
 * Holds all available hotels.
 *
 * @author Magdalena Furman
 */
public class HotelsHolder {

    private List<HotelType> hotels = new ArrayList<HotelType>();
    private static HotelsHolder INSTANCE = new HotelsHolder();

    private HotelsHolder() {
    }

    public HotelsHolder getInstance() {
        return INSTANCE;
    }

    public List<HotelType> getHotels() {
        return hotels;
    }
}
