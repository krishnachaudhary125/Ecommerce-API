package com.example.ecommerceapi.controller;

import com.example.ecommerceapi.dto.*;
import com.example.ecommerceapi.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping
    public ProductResponse createProduct(@Valid @RequestBody ProductRequest request) {
        return productService.createProduct(request);
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PutMapping("/{id}")
    public ProductResponse updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {

        return productService.updateProduct(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {

        productService.deleteProduct(id);

        return "Product deleted successfully";
    }

    @GetMapping("/search")
    public List<ProductResponse> searchProducts(
            @RequestParam String title) {

        return productService.searchProducts(title);
    }

    @GetMapping("/page")
    public Page<ProductResponse> getProducts(

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        return productService.getProducts(page, size, sortBy, direction);
    }

    @GetMapping("/category/{category}")
    public Page<ProductResponse> getProductsByCategory(

            @PathVariable String category,

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size,

            @RequestParam(defaultValue = "id") String sortBy,

            @RequestParam(defaultValue = "asc") String direction) {

        return productService.getProductsByCategory(
                category,
                page,
                size,
                sortBy,
                direction
        );
    }

    @PostMapping("/{id}/images")
    public ProductResponse addImage(
            @PathVariable Long id,
            @Valid @RequestBody ProductImageRequest request) {

        return productService.addImage(id, request.getImageUrl());
    }

    @PostMapping("/{id}/reviews")
    public ReviewResponse addReview(
            @PathVariable Long id,
            @Valid @RequestBody ReviewRequest request) {

        return productService.addReview(id, request);
    }

    @GetMapping("/{id}/reviews")
    public List<ReviewResponse> getReviews(@PathVariable Long id) {
        return productService.getReviews(id);
    }

    @GetMapping("/section")
    public ResponseEntity<ProductPageResponse> getProducts(
            @RequestParam String type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(
                productService.getProducts(
                        type,
                        page,
                        size
                )
        );
    }
}