package com.cloudmore.project.test.consumer.unit;


import com.cloudmore.project.test.consumer.utils.TaxCalculator;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

@ExtendWith(MockitoExtension.class)
class TaxCalculatorTest {

  @Value("${request.tax.percent}")
  private int tax;

  @InjectMocks
  private TaxCalculator taxCalculator;

  @Test
  void test_calculateWageWithTax() {
    var wage = BigDecimal.valueOf(100);
    var wageWithTax = taxCalculator.calculateWageWithTax(wage);
    var onlyTax = taxCalculator.calculateTax(wage);
    Assertions.assertEquals(wage
            .add(wage.multiply(BigDecimal.valueOf(tax))
            .divide(BigDecimal.valueOf(100),2, RoundingMode.HALF_UP)),
        wageWithTax, "110");
    Assertions.assertEquals(wage
            .multiply(BigDecimal.valueOf(tax))
            .divide(BigDecimal.valueOf(100),2, RoundingMode.HALF_UP),
        onlyTax, "10");
  }
}
