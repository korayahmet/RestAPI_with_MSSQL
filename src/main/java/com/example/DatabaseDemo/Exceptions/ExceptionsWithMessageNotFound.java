package com.example.DatabaseDemo.Exceptions;

public class ExceptionsWithMessageNotFound extends RuntimeException{
    public ExceptionsWithMessageNotFound(String message){
        super(message);
    }
}

