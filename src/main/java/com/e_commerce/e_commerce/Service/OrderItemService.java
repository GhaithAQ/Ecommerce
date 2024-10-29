package com.e_commerce.e_commerce.Service;

import java.util.List;

import com.e_commerce.e_commerce.Entity.Order;
import com.e_commerce.e_commerce.Entity.OrderItem;

public interface OrderItemService {

    void createorderItem(OrderItem orderitem);

    List<OrderItem> getAllOrderItemsInOrder(Order order);
}
