package com.cloudmore.project.test.producer.unit;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.cloudmore.project.test.producer.dto.RequestDto;
import com.cloudmore.project.test.producer.mq.KafkaProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.time.Instant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;

@ExtendWith(MockitoExtension.class)
class KafkaProducerTest {

  @Mock
  ObjectMapper mapper;

  @Mock
  KafkaTemplate<String, Object> kafkaTemplate;

  @InjectMocks
  KafkaProducer kafkaProducer;

  @BeforeEach
  void setup() {
    kafkaProducer = new KafkaProducer(mapper, kafkaTemplate);
  }

  @Test
  void test_sendRequest() throws JsonProcessingException {
    var requestDto = new RequestDto()
        .setName("Test-name")
        .setSurname("Test-surname")
        .setEventTime(Instant.now())
        .setWage(BigDecimal.TEN);
    ListenableFuture listenableFuture = mock(ListenableFuture.class);
    ArgumentCaptor<SuccessCallback> argumentCaptorS = ArgumentCaptor.forClass(SuccessCallback.class);
    ArgumentCaptor<FailureCallback> argumentCaptorF = ArgumentCaptor.forClass(FailureCallback.class);
    when(kafkaTemplate.send(any(Message.class))).thenReturn(listenableFuture);
    kafkaProducer.sendRequest(requestDto);
    verify(listenableFuture).addCallback(argumentCaptorS.capture(), argumentCaptorF.capture());
    assertNotNull(argumentCaptorS.getValue());
    assertNotNull(argumentCaptorF.getValue());
  }
}
