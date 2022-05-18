package com.cloudmore.project.test.producer.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.time.Instant;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Data;

@Data
public class RequestDto {

    @NotEmpty
    private String name;
    @NotEmpty
    private String surname;
    @Positive
    private BigDecimal wage;
    @NotNull
    private Instant eventTime;
    @JsonIgnore
    private BigDecimal tax;
}
