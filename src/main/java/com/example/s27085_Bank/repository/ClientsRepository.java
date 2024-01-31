package com.example.s27085_Bank.repository;

import com.example.s27085_Bank.exceptions.ClientNotFoundException;
import com.example.s27085_Bank.model.client.Client;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Repository
public class ClientsRepository {
    private List<Client> clients = new ArrayList<>();
    private Integer id = 0;
    public Client addClient(Client client){
        client.setId(id++);
        clients.add(client);
        return client;
    }

    public void removeAllClients(){
        clients.clear();
    }
    public Client getClientById(Integer id){
        return clients.stream()
                .filter(it -> it.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ClientNotFoundException("Client not found"));
    }
    public List<Client> getAllClients(){
        return clients;
    }
    public List<Client> getClientsByBalance(Integer balance){
        return clients.stream()
                .filter(it -> it.getBalance().compareTo(BigDecimal.valueOf(balance)) > 0)
                .collect(Collectors.toList());
    }
}
