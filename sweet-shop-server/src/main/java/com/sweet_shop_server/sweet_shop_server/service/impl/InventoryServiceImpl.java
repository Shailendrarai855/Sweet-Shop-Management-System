package com.sweet_shop_server.sweet_shop_server.service.impl;

import com.sweet_shop_server.sweet_shop_server.entity.Sweet;
import com.sweet_shop_server.sweet_shop_server.exceptions.ResourceNotFoundException;
import com.sweet_shop_server.sweet_shop_server.repository.SweetRepository;
import com.sweet_shop_server.sweet_shop_server.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService {

    private final ModelMapper modelMapper;
    private final SweetRepository sweetRepository;


    @Override
    public String purchaseSweet(Long sweetId, int quantity) {

        Sweet sweet = sweetRepository.findById(sweetId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Sweet not found with id: " + sweetId));

        // quantity must be positive
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be at least 1");
        }

        // check available stock
        if (sweet.getQuantity() < quantity) {
            throw new IllegalStateException(
                    "Not enough stock. Available: " + sweet.getQuantity());
        }

        // deduct stock
        sweet.setQuantity(sweet.getQuantity() - quantity);

        sweetRepository.save(sweet);

        return "Purchased " + quantity + " x " + sweet.getName();
    }


    @Override
    public String restockSweet(Long sweetId, int quantity) {
        Sweet sweet = sweetRepository.findById(sweetId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Sweet not found with id: " + sweetId));

        // quantity must be positive
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be at least 1");
        }

        // add stock
        sweet.setQuantity(sweet.getQuantity() + quantity);

        sweetRepository.save(sweet);

        return "Restocked " + quantity + " x " + sweet.getName();
    }


}
