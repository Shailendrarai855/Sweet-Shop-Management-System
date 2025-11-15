package com.sweet_shop_server.sweet_shop_server.service;

public interface InventoryService {
    String purchaseSweet(Long sweetId, int quantity);

    String restockSweet(Long sweetId, int quantity);
}
