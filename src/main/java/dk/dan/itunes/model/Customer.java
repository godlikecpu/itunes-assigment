package dk.dan.itunes.model;

/**
 * Represents the customer entity
 */
public class Customer {
    public String customerId;
    public String firstName;
    public String lastName;
    public String country;
    public String postalCode;
    public String phoneNumber;
    public String email;

    public Customer(String customerId, String firstName, String lastName, String country, String postalCode, String phoneNumber, String email) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}
