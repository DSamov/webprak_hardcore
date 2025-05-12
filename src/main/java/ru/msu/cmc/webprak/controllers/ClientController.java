package ru.msu.cmc.webprak.controllers;

import jakarta.validation.Valid;

import ru.msu.cmc.webprak.DAO.ClientDAO;
import ru.msu.cmc.webprak.models.Client;

import ru.msu.cmc.webprak.DAO.InstanceDAO;
import ru.msu.cmc.webprak.models.Instance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@Controller
public class ClientController {
    @Autowired
    ClientDAO clientDAO;

    @GetMapping("/clients")
    public String listClients(Model model,
                              @RequestParam(value = "surname", required = false) Optional<String> searchSurname,
                              @RequestParam(value = "name", required = false) Optional<String> searchName,
                              @RequestParam(value = "patron", required = false) Optional<String> searchPatron) {

        List<Client> clients;
        clients = clientDAO.getByName(searchSurname, searchName, searchPatron);
        if (clients.isEmpty()) {
            clients = clientDAO.getAll();
        }

        model.addAttribute("clients", clients);
        model.addAttribute("searchSurname",searchSurname);
        model.addAttribute("searchName",searchName);
        model.addAttribute("searchPatron",searchPatron);
        return "clients";
    }


    @GetMapping("/clients/{clientId}")
    public String showClient(@PathVariable Integer clientId, Model model) {
        Client client = clientDAO.getById(clientId);
        if (client == null) {
            return "error";
        }
        model.addAttribute("client", client);
        model.addAttribute("history", clientDAO.instanceById(clientId));
        return "clientId";
    }

    @GetMapping("/clients/add")
    public String addClientForm(Model model) {
        model.addAttribute("newClient", new Client());
        return "addClient";
    }

    @GetMapping("clients/{clientId}/delete")
    public String deleteClient(Model model, @PathVariable Integer clientId) {
        clientDAO.deleteById(clientId);
        return "redirect:/clients";
    }

    @PostMapping("/clients/add")
    public String addClient(
            @Valid @ModelAttribute("newClientrs") Client newClient,
            BindingResult result,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            String errorMessage = result.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.joining("<br>"));
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/clients/add";
        }

        try {
            clientDAO.save(newClient);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при добавлении: " + e.getMessage());
            return "redirect:/clients/add";
        }

        return "redirect:/clients";
    }

    @PostMapping("/clients/{clientId}/edit")
    public String updateClient(@PathVariable Integer clientId, @Valid @ModelAttribute("client") Client client,
                               BindingResult result,
                               RedirectAttributes redirectAttributes) {
        Client existingClient = clientDAO.getById(clientId);
        if (existingClient == null) {
            return "error";
        }

        if (result.hasErrors()) {
            String errorMessage = result.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.joining("<br>"));
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/clients/{clientId}";
        }

        existingClient.setSurname(client.getSurname());
        existingClient.setName(client.getName());
        existingClient.setPatron(client.getPatron());


        try {
            clientDAO.update(existingClient);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при обновлении: " + e.getMessage());
        }

        return "redirect:/clients";
    }


}