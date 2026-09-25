package com.example._4ecommerceapi.dto;

import com.example._4ecommerceapi.model.Category;

import java.time.LocalDateTime;

// What the client receives — includes id, imageUrl, timestamps
public class ProductResponse {

    private Long          id;
    private String        name;
    private String        description;
    private Double        price;
    private Integer       stock;
    private Category      category;
    private String        imageUrl;
    private Boolean       active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ProductResponse() {}

    // Getters & Setters
    public Long          getId()               { return id; }
    public void           setId(Long v)         { this.id = v; }
    public String        getName()             { return name; }
    public void           setName(String v)     { this.name = v; }
    public String        getDescription()      { return description; }
    public void           setDescription(String v){ this.description = v; }
    public Double        getPrice()            { return price; }
    public void           setPrice(Double v)    { this.price = v; }
    public Integer       getStock()            { return stock; }
    public void           setStock(Integer v)   { this.stock = v; }
    public Category      getCategory()         { return category; }
    public void           setCategory(Category v){ this.category = v; }
    public String        getImageUrl()         { return imageUrl; }
    public void           setImageUrl(String v) { this.imageUrl = v; }
    public Boolean       getActive()           { return active; }
    public void           setActive(Boolean v)  { this.active = v; }
    public LocalDateTime getCreatedAt()        { return createdAt; }
    public void           setCreatedAt(LocalDateTime v){ this.createdAt = v; }
    public LocalDateTime getUpdatedAt()        { return updatedAt; }
    public void           setUpdatedAt(LocalDateTime v){ this.updatedAt = v; }
}
