package com.workmotion.employee.Unit;

import com.google.gson.Gson;
import com.workmotion.employee.Models.Employee;
import com.workmotion.employee.Models.EmployeeEvent;
import com.workmotion.employee.Models.EmployeeState;
import com.workmotion.employee.Repositories.EmployeeRepository;
import com.workmotion.employee.Services.EmployeeService;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@SpringBootTest
public class EmployeeServiceUnitTests {

    @MockBean
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    @Test
    @Order(1)
    public void whenGetEmployeeById_thenReturnEmployee() {

        Employee employee = new Employee(1L, "John Doe", EmployeeState.ADDED);
        doReturn(Optional.of(employee)).when(employeeRepository).findById(anyLong());

        Employee result = employeeService.getEmployeeById(1L);

        assertEquals(employee, result);
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

        Employee employee = new Employee(1L, "John Doe", EmployeeState.ADDED);
        doReturn(employee).when(employeeRepository).save(any(Employee.class));

        Employee result = employeeService.addEmployee(employee);

        assertEquals(employee, result);
    }

    @Test
    @Order(4)
    public void whenChangeEmployeeState_thenReturnEmployeeAfterStateChange() {

        Employee employee = new Employee(1L, "John Doe", EmployeeState.ADDED);

        Gson gson = new Gson();
        Employee updatedEmployee = gson.fromJson(gson.toJson(employee), Employee.class);

        updatedEmployee.setState(EmployeeState.IN_CHECK);
        doReturn(updatedEmployee).when(employeeRepository).save(any(Employee.class));

        Employee result = employeeService.changeState(employee, EmployeeEvent.BEGIN_CHECK);

        assertEquals(updatedEmployee.getState(), result.getState());
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