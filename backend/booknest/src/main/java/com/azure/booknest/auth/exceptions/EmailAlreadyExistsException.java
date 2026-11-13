package com.azure.booknest.auth.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException(String email) {
        super("An account already exists for email: " + email);
    }
}
