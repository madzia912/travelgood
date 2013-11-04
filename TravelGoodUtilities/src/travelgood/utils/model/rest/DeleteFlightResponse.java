package travelgood.utils.model.rest;

/**
 *
 * @author Bartosz Grzegorz Cichecki
 */
public class DeleteFlightResponse extends AbstractListResponse<String> {

    protected boolean deleted;

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean cancelled) {
        this.deleted = cancelled;
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
        final DeleteFlightResponse other = (DeleteFlightResponse) obj;
        if (this.deleted != other.deleted) {
            return false;
        }
        return true;
    }
}
