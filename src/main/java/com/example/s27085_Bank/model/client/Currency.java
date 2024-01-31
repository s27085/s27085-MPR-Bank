package com.example.s27085_Bank.model.client;

import java.util.stream.Stream;

public enum Currency {
    PLN,
    EUR,
    USD;

    public static Stream < Currency > stream() {
        return Stream.of(Currency.values());
    }
}
