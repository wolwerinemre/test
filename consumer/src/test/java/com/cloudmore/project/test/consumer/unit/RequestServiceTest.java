package com.cloudmore.project.test.consumer.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import com.cloudmore.project.test.consumer.dto.RequestDto;
import com.cloudmore.project.test.consumer.mapper.RequestMapper;
import com.cloudmore.project.test.consumer.model.Request;
import com.cloudmore.project.test.consumer.repository.RequestRepository;
import com.cloudmore.project.test.consumer.service.RequestService;
import com.cloudmore.project.test.consumer.utils.TaxCalculator;
import java.math.BigDecimal;
import java.time.Instant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RequestServiceTest {

  @Mock
  TaxCalculator taxCalculator;

  RequestMapper requestMapper = RequestMapper.INSTANCE;

  @Mock
  RequestRepository requestRepository;

  @Captor
  ArgumentCaptor<Request> entityCaptor;

  RequestService requestService;

  @BeforeEach
  void setup() {
    requestService = new RequestService(requestRepository,requestMapper,taxCalculator);
  }

  @Test
  void test_save () {
    var requestDto = new RequestDto()
        .setName("Test-name").setSurname("Test-surname").setEventTime(Instant.now())
        .setWage(BigDecimal.TEN);

    requestService.save(requestDto);
    verify(requestRepository).save(entityCaptor.capture());

    var entity = entityCaptor.getValue();
    assertEquals(requestDto.getName(), entity.getName());
    assertEquals(requestDto.getSurname(), entity.getSurname());
    assertEquals(taxCalculator.calculateWageWithTax(requestDto.getWage()), entity.getWage());
    assertEquals(requestDto.getEventTime(), entity.getEventTime());
  }

}
