package com.example._4ecommerceapi.service;

import com.example._4ecommerceapi.dto.ProductDTO;
import com.example._4ecommerceapi.dto.ProductResponse;
import com.example._4ecommerceapi.exception.ResourceNotFoundException;
import com.example._4ecommerceapi.model.Category;
import com.example._4ecommerceapi.model.Product;
import com.example._4ecommerceapi.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository  productRepository;
    private final FileStorageService  fileStorageService;

    public ProductService(ProductRepository pr, FileStorageService fs) {
        productRepository = pr; fileStorageService = fs;
    }

    // ── GET all with search, filter, pagination ────────
    public Map<String, Object> getProducts(
            String search, Category category,
            Double minPrice, Double maxPrice,
            int page, int size, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        // single flexible query handles all filter combinations
        Page<Product> productPage =
                productRepository.searchProducts(search, category, minPrice, maxPrice, pageable);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("content",       productPage.getContent().stream().map(this::toResponse).collect(Collectors.toList()));
        response.put("pageNo",        productPage.getNumber());
        response.put("pageSize",      productPage.getSize());
        response.put("totalElements", productPage.getTotalElements());
        response.put("totalPages",    productPage.getTotalPages());
        response.put("last",         productPage.isLast());
        return response;
    }

    // ── GET one product ───────────────────────────────
    public ProductResponse getProductById(Long id) {
        Product p = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        return toResponse(p);
    }

    // ── CREATE product ────────────────────────────────
    @Transactional
    public ProductResponse createProduct(ProductDTO dto, MultipartFile image) {
        Product product = toEntity(dto);

        if (image != null && !image.isEmpty()) {
            String imagePath = fileStorageService.saveFile(image);
            product.setImageUrl(imagePath);
        }

        return toResponse(productRepository.save(product));
    }

    // ── UPDATE product ────────────────────────────────
    @Transactional
    public ProductResponse updateProduct(Long id, ProductDTO dto, MultipartFile image) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setCategory(dto.getCategory());

        if (image != null && !image.isEmpty()) {
            fileStorageService.deleteFile(product.getImageUrl()); // delete old image
            product.setImageUrl(fileStorageService.saveFile(image));
        }

        return toResponse(productRepository.save(product));
    }

    // ── SOFT DELETE (sets active=false, doesn't remove from DB) ─
    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        product.setActive(false); // soft delete — data preserved in DB
        productRepository.save(product);
    }

    // ── Entity → Response DTO ─────────────────────────
    private ProductResponse toResponse(Product p) {
        ProductResponse r = new ProductResponse();
        r.setId(p.getId()); r.setName(p.getName());
        r.setDescription(p.getDescription()); r.setPrice(p.getPrice());
        r.setStock(p.getStock()); r.setCategory(p.getCategory());
        r.setImageUrl(p.getImageUrl()); r.setActive(p.getActive());
        r.setCreatedAt(p.getCreatedAt()); r.setUpdatedAt(p.getUpdatedAt());
        return r;
    }

    // ── Request DTO → Entity ──────────────────────────
    private Product toEntity(ProductDTO dto) {
        Product p = new Product();
        p.setName(dto.getName()); p.setDescription(dto.getDescription());
        p.setPrice(dto.getPrice()); p.setStock(dto.getStock());
        p.setCategory(dto.getCategory());
        return p;
    }
}
