package com.cloudmore.project.test.producer.integration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cloudmore.project.test.producer.dto.RequestDto;
import com.cloudmore.project.test.producer.mq.KafkaProducer;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles({"test"})
class RequestControllerIT {

  @Autowired
  MockMvc mvc;

  @Autowired
  ObjectMapper mapper;

  @MockBean
  KafkaProducer kafkaProducer;

  @Test
  void test_produce() throws Exception {

    var request = new RequestDto();
    request.setName("Request");
    request.setSurname("Test");
    request.setWage(BigDecimal.valueOf(100));
    request.setEventTime(Instant.now());

    var builder = MockMvcRequestBuilders.post("/api/request/")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(mapper.writeValueAsBytes(request));
    mvc.perform(builder)
        .andExpect(status().isOk());
    verify(kafkaProducer, times(1)).send(any());
  }
}
