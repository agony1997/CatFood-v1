package com.example.catfood.domain.product.service;

import com.example.catfood.domain.product.entity.PriceHistory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class PriceDomainService {

    public Optional<PriceHistory> findLowestPrice(List<PriceHistory> histories) {
        return histories.stream()
                .min(Comparator.comparing(PriceHistory::getPrice));
    }

    public BigDecimal calculatePerHundredGrams(BigDecimal price, int weightGrams) {
        return price.multiply(BigDecimal.valueOf(100))
                .divide(BigDecimal.valueOf(weightGrams), 2, RoundingMode.HALF_UP);
    }
}
