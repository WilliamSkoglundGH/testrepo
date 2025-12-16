package com.skoglund.util.exceptions;

public class EmptyFieldException extends InvalidInputException {
    public EmptyFieldException(String message) {
        super(message);
    }
}
