package com.example.ecommerceapi.repository;

import com.example.ecommerceapi.model.Favourite;
import com.example.ecommerceapi.model.Product;
import com.example.ecommerceapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavouriteRepository
        extends JpaRepository<Favourite, Long> {

    List<Favourite> findAllByUser(User user);

    void deleteByUserAndProduct(User user, Product product);

    Favourite findByUserAndProduct(User user, Product product);
}