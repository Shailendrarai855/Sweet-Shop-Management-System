package com.sweet_shop_server.sweet_shop_server.controller;

import com.sweet_shop_server.sweet_shop_server.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sweets")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("/{id}/purchase")
    public ResponseEntity<String> purchase(
            @PathVariable Long id,
            @RequestParam(defaultValue = "1") int qty) {

        inventoryService.purchaseSweet(id, qty);
        return ResponseEntity.ok("Sweet purchased successfully");
    }

    @PostMapping("/{id}/restock")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> restock(
            @PathVariable Long id,
            @RequestParam int qty) {

        inventoryService.restockSweet(id, qty);
        return ResponseEntity.ok("Sweet restocked successfully");
    }
}
