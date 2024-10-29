package com.e_commerce.e_commerce.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e_commerce.e_commerce.Entity.Order;
import com.e_commerce.e_commerce.Entity.OrderItem;
import com.e_commerce.e_commerce.Entity.User;
import com.e_commerce.e_commerce.Repository.OrderRepository;
import com.e_commerce.e_commerce.Service.OrderItemService;
import com.e_commerce.e_commerce.Service.OrderService;
import com.e_commerce.e_commerce.Service.ProductService;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemService orderItemservice;

    @Autowired
    private EntityManager entitymanager;

    @Autowired
    private ProductService productService;

    @Override
    @Transactional
    public Order createOrder(Order order, List<OrderItem> orderitems, User user) {
        order = entitymanager.merge(order);  // Ensure order is managed
        order.setUser(entitymanager.merge(user));
        order.setOrder_date();
        order.setPaymentStatus(Order.PaymentStatus.PENDING);
        for (OrderItem orderItem : orderitems) {
            orderItem.setOrder(order);
            orderItemservice.createorderItem(orderItem);
        }

        order.setTotal_amount(CalculateOrder(orderitems));
        orderRepository.save(order);
        return order;
    }

    @Override
    @Transactional
    public double CalculateOrder(List<OrderItem> orderitems) {
        double sum = 0;
        for (OrderItem orderItem : orderitems) {
            double total_foreach_orderitem = orderItem.getQuantity() * orderItem.getPrice();
            sum = sum + total_foreach_orderitem;
        }
        return sum;
    }

    public OrderRepository getOrderRepository() {
        return this.orderRepository;
    }

    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public EntityManager getEntitymanager() {
        return this.entitymanager;
    }

    public void setEntitymanager(EntityManager entitymanager) {
        this.entitymanager = entitymanager;
    }

    public OrderItemService getOrderItemservice() {
        return this.orderItemservice;
    }

    public void setOrderItemservice(OrderItemService orderItemservice) {
        this.orderItemservice = orderItemservice;
    }

    public ProductService getProductService() {
        return this.productService;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public Order updateOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUser(user);
    }

    @Override
    public Order getOrderById(int id) {
        return orderRepository.findById(id).orElse(null);
    }

}
