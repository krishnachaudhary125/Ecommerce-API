package com.example.ecommerceapi.service;

import com.example.ecommerceapi.dto.DistrictResponse;
import com.example.ecommerceapi.dto.ProvinceResponse;
import com.example.ecommerceapi.repository.DistrictRepository;
import com.example.ecommerceapi.repository.ProvinceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final ProvinceRepository provinceRepository;
    private final DistrictRepository districtRepository;

    public List<ProvinceResponse> getProvinces() {
        return provinceRepository.findAll()
                .stream()
                .map(province -> new ProvinceResponse(
                        province.getId(),
                        province.getName()
                ))
                .toList();
    }

    public List<DistrictResponse> getDistrictsByProvince(Long provinceId) {
        return districtRepository.findByProvinceId(provinceId)
                .stream()
                .map(district -> new DistrictResponse(
                        district.getId(),
                        district.getName()
                ))
                .toList();
    }
}
