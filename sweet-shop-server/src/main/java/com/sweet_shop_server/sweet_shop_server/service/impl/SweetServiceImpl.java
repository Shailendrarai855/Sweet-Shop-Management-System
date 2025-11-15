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
        Sweet sweet = modelMapper.map(sweetDTO, Sweet.class);
        if(sweetRepository.existsByName(sweet.getName())){
            throw new RuntimeException("Sweet with name "+sweet.getName()+" already exist.");
        }
        if(sweet.getPrice()<0 || sweet.getQuantity()<0){
            throw new RuntimeException("Sweet price and Quantity cannot be Negative.");
        }
        sweetRepository.save(sweet);
        log.info("Sweet saved.");
        return modelMapper.map(sweet, SweetDTO.class);

    }

    @Override
    public List<SweetDTO> getAllSweets() {
        List<Sweet> sweets = sweetRepository.findAll();

        return sweets.stream()
                .map(sweet -> modelMapper.map(sweet, SweetDTO.class))
                .toList();
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
