package com.example.DatabaseDemo.Exceptions;

public class ExceptionsWithMessageConflict extends RuntimeException{
    public ExceptionsWithMessageConflict(String message){
        super(message);
    }
}
