package com.alcon3sl.cms.exception;

public class BankNotFoundException extends RuntimeException {
    public BankNotFoundException(String message) {
        super(message);
    }
}
