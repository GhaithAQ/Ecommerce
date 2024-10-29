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

@Entity
@Table(name = "paymentmethods")
public class Payment_method {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_method_id")
    private int payment_method_id;

    @Column(name = "card_number")
    private String card_number;

    @Column(name = "cardholder_name")
    private String cardholder_name;

    @Column(name = "expiry_date")
    private String expiry_date;

    @Column(name = "create_time")
    private Timestamp created_at;

    @Column(name = "update_time")
    private Timestamp updated_at;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;

    public Payment_method(int payment_method_id, String card_number, String cardholder_name, String expiry_date, Timestamp created_at, Timestamp updated_at, User user) {
        this.payment_method_id = payment_method_id;
        this.card_number = card_number;
        this.cardholder_name = cardholder_name;
        this.expiry_date = expiry_date;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.user = user;
    }

    public Payment_method() {
    }

    public int getPayment_method_id() {
        return this.payment_method_id;
    }

    public void setPayment_method_id(int payment_method_id) {
        this.payment_method_id = payment_method_id;
    }

    public String getCard_number() {
        return this.card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public String getCardholder_name() {
        return this.cardholder_name;
    }

    public void setCardholder_name(String cardholder_name) {
        this.cardholder_name = cardholder_name;
    }

    public String getExpiry_date() {
        return this.expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    public Timestamp getCreated_at() {
        return this.created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return this.updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
