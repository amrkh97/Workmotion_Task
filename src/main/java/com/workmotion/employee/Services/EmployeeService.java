package com.workmotion.employee.Services;

import com.workmotion.employee.Models.Employee;
import com.workmotion.employee.Models.EmployeeEvent;

public interface EmployeeService {

    Employee changeState(Employee employee, EmployeeEvent event);

    Employee addEmployee(Employee employee);

    Employee getEmployeeById(Long id);
}
