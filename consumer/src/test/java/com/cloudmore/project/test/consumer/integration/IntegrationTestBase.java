package com.cloudmore.project.test.consumer.integration;

import org.junit.After;
import org.junit.ClassRule;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class IntegrationTestBase {
  @ClassRule
  public static final MySQLContainer<?> CONTAINER;

  static {
    CONTAINER =
        new MySQLContainer<>(
            DockerImageName.parse("mysql/mysql-server:5.7").asCompatibleSubstituteFor("mysql:5.7"))
            .withReuse(true);
    CONTAINER.start();
  }

  @DynamicPropertySource
  static void mssqlProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", CONTAINER::getJdbcUrl);
    registry.add("spring.datasource.password", CONTAINER::getPassword);
    registry.add("spring.datasource.username", CONTAINER::getUsername);
    registry.add("spring.datasource.driver-class-name", CONTAINER::getDriverClassName);
  }

  @After
  public void after(){
    CONTAINER.close();
  }
}
