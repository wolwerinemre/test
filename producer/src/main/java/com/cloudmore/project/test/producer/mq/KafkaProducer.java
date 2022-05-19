package com.cloudmore.project.test.producer.mq;

import static java.util.Objects.nonNull;

import com.cloudmore.project.test.producer.dto.RequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProducer {

    private final ObjectMapper mapper;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${spring.kafka.topic.producer}")
    private String topicName;

    public void sendRequest(RequestDto requestDto) throws JsonProcessingException{
        log.info(mapper.writeValueAsString(requestDto));
        var messageBuilder = MessageBuilder
                .withPayload(requestDto).setHeader(KafkaHeaders.TOPIC, topicName);
        this.send(messageBuilder);
    }

    private void send(MessageBuilder<?> message) {
        this.kafkaTemplate
            .send(message.build())
            .addCallback(
                result ->
                    log.debug(
                        "Published message [{}] to Kafka topic [{}]",
                        nonNull(result) ? result.getProducerRecord().value() : "",
                        message.build().getHeaders().get(KafkaHeaders.TOPIC)),
                exception -> log.error("Couldn't send entity to Kafka.", exception));
    }
}
