package com.example.ecommerceapi.controller;

import com.example.ecommerceapi.dto.AddFavouriteRequest;
import com.example.ecommerceapi.model.User;
import com.example.ecommerceapi.service.FavouriteService;
import com.example.ecommerceapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/favourites")
@RequiredArgsConstructor
public class FavouriteController {

    private final FavouriteService favouriteService;
    private final UserService userService;


    @PostMapping
    public ResponseEntity<?> toggleFavourite(
            @RequestBody AddFavouriteRequest request
    ){

        User currentUser = userService.getCurrentUser();

        return ResponseEntity.ok(
                favouriteService.toggleFavourite(
                        currentUser,
                        request.getProductId()
                )
        );
    }


    @GetMapping
    public ResponseEntity<?> getFavourite(){

        User user = userService.getCurrentUser();

        return ResponseEntity.ok(
                favouriteService.getFavourite(user)
        );
    }
}