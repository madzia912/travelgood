package travelgood.utils.model.rest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bartosz Grzegorz Cichecki
 */
public abstract class AbstractListResponse<T> {

    protected List<T> nextStepUrls;

    public List<T> getNextStepUrls() {
        if (nextStepUrls == null) {
            nextStepUrls = new ArrayList<T>();
        }
        return nextStepUrls;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + (this.nextStepUrls != null ? this.nextStepUrls.hashCode() : 0);
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
        final AbstractListResponse<T> other = (AbstractListResponse<T>) obj;
        if (this.nextStepUrls != other.nextStepUrls && (this.nextStepUrls == null || !this.nextStepUrls.equals(other.nextStepUrls))) {
            return false;
        }
        return true;
    }
}
