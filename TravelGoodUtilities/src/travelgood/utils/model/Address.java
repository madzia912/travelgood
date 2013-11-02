package travelgood.utils.model;

/**
 *
 * @author Bartosz Grzegorz Cichecki
 */
public class Address {

    protected String city;
    protected String street;
    protected String zipCode;

    public String getCity() {
        return city;
    }

    public void setCity(String value) {
        this.city = value;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String value) {
        this.street = value;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String value) {
        this.zipCode = value;
    }
}
