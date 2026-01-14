package com.shaurya.Ecommerce_sb.service;

import com.shaurya.Ecommerce_sb.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();

    void createCategory(Category category);

    String deleteCategory(Long categoryId);

    Category updateCategory(Category category, Long categoryId);
}
