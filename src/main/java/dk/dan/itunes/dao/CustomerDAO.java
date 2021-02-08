package dk.dan.itunes.dao;

import dk.dan.itunes.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CustomerDAO {

    String URL = "jdbc:sqlite::resource:Chinook_Sqlite.sqlite";
    Connection conn = null;


    public Customer getCustomerById(String customerId) {
        Customer customer = null;
        try {
            // Open Connection
            conn = DriverManager.getConnection(URL);
            // Prepare Statement
            PreparedStatement preparedStatement =
                    conn.prepareStatement("SELECT CustomerId,FirstName,LastName,PostalCode,Country,Phone,Email FROM Customer WHERE CustomerId = ?");
            preparedStatement.setString(1, customerId);
            // Execute Statement
            ResultSet resultSet = preparedStatement.executeQuery();

            // Process Results
            if (resultSet.next()) {
                customer = new Customer(
                        resultSet.getString("CustomerId"),
                        resultSet.getString("FirstName"),
                        resultSet.getString("LastName"),
                        resultSet.getString("PostalCode"),
                        resultSet.getString("Country"),
                        resultSet.getString("Phone"),
                        resultSet.getString("Email")
                );
            }
        } catch (Exception e) {
            System.out.println("Something went wrong...");
            e.printStackTrace();
        } finally {
            try {
                // Close Connection
                conn.close();
            } catch (Exception e) {
                System.out.println("Something went wrong while closing connection.");
                e.printStackTrace();
            }
            return customer;
        }
    }

    public ArrayList<Customer> getAllCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();
        try {
            // Open Connection
            conn = DriverManager.getConnection(URL);
            // Prepare Statement
            PreparedStatement preparedStatement =
                    conn.prepareStatement("SELECT CustomerId,FirstName,LastName,PostalCode,Country,Phone,Email FROM Customer");

            // Execute Statement
            ResultSet resultSet = preparedStatement.executeQuery();
            // Process Results
            while (resultSet.next()) {
                customers.add(
                        new Customer(
                                resultSet.getString("CustomerId"),
                                resultSet.getString("FirstName"),
                                resultSet.getString("LastName"),
                                resultSet.getString("Country"),
                                resultSet.getString("PostalCode"),
                                resultSet.getString("Phone"),
                                resultSet.getString("Email")
                        ));
            }
        } catch (Exception e) {
            System.out.println("Something went wrong...");
            e.printStackTrace();
        } finally {
            try {
                // Close Connection
                conn.close();
            } catch (Exception e) {
                System.out.println("Something went wrong while closing connection.");
                e.printStackTrace();
            }
            return customers;
        }
    }


    public Customer addCustomer(Customer customer) {
        try {
            // Open Connection
            conn = DriverManager.getConnection(URL);
            // Prepare Statement
            PreparedStatement preparedStatement =
                    conn.prepareStatement("INSERT INTO Customer (FirstName,LastName,PostalCode,Country,Phone,Email) VALUES (?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, customer.firstName);
            preparedStatement.setString(2, customer.lastName);
            preparedStatement.setString(3, customer.postalCode);
            preparedStatement.setString(4, customer.country);
            preparedStatement.setString(5, customer.phoneNumber);
            preparedStatement.setString(6, customer.email);
            // Execute Statement
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Something went wrong...");
            e.printStackTrace();
        } finally {
            try {
                // Close Connection
                conn.close();
            } catch (Exception e) {
                System.out.println("Something went wrong while closing connection.");
                e.printStackTrace();
            }
        }
        return customer;
    }

    public Customer updateCustomer(Customer customer) {
        try {
            conn = DriverManager.getConnection(URL);
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE Customer SET FirstName = ?, LastName = ?, Country = ?, PostalCode = ?, Phone = ?, Email = ? WHERE CustomerId = ?");
            preparedStatement.setString(1, customer.firstName);
            preparedStatement.setString(2, customer.lastName);
            preparedStatement.setString(3, customer.country);
            preparedStatement.setString(4, customer.postalCode);
            preparedStatement.setString(5, customer.phoneNumber);
            preparedStatement.setString(6, customer.email);
            preparedStatement.setString(7, customer.customerId);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Something went wrong...");
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Something went wrong while closing connection.");
                e.printStackTrace();
            }
        }
        return customer;
    }

    public ArrayList<CountryCustomer> getCustomersPerCountry() {
        ArrayList<CountryCustomer> countries = new ArrayList<>();
        try {
            // Open Connection
            conn = DriverManager.getConnection(URL);
            // Prepare Statement
            PreparedStatement preparedStatement =
                    conn.prepareStatement("SELECT Country, COUNT(*) AS Count FROM Customer GROUP BY Country ORDER BY Count DESC");

            // Execute Statement
            ResultSet resultSet = preparedStatement.executeQuery();
            // Process Results
            while (resultSet.next()) {
                countries.add(
                        new CountryCustomer(
                                resultSet.getString("Country"),
                                resultSet.getInt("Count")
                        ));
            }
        } catch (Exception e) {
            System.out.println("Something went wrong...");
            e.printStackTrace();
        } finally {
            try {
                // Close Connection
                conn.close();
            } catch (Exception e) {
                System.out.println("Something went wrong while closing connection.");
                e.printStackTrace();
            }
            return countries;
        }
    }

    public ArrayList<SpendingCustomer> getCustomersBySpending() {
        ArrayList<SpendingCustomer> highestSpenders = new ArrayList<>();
        try {
            conn = DriverManager.getConnection(URL);

            PreparedStatement preparedStatement = conn.prepareStatement("SELECT Invoice.CustomerId, SUM(Total) AS sum FROM Invoice INNER JOIN Customer on Invoice.CustomerId = Customer.CustomerId GROUP BY Customer.CustomerId ORDER BY sum DESC");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                double sum = resultSet.getDouble("sum");
                BigDecimal sumBigDecimal = new BigDecimal(sum).setScale(2, RoundingMode.HALF_UP);
                sum = sumBigDecimal.doubleValue();
                highestSpenders.add(new SpendingCustomer(sum, resultSet.getString("CustomerId")));
            }
        } catch (Exception e) {
            System.out.println("Something went wrong...");
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Something went wrong while closing connection.");
                e.printStackTrace();
            }
        }
        return highestSpenders;
    }

    public FavouriteGenreCustomer getCustomerFavouriteGenre(String id) {
        FavouriteGenreCustomer favouriteGenreCustomer = new FavouriteGenreCustomer(new ArrayList<Genre>(), id);
        try {
            conn = DriverManager.getConnection(URL);

            PreparedStatement preparedStatement = conn.prepareStatement("WITH CountQuery AS (SELECT g.GenreId, g.Name, count(g.GenreId) as GenreCount\n" +
                    "                    FROM Customer AS c\n" +
                    "                             JOIN Invoice AS iv\n" +
                    "                                  ON iv.CustomerId = c.CustomerId\n" +
                    "                             JOIN InvoiceLine AS il\n" +
                    "                                  ON il.InvoiceId = iv.InvoiceId\n" +
                    "                             JOIN Track AS t\n" +
                    "                                  ON t.TrackId = il.TrackId\n" +
                    "                             JOIN Genre AS g\n" +
                    "                                  ON g.GenreId = t.GenreId\n" +
                    "                    WHERE c.CustomerId = ?\n" +
                    "                    GROUP BY g.GenreId\n" +
                    "                    ORDER BY GenreCount)\n" +
                    "SELECT Name, GenreCount, GenreId\n" +
                    "FROM CountQuery\n" +
                    "WHERE (SELECT MAX(GenreCount) from CountQuery) = GenreCount");
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("Name");
                String genreId = resultSet.getString("GenreId");
                favouriteGenreCustomer.favouriteGenres.add(new Genre(genreId, name));
            }
        } catch (Exception e) {
            System.out.println("Something went wrong...");
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Something went wrong while closing connection.");
                e.printStackTrace();
            }
        }
        return favouriteGenreCustomer;
    }


}
