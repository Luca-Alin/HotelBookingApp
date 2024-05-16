package com.acme.user.service;

public class InvalidSearchQueryException extends Throwable {

    public InvalidSearchQueryException() {
        super("Invalid query");
    }
}
