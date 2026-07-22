package com.example.ecommerceapi.service;

import com.example.ecommerceapi.dto.ProductRequest;
import com.example.ecommerceapi.dto.ProductResponse;
import com.example.ecommerceapi.dto.ReviewRequest;
import com.example.ecommerceapi.dto.ReviewResponse;
import com.example.ecommerceapi.exception.ProductNotFoundException;
import com.example.ecommerceapi.model.Product;
import com.example.ecommerceapi.model.ProductImage;
import com.example.ecommerceapi.model.ProductOption;
import com.example.ecommerceapi.model.ProductReview;
import com.example.ecommerceapi.repository.ProductImageRepository;
import com.example.ecommerceapi.repository.ProductRepository;
import com.example.ecommerceapi.repository.ProductReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductReviewRepository productReviewRepository;

    public ProductService(ProductRepository productRepository,
                          ProductImageRepository productImageRepository,
                          ProductReviewRepository productReviewRepository) {
        this.productRepository = productRepository;
        this.productImageRepository = productImageRepository;
        this.productReviewRepository = productReviewRepository;
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public ProductResponse createProduct(ProductRequest request) {

        Product product = new Product();

        product.setTitle(request.getTitle());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setCategory(request.getCategory());
        product.setThumbnail(request.getThumbnail());
        product.setStock(request.getStock());
        product.setRating(
                request.getRating() == null ? 0.0 : request.getRating()
        );
        product.setReviewCount(
                request.getReviewCount() == null ? 0 : request.getReviewCount()
        );

        if (request.getImages() != null) {

            for (String imageUrl : request.getImages()) {

                ProductImage image = new ProductImage();

                image.setImageUrl(imageUrl);
                image.setProduct(product);

                product.getImages().add(image);
            }
        }

        if (request.getOptions() != null) {

            request.getOptions().forEach((key, values) -> {

                for (String value : values) {

                    ProductOption option = new ProductOption();

                    option.setOptionName(key);
                    option.setOptionValue(value);
                    option.setProduct(product);

                    product.getOptions().add(option);
                }
            });
        }

        Product savedProduct = productRepository.save(product);

        return mapToResponse(savedProduct);
    }

    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        return mapToResponse(product);
    }

    public ProductResponse updateProduct(Long id, Product updatedProduct) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        product.setTitle(updatedProduct.getTitle());
        product.setDescription(updatedProduct.getDescription());
        product.setPrice(updatedProduct.getPrice());
        product.setCategory(updatedProduct.getCategory());
        product.setThumbnail(updatedProduct.getThumbnail());
        product.setStock(updatedProduct.getStock());

        Product savedProduct = productRepository.save(product);

        return mapToResponse(savedProduct);
    }

    public void deleteProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        productRepository.delete(product);
    }

    public List<ProductResponse> searchProducts(String title) {
        return productRepository
                .findByTitleContainingIgnoreCase(title)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public Page<ProductResponse> getProducts(int page,
                                     int size,
                                     String sortBy,
                                     String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return productRepository
                .findAll(pageable)
                .map(this::mapToResponse);
    }

    public Page<ProductResponse> getProductsByCategory(
            String category,
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return productRepository
                .findByCategoryIgnoreCase(category, pageable)
                .map(this::mapToResponse);
    }

    public ProductResponse addImage(Long productId, String imageUrl) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        ProductImage image = new ProductImage();

        image.setImageUrl(imageUrl);
        image.setProduct(product);

        product.getImages().add(image);

        Product savedProduct = productRepository.save(product);

        return mapToResponse(savedProduct);
    }

    private ProductResponse mapToResponse(Product product) {

        ProductResponse response = new ProductResponse();

        response.setId(product.getId());
        response.setTitle(product.getTitle());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setCategory(product.getCategory());
        response.setThumbnail(product.getThumbnail());
        response.setStock(product.getStock());
        response.setRating(product.getRating());
        response.setReviewCount(product.getReviewCount());

        response.setImages(
                product.getImages()
                        .stream()
                        .map(ProductImage::getImageUrl)
                        .toList()
        );

        Map<String, List<String>> options = new LinkedHashMap<>();

        for (ProductOption option : product.getOptions()) {

            options.computeIfAbsent(
                    option.getOptionName(),
                    k -> new ArrayList<>()
            ).add(option.getOptionValue());
        }

        response.setOptions(options);

        return response;
    }

    public ReviewResponse addReview(Long productId, ReviewRequest request) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        ProductReview review = new ProductReview();

        review.setUserName(request.getUserName());
        review.setComment(request.getComment());
        review.setRating(request.getRating());
        review.setCreatedAt(LocalDateTime.now());
        review.setProduct(product);

        productReviewRepository.save(review);
        updateProductRating(product);

        return new ReviewResponse(
                review.getId(),
                review.getUserName(),
                review.getComment(),
                review.getRating(),
                review.getCreatedAt()
        );
    }

    private void updateProductRating(Product product) {

        List<ProductReview> reviews =
                productReviewRepository.findByProductId(product.getId());

        if (reviews.isEmpty()) {
            product.setRating(0.0);
            product.setReviewCount(0);
        } else {

            double average = reviews.stream()
                    .mapToInt(ProductReview::getRating)
                    .average()
                    .orElse(0);

            product.setRating(Math.round(average * 10.0) / 10.0);
            product.setReviewCount(reviews.size());
        }

        productRepository.save(product);
    }

    public List<ReviewResponse> getReviews(Long productId) {

        productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        return productReviewRepository
                .findByProductIdOrderByCreatedAtDesc(productId)
                .stream()
                .map(review -> new ReviewResponse(
                        review.getId(),
                        review.getUserName(),
                        review.getComment(),
                        review.getRating(),
                        review.getCreatedAt()
                ))
                .toList();
    }
}