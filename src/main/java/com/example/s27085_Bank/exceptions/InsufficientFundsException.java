package com.example.s27085_Bank.exceptions;

public class InsufficientFundsException extends RuntimeException{
    public InsufficientFundsException() {
        super("Not enough funds");
    }
}
