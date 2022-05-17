package com.cloudmore.project.test.producer.controller;

import com.cloudmore.project.test.producer.dto.RequestDto;
import com.cloudmore.project.test.producer.service.RequestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Validated
@RequestMapping(value = "/api/request")
@CrossOrigin(exposedHeaders = "Access-Control-Allow-Origin")
public class RequestController {

    private final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @Operation(summary = "Send requests")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request has been sent successfully", content = @Content),
            @ApiResponse(responseCode = "400", description = "Cannot convert request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal service error", content = @Content) })
    @PostMapping("/")
    public ResponseEntity<Void> produce(@Valid @RequestBody RequestDto request) {
        try {
            requestService.send(request);
            return ResponseEntity.ok().build();
        }
        catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().build();
        }
        catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
