package com.example.s27085_Bank.model.service;

import com.example.s27085_Bank.exceptions.ValidationException;
import com.example.s27085_Bank.model.client.Currency;
import com.example.s27085_Bank.model.repository.ClientsRepository;
import com.example.s27085_Bank.model.client.Client;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ClientService {
    private static final Logger logger = LogManager.getLogger();

    private final ClientsRepository clientsRepository;

    public Client addClient(Client client) {
        validateNewClient(client);
        Client createdClient = clientsRepository.addClient(client);
        logger.info("Client created successfully with id " + createdClient.getId());
        return createdClient;
    }
    public List<Client> getClientsByBalance(Integer balance) {
        return clientsRepository.getClientsByBalance(balance);
    }
    public List<Client> getAllClients() {
        return clientsRepository.getAllClients();
    }
    public void removeAllClients() {
        clientsRepository.removeAllClients();
    }

    public void removeClientById(Integer id) {
        clientsRepository.removeClientById(id);
    }

    public Client getClientById(Integer id) {
        return clientsRepository.getClientById(id);
    }

    private static void validateNewClient(Client client)
    {
        Map<String, String> errors = new HashMap<>();
        if(client.getPesel() == null || client.getPesel().length() != 11){
            errors.put("pesel", "Pesel musi mieć 11 znaków");
        }
        if(client.getFirstName() == null || client.getFirstName().isBlank()){
            errors.put("name", "Imię nie może być puste");
        }
        if(client.getLastName() == null ||  client.getLastName().isBlank()){
            errors.put("second name", "Nazwisko nie może być puste");
        }
        if(client.getBalance() == null || client.getBalance().compareTo(BigDecimal.ZERO) < 0){
            errors.put("balance", "Saldo nie może być ujemne");
        }
        if(client.getCurrency() == null){
            errors.put("currency", "Waluta nie może być pusta");
        }
        try {
            int i = 0;
            for (Currency currency : Currency.values()) {
                if (currency.equals(client.getCurrency())) {
                    logger.info("Currency is " + currency + " and " + client.getCurrency());
                    i++;
                }
            }
            if(i == 0) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            errors.put("currency", "Nieprawidłowa waluta");
        }
        if (!errors.isEmpty()) {
            ValidationException error  = new ValidationException(errors);
            logger.error("Failed to create user with error: " + error.getErrors());
            throw error;
        }
    }
}
