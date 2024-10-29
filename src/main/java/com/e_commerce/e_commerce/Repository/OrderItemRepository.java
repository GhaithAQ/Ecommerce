package com.e_commerce.e_commerce.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.e_commerce.e_commerce.Entity.Order;
import com.e_commerce.e_commerce.Entity.OrderItem;
import com.e_commerce.e_commerce.Entity.Product;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

    List<OrderItem> findByOrder(Order order);

    List<OrderItem> findByProduct(Product product);

}
