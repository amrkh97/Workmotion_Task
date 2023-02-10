package com.workmotion.employee.Controller;

import com.workmotion.employee.Models.Employee;
import com.workmotion.employee.Models.EmployeeEvent;
import com.workmotion.employee.Models.EmployeeState;
import com.workmotion.employee.Repositories.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Order(3)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private EmployeeRepository employeeRepository;

    private String baseUrl;

    @BeforeEach
    public void setUp(){
        baseUrl = "http://localhost:"+ randomServerPort + "/employee";
    }

    @Test
    @Order(1)
    public void whenGetEmployeeById_thenReturnEmployee() throws URISyntaxException {
        //Save an initial employee in the test database:
        Employee employee = employeeRepository.save(new Employee());

        String url = String.format("%s/%s", baseUrl,employee.getId());
        URI uri = new URI(url);
        ResponseEntity<Employee> response = restTemplate.getForEntity(uri, Employee.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Employee returnedEmployee = response.getBody();
        assertNotNull(returnedEmployee);
        assertEquals(returnedEmployee.getId(), employee.getId());
    }

    @Test
    @Order(2)
    public void whenGetEmployeeById_thenReturnNotFound() throws URISyntaxException {

        String url = String.format("%s/1", baseUrl);
        URI uri = new URI(url);

        ResponseEntity<Employee> response = restTemplate.getForEntity(uri, Employee.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Order(3)
    public void whenAddEmployee_thenReturnEmployee() throws URISyntaxException {

        String url = String.format("%s", baseUrl);
        URI uri = new URI(url);

        Employee employee = new Employee();

        HttpEntity<Employee> request = new HttpEntity<>(employee);

        ResponseEntity<Employee> response = restTemplate.postForEntity(uri, request, Employee.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Employee returnedEmployee = response.getBody();
        assertNotNull(returnedEmployee);
        assertEquals(returnedEmployee.getId(), 1L);
    }

    @Test
    @Order(4)
    public void whenChangeEmployeeState_thenReturnEmployeeAfterStateChange() throws URISyntaxException {
        //Save an initial employee in the test database:
        Employee employee = employeeRepository.save(new Employee());

        String url = String.format("%s/%s/%s", baseUrl, employee.getId(), EmployeeEvent.BEGIN_CHECK);
        URI uri = new URI(url);

        ResponseEntity<Employee> response = restTemplate.postForEntity(uri, null, Employee.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Employee returnedEmployee = response.getBody();
        assertNotNull(returnedEmployee);
        assertEquals(returnedEmployee.getState(), EmployeeState.IN_CHECK);
    }

    @Test
    @Order(5)
    public void whenChangeEmployeeState_thenThrowException() throws URISyntaxException {
        //Save an initial employee in the test database:
        Employee employee = employeeRepository.save(new Employee());

        String url = String.format("%s/%s/%s", baseUrl, employee.getId(), EmployeeEvent.UNAPPROVE);
        URI uri = new URI(url);

        ResponseEntity<Employee> response = restTemplate.postForEntity(uri, null, Employee.class);
        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
    }

}
