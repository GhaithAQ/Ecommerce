package com.e_commerce.e_commerce.Entity;

import java.sql.Timestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private int address_id;

    @NotNull(message = "The name of housing must be filled.")
    @Column(name = "address_line1")
    private String address_line1;

    @NotNull(message = "The name of street must be filled.")
    @Column(name = "address_line2")
    private String address_line2;

    @NotNull(message = "The name of city must be filled.")
    @Column(name = "city")
    private String city;

    @NotNull(message = "The name of state must be filled.")
    @Column(name = "state")
    private String state;

    @NotNull(message = "The name of postal_code must be filled.")
    @Pattern(regexp = "^[a-zA-Z0-9]{5}", message = "The postal_code must be 5 chars upper and lower case and dights")
    @Column(name = "postal_code")
    private String postal_code;

    @NotNull(message = "The name of country must be filled.")
    @Column(name = "country")
    private String country;

    @Column(name = "create_time")
    private Timestamp create_time;

    @Column(name = "update_time")
    private Timestamp update_time;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;

    public Address() {
    }

    public Address(String address_line1, String address_line2, String city, String state, String postal_code, String country, Timestamp create_time, Timestamp update_time, User user) {
        this.address_line1 = address_line1;
        this.address_line2 = address_line2;
        this.city = city;
        this.state = state;
        this.postal_code = postal_code;
        this.country = country;
        this.create_time = create_time;
        this.update_time = update_time;
        this.user = user;
    }

    public int getAddress_id() {
        return this.address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public String getAddress_line1() {
        return this.address_line1;
    }

    public void setAddress_line1(String address_line1) {
        this.address_line1 = address_line1;
    }

    public String getAddress_line2() {
        return this.address_line2;
    }

    public void setAddress_line2(String address_line2) {
        this.address_line2 = address_line2;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostal_code() {
        return this.postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Timestamp getCreate_time() {
        return this.create_time;
    }

    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
    }

    public Timestamp getUpdate_time() {
        return this.update_time;
    }

    public void setUpdate_time(Timestamp update_time) {
        this.update_time = update_time;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
