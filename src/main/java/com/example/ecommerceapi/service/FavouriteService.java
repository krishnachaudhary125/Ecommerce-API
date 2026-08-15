package com.example.ecommerceapi.service;

import com.example.ecommerceapi.model.Favourite;
import com.example.ecommerceapi.model.Product;
import com.example.ecommerceapi.model.User;
import com.example.ecommerceapi.dto.FavouriteResponse;
import com.example.ecommerceapi.repository.FavouriteRepository;
import com.example.ecommerceapi.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavouriteService {

    private final FavouriteRepository favouriteRepository;
    private final ProductRepository productRepository;

    public FavouriteResponse toggleFavourite(
            User user,
            Long productId
    ){

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new RuntimeException("Product not found")
                );

        Favourite existingFavourite = favouriteRepository.findByUserAndProduct(user, product);

        if(existingFavourite != null){
            favouriteRepository.delete(existingFavourite);
            return new FavouriteResponse(
                    product.getId(),
                    product.getTitle(),
                    product.getThumbnail(),
                    product.getPrice(),
                    product.getBrand()
            );
        }

        Favourite favourite = Favourite.builder()
                .user(user)
                .product(product)
                .build();

        Favourite saved = favouriteRepository.save(favourite);

        return new FavouriteResponse(
                saved.getProduct().getId(),
                saved.getProduct().getTitle(),
                saved.getProduct().getThumbnail(),
                saved.getProduct().getPrice(),
                saved.getProduct().getBrand()
        );
    }

    public List<FavouriteResponse> getFavourite(User user){

        return favouriteRepository.findAllByUser(user)
                .stream()
                .map(favourite -> new FavouriteResponse(
                        favourite.getProduct().getId(),
                        favourite.getProduct().getTitle(),
                        favourite.getProduct().getThumbnail(),
                        favourite.getProduct().getPrice(),
                        favourite.getProduct().getBrand()
                ))
                .toList();
    }
}
