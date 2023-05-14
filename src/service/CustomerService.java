package service;

import model.Customer;

import java.util.Collection;
import java.util.HashMap;

public class CustomerService {
    private static final CustomerService instance = new CustomerService();
    private HashMap<String, Customer> customers;

    private CustomerService() {
    }

    public CustomerService getInstance() {
        return instance;
    }

    public void addCustomer(String email, String firstName, String lastName) {
        String message = String.format("Adding customer with email: %s, firstName: %s, lastName: %s", email, firstName, lastName);
        System.out.println(message);

        Customer newCustomer = new Customer(firstName, lastName, email);
        customers.put(email, newCustomer);
    }

    public Customer getCustomer(String customerEmail) {
        return customers.get(customerEmail);
    }

    public Collection<Customer> getAllCustomers() {
        return customers.values();
    }
}
