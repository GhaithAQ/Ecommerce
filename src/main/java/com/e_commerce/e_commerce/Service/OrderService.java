package com.e_commerce.e_commerce.Service;

import java.util.List;

import com.e_commerce.e_commerce.Entity.Order;
import com.e_commerce.e_commerce.Entity.OrderItem;
import com.e_commerce.e_commerce.Entity.User;

public interface OrderService {

    Order createOrder(Order order, List<OrderItem> orderitems, User user);

    double CalculateOrder(List<OrderItem> orderitems);

    Order updateOrder(Order order);

    Order getOrderById(int id);

    List<Order> getOrdersByUser(User user);
}
