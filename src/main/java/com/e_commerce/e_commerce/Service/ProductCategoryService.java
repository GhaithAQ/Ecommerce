package com.e_commerce.e_commerce.Service;

import com.e_commerce.e_commerce.Entity.Product;
import com.e_commerce.e_commerce.Entity.ProductCategory;

public interface ProductCategoryService {

    void saveProductCategory(ProductCategory productCategory, Product product);

    // void deleteProductCategoryByProductId(int id);
}
