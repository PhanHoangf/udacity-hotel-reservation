package test;

import model.Customer;

public class Driver {
    public static void main(String[] args) {
        try {
            Customer c1 = new Customer("first", "second", "j@domain.com");
            System.out.println(c1);

            Customer c2 = new Customer("first", "second", "email");
            System.out.println(c2);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }
}
