package com.shaurya.Ecommerce_sb.controller;

import com.shaurya.Ecommerce_sb.model.Category;
import com.shaurya.Ecommerce_sb.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
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
        List<Category> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PostMapping("/public/categories")
    public ResponseEntity<?> createCategory(@Valid @RequestBody Category category) {
        categoryService.createCategory(category);
        return new ResponseEntity<>("Category added successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long categoryId) {
        try {
            String status = categoryService.deleteCategory(categoryId);
            // return new ResponseEntity<>(status, HttpStatus.OK);
            // return ResponseEntity.status(HttpStatus.OK).body(status);
            return ResponseEntity.ok(status);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }

    }

    @PutMapping("/public/categories/{categoryId}")
    public ResponseEntity<?> updateCategory(@RequestBody Category category,
                                            @PathVariable Long categoryId) {
        try{
            Category savedCategory = categoryService.updateCategory(category, categoryId);
            return new ResponseEntity<>("Updated Category with category id: " + categoryId, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }

}
