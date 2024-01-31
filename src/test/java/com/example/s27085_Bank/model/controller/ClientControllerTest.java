package com.example.s27085_Bank.model.controller;

import com.example.s27085_Bank.model.client.Client;
import com.example.s27085_Bank.model.client.Currency;
import com.example.s27085_Bank.service.ClientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebClient

class ClientControllerTest {
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private ClientService clientService;
    @Autowired
    private ObjectMapper objectMapper;
    @BeforeEach
    void setUp() {
        clientService.removeAllClients();
    }

    @Test
    public void should_CreateNewClient() throws JsonProcessingException {
        Client client1 = new Client(BigDecimal.valueOf(100));
        client1.setPesel("12345678901");
        String serializedClient = objectMapper.writeValueAsString(client1);
        Client result = webTestClient.post().uri("/clients/create")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(serializedClient)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Client.class)
                .returnResult().getResponseBody();
        assert result != null;
        Client clientById = clientService.getClientById(result.getId());
        assertEquals(1, (long) clientService.getAllClients().size());

    }
    @Test
    public void should_ThrowPeselError() throws JsonProcessingException {
        Client client1 = new Client(0, "123456789", "Jan", "Kowalski", BigDecimal.valueOf(100), Currency.PLN);
        String serializedClient = objectMapper.writeValueAsString(client1);
        String result = webTestClient.post().uri("/clients/create")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(serializedClient)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(String.class)
                .returnResult().getResponseBody();

        assertEquals("{\"message\":\"Validation error\",\"errors\":{\"pesel\":\"Pesel musi mieć 11 znaków\"}}", result);

    }
}