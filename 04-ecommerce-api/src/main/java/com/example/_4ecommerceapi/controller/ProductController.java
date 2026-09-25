package com.example._4ecommerceapi.controller;

import com.example._4ecommerceapi.dto.ProductDTO;
import com.example._4ecommerceapi.dto.ProductResponse;
import com.example._4ecommerceapi.model.Category;
import com.example._4ecommerceapi.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService ps) { productService = ps; }

    // GET /api/products?search=phone&category=ELECTRONICS&minPrice=100&maxPrice=500
    @GetMapping
    public ResponseEntity<Map<String, Object>> getProducts(
            @RequestParam(required = false)              String   search,
            @RequestParam(required = false) Category category,
            @RequestParam(required = false)              Double   minPrice,
            @RequestParam(required = false)              Double   maxPrice,
            @RequestParam(defaultValue = "0")            int      page,
            @RequestParam(defaultValue = "10")           int      size,
            @RequestParam(defaultValue = "createdAt")    String   sortBy,
            @RequestParam(defaultValue = "desc")         String   sortDir) {

        return ResponseEntity.ok(
                productService.getProducts(search, category, minPrice, maxPrice,
                        page, size, sortBy, sortDir));
    }

    // GET /api/products/1
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    // POST /api/products  (multipart/form-data with optional image)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductResponse> createProduct(
            @Valid @RequestPart("product")          ProductDTO     dto,
            @RequestPart(value = "image", required = false) MultipartFile  image) {

        return new ResponseEntity<>(
                productService.createProduct(dto, image), HttpStatus.CREATED);
    }

    // PUT /api/products/1
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @Valid @RequestPart("product") ProductDTO dto,
            @RequestPart(value = "image", required = false) MultipartFile image) {

        return ResponseEntity.ok(productService.updateProduct(id, dto, image));
    }

    // DELETE /api/products/1 (soft delete — sets active=false)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deactivated successfully");
    }
}