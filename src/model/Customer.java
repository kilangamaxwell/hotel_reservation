package model;

import java.util.Objects;
import java.util.regex.Pattern;

public class Customer {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String emailRegEx = "^(.+)@(.+)\\.(com|net|info|org|co.uk|dev)$";
    private final Pattern pattern = Pattern.compile(emailRegEx);

    public Customer(String firstName, String lastName, String email){
        if(!pattern.matcher(email).matches()) {
            throw new IllegalArgumentException("Error: Invalid Email Address");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public final String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(getFirstName(), customer.getFirstName()) && Objects.equals(getLastName(), customer.getLastName()) && Objects.equals(getEmail(), customer.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getEmail());
    }

    @Override
    public String toString() {
        return "firstName=" + this.firstName + ", lastName=" + this.lastName  +
                ", email=" + this.email;
    }
}
