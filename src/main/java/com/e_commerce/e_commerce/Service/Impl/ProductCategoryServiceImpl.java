package com.e_commerce.e_commerce.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e_commerce.e_commerce.Entity.Product;
import com.e_commerce.e_commerce.Entity.ProductCategory;
import com.e_commerce.e_commerce.Repository.ProductCategoryRepository;
import com.e_commerce.e_commerce.Repository.ProductRepository;
import com.e_commerce.e_commerce.Service.ProductCategoryService;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private EntityManager entityManagerPC;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    @Transactional
    public void saveProductCategory(ProductCategory productCategory, Product product) {
        productCategory.setCategory(entityManagerPC.merge(product.getCategory()));
        productCategory.setProduct(entityManagerPC.merge(product));
        productCategoryRepository.save(productCategory);
    }

    /* @Transactional
    public void deleteProductCategoryByProductId(int id) {
        Product product = entityManagerPC.find(Product.class, id);
        if (product != null) {
            List<ProductCategory> productcategories = productCategoryRepository.findAll();
            for (ProductCategory productCategory : productcategories) {
                if (product.equals(productCategory.getProduct())) {

                    productCategoryRepository.delete(productCategory);
                }
            }
        }

    }*/
    public EntityManager getEntityManagerPC() {
        return this.entityManagerPC;
    }

    public void setEntityManagerPC(EntityManager entityManagerPC) {
        this.entityManagerPC = entityManagerPC;
    }

    public ProductCategoryRepository getProductCategoryRepository() {
        return this.productCategoryRepository;
    }

    public void setProductCategoryRepository(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    public ProductRepository getProductRepository() {
        return this.productRepository;
    }

    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

}
