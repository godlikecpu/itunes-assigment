package dk.dan.itunes.model;

/**
 * Represents a country and an amount of customers
 */
public class CountryCustomer {
    public String country;
    public int customerAmount;

    public CountryCustomer(String country, int customerAmount) {
        this.country = country;
        this.customerAmount = customerAmount;
    }
}

