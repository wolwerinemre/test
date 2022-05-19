package com.cloudmore.project.test.consumer.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TaxCalculator {

    @Min(1)
    @Max(100)
    @Value("${request.tax.percent}")
    private int taxPercent;

    public BigDecimal calculateWageWithTax(BigDecimal wage) {
        return wage.add(this.calculateTax(wage));
    }

    public BigDecimal calculateTax(BigDecimal wage) {
        return wage
                .multiply(BigDecimal.valueOf(taxPercent))
                .divide(BigDecimal.valueOf(100),2, RoundingMode.HALF_UP);
    }
}
