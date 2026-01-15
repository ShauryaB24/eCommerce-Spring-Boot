package com.shaurya.Ecommerce_sb.service.impl;

import com.shaurya.Ecommerce_sb.exceptions.APIException;
import com.shaurya.Ecommerce_sb.exceptions.ResourceNotFoundException;
import com.shaurya.Ecommerce_sb.model.Category;
import com.shaurya.Ecommerce_sb.repository.CategoryRepository;
import com.shaurya.Ecommerce_sb.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {

        return categoryRepository.findAll();
    }

    @Override
    public void createCategory(Category category) {

        Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if(savedCategory != null)
            throw new APIException("Category with the name " + category.getCategoryName() + "already exists!!!");
        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));

        categoryRepository.delete(category);
        return "Category with categoryId:" + categoryId + " deleted successfully";
    }

    @Override
    public Category updateCategory(Category category,
                                   Long categoryId) {

        Category savedCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));

        category.setCategoryId(categoryId);
        savedCategory = categoryRepository.save(category);

        return savedCategory;
    }
}