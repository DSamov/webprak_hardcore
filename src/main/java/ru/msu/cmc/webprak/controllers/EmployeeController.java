package ru.msu.cmc.webprak.controllers;

import jakarta.validation.Valid;

import ru.msu.cmc.webprak.DAO.EmployeeDAO;
import ru.msu.cmc.webprak.models.Employee;

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
public class EmployeeController {
    @Autowired
    EmployeeDAO employeeDAO;

    @GetMapping("/employees")
    public String listEmployees(Model model,
                                @RequestParam(value = "surname", required = false) Optional<String> searchSurname,
                                @RequestParam(value = "name", required = false) Optional<String> searchName,
                                @RequestParam(value = "patron", required = false) Optional<String> searchPatron,
                                @RequestParam(value = "address", required = false) Optional<String> searchAddress,
                                @RequestParam(value = "education", required = false) Optional<String> searchEducation,
                                @RequestParam(value = "workPost", required = false) Optional<String> searchWorkPost) {

        List<Employee> employees;
        employees = employeeDAO.getByName(searchSurname, searchName, searchPatron);
        if (employees.isEmpty()) {
            employees = employeeDAO.getAll();
        }

        model.addAttribute("employees", employees);
        model.addAttribute("searchSurname", searchSurname);
        model.addAttribute("searchName", searchName);
        model.addAttribute("searchPatron", searchPatron);
        model.addAttribute("searchAddress", searchAddress);
        model.addAttribute("searchEducation", searchEducation);
        model.addAttribute("searchWorkPost", searchWorkPost);
        return "employees";
    }

    @GetMapping("/employees/{employeeId}")
    public String showEmployee(@PathVariable Integer employeeId, Model model) {
        Employee employee = employeeDAO.getById(employeeId);
        if (employee == null) {
            return "error";
        }
        model.addAttribute("employee", employee);
        return "employeeId";
    }

    @GetMapping("/employees/add")
    public String addEmployeeForm(Model model) {
        model.addAttribute("newEmployee", new Employee());
        return "addEmployee";
    }

    @GetMapping("/employees/{employeeId}/delete")
    public String deleteEmployee(Model model, @PathVariable Integer employeeId) {
        employeeDAO.deleteById(employeeId);
        return "redirect:/employees";
    }

    @PostMapping("/employees/add")
    public String addEmployee(
            @Valid @ModelAttribute("newEmployee") Employee newEmployee,
            BindingResult result,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            String errorMessage = result.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.joining("<br>"));
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/employees/add";
        }

        try {
            employeeDAO.save(newEmployee);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при добавлении: " + e.getMessage());
            return "redirect:/employees/add";
        }

        return "redirect:/employees";
    }

    @PostMapping("/employees/{employeeId}/edit")
    public String updateEmployee(@PathVariable Integer employeeId, @Valid @ModelAttribute("employee") Employee employee,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes) {
        Employee existingEmployee = employeeDAO.getById(employeeId);
        if (existingEmployee == null) {
            return "error";
        }

        if (result.hasErrors()) {
            String errorMessage = result.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.joining("<br>"));
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/employees/{employeeId}";
        }

        existingEmployee.setSurname(employee.getSurname());
        existingEmployee.setName(employee.getName());
        existingEmployee.setPatron(employee.getPatron());
        existingEmployee.setAddress(employee.getAddress());
        existingEmployee.setEducation(employee.getEducation());
        existingEmployee.setWork_post(employee.getWork_post());

        try {
            employeeDAO.update(existingEmployee);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при обновлении: " + e.getMessage());
        }

        return "redirect:/employees";
    }
}