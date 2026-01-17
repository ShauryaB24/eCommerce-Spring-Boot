package com.shaurya.Ecommerce_sb.service;

import com.shaurya.Ecommerce_sb.dto.request.CategoryRequest;
import com.shaurya.Ecommerce_sb.dto.response.CategoryResponse;
import com.shaurya.Ecommerce_sb.model.Category;


public interface CategoryService {
    CategoryResponse getAllCategories();

    CategoryRequest createCategory(CategoryRequest categoryRequest);

    CategoryRequest deleteCategory(Long categoryId);

    CategoryRequest updateCategory(CategoryRequest categoryRequest, Long categoryId);
}
