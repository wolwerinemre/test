package com.cloudmore.project.test.producer.mq;

import com.cloudmore.project.test.producer.dto.RequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaProducer {

    private final ObjectMapper mapper;
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${spring.kafka.topic.producer}")
    private String topicName;

    public KafkaProducer(ObjectMapper mapper, KafkaTemplate<String, Object> kafkaTemplate) {
        this.mapper = mapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(RequestDto requestDto) throws JsonProcessingException{
        log.info(mapper.writeValueAsString(requestDto));
        Message<RequestDto> messageBuilder = MessageBuilder
                .withPayload(requestDto).setHeader(KafkaHeaders.TOPIC, topicName)
                .build();
        log.info(messageBuilder.toString());
        kafkaTemplate.send(messageBuilder);
    }
}
