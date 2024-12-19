package com.technicaltest.ecommerce.service;

import com.technicaltest.ecommerce.model.dto.ApiResponse;
import com.technicaltest.ecommerce.model.dto.PriceRequestDto;
import com.technicaltest.ecommerce.model.dto.PriceResponseDto;
import com.technicaltest.ecommerce.model.entity.Price;
import com.technicaltest.ecommerce.repository.BrandRepository;
import com.technicaltest.ecommerce.repository.PriceRepository;
import com.technicaltest.ecommerce.repository.ProductRepository;
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
    public ResponseEntity<ApiResponse<PriceResponseDto>> findPrice(PriceRequestDto priceRequestDto) {
        log.info("Starting price lookup for Brand ID: {}, Product ID: {}, Application Date: {}",
                priceRequestDto.getBrandId(), priceRequestDto.getProductId(), priceRequestDto.getApplicationDate());

        Optional<Price> priceOptional = priceRepository.findTopByApplicablePrice(
                priceRequestDto.getBrandId(),
                priceRequestDto.getProductId(),
                priceRequestDto.getApplicationDate());

        if (priceOptional.isEmpty()) {
            if (!productRepository.existsById(priceRequestDto.getProductId())){
                log.warn("The product with id: {} does not exist",priceRequestDto.getProductId());
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("The product with id: "+priceRequestDto.getProductId()+" does not exist" ,null));
            }
            if (!brandRepository.existsById(priceRequestDto.getBrandId())){
                log.warn("The brand with id: {} does not exist",priceRequestDto.getBrandId());
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("The brand with id: "+priceRequestDto.getBrandId()+" does not exist",null));
            }
            log.warn("There is no price available for this date: {}",priceRequestDto.getApplicationDate());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("There is no price available for this date: "+priceRequestDto.getApplicationDate(),null));
        }

        PriceResponseDto priceResponseDto = new PriceResponseDto(priceOptional.get().getProduct().getId(),
                priceOptional.get().getBrand().getId(),
                priceOptional.get().getId(),
                priceRequestDto.getApplicationDate(),
                priceOptional.get().getPrice());

        log.info("Price found: Product ID: {}, Brand ID: {}, Price List ID: {}, Price: {}, Application Date: {}",
                priceResponseDto.getProductId(), priceResponseDto.getBrandId(),
                priceResponseDto.getPriceListId(), priceResponseDto.getFinalPrice(), priceResponseDto.getApplicationDate());

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("Price found correctly",priceResponseDto));
    }
}
