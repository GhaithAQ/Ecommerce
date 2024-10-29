package com.e_commerce.e_commerce.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e_commerce.e_commerce.Entity.Order;
import com.e_commerce.e_commerce.Entity.OrderItem;
import com.e_commerce.e_commerce.Entity.Product;
import com.e_commerce.e_commerce.Repository.OrderItemRepository;
import com.e_commerce.e_commerce.Service.OrderItemService;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private final OrderItemRepository orderItemRepository;

    @Autowired
    private final EntityManager entityManager;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository, EntityManager entityManager) {
        this.orderItemRepository = orderItemRepository;
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void createorderItem(OrderItem orderitem) {
        Product managedProduct = entityManager.find(Product.class, orderitem.getProduct().getId());
        if (managedProduct == null) {
            managedProduct = entityManager.merge(orderitem.getProduct());  // Merge if not managed
        }
        orderitem.setProduct(managedProduct);
        if (managedProduct.getStock() < orderitem.getQuantity()) {
            return;
        }
        managedProduct.setStock(managedProduct.getStock() - orderitem.getQuantity());
        entityManager.merge(managedProduct);
        orderItemRepository.save(orderitem);

    }

    @Override
    public List<OrderItem> getAllOrderItemsInOrder(Order order) {
        return orderItemRepository.findByOrder(order);
    }
}
