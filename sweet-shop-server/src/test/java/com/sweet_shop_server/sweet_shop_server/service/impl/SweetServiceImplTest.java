package com.sweet_shop_server.sweet_shop_server.service.impl;

import com.sweet_shop_server.sweet_shop_server.dto.SweetDTO;
import com.sweet_shop_server.sweet_shop_server.entity.Sweet;
import com.sweet_shop_server.sweet_shop_server.repository.SweetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class SweetServiceImplTest {

    @Mock
    private SweetRepository sweetRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private SweetServiceImpl sweetService;

    private SweetDTO validSweetDTO;
    private Sweet validSweetEntity;

    @BeforeEach
    void setUp() {

        // Valid SweetDTO
        validSweetDTO = new SweetDTO();
        validSweetDTO.setName("Gulab Jamun");
        validSweetDTO.setPrice(50.0);
        validSweetDTO.setQuantity(10);

        // Corresponding Sweet entity
        validSweetEntity = new Sweet();
        validSweetEntity.setName("Gulab Jamun");
        validSweetEntity.setPrice(50.0);
        validSweetEntity.setQuantity(10);
    }

    @Test
    void testAddSweet_whenValidSweet_thenAddedSweet() {
        when(modelMapper.map(validSweetDTO, Sweet.class)).thenReturn(validSweetEntity);
        when(sweetRepository.existsByName(validSweetEntity.getName())).thenReturn(false);
        when(sweetRepository.save(validSweetEntity)).thenReturn(validSweetEntity);
        when(modelMapper.map(validSweetEntity, SweetDTO.class)).thenReturn(validSweetDTO);

        SweetDTO savedDTO = sweetService.addSweet(validSweetDTO);

        assertNotNull(savedDTO);
        assertEquals("Gulab Jamun", savedDTO.getName());
        assertEquals(50.0, savedDTO.getPrice());
        assertEquals(10, savedDTO.getQuantity());

        verify(modelMapper).map(validSweetDTO, Sweet.class);
        verify(sweetRepository).existsByName(validSweetEntity.getName());
        verify(sweetRepository).save(validSweetEntity);
        verify(modelMapper).map(validSweetEntity, SweetDTO.class);
    }

    @Test
    void testAddSweet_whenDuplicateName_thenThrowsException() {
        when(modelMapper.map(validSweetDTO, Sweet.class)).thenReturn(validSweetEntity);
        when(sweetRepository.existsByName(validSweetEntity.getName())).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            sweetService.addSweet(validSweetDTO);
        });

        assertEquals("Sweet with name Gulab Jamun already exist.", exception.getMessage());

        verify(sweetRepository).existsByName(validSweetEntity.getName());
        verify(sweetRepository, never()).save(any());
    }

    @Test
    void testAddSweet_whenNegativePrice_ThenThrowsException() {
        SweetDTO invalidDTO = new SweetDTO();
        invalidDTO.setName("Ladoo");
        invalidDTO.setPrice(-10.0); // negative price
        invalidDTO.setQuantity(5);

        Sweet invalidEntity = new Sweet();
        invalidEntity.setName("Ladoo");
        invalidEntity.setPrice(-10.0);
        invalidEntity.setQuantity(5);

        when(modelMapper.map(invalidDTO, Sweet.class)).thenReturn(invalidEntity);
        when(sweetRepository.existsByName("Ladoo")).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            sweetService.addSweet(invalidDTO);
        });

        assertEquals("Sweet price and Quantity cannot be Negative.", exception.getMessage());

        verify(sweetRepository).existsByName("Ladoo");
        verify(sweetRepository, never()).save(any());
    }

    @Test
    void testAddSweet_whenNegativeQuantity_thenThrowsException() {
        SweetDTO invalidDTO = new SweetDTO();
        invalidDTO.setName("Barfi");
        invalidDTO.setPrice(30.0);
        invalidDTO.setQuantity(-5); // negative quantity

        Sweet invalidEntity = new Sweet();
        invalidEntity.setName("Barfi");
        invalidEntity.setPrice(30.0);
        invalidEntity.setQuantity(-5);

        when(modelMapper.map(invalidDTO, Sweet.class)).thenReturn(invalidEntity);
        when(sweetRepository.existsByName("Barfi")).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            sweetService.addSweet(invalidDTO);
        });

        assertEquals("Sweet price and Quantity cannot be Negative.", exception.getMessage());

        verify(sweetRepository).existsByName("Barfi");
        verify(sweetRepository, never()).save(any());
    }


}