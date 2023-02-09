package com.workmotion.employee.Services;

import com.github.oxo42.stateless4j.StateMachine;
import com.workmotion.employee.Models.Employee;
import com.workmotion.employee.Models.EmployeeEvent;
import com.workmotion.employee.Models.EmployeeState;
import com.workmotion.employee.Repositories.EmployeeRepository;
import com.workmotion.employee.StateMachine.EmployeeStateConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public Employee changeState(Employee employee, EmployeeEvent event) {

        StateMachine<EmployeeState, EmployeeEvent> stateMachine = new StateMachine<>(employee.getState(),
                EmployeeStateConfig.build());
        try {
            stateMachine.fire(event);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        if (!employee.getState().equals(stateMachine.getState())) {
            employee.setState(stateMachine.getState());
            employeeRepository.save(employee);

        }
        return employee;
    }

    public Employee addEmployee(Employee employee) {
        return this.employeeRepository.save(employee);
    }

    public Employee getEmployeeById(Long id) {
        Optional<Employee> employee = this.employeeRepository.findById(id);
        return employee.orElseThrow(() -> new RuntimeException("No Employee found with provided id"));
    }
}
