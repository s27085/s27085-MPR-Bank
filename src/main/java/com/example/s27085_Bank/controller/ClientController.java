package com.example.s27085_Bank.controller;

import com.example.s27085_Bank.model.client.Client;
import com.example.s27085_Bank.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @PostMapping("/create")
    public ResponseEntity<Client> createClient(@RequestBody Client client){
        Client createdClient = clientService.addClient(client);
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(createdClient);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Client> getClient(@PathVariable int id){
        Client Client = clientService.getClientById(id);
        return ResponseEntity.ok(Client);
    }
    @GetMapping("/balance/{balance}")
    public ResponseEntity<List<Client>> getClientsByBalance(@PathVariable int balance){
        List<Client> Clients = clientService.getClientsByBalance(balance);
        return ResponseEntity.ok(Clients);
    }

}
