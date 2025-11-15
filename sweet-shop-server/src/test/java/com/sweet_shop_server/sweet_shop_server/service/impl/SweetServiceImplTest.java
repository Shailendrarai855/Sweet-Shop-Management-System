package com.sweet_shop_server.sweet_shop_server.service.impl;

import com.sweet_shop_server.sweet_shop_server.dto.SweetDTO;
import com.sweet_shop_server.sweet_shop_server.entity.Sweet;
import com.sweet_shop_server.sweet_shop_server.exceptions.ResourceNotFoundException;
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
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
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
        sweetService = new SweetServiceImpl( modelMapper, sweetRepository);

    }

    private Sweet sweet(String name, String category, double price) {
        return new Sweet(null, name, category, price, 10);
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


    @Test
    void testSearchSweets_ByName() {
        // given
        List<Sweet> sweets = Arrays.asList(
                sweet("Gulab Jamun", "Dessert", 50.0),
                sweet("Kaju Katli", "Dessert", 100.0),
                sweet("Rasgulla", "Bengali", 40.0)
        );

        when(sweetRepository.findAll()).thenReturn(sweets);

        // when
        List<SweetDTO> result = sweetService.searchSweets("gulab", null, null, null);

        // then
        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getName()).isEqualTo("Gulab Jamun");
    }

    @Test
    void testSearchSweets_ByCategory() {
        when(sweetRepository.findAll()).thenReturn(List.of(
                sweet("Barfi", "North", 70.0),
                sweet("Sandesh", "Bengali", 60.0)
        ));
        when(modelMapper.map(any(Sweet.class), eq(SweetDTO.class)))
                .thenAnswer(invocation -> {
                    Sweet s = invocation.getArgument(0);
                    SweetDTO dto = new SweetDTO();
                    dto.setName(s.getName());
                    dto.setCategory(s.getCategory());
                    dto.setPrice(s.getPrice());
                    return dto;
                });
        List<SweetDTO> result = sweetService.searchSweets(null, "Bengali", null, null);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCategory()).isEqualTo("Bengali");
    }

    @Test
    void testSearchSweets_ByPriceRange() {
        when(sweetRepository.findAll()).thenReturn(List.of(
                sweet("Ladoo", "Indian", 30.0),
                sweet("Peda", "Indian", 80.0),
                sweet("Halwa", "Indian", 120.0)
        ));
        when(modelMapper.map(any(Sweet.class), eq(SweetDTO.class)))
                .thenAnswer(invocation -> {
                    Sweet s = invocation.getArgument(0);
                    SweetDTO dto = new SweetDTO();
                    dto.setName(s.getName());
                    dto.setCategory(s.getCategory());
                    dto.setPrice(s.getPrice());
                    return dto;
                });
        List<SweetDTO> result = sweetService.searchSweets(null, null, 50.0, 100.0);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Peda");
    }


    @Test
    void testSearchSweets_MultipleFilters() {

        when(sweetRepository.findAll()).thenReturn(List.of(
                sweet("Kaju Katli", "Royal", 200.0),
                sweet("Milk Cake", "Royal", 150.0),
                sweet("Jalebi", "Street", 40.0)
        ));

        // FIX: mock modelMapper so it does not return null
        when(modelMapper.map(any(Sweet.class), eq(SweetDTO.class)))
                .thenAnswer(invocation -> {
                    Sweet s = invocation.getArgument(0);
                    SweetDTO dto = new SweetDTO();
                    dto.setName(s.getName());
                    dto.setCategory(s.getCategory());
                    dto.setPrice(s.getPrice());
                    return dto;
                });

        List<SweetDTO> result = sweetService.searchSweets("kaju", "Royal", 100.0, 300.0);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Kaju Katli");
    }

    @Test
    void testUpdateSweet_WhenExists_ShouldUpdateAndReturnDTO() {
        Long id = 1L;

        SweetDTO inputDTO = new SweetDTO();
        inputDTO.setName("Updated Gulab Jamun");
        inputDTO.setPrice(70.0);
        inputDTO.setCategory("Royal");
        inputDTO.setQuantity(15);

        Sweet existingSweet = new Sweet();
        existingSweet.setId(id);
        existingSweet.setName("Old Name");
        existingSweet.setPrice(50.0);
        existingSweet.setCategory("Dessert");
        existingSweet.setQuantity(10);

        Sweet updatedSweet = new Sweet();
        updatedSweet.setId(id);
        updatedSweet.setName("Updated Gulab Jamun");
        updatedSweet.setPrice(70.0);
        updatedSweet.setCategory("Royal");
        updatedSweet.setQuantity(15);

        SweetDTO outputDTO = new SweetDTO();
        outputDTO.setName("Updated Gulab Jamun");
        outputDTO.setPrice(70.0);
        outputDTO.setCategory("Royal");
        outputDTO.setQuantity(15);

        // Mock repo find
        when(sweetRepository.findById(id)).thenReturn(java.util.Optional.of(existingSweet));

        // Mock save
        when(sweetRepository.save(existingSweet)).thenReturn(updatedSweet);

        // Mock model mapper
        when(modelMapper.map(updatedSweet, SweetDTO.class)).thenReturn(outputDTO);

        // Call service
        SweetDTO result = sweetService.updateSweet(id, inputDTO);

        // Verify result
        assertNotNull(result);
        assertEquals("Updated Gulab Jamun", result.getName());
        assertEquals(70.0, result.getPrice());
        assertEquals("Royal", result.getCategory());
        assertEquals(15, result.getQuantity());

        // Verify interactions
        verify(sweetRepository).findById(id);
        verify(sweetRepository).save(existingSweet);
        verify(modelMapper).map(updatedSweet, SweetDTO.class);
    }


    @Test
    void testUpdateSweet_WhenSweetNotFound_ShouldThrowException() {
        Long id = 1L;

        SweetDTO inputDTO = new SweetDTO();
        inputDTO.setName("New Name");

        when(sweetRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            sweetService.updateSweet(id, inputDTO);
        });

        verify(sweetRepository).findById(id);
        verify(sweetRepository, never()).save(any());
        verify(modelMapper, never()).map(any(), any());
    }


    @Test
    void deleteSweet_WhenExists_ShouldDeleteSuccessfully() {
        Long id = 1L;

        // given
        when(sweetRepository.existsById(id)).thenReturn(true);

        // when
        sweetService.deleteSweet(id);

        // then
        verify(sweetRepository, times(1)).existsById(id);
        verify(sweetRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteSweet_WhenNotExists_ShouldThrowException() {
        Long id = 1L;

        // given
        when(sweetRepository.existsById(id)).thenReturn(false);

        // then
        assertThatThrownBy(() -> sweetService.deleteSweet(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Sweet not found with id: " + id);

        verify(sweetRepository, times(1)).existsById(id);
        verify(sweetRepository, never()).deleteById(anyLong());
    }


}
