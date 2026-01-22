package com.shaurya.Ecommerce_sb.service;

import com.shaurya.Ecommerce_sb.dto.request.ProductRequest;
import com.shaurya.Ecommerce_sb.dto.response.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {
    ProductRequest addProduct(Long categoryId, ProductRequest productRequest);

    ProductResponse getAllProducts();

    ProductResponse searchByCategory(Long categoryId);

    ProductResponse searchProductByKeyword(String keyword);

    ProductRequest updateProduct(Long productId, ProductRequest product);

    ProductRequest deleteProduct(Long productId);

    ProductRequest updateProductImage(Long productId, MultipartFile image) throws IOException;
}
