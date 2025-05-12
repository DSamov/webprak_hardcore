package ru.msu.cmc.webprak.controllers;

import jakarta.validation.Valid;
import ru.msu.cmc.webprak.DAO.InstanceDAO;
import ru.msu.cmc.webprak.models.*;
import ru.msu.cmc.webprak.DAO.ClientDAO;
import ru.msu.cmc.webprak.DAO.EmployeeDAO;
import ru.msu.cmc.webprak.DAO.ServiceDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@Controller
public class InstanceController {
    @Autowired
    private InstanceDAO instanceDAO;

    @Autowired
    private ClientDAO clientDAO;

    @Autowired
    private EmployeeDAO employeeDAO;

    @Autowired
    private ServiceDAO serviceDAO;

    @GetMapping("/instances")
    public String listInstances(Model model,
                                @RequestParam(value = "clientId", required = false) Optional<Integer> searchClientId,
                                @RequestParam(value = "employeeId", required = false) Optional<Integer> searchEmployeeId,
                                @RequestParam(value = "serviceId", required = false) Optional<Integer> searchServiceId,
                                @RequestParam(value = "startDate", required = false) Optional<LocalDate> searchStartDate,
                                @RequestParam(value = "finishDate", required = false) Optional<LocalDate> searchFinishDate) {

        List<Instance> instances;

        if (searchClientId.isPresent()) {
            instances = instanceDAO.getByClientId(searchClientId.get());
        } else if (searchEmployeeId.isPresent()) {
            instances = instanceDAO.getByEmployeeId(searchEmployeeId.get());
        } else if (searchServiceId.isPresent()) {
            instances = instanceDAO.getByService(searchServiceId.get());
        } else if (searchStartDate.isPresent() && searchFinishDate.isPresent()) {
            instances = instanceDAO.getByStartDateRange(searchStartDate.get(), searchFinishDate.get());
        } else if (searchStartDate.isPresent()) {
            instances = instanceDAO.getByStartDate(searchStartDate.get());
        } else if (searchFinishDate.isPresent()) {
            instances = instanceDAO.getByFinishDate(searchFinishDate.get());
        } else {
            instances = instanceDAO.getAll();
        }

        model.addAttribute("instances", instances);
        model.addAttribute("searchClientId", searchClientId);
        model.addAttribute("searchEmployeeId", searchEmployeeId);
        model.addAttribute("searchServiceId", searchServiceId);
        model.addAttribute("searchStartDate", searchStartDate);
        model.addAttribute("searchFinishDate", searchFinishDate);

        return "instances";
    }

    @GetMapping("/instances/{clientId}/{employeeId}/{serviceId}")
    public String showInstance(@PathVariable Integer clientId,
                               @PathVariable Integer employeeId,
                               @PathVariable Integer serviceId,
                               Model model) {
        Instance instance = instanceDAO.getById(new InstanceID(clientId, employeeId, serviceId));
        if (instance == null) {
            return "error";
        }
        model.addAttribute("instance", instance);
        return "instanceDetails";
    }

    @GetMapping("/instances/add")
    public String addInstanceForm(Model model) {
        model.addAttribute("newInstance", new Instance());
        model.addAttribute("clients", clientDAO.getAll());
        model.addAttribute("employees", employeeDAO.getAll());
        model.addAttribute("services", serviceDAO.getAll());
        return "addInstance";
    }

    @GetMapping("/instances/{clientId}/{employeeId}/{serviceId}/delete")
    public String deleteInstance(Model model,
                                 @PathVariable Integer clientId,
                                 @PathVariable Integer employeeId,
                                 @PathVariable Integer serviceId) {
        instanceDAO.deleteById(new InstanceID(clientId, employeeId, serviceId));
        return "redirect:/instances";
    }

    @PostMapping("/instances/add")
    public String addInstance(
            @Valid @ModelAttribute("newInstance") Instance newInstance,
            BindingResult result,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            String errorMessage = result.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.joining("<br>"));
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/instances/add";
        }

        try {
            instanceDAO.save(newInstance);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при добавлении: " + e.getMessage());
            return "redirect:/instances/add";
        }

        return "redirect:/instances";
    }

    @PostMapping("/instances/{clientId}/{employeeId}/{serviceId}/edit")
    public String updateInstance(@PathVariable Integer clientId,
                                 @PathVariable Integer employeeId,
                                 @PathVariable Integer serviceId,
                                 @Valid @ModelAttribute("instance") Instance instance,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes) {
        Instance existingInstance = instanceDAO.getById(new InstanceID(clientId, employeeId, serviceId));
        if (existingInstance == null) {
            return "error";
        }

        if (result.hasErrors()) {
            String errorMessage = result.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.joining("<br>"));
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/instances/{clientId}/{employeeId}/{serviceId}";
        }

        existingInstance.setClients(instance.getClients());
        existingInstance.setEmployee(instance.getEmployee());
        existingInstance.setServices(instance.getServices());
        existingInstance.setStart(instance.getStart());
        existingInstance.setFinish(instance.getFinish());

        try {
            instanceDAO.update(existingInstance);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при обновлении: " + e.getMessage());
        }

        return "redirect:/instances";
    }
}