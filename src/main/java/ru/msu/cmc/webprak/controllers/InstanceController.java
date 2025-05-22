package ru.msu.cmc.webprak.controllers;

import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
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

import java.sql.Date;
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
                                @RequestParam(value = "clientSurname", required = false) Optional<String> searchClientSurname,
                                @RequestParam(value = "clientName", required = false) Optional<String> searchClientName,
                                @RequestParam(value = "clientPatron", required = false) Optional<String> searchClientPatron,
                                @RequestParam(value = "employeeSurname", required = false) Optional<String> searchEmployeeSurname,
                                @RequestParam(value = "employeeName", required = false) Optional<String> searchEmployeeName,
                                @RequestParam(value = "employeePatron", required = false) Optional<String> searchEmployeePatron,
                                @RequestParam(value = "serviceName", required = false) Optional<String> searchService) {

        List<Instance> instances;
        List<Client> clients = List.of();
        List<Employee> employees = List.of();
        List<Service> services = List.of();

        // Fix: Use correct parameter names for employee search
        if (searchClientSurname.isPresent() || searchClientName.isPresent() || searchClientPatron.isPresent()) {
            clients = clientDAO.getByName(searchClientSurname, searchClientName, searchClientPatron);
        }
        if (searchEmployeeSurname.isPresent() || searchEmployeeName.isPresent() || searchEmployeePatron.isPresent()) {
            employees = employeeDAO.getByName(searchEmployeeSurname, searchEmployeeName, searchEmployeePatron);
        }
        if (searchService.isPresent()) {
            services = serviceDAO.getByName(searchService);
        }

        // Rest of the method remains the same...
        if (clients.isEmpty() && employees.isEmpty() && services.isEmpty()) {
            instances = instanceDAO.getAll();
        } else {
            List<Integer> clientIds = clients.stream().map(Client::getId).collect(Collectors.toList());
            List<Integer> employeeIds = employees.stream().map(Employee::getId).collect(Collectors.toList());
            List<Integer> serviceIds = services.stream().map(Service::getId).collect(Collectors.toList());

            instances = instanceDAO.getByObjectId(
                    employeeIds.isEmpty() ? Optional.empty() : Optional.of(employeeIds),
                    clientIds.isEmpty() ? Optional.empty() : Optional.of(clientIds),
                    serviceIds.isEmpty() ? Optional.empty() : Optional.of(serviceIds)
            );
        }

        model.addAttribute("instances", instances);
        model.addAttribute("searchClientSurname", searchClientSurname.orElse(""));
        model.addAttribute("searchClientName", searchClientName.orElse(""));
        model.addAttribute("searchClientPatron", searchClientPatron.orElse(""));
        model.addAttribute("searchEmployeeSurname", searchEmployeeSurname.orElse(""));
        model.addAttribute("searchEmployeeName", searchEmployeeName.orElse(""));
        model.addAttribute("searchEmployeePatron", searchEmployeePatron.orElse(""));
        model.addAttribute("searchService", searchService.orElse(""));

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
        return "instanceId";
    }

    @GetMapping("/instances/{clientId}/{employeeId}/{serviceId}/delete")
    public String deleteInstance(Model model,
                                 @PathVariable Integer clientId,
                                 @PathVariable Integer employeeId,
                                 @PathVariable Integer serviceId) {
        instanceDAO.deleteById(new InstanceID(clientId, employeeId, serviceId));
        return "redirect:/instances";
    }

    @GetMapping("/instances/add")
    public String addInstanceForm(Model model) {
        model.addAttribute("newInstance", new Instance());
        model.addAttribute("clients", clientDAO.getAll());
        model.addAttribute("employees", employeeDAO.getAll());
        model.addAttribute("services", serviceDAO.getAll());
        return "addInstance";
    }

    @PostMapping("/instances/add")
    public String addInstance(
            @RequestParam("clientId") Integer clientId,
            @RequestParam("employeeId") Integer employeeId,
            @RequestParam("serviceId") Integer serviceId,
            @RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(value = "finish", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<LocalDate> finishDate,
            RedirectAttributes redirectAttributes) {

        try {
            // 1. Get the actual entities first
            Client client = clientDAO.getById(clientId);
            Employee employee = employeeDAO.getById(employeeId);
            Service service = serviceDAO.getById(serviceId);

            if (client == null || employee == null || service == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Один из объектов (клиент, сотрудник или услуга) не найден");
                return "redirect:/instances/add";
            }

            // 2. Create ID and new instance
            InstanceID instanceId = new InstanceID(clientId, employeeId, serviceId);
            Instance newInstance = new Instance();
            newInstance.setId(instanceId);
            newInstance.setStart(Date.valueOf(startDate));

            if (finishDate.isPresent()) {
                newInstance.setFinish(Date.valueOf(finishDate.get()));
            }

            // 3. Set the actual entities
            newInstance.setClients(client);
            newInstance.setEmployee(employee);
            newInstance.setServices(service);

            // 4. Save
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