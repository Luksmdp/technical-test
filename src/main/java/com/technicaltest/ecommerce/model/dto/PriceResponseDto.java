package com.technicaltest.ecommerce.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public class PriceResponseDto {
    @Schema(description = "Product Id")
    private Long productId;
    @Schema(description = "Brand Id")
    private Long brandId;
    @Schema(description = "PriceList Id")
    private Long priceListId;
    @Schema(description = "Application date")
    @JsonFormat(pattern = "yyyy-MM-dd' 'HH:mm:ss")
    private LocalDateTime applicationDate;
    @Schema(description = "Final price")
    private Double finalPrice;

    public PriceResponseDto(Long productId, Long brandId, Long priceListId, LocalDateTime applicationDate, Double finalPrice) {
        this.productId = productId;
        this.brandId = brandId;
        this.priceListId = priceListId;
        this.applicationDate = applicationDate;
        this.finalPrice = finalPrice;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Long getPriceListId() {
        return priceListId;
    }

    public void setPriceListId(Long priceListId) {
        this.priceListId = priceListId;
    }

    public LocalDateTime getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(LocalDateTime applicationDate) {
        this.applicationDate = applicationDate;
    }

    public Double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Double finalPrice) {
        this.finalPrice = finalPrice;
    }
    @Override
    public String toString() {
        return "PriceResponseDto{" +
                "productId=" + productId +
                ", brandId=" + brandId +
                ", priceList=" + priceListId +
                ", applicationDate=" + applicationDate +
                ", price=" + finalPrice +
                '}';
    }
}
