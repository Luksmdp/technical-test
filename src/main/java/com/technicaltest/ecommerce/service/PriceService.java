package com.technicaltest.ecommerce.service;

import com.technicaltest.ecommerce.model.dto.ApiResponse;
import com.technicaltest.ecommerce.model.dto.PriceRequestDto;
import com.technicaltest.ecommerce.model.dto.PriceResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface PriceService {
    public ResponseEntity<ApiResponse<PriceResponseDto>> findPrice(@RequestBody PriceRequestDto priceRequestDto);
}
