package com.sweet_shop_server.sweet_shop_server.service.impl;

import com.sweet_shop_server.sweet_shop_server.dto.SweetDTO;
import com.sweet_shop_server.sweet_shop_server.entity.Sweet;
import com.sweet_shop_server.sweet_shop_server.repository.SweetRepository;
import com.sweet_shop_server.sweet_shop_server.service.SweetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SweetServiceImpl implements SweetService {

    private final ModelMapper modelMapper;
    private final SweetRepository sweetRepository;

    @Override
    public SweetDTO addSweet(SweetDTO sweetDTO) {
        return null;
    }

    @Override
    public List<SweetDTO> getAllSweets() {
        return List.of();
    }

    @Override
    public List<SweetDTO> searchSweets(String name, String category, Double minPrice, Double maxPrice) {
        return List.of();
    }

    @Override
    public SweetDTO getSweetById(Long id) {
        return null;
    }

    @Override
    public SweetDTO updateSweet(Long id, SweetDTO sweetDTO) {
        return null;
    }

    @Override
    public void deleteSweet(Long id) {

    }
}
