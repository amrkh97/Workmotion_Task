package com.workmotion.employee.Integration;


import com.workmotion.employee.Models.Employee;
import com.workmotion.employee.Models.EmployeeEvent;
import com.workmotion.employee.Models.EmployeeState;
import com.workmotion.employee.Repositories.EmployeeRepository;
import com.workmotion.employee.Services.EmployeeService;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Order(2)
@Transactional
@SpringBootTest
public class EmployeeServiceIntegrationTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    @Test
    @Order(1)
    public void whenGetEmployeeById_thenReturnEmployee() {

        Employee employee = new Employee();
        employee = employeeRepository.save(employee);

        Employee found = employeeService.getEmployeeById(employee.getId());

        assertEquals(found.getId(), 1L);
    }

    @Test
    @Order(2)
    public void whenGetEmployeeByIdAndNotFound_thenThrowException() {

        Exception exception = assertThrows(RuntimeException.class, () -> employeeService.getEmployeeById(1L));

        assertNotNull(exception);
    }


    @Test
    @Order(3)
    public void whenAddEmployee_thenReturnEmployee() {

        Employee employee = new Employee();

        Employee result = employeeService.addEmployee(employee);

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(employee);
    }

    @Test
    @Order(4)
    public void whenChangeEmployeeState_thenReturnEmployeeAfterStateChange() {

        Employee employee = new Employee(1L, "John Doe", EmployeeState.ADDED);

        Employee result = employeeService.changeState(employee, EmployeeEvent.BEGIN_CHECK);

        assertEquals(EmployeeState.IN_CHECK, result.getState());
    }

    @Test
    @Order(5)
    public void whenChangeEmployeeState_thenThrowException() {

        Employee employee = new Employee(1L, "John Doe", EmployeeState.ADDED);

        Exception exception = assertThrows(RuntimeException.class, () -> employeeService.changeState(employee,
                EmployeeEvent.ACTIVATE));

        assertNotNull(exception);
    }
}

