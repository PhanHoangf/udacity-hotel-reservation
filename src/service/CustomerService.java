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
        try {
            Customer newCustomer = new Customer(firstName, lastName, email);
            boolean existFlag = false;
            for (Customer cus : customers.values()) {
                if (cus.equals(newCustomer)) {
                    existFlag = true;
                    System.out.println("Email already exist, please use another email.");
                }
            }
            if (!existFlag) {
                customers.put(email, newCustomer);
            }
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }

    public Customer getCustomer(String customerEmail) {
        try {
            return customers.get(customerEmail);
        } catch (Exception ex) {
            System.out.println("You don't have account yet");
            throw new IllegalArgumentException();
        }
    }

    public Collection<Customer> getAllCustomers() {
        return customers.values();
    }
}
