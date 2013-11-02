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
public class Hotels {

    @XmlElement(name="hotel")
    protected List<Hotel> hotels;

    public void setHotels(List<Hotel> value) {
        getHotels().clear();
        getHotels().addAll(value);
        
    }
    
    public List<Hotel> getHotels() {
        if (hotels == null) {
            hotels = new ArrayList<Hotel>();
        }
        return this.hotels;
    }
}
