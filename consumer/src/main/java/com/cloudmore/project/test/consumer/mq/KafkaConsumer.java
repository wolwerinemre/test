package com.cloudmore.project.test.consumer.mq;


import com.cloudmore.project.test.consumer.dto.RequestDto;
import com.cloudmore.project.test.consumer.service.RequestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    private final RequestService requestService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "${spring.kafka.topic-name.consumer}", groupId = "${spring.kafka.group-id.consumer}")
    public synchronized void receive(@Payload String payload) throws JsonProcessingException {
        log.info("payload: {}", payload);
        var requestDto = objectMapper.readValue(payload, RequestDto.class);
        requestService.save(requestDto);
    }
}
