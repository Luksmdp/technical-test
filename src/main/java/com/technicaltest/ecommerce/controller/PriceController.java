package com.technicaltest.ecommerce.controller;

import com.technicaltest.ecommerce.model.dto.ApiResponse;
import com.technicaltest.ecommerce.model.dto.PriceRequestDto;
import com.technicaltest.ecommerce.model.dto.PriceResponseDto;
import com.technicaltest.ecommerce.service.PriceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@Slf4j
@Validated
@RequestMapping("/api/v1")
public class PriceController {
    private final PriceService priceService;

    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @Operation(summary = "Find price", description = "Gets the applicable price for a product and brand on a specific date")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Price found successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(value = "{\n" +
                                    "  \"message\": \"Price found successfully\",\n" +
                                    "  \"data\": {\n" +
                                    "    \"productId\": 35455,\n" +
                                    "    \"brandId\": 1,\n" +
                                    "    \"priceListId\": 4,\n" +
                                    "    \"applicationDate\": \"2020-06-15 21:00:00\",\n" +
                                    "    \"finalPrice\": 38.95\n" +
                                    "  }\n" +
                                    "}"))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Not found (multiple reasons may apply)",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(value = "{\n" +
                                    "  \"message\": \"Product ID does not exist, Brand ID does not exist, or no price available for the specified date\",\n" +
                                    "  \"data\": null\n" +
                                    "}"))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Bad Request (invalid request data)",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(value = "{\n" +
                                    "  \"timestamp\": \"2024-12-11T18:48:25.775+00:00\",\n" +
                                    "  \"status\": 400,\n" +
                                    "  \"error\": \"Bad Request\",\n" +
                                    "  \"path\": \"/api/v1/prices\"\n" +
                                    "}"))
            )
    })
    @PostMapping("prices")
    public ResponseEntity<ApiResponse<PriceResponseDto>> findPrice(
            @Valid @RequestBody @Schema(
            example = "{\n" +
                    "  \"applicationDate\": \"2020-06-15 21:00:00\",\n" +
                    "  \"productId\": 35455,\n" +
                    "  \"brandId\": 1\n" +
                    "}"
    ) PriceRequestDto priceRequestDto){
        log.info("Received request to find price with data: Brand ID: {}, Product ID: {}, Application Date: {}",
                priceRequestDto.getBrandId(), priceRequestDto.getProductId(), priceRequestDto.getApplicationDate());

        ResponseEntity<ApiResponse<PriceResponseDto>> response = priceService.findPrice(priceRequestDto);

        log.info("Response for price lookup: Status: {}, Body: {}", response.getStatusCode(), response.getBody());
        return response;

    }

    @Operation(
            summary = "Find price using query parameters",
            description = "Gets the applicable price for a product and brand on a specific date using query parameters."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Price found successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(value = "{\n" +
                                    "  \"message\": \"Price found successfully\",\n" +
                                    "  \"data\": {\n" +
                                    "    \"productId\": 35455,\n" +
                                    "    \"brandId\": 1,\n" +
                                    "    \"priceListId\": 4,\n" +
                                    "    \"applicationDate\": \"2020-06-15 21:00:00\",\n" +
                                    "    \"finalPrice\": 38.95\n" +
                                    "  }\n" +
                                    "}"))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Not found (multiple reasons may apply)",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(value = "{\n" +
                                    "  \"message\": \"Product ID does not exist, Brand ID does not exist, or no price available for the specified date\",\n" +
                                    "  \"data\": null\n" +
                                    "}"))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Bad Request (invalid request data)",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(value = "{\n" +
                                    "  \"timestamp\": \"2024-12-11T18:48:25.775+00:00\",\n" +
                                    "  \"status\": 400,\n" +
                                    "  \"error\": \"Bad Request\",\n" +
                                    "  \"path\": \"/api/v1/prices\"\n" +
                                    "}"))
            )
    })
    @GetMapping("prices")
    public ResponseEntity<ApiResponse<PriceResponseDto>> getFindPrice(
            @Parameter(description = "Date and time for which to find the price.", example = "2020-06-15T21:00:00")
            @RequestParam("applicationDate") @NotNull(message = "applicationDate is required") LocalDateTime applicationDate,
            @Parameter(description = "ID of the product.", example = "35455")
            @RequestParam("productId") @NotNull(message = "productId is required") Long productId,
            @Parameter(description = "ID of the brand.", example = "1")
            @RequestParam("brandId") @NotNull(message = "brandId is required") Long brandId
    ) {
        PriceRequestDto priceRequestDto = new PriceRequestDto(applicationDate, productId, brandId);

        log.info("Received request to find price with data: Brand ID: {}, Product ID: {}, Application Date: {}",
                priceRequestDto.getBrandId(), priceRequestDto.getProductId(), priceRequestDto.getApplicationDate());

        ResponseEntity<ApiResponse<PriceResponseDto>> response = priceService.findPrice(priceRequestDto);

        log.info("Response for price lookup: Status: {}, Body: {}", response.getStatusCode(), response.getBody());
        return response;
    }
}
