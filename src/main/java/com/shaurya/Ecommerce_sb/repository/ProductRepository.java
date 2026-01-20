package com.shaurya.Ecommerce_sb.repository;

import com.shaurya.Ecommerce_sb.model.Category;
import com.shaurya.Ecommerce_sb.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryOrderByPriceAsc(Category category);

    List<Product> findByProductNameLikeIgnoreCase(String keyword);
}
