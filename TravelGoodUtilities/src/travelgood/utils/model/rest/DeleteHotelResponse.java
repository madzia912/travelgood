package travelgood.utils.model.rest;

/**
 *
 * @author Bartosz Grzegorz Cichecki
 */
public class DeleteHotelResponse extends AbstractListResponse<String> {

    protected boolean deleted;

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + (this.deleted ? 1 : 0);
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
        final DeleteHotelResponse other = (DeleteHotelResponse) obj;
        if (this.deleted != other.deleted) {
            return false;
        }
        return true;
    }
}
