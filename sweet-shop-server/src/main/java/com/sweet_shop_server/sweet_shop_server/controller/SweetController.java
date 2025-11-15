package com.sweet_shop_server.sweet_shop_server.controller;

import com.sweet_shop_server.sweet_shop_server.dto.SweetDTO;
import com.sweet_shop_server.sweet_shop_server.repository.SweetRepository;
import com.sweet_shop_server.sweet_shop_server.service.SweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/")
@RequiredArgsConstructor
public class SweetController {

    private final SweetService sweetService;

    @PostMapping("/sweets")
    public ResponseEntity<SweetDTO> addSweet(@RequestBody SweetDTO sweet) {
        SweetDTO created = sweetService.addSweet(sweet);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/sweets")
    public ResponseEntity<List<SweetDTO>> getAllSweets() {
        List<SweetDTO> sweets = sweetService.getAllSweets();
        return ResponseEntity.ok(sweets);
    }

    @GetMapping("/sweets/search")
    public ResponseEntity<List<SweetDTO>> searchSweets(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
    ) {
        List<SweetDTO> results = sweetService.searchSweets(name, category, minPrice, maxPrice);
        return ResponseEntity.ok(results);
    }

    @PutMapping("/sweets/{id}")
    public ResponseEntity<SweetDTO> updateSweet(@PathVariable Long id, @RequestBody SweetDTO sweet) {
        SweetDTO updated = sweetService.updateSweet(id, sweet);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteSweet(@PathVariable Long id) {
        sweetService.deleteSweet(id);
        return ResponseEntity.ok("Sweet deleted successfully");
    }
}
