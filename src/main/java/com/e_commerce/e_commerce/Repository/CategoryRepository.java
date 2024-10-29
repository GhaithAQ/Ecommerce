package com.e_commerce.e_commerce.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.e_commerce.e_commerce.Entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    void deleteProductById(int id);
}
