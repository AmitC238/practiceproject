package com.tasksystem.practiceproject.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tasksystem.practiceproject.client.RiLoResponseException;
import com.tasksystem.practiceproject.service.UUIDService;
import com.tasksystem.practiceproject.client.RiLoResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("atm/access-tickets")
public class AccessTicketController {

    private final UUIDService uuidService;
    private final RiLoResponse riLoResponse;

    public AccessTicketController(UUIDService uuidService, RiLoResponse riLoResponse) {
        this.uuidService = uuidService;
        this.riLoResponse = riLoResponse;
    }

    @GetMapping("/{householdId}")
    public ResponseEntity<String> getAccessTickets(@RequestHeader(name = "x-sky-request-id", required = false) String xSkyRequestId) throws IOException, RiLoResponseException
    {
        HttpHeaders responseHeaders = new HttpHeaders();

        UUID requestId = uuidService.getUUID(xSkyRequestId);
        responseHeaders.set("x-sky-request-id", String.valueOf(requestId));
        String responseBody = null;
        try {
            responseBody = riLoResponse.getAccessTickets("1").accessTickets.toString();
        } catch (IOException e) {
            throw new RiLoResponseException(e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Failed/Error 500");
        }

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(responseBody);
    }

}


