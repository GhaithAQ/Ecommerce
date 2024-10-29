package com.e_commerce.e_commerce.Service;

import java.util.List;

import com.e_commerce.e_commerce.Entity.Category;
import com.e_commerce.e_commerce.Repository.CategoryRepository;

public interface CategoryService {

    void setCategoryData(Category category);

    List<Category> getAllCategories();

    CategoryRepository getCategoryRepository();

    void setCategoryRepository(CategoryRepository categoryRepository);

}
