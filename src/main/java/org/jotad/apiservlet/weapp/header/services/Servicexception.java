package org.jotad.apiservlet.weapp.header.services;

public class Servicexception extends RuntimeException{

    public Servicexception(String message) {
        super(message);
    }

    public Servicexception(String message, Throwable cause) {
        super(message, cause);
    }
}
