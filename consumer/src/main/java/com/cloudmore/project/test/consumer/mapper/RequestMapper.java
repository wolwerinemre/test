package com.cloudmore.project.test.consumer.mapper;

import com.cloudmore.project.test.consumer.dto.RequestDto;
import com.cloudmore.project.test.consumer.model.Request;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RequestMapper {

    RequestMapper INSTANCE = Mappers.getMapper(RequestMapper.class);

    RequestDto modelToDto(Request request);

    @InheritInverseConfiguration
    Request dtoToModel(RequestDto requestDto);

}
