package com.e_commerce.e_commerce.Entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer user_id;

    @Pattern(regexp = "^[a-zA-Z0-9]{5,10}", message = "The confirmation password must be from 5 to 10 chars whether appercase or lowercase or dights ")
    @NotNull(message = "You must insert your username")
    @Column(name = "username")
    private String username;

    @NotNull(message = "The email is required")
    @Email
    @Column(name = "email")
    private String email;

    @Pattern(regexp = "^[a-zA-Z0-9]{10,20}", message = "The password must be from 10 to 20 chars whether appercase or lowercase or dights ")
    @Column(name = "password")
    private String password;

    @NotNull(message = "firstname is required")
    @Column(name = "first_name")
    private String first_name;

    @Column(name = "confirmation_password")
    private String confirmation_password;

    @Column(name = "create_time")
    private Timestamp create_time;

    @Column(name = "update_time")
    private Timestamp update_time;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Address> addresses = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Payment_method> payment_methods;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Order> orders;

    public User() {
    }

    public User(String username, String email, String password, String first_name, Timestamp create_time, Timestamp update_time) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.first_name = first_name;
        this.create_time = create_time;
        this.update_time = update_time;
    }

    public Integer getUser_id() {
        return this.user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirst_name() {
        return this.first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public Timestamp getCreate_time() {
        return this.create_time;
    }

    public void setCreate_time() {
        this.create_time = new Timestamp(System.currentTimeMillis());
    }

    public Timestamp getUpdate_time() {
        return this.update_time;
    }

    public void setUpdate_time() {
        this.update_time = new Timestamp(System.currentTimeMillis());
    }

    /*  public void addOrder(Order order) {
        if (orders == null) {
            orders = new ArrayList<>();
        }
        orders.add(order);
    }

    public void addPayment_method(Payment_method payment_method) {
        if (payment_methods == null) {
            payment_methods = new ArrayList<>();
        }
        payment_methods.add(payment_method);
    }*/
    public String getConfirmation_password() {
        return this.confirmation_password;
    }

    public void setConfirmation_password(String confirmation_password) {
        this.confirmation_password = confirmation_password;
    }

    public List<Address> getAddresses() {
        return this.addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<Payment_method> getPayment_methods() {
        return this.payment_methods;
    }

    public void setPayment_methods(List<Payment_method> payment_methods) {
        this.payment_methods = payment_methods;
    }

    public List<Order> getOrders() {
        return this.orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void addAddress(Address address) {
        if (addresses == null) {
            addresses = new ArrayList<>();
        }
        addresses.add(new Address());
        address.setUser(this);
    }

}
