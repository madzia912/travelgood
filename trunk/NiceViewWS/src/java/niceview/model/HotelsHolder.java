package niceview.model;

import java.util.ArrayList;
import java.util.List;
import travelgood.utils.model.Hotel;

/**
 * Holds all available hotels.
 *
 * @author Bartosz Cichecki
 */
public class HotelsHolder {

    private List<Hotel> hotels = new ArrayList<Hotel>();
    private static HotelsHolder INSTANCE = new HotelsHolder();

    private HotelsHolder() {
    }

    public HotelsHolder getInstance() {
        return INSTANCE;
    }

    public List<Hotel> getFlights() {
        return hotels;
    }
}
