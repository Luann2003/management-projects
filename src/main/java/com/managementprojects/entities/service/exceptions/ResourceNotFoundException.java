package com.managementprojects.entities.service.exceptions;

@SuppressWarnings("serial")
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String msg) {
        super(msg);
    }
}
