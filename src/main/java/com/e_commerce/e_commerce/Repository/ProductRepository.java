package com.e_commerce.e_commerce.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.e_commerce.e_commerce.Entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    Optional<Product> findByName(String name);

    List<Product> findByCategoryId(int categoryId);

    void deleteProductByCategoryId(int id);

    @Query("SELECT p FROM Product p WHERE p.deleted = false")
    List<Product> findAllActiveProducts();

    @Query("SELECT p FROM Product p WHERE p.id = :product_id AND p.deleted = false")
    Optional<Product> findActiveProductById(@Param("product_id") int product_id);

    @Query("SELECT p FROM Product p WHERE p.id = :product_id")
    Optional<Product> findProductById(@Param("product_id") int product_id); // Includes deleted products

    @Query("SELECT p FROM Product p WHERE p.category.id = :category_id AND p.deleted = false")
    List<Product> findAllActiveProductsByCategoryId(@Param("category_id") int categoryId);
}
