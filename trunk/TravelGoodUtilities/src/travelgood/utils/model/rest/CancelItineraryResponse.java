package travelgood.utils.model.rest;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Bartosz Grzegorz Cichecki
 */
@XmlRootElement
public class CancelItineraryResponse extends AbstractListResponse {

    protected boolean cancelled;

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + (this.cancelled ? 1 : 0);
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
        final CancelItineraryResponse other = (CancelItineraryResponse) obj;
        if (this.cancelled != other.cancelled) {
            return false;
        }
        return true;
    }
}
