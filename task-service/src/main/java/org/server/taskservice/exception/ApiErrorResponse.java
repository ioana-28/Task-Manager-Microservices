package org.server.taskservice.exception;

public record ApiErrorResponse(
        int status,
        String error,
        String message
) {
}

