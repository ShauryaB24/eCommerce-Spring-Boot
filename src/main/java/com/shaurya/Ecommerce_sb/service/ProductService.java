package com.shaurya.Ecommerce_sb.service;

import com.shaurya.Ecommerce_sb.dto.request.ProductRequest;
import com.shaurya.Ecommerce_sb.dto.response.ProductResponse;

public interface ProductService {
    ProductRequest addProduct(Long categoryId, ProductRequest productRequest);

    ProductResponse getAllProducts();

    ProductResponse searchByCategory(Long categoryId);

    ProductResponse searchProductByKeyword(String keyword);

    ProductRequest updateProduct(Long productId, ProductRequest product);

    ProductRequest deleteProduct(Long productId);
}
