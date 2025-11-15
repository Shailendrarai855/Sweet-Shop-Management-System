package com.sweet_shop_server.sweet_shop_server.service.impl;

import com.sweet_shop_server.sweet_shop_server.dto.SweetDTO;
import com.sweet_shop_server.sweet_shop_server.entity.Sweet;
import com.sweet_shop_server.sweet_shop_server.repository.SweetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;

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

    private Sweet sweet1;
    private Sweet sweet2;

    private SweetDTO sweetDTO1;
    private SweetDTO sweetDTO2;

    @BeforeEach
    void setUp() {

        // Valid SweetDTO
        validSweetDTO = new SweetDTO();
        validSweetDTO.setName("Gulab Jamun");
        validSweetDTO.setPrice(50.0);
        validSweetDTO.setQuantity(10);

        // Entity
        validSweetEntity = new Sweet();
        validSweetEntity.setName("Gulab Jamun");
        validSweetEntity.setPrice(50.0);
        validSweetEntity.setQuantity(10);

        // Sweet list for getAllSweets()
        sweet1 = new Sweet();
        sweet1.setName("Gulab Jamun");
        sweet1.setPrice(50.0);
        sweet1.setQuantity(10);

        sweet2 = new Sweet();
        sweet2.setName("Ladoo");
        sweet2.setPrice(40.0);
        sweet2.setQuantity(20);

        // DTOs
        sweetDTO1 = new SweetDTO();
        sweetDTO1.setName("Gulab Jamun");
        sweetDTO1.setPrice(50.0);
        sweetDTO1.setQuantity(10);

        sweetDTO2 = new SweetDTO();
        sweetDTO2.setName("Ladoo");
        sweetDTO2.setPrice(40.0);
        sweetDTO2.setQuantity(20);
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
        invalidDTO.setPrice(-10.0);
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

    // ------------------------ getAllSweets tests ------------------------

    @Test
    void testGetAllSweets_WhenSweetsExist_ReturnsSweetDTOList() {

        when(sweetRepository.findAll()).thenReturn(Arrays.asList(sweet1, sweet2));
        when(modelMapper.map(sweet1, SweetDTO.class)).thenReturn(sweetDTO1);
        when(modelMapper.map(sweet2, SweetDTO.class)).thenReturn(sweetDTO2);

        List<SweetDTO> result = sweetService.getAllSweets();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Gulab Jamun", result.get(0).getName());
        assertEquals("Ladoo", result.get(1).getName());

        verify(sweetRepository).findAll();
        verify(modelMapper).map(sweet1, SweetDTO.class);
        verify(modelMapper).map(sweet2, SweetDTO.class);
    }

    @Test
    void testGetAllSweets_WhenNoSweetsExist_ReturnsEmptyList() {

        when(sweetRepository.findAll()).thenReturn(List.of());

        List<SweetDTO> result = sweetService.getAllSweets();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(sweetRepository).findAll();
        verify(modelMapper, never()).map(any(), any());
    }
}
