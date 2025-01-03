package com.technicaltest.ecommerce.service;

import com.technicaltest.ecommerce.model.dto.ApiResponse;
import com.technicaltest.ecommerce.model.dto.PriceRequestDto;
import com.technicaltest.ecommerce.model.dto.PriceResponseDto;
import com.technicaltest.ecommerce.model.entity.Brand;
import com.technicaltest.ecommerce.model.entity.Currency;
import com.technicaltest.ecommerce.model.entity.Price;
import com.technicaltest.ecommerce.model.entity.Product;
import com.technicaltest.ecommerce.repository.BrandRepository;
import com.technicaltest.ecommerce.repository.PriceRepository;
import com.technicaltest.ecommerce.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceServiceImplTest {

    @Mock
    private PriceRepository priceRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private BrandRepository brandRepository;

    @InjectMocks
    private PriceServiceImpl priceService;

    @Test
    void testFindPrice_PriceFound() {
        // Arrange
        Long brandId = 1L;
        Long productId = 35455L;
        LocalDateTime applicationDate = LocalDateTime.parse("2020-06-14T10:00:00");

        PriceRequestDto requestDto = new PriceRequestDto(applicationDate, productId, brandId);

        Price mockPrice = new Price(1L,
                new Brand(1L, "Brand A",null),
                new Product(35455L, "Product A",null),
                new Currency("EUR","EURO",null),
                LocalDateTime.parse("2020-06-14T00:00:00"),
                LocalDateTime.parse("2020-12-31T23:59:59"),
                0,
                35.5
        );

        when(priceRepository.findTopByApplicablePrice(
                brandId,
                productId,
                applicationDate
        )).thenReturn(Optional.of(mockPrice));

        // Act
        PriceResponseDto response = priceService.findPrice(requestDto);

        // Assert
        assertNotNull(response);
        assertEquals(mockPrice.getProduct().getId(), response.getProductId());
        assertEquals(mockPrice.getBrand().getId(), response.getBrandId());
        assertEquals(mockPrice.getId(), response.getPriceListId());
        assertEquals(requestDto.getApplicationDate(), response.getApplicationDate());
        assertEquals(mockPrice.getPrice().doubleValue(), response.getFinalPrice());
    }
    @Test
    void testFindPrice_PriceNotFound() {
        // Arrange
        PriceRequestDto requestDto = new PriceRequestDto(
                LocalDateTime.parse("2019-06-14T10:00:00"), 35455L, 1L
        );


        when(priceRepository.findTopByApplicablePrice(
                requestDto.getBrandId(),
                requestDto.getProductId(),
                requestDto.getApplicationDate()
        )).thenReturn(Optional.empty());

        when(productRepository.existsById(requestDto.getProductId())).thenReturn(true);

        when(brandRepository.existsById(requestDto.getBrandId())).thenReturn(true);

        // Act
        PriceResponseDto response = priceService.findPrice(requestDto);

        // Assert

    }

    @Test
    void testFindPrice_ProductNotFound() {
        // Arrange
        PriceRequestDto requestDto = new PriceRequestDto(
                LocalDateTime.parse("2019-06-14T10:00:00"), 35455L, 1L
        );


        when(priceRepository.findTopByApplicablePrice(
                requestDto.getBrandId(),
                requestDto.getProductId(),
                requestDto.getApplicationDate()
        )).thenReturn(Optional.empty());

        when(productRepository.existsById(requestDto.getProductId())).thenReturn(false);


        // Act
        PriceResponseDto response = priceService.findPrice(requestDto);

        // Assert

    }

    @Test
    void testFindPrice_BrandtNotFound() {
        // Arrange
        PriceRequestDto requestDto = new PriceRequestDto(
                LocalDateTime.parse("2019-06-14T10:00:00"), 35455L, 1L
        );


        when(priceRepository.findTopByApplicablePrice(
                requestDto.getBrandId(),
                requestDto.getProductId(),
                requestDto.getApplicationDate()
        )).thenReturn(Optional.empty());

        when(productRepository.existsById(requestDto.getProductId())).thenReturn(true);

        when(brandRepository.existsById(requestDto.getBrandId())).thenReturn(false);


        // Act
        PriceResponseDto response = priceService.findPrice(requestDto);

        // Assert

    }

}

