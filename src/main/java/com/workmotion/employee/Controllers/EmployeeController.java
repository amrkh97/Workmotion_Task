package com.workmotion.employee.Controllers;

import com.workmotion.employee.Models.Employee;
import com.workmotion.employee.Models.EmployeeEvent;
import com.workmotion.employee.Services.EmployeeService;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("employee")
@RequiredArgsConstructor
@SwaggerDefinition(tags = {@Tag(name = "Employees Controller", description = "Operations related to employees")})
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    @Operation(summary = "Add a new employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful addition of an employee",
                    content = @Content(schema = @Schema(implementation = Employee.class)))
    })
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        Employee addedEmployee = employeeService.addEmployee(employee);
        return new ResponseEntity<>(addedEmployee, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/{state}")
    @Operation(summary = "Change an employee's state")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful status change for an employee",
                    content = @Content(schema = @Schema(implementation = Employee.class))),
            @ApiResponse(responseCode = "406", description = "Illegal status change for an employee")
    })
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of employee",
                    content = @Content(schema = @Schema(implementation = Employee.class))),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        try {
            Employee employee = employeeService.getEmployeeById(id);
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
