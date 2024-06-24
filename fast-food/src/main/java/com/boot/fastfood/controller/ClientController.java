package com.boot.fastfood.controller;

import com.boot.fastfood.dto.ClientDto;
import com.boot.fastfood.entity.Clients;
import com.boot.fastfood.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @GetMapping("/client")
    public String clientPage(Model model){
        List<Clients> clients = clientService.getAllClients();
        model.addAttribute("clients", clients);
        return "system/client";
    }

    @PostMapping("/client/new")
    public String newClient(ClientDto clientDto, Model model, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "system/client";
        }
        try {
            Clients clients = Clients.createClient(clientDto);
            clientService.saveClient(clients);
        }catch (IllegalStateException e){
            model.addAttribute("errorMessage",e.getMessage());
            return "system/client";
        }

        return "redirect:/client";
    }

    @PostMapping("/client/update")
    public String updateClient(@RequestParam("clCode") String clCode,
                               @RequestParam("clName") String clName,
                               @RequestParam("clType") String clType,
                               @RequestParam("clPhone") String clPhone) {
        Clients client = new Clients();
        client.setClCode(clCode);
        client.setClName(clName);
        client.setClType(clType);
        client.setClPhone(clPhone);

        clientService.updateClient(client);
        return "redirect:/client";
    }

}
