package travelgood.utils.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bartosz Grzegorz Cichecki
 */
public class Hotels {

    protected List<Hotel> hotels;

    public List<Hotel> getHotels() {
        if (hotels == null) {
            hotels = new ArrayList<Hotel>();
        }
        return this.hotels;
    }
}
