package travelgood.utils.model.rest;

/**
 *
 * @author Bartosz Grzegorz Cichecki
 */
public class BookFlightResponse extends AbstractListResponse<String> {

    protected boolean booked;

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.booked ? 1 : 0);
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
        final BookFlightResponse other = (BookFlightResponse) obj;
        if (this.booked != other.booked) {
            return false;
        }
        return true;
    }
}
