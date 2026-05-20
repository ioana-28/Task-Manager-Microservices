package org.server.userservice.exception;

public record ApiErrorResponse(
        int status,
        String error,
        String message
) {
}

