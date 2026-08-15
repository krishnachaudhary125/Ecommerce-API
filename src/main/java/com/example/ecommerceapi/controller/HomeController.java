package com.example.ecommerceapi.controller;

import com.example.ecommerceapi.dto.HomeResponse;
import com.example.ecommerceapi.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;

    @GetMapping
    public ResponseEntity<HomeResponse> getHome() {

        return ResponseEntity.ok(
                homeService.getHome()
        );
    }
}