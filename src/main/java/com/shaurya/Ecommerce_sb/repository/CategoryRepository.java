package com.shaurya.Ecommerce_sb.repository;

import com.shaurya.Ecommerce_sb.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
