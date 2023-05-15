package service;

import model.Customer;

import java.util.Collection;
import java.util.HashMap;

public class CustomerService {
    private static CustomerService instance = null;
    private final HashMap<String, Customer> customers = new HashMap<String, Customer>();

    private CustomerService() {
    }

    public static CustomerService getInstance() {
        if (instance == null) {
            instance = new CustomerService();
        }
        return instance;
    }

    public void addCustomer(String email, String firstName, String lastName) {
        Customer newCustomer = new Customer(firstName, lastName, email);
        customers.put(email, newCustomer);
    }

    public Customer getCustomer(String customerEmail) {
        try {
            return customers.get(customerEmail);
        } catch (Exception ex) {
            System.out.println("You don't have any reservations yet");
            throw new IllegalArgumentException();
        }
    }

    public Collection<Customer> getAllCustomers() {
        return customers.values();
    }
}
