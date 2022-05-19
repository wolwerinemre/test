package com.cloudmore.project.test.consumer.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.time.Instant;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RequestDto {

    @NotEmpty
    @Size(min = 1, max = 200)
    private String name;
    @NotEmpty
    @Size(min = 1, max = 200)
    private String surname;
    @NotNull
    @DecimalMin(value = "0", inclusive = false)
    @Digits(integer=7, fraction=2)
    private BigDecimal wage;
    @NotNull
    private Instant eventTime;
    @JsonIgnore
    private BigDecimal tax;
}
