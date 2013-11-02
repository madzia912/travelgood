package travelgood.utils.model;

/**
 * 
 * @author Bartosz Grzegorz Cichecki
 */
public class Hotel {

    protected String id;
    protected String bookingNumber;
    protected String name;
    protected Address address;
    protected String provider;
    protected int price;
    protected boolean creditCardGuarantee;

    public String getId() {
        return id;
    }

    public void setId(String value) {
        this.id = value;
    }

    public String getBookingNumber() {
        return bookingNumber;
    }

    public void setBookingNumber(String value) {
        this.bookingNumber = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address value) {
        this.address = value;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String value) {
        this.provider = value;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int value) {
        this.price = value;
    }

    public boolean isCreditCardGuarantee() {
        return creditCardGuarantee;
    }

    public void setCreditCardGuarantee(boolean value) {
        this.creditCardGuarantee = value;
    }
}
