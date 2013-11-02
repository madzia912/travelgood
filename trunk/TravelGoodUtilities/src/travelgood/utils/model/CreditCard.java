package travelgood.utils.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Bartosz Grzegorz Cichecki
 */
@XmlRootElement
public class CreditCard {

    protected String name;
    protected String number;
    protected int expMonth;
    protected int expYear;

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String value) {
        this.number = value;
    }

    public int getExpMonth() {
        return expMonth;
    }

    public void setExpMonth(int value) {
        this.expMonth = value;
    }

    public int getExpYear() {
        return expYear;
    }

    public void setExpYear(int value) {
        this.expYear = value;
    }
}
