package com.cloudmore.project.test.producer.unit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.cloudmore.project.test.producer.dto.RequestDto;
import com.cloudmore.project.test.producer.mq.KafkaProducer;
import com.cloudmore.project.test.producer.service.RequestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.math.BigDecimal;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RequestServiceTest {

  @Mock
  KafkaProducer kafkaProducer;

  @InjectMocks
  RequestService requestService;

  @Test
  void test_send() throws JsonProcessingException {
    var requestDto = new RequestDto()
        .setName("Test-name").setSurname("Test-surname").setEventTime(Instant.now())
        .setWage(BigDecimal.TEN);
    requestService.send(requestDto);
    verify(kafkaProducer, times(1)).sendRequest(any());
  }

}
