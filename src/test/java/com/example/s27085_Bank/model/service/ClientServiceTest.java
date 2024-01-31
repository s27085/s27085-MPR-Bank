package com.example.s27085_Bank.model.service;

import com.example.s27085_Bank.exceptions.ValidationException;
import com.example.s27085_Bank.model.client.Client;
import com.example.s27085_Bank.model.client.Currency;
import com.example.s27085_Bank.repository.ClientsRepository;
import com.example.s27085_Bank.service.ClientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


class ClientServiceTest {
    private ClientService clientService;

    @BeforeEach
    void setUp() {
        clientService = new ClientService(new ClientsRepository());
    }

    @Test
    void shouldCreateNewClient(){
        Client client = new Client(
                null, "12345678901", "Jan", "Kowalski", new BigDecimal(1000), Currency.PLN
        );
        Client result = assertDoesNotThrow(() -> clientService.addClient(client));
        assertEquals(result, client);
    }
    @ParameterizedTest
    @MethodSource("provideInvalidClientCredentials")
    void shouldNotRegisterNewClient(Client client, String message) {
        ValidationException result = Assertions.assertThrows(ValidationException.class, () -> clientService.addClient(client));
        Assertions.assertEquals(result.getErrors().toString(), message);
    }

    public static Stream<Arguments> provideInvalidClientCredentials() {
        return Stream.of(
                Arguments.of(new Client(null, "12345678901", "null", "null",   -1000, Currency.PLN), "{balance=Saldo nie może być ujemne}"),
                Arguments.of(new Client(null, "12345678901", null, "null",   1000, Currency.PLN), "{name=Imię nie może być puste}"),
                Arguments.of(new Client(null, "12345678901", "Jan", null,   1000, Currency.PLN), "{second name=Nazwisko nie może być puste}"),
                Arguments.of(new Client(null, "12340", "Jan", "Kowalski",   1000, Currency.PLN), "{pesel=Pesel musi mieć 11 znaków}"
        ));
    }

}