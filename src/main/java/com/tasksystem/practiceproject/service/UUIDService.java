package com.tasksystem.practiceproject.service;


import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public class UUIDService {

    public UUID getUUID(String requestHeaderValue) {
        try {
            return UUID.fromString(requestHeaderValue);
        } catch (IllegalArgumentException | NullPointerException e) {
            return UUID.randomUUID();
        }
    }
}