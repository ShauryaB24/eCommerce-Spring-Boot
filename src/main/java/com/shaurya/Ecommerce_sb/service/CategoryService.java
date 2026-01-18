package com.shaurya.Ecommerce_sb.service;

import com.shaurya.Ecommerce_sb.dto.request.CategoryRequest;
import com.shaurya.Ecommerce_sb.dto.response.CategoryResponse;


public interface CategoryService {
    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    CategoryRequest createCategory(CategoryRequest categoryRequest);

    CategoryRequest deleteCategory(Long categoryId);

    CategoryRequest updateCategory(CategoryRequest categoryRequest, Long categoryId);
}
