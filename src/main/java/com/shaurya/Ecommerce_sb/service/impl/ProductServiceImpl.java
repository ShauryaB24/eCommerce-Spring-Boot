package com.shaurya.Ecommerce_sb.service.impl;

import com.shaurya.Ecommerce_sb.dto.request.ProductRequest;
import com.shaurya.Ecommerce_sb.dto.response.ProductResponse;
import com.shaurya.Ecommerce_sb.exceptions.APIException;
import com.shaurya.Ecommerce_sb.exceptions.ResourceNotFoundException;
import com.shaurya.Ecommerce_sb.model.Category;
import com.shaurya.Ecommerce_sb.model.Product;
import com.shaurya.Ecommerce_sb.repository.CategoryRepository;
import com.shaurya.Ecommerce_sb.repository.ProductRepository;
import com.shaurya.Ecommerce_sb.service.FileService;
import com.shaurya.Ecommerce_sb.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    @Override
    public ProductRequest addProduct(Long categoryId, ProductRequest productRequest) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category", "categoryId", categoryId));

        boolean isProductNotPresent = true;
        List<Product> products = category.getProducts();
        for (Product value : products) {
            if (value.getProductName().equals(productRequest.getProductName())) {
                isProductNotPresent = false;
                break;
            }
        }

        if (isProductNotPresent) {
            Product product = modelMapper.map(productRequest, Product.class);
            product.setImage("default.png");
            product.setCategory(category);
            Double specialPrice = product.getPrice() -
                    ((product.getDiscount() * 0.01) * product.getPrice());
            product.setSpecialPrice(specialPrice);
            Product savedProduct = productRepository.save(product);
            return modelMapper.map(savedProduct, ProductRequest.class);
        } else {
            throw new APIException("Product already exist!!!");
        }

    }

    @Override
    public ProductResponse getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductRequest> productRequests = products.stream()
                .map(product -> modelMapper.map(product, ProductRequest.class))
                .toList();

        // Easy Logic
        if (products.isEmpty())
            throw new APIException("Product list is Empty!!!");

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

        if (products.isEmpty())
            throw new APIException("Product list is Empty!!!");

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

        if (products.isEmpty())
            throw new APIException("Product list is Empty!!!");

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productRequests);
        return productResponse;
    }

    @Override
    public ProductRequest updateProduct(Long productId, ProductRequest productRequest) {
        //Get the existing product from DB
        Product productFromDb = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        Product product = modelMapper.map(productRequest, Product.class);
        // Update the product info with the one in request body
        productFromDb.setProductName(product.getProductName());
        productFromDb.setDescription(product.getDescription());
        productFromDb.setQuantity(product.getQuantity());
        productFromDb.setDiscount(product.getDiscount());
        productFromDb.setPrice(product.getPrice());
        productFromDb.setSpecialPrice(product.getSpecialPrice());

        // Save to DB
        Product savedProduct = productRepository.save(productFromDb);

        return modelMapper.map(savedProduct, ProductRequest.class);
    }

    @Override
    public ProductRequest deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        productRepository.delete(product);
        return modelMapper.map(product, ProductRequest.class);
    }

    @Override
    public ProductRequest updateProductImage(Long productId, MultipartFile image) throws IOException {
        // Get the product from DB
        Product productFromDB = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        // Upload image to server
        // Get the file name of uploaded image
        String fileName = fileService.uploadImage(path, image);

        // Updating the new file name to the product
        productFromDB.setImage(fileName);

        // Save the updated product
        Product updatedProduct = productRepository.save(productFromDB);

        //return DTO after mapping product to DTO
        return modelMapper.map(updatedProduct, ProductRequest.class);
    }

}
