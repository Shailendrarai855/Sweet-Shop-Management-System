package com.sweet_shop_server.sweet_shop_server.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.sweet_shop_server.sweet_shop_server.entity.Sweet;
import com.sweet_shop_server.sweet_shop_server.exceptions.ResourceNotFoundException;
import com.sweet_shop_server.sweet_shop_server.repository.SweetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;



@ExtendWith(MockitoExtension.class)
public class InventoryServiceTest {

    @Mock
    private SweetRepository sweetRepository;

    @InjectMocks
    private InventoryServiceImpl inventoryService; // your service implementation

    @BeforeEach
    void setUp() {

    }

    @Test
    void testPurchaseSweet_Success() {
        // Given
        Sweet sweet = new Sweet();
        sweet.setId(1L);
        sweet.setName("Chocolate");
        sweet.setQuantity(10);

        when(sweetRepository.findById(1L)).thenReturn(Optional.of(sweet));
        when(sweetRepository.save(any(Sweet.class))).thenReturn(sweet);

        // When
        String result = inventoryService.purchaseSweet(1L, 3);

        // Then
        assertThat(result).isEqualTo("Purchased 3 x Chocolate");
        assertThat(sweet.getQuantity()).isEqualTo(7);
        verify(sweetRepository).save(sweet);
    }

    @Test
    void testPurchaseSweet_SweetNotFound() {
        when(sweetRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> inventoryService.purchaseSweet(1L, 1))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Sweet not found with id: 1");
    }

    @Test
    void testPurchaseSweet_InvalidQuantity() {
        Sweet sweet = new Sweet();
        sweet.setId(1L);
        sweet.setName("Chocolate");
        sweet.setQuantity(10);

        when(sweetRepository.findById(1L)).thenReturn(Optional.of(sweet));

        assertThatThrownBy(() -> inventoryService.purchaseSweet(1L, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Quantity must be at least 1");
    }

    @Test
    void testPurchaseSweet_NotEnoughStock() {
        Sweet sweet = new Sweet();
        sweet.setId(1L);
        sweet.setName("Chocolate");
        sweet.setQuantity(2);

        when(sweetRepository.findById(1L)).thenReturn(Optional.of(sweet));

        assertThatThrownBy(() -> inventoryService.purchaseSweet(1L, 5))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Not enough stock. Available: 2");
    }
}
