package com.shaurya.Ecommerce_sb.service;

import com.shaurya.Ecommerce_sb.dto.request.ProductRequest;
import com.shaurya.Ecommerce_sb.dto.response.ProductResponse;
import com.shaurya.Ecommerce_sb.model.Product;

public interface ProductService {
    ProductRequest addProduct(Long categoryId, Product product);

    ProductResponse getAllProducts();

    ProductResponse searchByCategory(Long categoryId);

    ProductResponse searchProductByKeyword(String keyword);
}
