package travelgood.utils.model.rest;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Bartosz Grzegorz Cichecki
 */
@XmlRootElement
public class CreateItineraryResponse extends AbstractListResponse {

    protected String bookingNumber;

    public String getBookingNumber() {
        return bookingNumber;
    }

    public void setBookingNumber(String bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + (this.bookingNumber != null ? this.bookingNumber.hashCode() : 0);
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
        final CreateItineraryResponse other = (CreateItineraryResponse) obj;
        if ((this.bookingNumber == null) ? (other.bookingNumber != null) : !this.bookingNumber.equals(other.bookingNumber)) {
            return false;
        }
        return true;
    }
}
