package com.sengul.biddingapinew.application.exception;

public class ItemNotFoundException extends NotFoundException {

    public ItemNotFoundException(String id) {
        super("Item with id:" + id + "is not found!");
    }
}