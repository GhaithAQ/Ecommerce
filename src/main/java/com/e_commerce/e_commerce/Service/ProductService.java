package com.e_commerce.e_commerce.Service;

import java.util.List;

import com.e_commerce.e_commerce.Entity.Product;

public interface ProductService {

    void saveProduct(Product product);

    Product getProductbyId(int Id);

    List<Product> getAllproducts();

    boolean productNameIsexist(String name);

    Product getProductbyName(String name);

    List<Product> getAllProductsbyCategory(int id);

    void deleteProductById(int id);

    List<Product> getAllActiveProductsbyCategory(int id);
}
