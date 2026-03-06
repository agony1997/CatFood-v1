package com.example.catfood.domain.product.service;

import com.example.catfood.domain.product.entity.PriceHistory;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PriceDomainServiceTest {

    private final PriceDomainService service = new PriceDomainService();

    @Test
    void findLowestPrice_returnsLowest() {
        PriceHistory low = createPrice(new BigDecimal("43.00"));
        PriceHistory mid = createPrice(new BigDecimal("45.00"));
        PriceHistory high = createPrice(new BigDecimal("46.00"));

        Optional<PriceHistory> result = service.findLowestPrice(List.of(high, low, mid));

        assertTrue(result.isPresent());
        assertEquals(new BigDecimal("43.00"), result.get().getPrice());
    }

    @Test
    void findLowestPrice_emptyList_returnsEmpty() {
        Optional<PriceHistory> result = service.findLowestPrice(Collections.emptyList());
        assertTrue(result.isEmpty());
    }

    @Test
    void calculatePerHundredGrams_normalCase() {
        BigDecimal result = service.calculatePerHundredGrams(new BigDecimal("43.00"), 85);
        assertEquals(new BigDecimal("50.59"), result);
    }

    @Test
    void calculatePerHundredGrams_boxCase() {
        // 24罐箱 900元, 每罐85g, 總重 85*24=2040g
        BigDecimal result = service.calculatePerHundredGrams(new BigDecimal("900.00"), 2040);
        assertEquals(new BigDecimal("44.12"), result);
    }

    private PriceHistory createPrice(BigDecimal price) {
        PriceHistory ph = new PriceHistory();
        ph.setId(UUID.randomUUID());
        ph.setStoreId(UUID.randomUUID());
        ph.setPrice(price);
        return ph;
    }
}
