package service;

import model.Customer;

import java.util.*;

public class CustomerService {
    private static final CustomerService INSTANCE = new CustomerService();
    private final Map<String, Customer> customers = new HashMap<>();

    private CustomerService(){}
    public static CustomerService getInstance(){
        return INSTANCE;
    }

    public final void addCustomer(String email, String firstName, String lastName) {
        if (!isValidEmail(email)){
            throw new IllegalArgumentException("Email format invalid: " + email);
        }
        if (customers.containsKey(email)){
            throw new IllegalStateException("Email already exists.");
        }
        Customer customer = new Customer(firstName, lastName, email);
        customers.put(email, customer);
    }

    public final Customer getCustomer(String customerEmail) {
        try {
            if (!customers.containsKey(customerEmail)) {
                throw new IllegalArgumentException("Invalid Email: " + customerEmail + " does not exist.");
            }
        } catch (IllegalArgumentException e) {
            System.out.print(e.getLocalizedMessage());
        }
        return customers.get(customerEmail);
    }

    public final Collection<Customer> getAllCustomers(){
        return new ArrayList<>(customers.values());
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches("^(.+)@(.+)\\.(com|net|info|org|co.uk|dev)$");
    }

    public final boolean verifyEmail(String email) {
        return isValidEmail(email);
    }
}
