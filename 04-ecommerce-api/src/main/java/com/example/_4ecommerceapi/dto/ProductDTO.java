package com.example._4ecommerceapi.dto;

import com.example._4ecommerceapi.model.Category;
import jakarta.validation.constraints.*;

public class ProductDTO {

    @NotBlank(message = "Product name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private Double price;

    @NotNull(message = "Stock quantity is required")
    @Min(value = 0, message = "Stock cannot be negative")
    private Integer stock;

    @NotNull(message = "Category is required")
    private Category category;

    // Getters & Setters
    public String   getName()               { return name; }
    public void      setName(String v)        { this.name = v; }
    public String   getDescription()        { return description; }
    public void      setDescription(String v) { this.description = v; }
    public Double   getPrice()              { return price; }
    public void      setPrice(Double v)      { this.price = v; }
    public Integer  getStock()              { return stock; }
    public void      setStock(Integer v)     { this.stock = v; }
    public Category getCategory()           { return category; }
    public void      setCategory(Category v) { this.category = v; }
}
