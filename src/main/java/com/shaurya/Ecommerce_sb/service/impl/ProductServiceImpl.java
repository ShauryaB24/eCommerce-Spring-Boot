package com.shaurya.Ecommerce_sb.service.impl;

import com.shaurya.Ecommerce_sb.dto.request.ProductRequest;
import com.shaurya.Ecommerce_sb.dto.response.ProductResponse;
import com.shaurya.Ecommerce_sb.exceptions.ResourceNotFoundException;
import com.shaurya.Ecommerce_sb.model.Category;
import com.shaurya.Ecommerce_sb.model.Product;
import com.shaurya.Ecommerce_sb.repository.CategoryRepository;
import com.shaurya.Ecommerce_sb.repository.ProductRepository;
import com.shaurya.Ecommerce_sb.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductRequest addProduct(Long categoryId, Product product) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category", "categoryId", categoryId));


        product.setImage("default.png");
        product.setCategory(category);
        Double specialPrice = product.getPrice() -
                ((product.getDiscount() / 100) * product.getPrice());
        product.setSpecialPrice(specialPrice);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductRequest.class);
    }

    @Override
    public ProductResponse getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductRequest> productRequests = products.stream()
                .map(product -> modelMapper.map(product, ProductRequest.class))
                .toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productRequests);
        return productResponse;
    }

    @Override
    public ProductResponse searchByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category", "categoryId", categoryId));

        List<Product> products = productRepository.findByCategoryOrderByPriceAsc(category);
        List<ProductRequest> productRequests = products.stream()
                .map(product -> modelMapper.map(product, ProductRequest.class))
                .toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productRequests);
        return productResponse;
    }

    @Override
    public ProductResponse searchProductByKeyword(String keyword) {

        List<Product> products = productRepository.findByProductNameLikeIgnoreCase('%' + keyword + '%');
        List<ProductRequest> productRequests = products.stream()
                .map(product -> modelMapper.map(product, ProductRequest.class))
                .toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productRequests);
        return productResponse;
    }
}
