package com.e_commerce.e_commerce.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.e_commerce.e_commerce.Entity.Order;
import com.e_commerce.e_commerce.Entity.User;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByUser(User user);
;

}
