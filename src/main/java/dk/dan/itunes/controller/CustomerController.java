package dk.dan.itunes.controller;

import dk.dan.itunes.dao.CustomerDAO;
import dk.dan.itunes.model.CountryCustomer;
import dk.dan.itunes.model.Customer;
import dk.dan.itunes.model.FavouriteGenreCustomer;
import dk.dan.itunes.model.SpendingCustomer;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    CustomerDAO customerDAO = new CustomerDAO();

    @GetMapping
    public ArrayList<Customer> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }

    @GetMapping(value = "/{id}")
    public Customer getCustomerById(@PathVariable String id) {
        return customerDAO.getCustomerById(id);
    }

    @GetMapping(value = "/{id}/favourite/genre")
    public FavouriteGenreCustomer getFavouriteGenreById(@PathVariable String id) {
        return customerDAO.getCustomerFavouriteGenre(id);
    }

    @PutMapping
    public Customer updateCustomerById(@RequestBody Customer customer) {
        return customerDAO.updateCustomer(customer);
    }

    @PostMapping
    public Customer addCustomer(@RequestBody Customer customer) {
        return customerDAO.addCustomer(customer);
    }

    @GetMapping(value = "/country/amount")
    public ArrayList<CountryCustomer> getCustomersOrderedByCountry() {
        return customerDAO.getCustomersPerCountry();
    }

    @GetMapping(value = "/spending")
    public ArrayList<SpendingCustomer> getCustomersOrderedBySpending() {
        return customerDAO.getCustomersBySpending();
    }


}
