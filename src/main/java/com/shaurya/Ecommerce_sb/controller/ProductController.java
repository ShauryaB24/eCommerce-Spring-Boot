package com.shaurya.Ecommerce_sb.controller;

import com.shaurya.Ecommerce_sb.dto.request.ProductRequest;
import com.shaurya.Ecommerce_sb.dto.response.ProductResponse;
import com.shaurya.Ecommerce_sb.model.Product;
import com.shaurya.Ecommerce_sb.repository.ProductRepository;
import com.shaurya.Ecommerce_sb.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    ProductService productService;

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<?> addProduct(@RequestBody Product product,
                                        @PathVariable Long categoryId) {
        ProductRequest productRequest = productService.addProduct(categoryId, product);
        return new ResponseEntity<>(productRequest, HttpStatus.CREATED);
    }

    @GetMapping("/public/products")
    public ResponseEntity<?> getAllProducts() {
        ProductResponse productResponse = productService.getAllProducts();
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<?> getProductsByCategory(@PathVariable Long categoryId) {
        ProductResponse productResponse = productService.searchByCategory(categoryId);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);

    }

    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<?> getProductsByKeyword(@PathVariable String keyword) {
        ProductResponse productResponse = productService.searchProductByKeyword(keyword);
        return new ResponseEntity<>(productResponse, HttpStatus.FOUND);
    }
}
