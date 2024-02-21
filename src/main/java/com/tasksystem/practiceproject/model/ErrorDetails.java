package com.tasksystem.practiceproject.model;

public record ErrorDetails (String errorCode, String description) {
    public static class ErrorConstants {
        public static final ErrorDetails RILO_DOWNSTREAM_FAILURE = new ErrorDetails("ATM_00101", "Downstream failure has occurred: RILO");

    }
}
