package com.example.ecommerceapi.mapper;

import com.example.ecommerceapi.dto.CategoryResponse;
import com.example.ecommerceapi.dto.ProductResponse;
import com.example.ecommerceapi.model.Product;
import com.example.ecommerceapi.model.ProductOption;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductMapper {

    public ProductResponse toResponse(Product product) {

        ProductResponse response = new ProductResponse();

        response.setId(product.getId());
        response.setTitle(product.getTitle());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setDiscountPercentage(product.getDiscountPercentage());

        if (product.getCategory() != null) {
            CategoryResponse categoryResponse = new CategoryResponse(
                    product.getCategory().getId(),
                    product.getCategory().getName()
            );

            response.setCategory(categoryResponse);
        }

        response.setThumbnail(product.getThumbnail());
        response.setStock(product.getStock());
        response.setBrand(product.getBrand());
        response.setRating(product.getRating());
        response.setReviewCount(product.getReviewCount());

        response.setFeatured(product.getFeatured());
        response.setActive(product.getActive());

        response.setImages(
                product.getImages()
                        .stream()
                        .map(image -> image.getImageUrl())
                        .toList()
        );

        Map<String, List<String>> options = new LinkedHashMap<>();

        for (ProductOption option : product.getOptions()) {

            options.computeIfAbsent(
                    option.getOptionName(),
                    key -> new ArrayList<>()
            ).add(option.getOptionValue());
        }

        response.setOptions(options);

        return response;
    }
}