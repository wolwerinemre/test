package com.cloudmore.project.test.consumer.unit;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.cloudmore.project.test.consumer.dto.RequestDto;
import com.cloudmore.project.test.consumer.mq.KafkaConsumer;
import com.cloudmore.project.test.consumer.service.RequestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class KafkaConsumerTest {

  @Mock
  RequestService requestService;

  KafkaConsumer kafkaConsumer;

  private static ObjectMapper objectMapper;

  @BeforeEach
  void setup() {
    objectMapper = new ObjectMapper().registerModules(new JavaTimeModule());
    kafkaConsumer = new KafkaConsumer(requestService, objectMapper);
  }

  @Test
  void test_receive() throws IOException {
    var payload = new String(getClass().getResourceAsStream("/payload.json").readAllBytes());
    var convertedDto = objectMapper.readValue(payload, RequestDto.class);
    ArgumentCaptor<RequestDto> captor = ArgumentCaptor.forClass(RequestDto.class);
    kafkaConsumer.receive(payload);
    verify(requestService, times(1)).save(captor.capture());
    var dto = captor.getValue();
    Assertions.assertEquals(dto.getName(), convertedDto.getName());
  }
}
