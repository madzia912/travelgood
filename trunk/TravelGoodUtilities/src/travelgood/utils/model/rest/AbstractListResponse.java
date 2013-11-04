package travelgood.utils.model.rest;

import java.util.ArrayList;
import java.util.Collection;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author Bartosz Grzegorz Cichecki
 */
public abstract class AbstractListResponse {

    protected Collection<NextStep> nextStepUrls;

    public void setNextStepUrls(Collection<NextStep> nextStepUrls) {
        this.nextStepUrls = nextStepUrls;
    }

    @XmlElement(name = "url")
    public Collection<NextStep> getNextStepUrls() {
        if (nextStepUrls == null) {
            nextStepUrls = new ArrayList();
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
        final AbstractListResponse other = (AbstractListResponse) obj;
        if (this.nextStepUrls != other.nextStepUrls && (this.nextStepUrls == null || !this.nextStepUrls.equals(other.nextStepUrls))) {
            return false;
        }
        return true;
    }
}
