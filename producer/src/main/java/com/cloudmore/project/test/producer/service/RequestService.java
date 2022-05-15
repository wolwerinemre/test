package com.cloudmore.project.test.producer.service;

import com.cloudmore.project.test.producer.dto.RequestDto;
import com.cloudmore.project.test.producer.mq.KafkaProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RequestService {

    private final KafkaProducer producer;

    public RequestService(KafkaProducer producer) {
        this.producer = producer;
    }

    public void send (RequestDto requestDto) throws JsonProcessingException {
        producer.send(requestDto);
    }

}
