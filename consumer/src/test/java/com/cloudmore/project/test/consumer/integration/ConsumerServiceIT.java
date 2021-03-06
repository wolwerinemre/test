package com.cloudmore.project.test.consumer.integration;

import static org.assertj.core.api.Assertions.assertThat;

import com.cloudmore.project.test.consumer.dto.RequestDto;
import com.cloudmore.project.test.consumer.service.RequestService;
import java.math.BigDecimal;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class ConsumerServiceIT extends IntegrationTestBase {

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
    assertThat(requestDB.getName()).isEqualTo(request.getName());
    assertThat(requestDB.getSurname()).isEqualTo(request.getSurname());
  }
}
