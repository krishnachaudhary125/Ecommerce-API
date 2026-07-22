package com.example.ecommerceapi.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ProductRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @Positive(message = "Price must be greater than 0")
    private Double price;

    @NotBlank(message = "Category is required")
    private String category;

    @NotBlank(message = "Thumbnail is required")
    private String thumbnail;

    @Min(value = 0)
    private Integer stock;

    private List<String> images;

    private Map<String, List<String>> options;

    private Double rating;

    private Integer reviewCount;
}