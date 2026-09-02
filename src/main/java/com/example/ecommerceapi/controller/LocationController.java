package com.example.ecommerceapi.controller;

import com.example.ecommerceapi.dto.DistrictResponse;
import com.example.ecommerceapi.dto.ProvinceResponse;
import com.example.ecommerceapi.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locations")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @GetMapping("/provinces")
    public List<ProvinceResponse> getProvinces() {
        return locationService.getProvinces();
    }

    @GetMapping("/provinces/{provinceId}/districts")
    public List<DistrictResponse> getDistricts(
            @PathVariable Long provinceId
    ) {
        return locationService.getDistrictsByProvince(provinceId);
    }
}