package com.technicaltest.ecommerce.controller;

import com.technicaltest.ecommerce.model.dto.ApiResponse;
import com.technicaltest.ecommerce.model.dto.PriceRequestDto;
import com.technicaltest.ecommerce.model.dto.PriceResponseDto;
import com.technicaltest.ecommerce.service.PriceService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PriceController.class)
class PriceControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PriceService priceService;
    private static final String BASE_URL = "/api/v1/prices";

    @Test
    void testGetFindPrice_Success() throws Exception {
        // Arrange
        PriceResponseDto responseDto = new PriceResponseDto(
                35455L,
                1L,
                4L,
                LocalDateTime.parse("2020-06-15T21:00:00"),
                38.95
        );

        ApiResponse<PriceResponseDto> apiResponse = new ApiResponse<>("Price found successfully", responseDto);

        when(priceService.findPrice(any(PriceRequestDto.class))).thenReturn(ResponseEntity.ok(apiResponse));

        // Act & Assert
        mockMvc.perform(get(BASE_URL)
                        .param("applicationDate", "2020-06-15T21:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Price found successfully"))
                .andExpect(jsonPath("$.data.productId").value(35455))
                .andExpect(jsonPath("$.data.applicationDate").value("2020-06-15 21:00:00"))
                .andExpect(jsonPath("$.data.brandId").value(1))
                .andExpect(jsonPath("$.data.finalPrice").value(38.95));

        Mockito.verify(priceService, times(1)).findPrice(any(PriceRequestDto.class));
    }

    @Test
    void testCase1() throws Exception {
        PriceResponseDto responseDto = new PriceResponseDto(
                35455L,
                1L,
                4L,
                LocalDateTime.parse("2020-06-15T21:00:00"),
                38.95
        );

        ApiResponse<PriceResponseDto> apiResponse = new ApiResponse<>("Price found successfully", responseDto);

        when(priceService.findPrice(any(PriceRequestDto.class))).thenReturn(ResponseEntity.ok(apiResponse));

        mockMvc.perform(get(BASE_URL)
                        .param("applicationDate", "2020-06-14T10:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Price found successfully"))
                .andExpect(jsonPath("$.data.productId").value(35455))
                .andExpect(jsonPath("$.data.applicationDate").value("2020-06-15 21:00:00"))
                .andExpect(jsonPath("$.data.brandId").value(1))
                .andExpect(jsonPath("$.data.finalPrice").value(38.95));

        Mockito.verify(priceService, times(1)).findPrice(any(PriceRequestDto.class));
    }

}

