package com.shaurya.Ecommerce_sb.service.impl;

import com.shaurya.Ecommerce_sb.dto.request.CategoryRequest;
import com.shaurya.Ecommerce_sb.dto.response.CategoryResponse;
import com.shaurya.Ecommerce_sb.exceptions.APIException;
import com.shaurya.Ecommerce_sb.exceptions.ResourceNotFoundException;
import com.shaurya.Ecommerce_sb.model.Category;
import com.shaurya.Ecommerce_sb.repository.CategoryRepository;
import com.shaurya.Ecommerce_sb.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories() {
        List<Category> categories =  categoryRepository.findAll();

        if(categories.isEmpty())
            throw new APIException("No category created till now.");
        List<CategoryRequest> categoryRequests = categories.stream()
                .map(category -> modelMapper.map(category, CategoryRequest.class))
                .toList();
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryRequests);
        return categoryResponse;
    }

    @Override
    public CategoryRequest createCategory(CategoryRequest categoryRequest) {
        Category category = modelMapper.map(categoryRequest, Category.class);
        Category categoryFromDb = categoryRepository.findByCategoryName(category.getCategoryName());
        if(categoryFromDb != null)
            throw new APIException("Category with the name " + category.getCategoryName() + "already exists!!!");
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryRequest.class);
    }

    @Override
    public CategoryRequest deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));

        categoryRepository.delete(category);
        return modelMapper.map(category, CategoryRequest.class);
    }

    @Override
    public CategoryRequest updateCategory(CategoryRequest categoryRequest,
                                   Long categoryId) {
        Category category = modelMapper.map(categoryRequest, Category.class);
        Category savedCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));


        category.setCategoryId(categoryId);
        savedCategory = categoryRepository.save(category);

        return modelMapper.map(savedCategory, CategoryRequest.class);
    }
}