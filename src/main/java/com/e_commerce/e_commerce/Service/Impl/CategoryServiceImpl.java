package com.e_commerce.e_commerce.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e_commerce.e_commerce.Entity.Category;
import com.e_commerce.e_commerce.Repository.CategoryRepository;
import com.e_commerce.e_commerce.Service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void setCategoryData(Category category) {

        categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public CategoryRepository getCategoryRepository() {
        return this.categoryRepository;
    }

    @Override
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

}
