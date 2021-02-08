package dk.dan.itunes.model;

/**
 * Represents spending of a customer
 */
public class SpendingCustomer {
    public double spending;
    public String customerId;

    public SpendingCustomer(double spending, String customerId) {
        this.spending = spending;
        this.customerId = customerId;
    }
}

