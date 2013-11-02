package travelgood.utils.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author Bartosz Grzegorz Cichecki
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Flights {

    @XmlElement(name="flight")
    protected List<Flight> flights;

    public void setFlights(List<Flight> value) {
        getFlights().clear();
        getFlights().addAll(value);
        
    }
    
    public List<Flight> getFlights() {
        if (flights == null) {
            flights = new ArrayList<Flight>();
        }
        return this.flights;
    }
}
