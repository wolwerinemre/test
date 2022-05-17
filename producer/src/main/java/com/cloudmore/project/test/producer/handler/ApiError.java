package com.cloudmore.project.test.producer.handler;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import lombok.Data;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

@Data
public class ApiError {
    public static final String DATE_TIME_PATTERN = "dd-MM-yyyy hh:mm:ss";
    private HttpStatus status;
    @JsonIgnore
    private HttpHeaders headers;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN)
    private LocalDateTime timestamp;
    private String message;
    private String debugMessage;

    private ApiError() {
        timestamp = LocalDateTime.now();
    }

    public ApiError(HttpStatus status, HttpHeaders headers, Throwable ex) {
        this(status, headers, ex.getLocalizedMessage(), ex);
    }

    public ApiError(HttpStatus status, HttpHeaders headers, String message, Throwable ex) {
        this();
        this.status = status;
        this.headers = headers;
        this.message = Strings.isBlank(message) ? ExceptionUtils.getRootCauseMessage(ex) : message;
        this.debugMessage = ExceptionUtils.getStackTrace(ex);
    }
}
