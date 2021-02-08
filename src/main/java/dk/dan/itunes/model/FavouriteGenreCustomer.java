package dk.dan.itunes.model;

import java.util.List;

/**
 * Object representing a favourite genre(s) for a customer
 */
public class FavouriteGenreCustomer {
    public List<Genre> favouriteGenres;
    public String customerId;

    public FavouriteGenreCustomer(List<Genre> favouriteGenres, String customerId) {
        this.favouriteGenres = favouriteGenres;
        this.customerId = customerId;
    }
}

