package com.cloudmore.project.test.consumer.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public final class TaxCalculator {

    @Min(1)
    @Max(100)
    @Value("${request.tax.percent}")
    private int taxPercent;

    public BigDecimal calculateWageWithTax(BigDecimal wage) {
        return wage
                .multiply(BigDecimal.valueOf(100 + taxPercent))
                .divide(BigDecimal.valueOf(100),2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateTax(BigDecimal wage) {
        return wage
                .multiply(BigDecimal.valueOf(taxPercent))
                .divide(BigDecimal.valueOf(100),2, RoundingMode.HALF_UP);
    }
}
