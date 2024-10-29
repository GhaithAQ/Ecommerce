package com.e_commerce.e_commerce.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.e_commerce.e_commerce.Entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer> {

}
