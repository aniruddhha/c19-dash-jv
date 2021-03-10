package com.ani.coding.assignment.authentication.exception;

public class AppUserNotFoundException extends RuntimeException {

    @Override
    public String getMessage() {
        return "User Not Found";
    }
}
