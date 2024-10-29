package com.e_commerce.e_commerce.Service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e_commerce.e_commerce.Entity.Product;
import com.e_commerce.e_commerce.Repository.ProductRepository;
import com.e_commerce.e_commerce.Service.ProductService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private final EntityManager entitymanager;

    @Autowired
    private final ProductRepository productRepository;

    public ProductServiceImpl(EntityManager entitymanager, ProductRepository productRepository) {
        this.entitymanager = entitymanager;
        this.productRepository = productRepository;

    }

    @Override
    @Transactional
    public void saveProduct(Product product) {
        if (product.getCategory() != null) {
            product.setCategory(entitymanager.merge(product.getCategory()));
        }
        productRepository.save(product);
    }

    @Override
    public Product getProductbyId(int Id) {
        return productRepository.findProductById(Id)
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + Id + " not found"));
    }

    @Override
    public List<Product> getAllProductsbyCategory(int id) {
        return productRepository.findByCategoryId(id);
    }

    @Override
    public List<Product> getAllActiveProductsbyCategory(int id) {
        return productRepository.findAllActiveProductsByCategoryId(id);
    }

    @Override
    public List<Product> getAllproducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductbyName(String name) {
        return productRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Product with name: " + name + "not found"));
    }

    @Override
    public boolean productNameIsexist(String name) {
        Optional<Product> result = productRepository.findByName(name);
        return result.isPresent();
    }

    @Override
    @Transactional
    public void deleteProductById(int id) {
        Product product = productRepository.findProductById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + id + " not found"));
        product.setDeleted(true);
        productRepository.save(product);

    }

}
