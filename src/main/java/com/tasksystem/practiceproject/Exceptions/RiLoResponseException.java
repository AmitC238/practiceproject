package com.tasksystem.practiceproject.Exceptions;

import java.io.IOException;
import com.tasksystem.practiceproject.model.ErrorDetails;

public class RiLoResponseException extends RuntimeException {

    private final ErrorDetails errorDetails;

    public RiLoResponseException(ErrorDetails errorDetails) {
        super(errorDetails.description());
        this.errorDetails = errorDetails;
    }
    public ErrorDetails getAtmError() {
        return errorDetails;
    }
}
