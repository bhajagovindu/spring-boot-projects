package com.example._4ecommerceapi.repository;

import com.example._4ecommerceapi.model.Category;
import com.example._4ecommerceapi.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Filter by category (only active products)
    Page<Product> findByCategoryAndActiveTrue(Category category, Pageable pageable);

    // Search by name (case-insensitive, partial match)
    Page<Product> findByNameContainingIgnoreCaseAndActiveTrue(String name, Pageable pageable);

    // All active products (excludes soft-deleted)
    Page<Product> findByActiveTrue(Pageable pageable);

    // Combined: search + category + price range filter using @Query
    @Query("""
        SELECT p FROM Product p
        WHERE p.active = true
          AND (:search   IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')))
          AND (:category IS NULL OR p.category = :category)
          AND (:minPrice IS NULL OR p.price >= :minPrice)
          AND (:maxPrice IS NULL OR p.price <= :maxPrice)
    """)
    Page<Product> searchProducts(
            @Param("search")   String   search,
            @Param("category") Category category,
            @Param("minPrice") Double   minPrice,
            @Param("maxPrice") Double   maxPrice,
            Pageable pageable
    );
}