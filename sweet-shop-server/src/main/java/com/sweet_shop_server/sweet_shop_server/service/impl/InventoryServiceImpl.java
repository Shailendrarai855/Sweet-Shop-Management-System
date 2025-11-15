package com.sweet_shop_server.sweet_shop_server.service.impl;

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
        return "";
    }

    @Override
    public String restockSweet(Long sweetId, int quantity) {
        return "";
    }
}
