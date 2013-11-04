package travelgood.utils.model.rest;

import javax.xml.bind.annotation.XmlRootElement;
import travelgood.utils.model.Hotels;

/**
 * @author Bartosz Grzegorz Cichecki
 */
@XmlRootElement
public class GetHotelsResponse extends AbstractListResponse<String> {

    protected Hotels hotels;

    public Hotels getHotels() {
        return hotels;
    }

    public void setHotels(Hotels hotels) {
        this.hotels = hotels;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.hotels != null ? this.hotels.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GetHotelsResponse other = (GetHotelsResponse) obj;
        if (this.hotels != other.hotels && (this.hotels == null || !this.hotels.equals(other.hotels))) {
            return false;
        }
        return true;
    }
}
