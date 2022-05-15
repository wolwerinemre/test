package com.cloudmore.project.test.consumer.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name = "request")
public class Request {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false, length = 200)
    private String name;
    @Column(name = "surname", nullable = false, length = 200)
    private String surname;
    @Column(name = "wage", nullable = false, precision=7, scale=2)
    private BigDecimal wage;
    @Column(name = "event_time", nullable = false)
    private Instant eventTime;
    @Column(name = "tax", nullable = false, precision=7, scale=2)
    private BigDecimal tax;
}
