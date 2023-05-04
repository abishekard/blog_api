package com.example.blog_app_api.exceptions;

public class EmailAlreadyExistsException extends Exception{
    public EmailAlreadyExistsException() {
    }

    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
