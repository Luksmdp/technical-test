package com.technicaltest.ecommerce.service;

import com.technicaltest.ecommerce.model.dto.ApiResponse;
import com.technicaltest.ecommerce.model.dto.PriceRequestDto;
import com.technicaltest.ecommerce.model.dto.PriceResponseDto;
import com.technicaltest.ecommerce.model.entity.Price;
import com.technicaltest.ecommerce.repository.BrandRepository;
import com.technicaltest.ecommerce.repository.PriceRepository;
import com.technicaltest.ecommerce.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Slf4j
@Service
public class PriceServiceImpl implements PriceService {
    private final PriceRepository priceRepository;
    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;

    public PriceServiceImpl(PriceRepository priceRepository, ProductRepository productRepository, BrandRepository brandRepository) {
        this.priceRepository = priceRepository;
        this.productRepository = productRepository;
        this.brandRepository = brandRepository;
    }

    @Override
    public PriceResponseDto findPrice(PriceRequestDto priceRequestDto) {
        log.info("Starting price lookup for Brand ID: {}, Product ID: {}, Application Date: {}",
                priceRequestDto.getBrandId(), priceRequestDto.getProductId(), priceRequestDto.getApplicationDate());

        Optional<Price> priceOptional = priceRepository.findTopByApplicablePrice(
                priceRequestDto.getBrandId(),
                priceRequestDto.getProductId(),
                priceRequestDto.getApplicationDate());

        if (priceOptional.isEmpty()) {
            if (!productRepository.existsById(priceRequestDto.getProductId())) {
                throw new EntityNotFoundException("The product with id: " + priceRequestDto.getProductId() + " does not exist");
            }
            if (!brandRepository.existsById(priceRequestDto.getBrandId())) {
                throw new EntityNotFoundException("The brand with id: " + priceRequestDto.getBrandId() + " does not exist");
            }
            throw new EntityNotFoundException("There is no price available for this date: " + priceRequestDto.getApplicationDate());
        }

        Price price = priceOptional.get();
        return new PriceResponseDto(
                price.getProduct().getId(),
                price.getBrand().getId(),
                price.getId(),
                priceRequestDto.getApplicationDate(),
                price.getPrice());
    }
}
