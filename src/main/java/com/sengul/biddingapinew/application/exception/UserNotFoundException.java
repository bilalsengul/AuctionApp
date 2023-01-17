package com.sengul.biddingapinew.application.exception;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(String id) {
        super("User with id:" + id + "is not found!");
    }
}