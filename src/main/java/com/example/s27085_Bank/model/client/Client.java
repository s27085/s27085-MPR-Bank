package com.example.s27085_Bank.model.client;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Client {
    private Integer id;
    private String pesel;
    private String firstName;
    private String lastName;
    private BigDecimal balance;
    private Currency currency;

    public Client(BigDecimal balance) {
        this.balance = balance;
    }

    public Client(Integer id, String pesel, String firstName, String lastName, int balance, Currency currency) {
        this.id = id;
        this.pesel = pesel;
        this.firstName = firstName;
        this.lastName = lastName;
        this.balance = BigDecimal.valueOf(balance);
        this.currency = currency;
    }
}
