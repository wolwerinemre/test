package com.cloudmore.project.test.consumer.integration;

import com.cloudmore.project.test.consumer.dto.RequestDto;
import com.cloudmore.project.test.consumer.service.RequestService;
import java.math.BigDecimal;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ConsumerServiceIT extends IntegrationTestBase {

  @Autowired
  private RequestService requestService;

  @Test
  void test_save() {
    //given
    var request = new RequestDto();
    request.setName("Request");
    request.setSurname("Test");
    request.setWage(BigDecimal.valueOf(100));
    request.setEventTime(Instant.now());

    //when
    var requestDB = requestService.save(request);

    //then
    assertThat(requestDB).isNotNull();
    assertThat(requestDB.getId()).isNotNull();
  }
}
