package com.technicaltest.ecommerce.controller;

import com.technicaltest.ecommerce.model.entity.Brand;
import com.technicaltest.ecommerce.model.entity.Currency;
import com.technicaltest.ecommerce.model.entity.Price;
import com.technicaltest.ecommerce.model.entity.Product;
import com.technicaltest.ecommerce.repository.PriceRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "spring.profiles.active=test")
@AutoConfigureMockMvc
public class PriceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PriceRepository priceRepository;

    @BeforeEach
    void setup() {
        priceRepository.save(new Price(1L,
                new Brand(1L, "Brand A",null),
                new Product(35455L, "Product A",null),
                new Currency("EUR","EURO",null),
                LocalDateTime.parse("2020-06-14T00:00:00"),
                LocalDateTime.parse("2020-12-31T23:59:59"),
                0,
                35.5
        ));
    }

    @AfterEach
    void teardown() {
        priceRepository.deleteAll();
    }

    @Test
    void testFindPrice_Success() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/api/v1/prices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"applicationDate\": \"2020-06-15T21:00:00\",\n" +
                                "  \"productId\": 35455,\n" +
                                "  \"brandId\": 1\n" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Price found successfully"))
                .andExpect(jsonPath("$.data.productId").value(35455))
                .andExpect(jsonPath("$.data.finalPrice").value(38.95));
    }

    @Test
    void testFindPrice_NotFound() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/api/v1/prices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"applicationDate\": \"2019-06-14T10:00:00\",\n" +
                                "  \"productId\": 35455,\n" +
                                "  \"brandId\": 1\n" +
                                "}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Product ID does not exist, Brand ID does not exist, or no price available for the specified date"))
                .andExpect(jsonPath("$.data").isEmpty());
    }
}
