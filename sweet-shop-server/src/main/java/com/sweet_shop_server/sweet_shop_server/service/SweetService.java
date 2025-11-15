package com.sweet_shop_server.sweet_shop_server.service;

import com.sweet_shop_server.sweet_shop_server.dto.SweetDTO;
import com.sweet_shop_server.sweet_shop_server.entity.Sweet;

import java.util.List;

public interface SweetService {

    // CREATE
    SweetDTO addSweet(SweetDTO sweetDTO);

    // READ
    List<SweetDTO> getAllSweets();

    List<SweetDTO> searchSweets(String name, String category, Double minPrice, Double maxPrice);

    SweetDTO getSweetById(Long id);

    // UPDATE
    SweetDTO updateSweet(Long id, SweetDTO sweetDTO);

    // DELETE
    void deleteSweet(Long id);  // Admin-only restriction handled in controller/security layer
}

