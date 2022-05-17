package com.cloudmore.project.test.producer.handler;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@Slf4j
public class RestErrorHandler {

    @ExceptionHandler({Exception.class, RuntimeException.class})
    protected ResponseEntity<ApiError> handleError(Exception ex, WebRequest request) {
        var responseStatus = this.resolveAnnotatedResponseStatus(ex);
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var apiError = new ApiError(responseStatus, headers, ex);
        log.warn(this.getLogMessage(apiError), ex);
        return buildResponseEntity(apiError);
    }

    private HttpStatus resolveAnnotatedResponseStatus(Exception exception) {
        ResponseStatus annotation = AnnotatedElementUtils   .findMergedAnnotation(exception.getClass(), ResponseStatus.class);
        return Objects.nonNull(annotation) ? annotation.value() : HttpStatus.INTERNAL_SERVER_ERROR;
    }

    private String getLogMessage(ApiError apiError) {
        return String.format("Response status: %s, headers: %s, message: %s", apiError.getStatus(), apiError.getHeaders().toString(), apiError.getMessage());
    }

    private ResponseEntity<ApiError> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getHeaders(), apiError.getStatus());
    }
}

