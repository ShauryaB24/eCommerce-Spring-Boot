package com.shaurya.Ecommerce_sb.controller;

import com.shaurya.Ecommerce_sb.dto.request.CategoryRequest;
import com.shaurya.Ecommerce_sb.dto.response.CategoryResponse;
import com.shaurya.Ecommerce_sb.model.Category;
import com.shaurya.Ecommerce_sb.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RequestMapping("/api")
@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    

    @GetMapping("/public/categories")
    //@RequestMapping(value = "/api/public/categories", method = RequestMethod.GET)
    public ResponseEntity<?> getAllCategories() {
        CategoryResponse categoryResponse = categoryService.getAllCategories();
        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
    }

    @PostMapping("/public/categories")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        CategoryRequest savedCategoryRequest = categoryService.createCategory(categoryRequest);
        return new ResponseEntity<>(savedCategoryRequest, HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long categoryId) {
            CategoryRequest deletedCategory = categoryService.deleteCategory(categoryId);
            return new ResponseEntity<>(deletedCategory, HttpStatus.OK);


    }

    @PutMapping("/public/categories/{categoryId}")
    public ResponseEntity<?> updateCategory(@Valid @RequestBody CategoryRequest categoryRequest,
                                            @PathVariable Long categoryId) {

            CategoryRequest savedCategoryRequest = categoryService.updateCategory(categoryRequest, categoryId);
            return new ResponseEntity<>(savedCategoryRequest, HttpStatus.OK);

    }

}
