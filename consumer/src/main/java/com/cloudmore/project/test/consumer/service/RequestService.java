package com.cloudmore.project.test.consumer.service;

import com.cloudmore.project.test.consumer.dto.RequestDto;
import com.cloudmore.project.test.consumer.mapper.RequestMapper;
import com.cloudmore.project.test.consumer.model.Request;
import com.cloudmore.project.test.consumer.repository.RequestRepository;
import com.cloudmore.project.test.consumer.utils.TaxCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;
    private final TaxCalculator taxCalculator;


    public Request save (RequestDto requestDto) {
        var request = requestMapper.dtoToModel(requestDto);
        request.setTax(taxCalculator.calculateTax(request.getWage()));
        request.setWage(taxCalculator.calculateWageWithTax(request.getWage()));
        return requestRepository.save(request);
    }
}
