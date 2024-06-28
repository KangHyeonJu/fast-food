package com.boot.fastfood.service;

import com.boot.fastfood.entity.Clients;
import com.boot.fastfood.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    public Clients saveClient(Clients clients){
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        clients.setClCode("CL"+ date);
        clients.setClAmount(0L);
        return clientRepository.save(clients);
    }

    public List<Clients> getAllClients(){
        return clientRepository.findAll();
    }

    public void updateClient(Clients client) {
        Clients existingClient = clientRepository.findById(client.getClCode()).orElse(null);
        if (existingClient != null) {
            existingClient.setClName(client.getClName());
            existingClient.setClType(client.getClType());
            existingClient.setClPhone(client.getClPhone());
            clientRepository.save(existingClient);
        }
    }




}
