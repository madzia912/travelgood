package travelgood.utils.model.rest;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlEnumValue;

/**
 *
 * @author Bartosz Grzegorz Cichecki
 */
public class NextStep {

    public enum NextStepAction {

        @XmlEnumValue("urn:travelgood/get")
        GET,
        @XmlEnumValue("urn:travelgood/put")
        PUT,
        @XmlEnumValue("urn:travelgood/post")
        POST,
        @XmlEnumValue("urn:travelgood/delete")
        DELETE;
    }
    
    protected String url;
    protected NextStepAction action;

    public NextStep(NextStepAction action, String... urls) {
        StringBuilder urlBuilder = new StringBuilder();
        for (String u : urls) {
            urlBuilder.append(u).append("/");
        }
        this.url = urlBuilder.toString();
        this.action = action;
    }

    public NextStep() {
    }

    @XmlAttribute
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @XmlAttribute
    public NextStepAction getAction() {
        return action;
    }

    public void setAction(NextStepAction action) {
        this.action = action;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.url != null ? this.url.hashCode() : 0);
        hash = 59 * hash + (this.action != null ? this.action.hashCode() : 0);
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
        final NextStep other = (NextStep) obj;
        if ((this.url == null) ? (other.url != null) : !this.url.equals(other.url)) {
            return false;
        }
        if (this.action != other.action) {
            return false;
        }
        return true;
    }
}
