package com.e_commerce.e_commerce.Entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private int id;

    @NotNull(message = "you must enter the category name")
    @Column(name = "name")
    private String name;

    @NotNull(message = "the descrption is important")
    @Column(name = "description")
    private String description;

    @Column(name = "create_time")
    private Timestamp create_at;

    @Column(name = "update_time")
    private Timestamp update_at;

    @OneToMany(mappedBy = "category", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Product> products = new ArrayList<>();

    public Category(String name, String description, Timestamp created_at, Timestamp updated_at, List<Product> products) {
        this.name = name;
        this.description = description;
        this.create_at = created_at;
        this.update_at = updated_at;
        this.products = products;
    }

    public Category() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreate_at() {
        return this.create_at;
    }

    public void setCreate_at() {
        this.create_at = new Timestamp(System.currentTimeMillis());
    }

    public Timestamp getUpdate_at() {
        return this.update_at;
    }

    public void setUpdate_at() {
        this.update_at = new Timestamp(System.currentTimeMillis());
    }

    public List<Product> getProducts() {
        return this.products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    /*    public ProductCategory getProductCategory() {
        return this.productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }
     */
}
