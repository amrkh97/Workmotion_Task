package com.workmotion.employee.Controllers;

import com.workmotion.employee.Models.Employee;
import com.workmotion.employee.Models.EmployeeEvent;
import com.workmotion.employee.Services.EmployeeService;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("employee")
@RequiredArgsConstructor
@SwaggerDefinition(tags = {@Tag(name = "Employees", description = "Operations related to employees")})
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    @Operation(summary = "Add a new employee")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        Employee addedEmployee = employeeService.addEmployee(employee);
        return new ResponseEntity<>(addedEmployee, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/{state}")
    @Operation(summary = "Change an employee's state")
    public ResponseEntity<Employee> changeEmployeeState(@PathVariable Long id, @PathVariable("state") EmployeeEvent event) {
        try {
            Employee updatedEmployee = employeeService.changeState(employeeService.getEmployeeById(id), event);
            return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Gets an employee by their id")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        try {
            Employee employee = employeeService.getEmployeeById(id);
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
