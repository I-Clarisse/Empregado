package com.example.springbootbackend.service;

import com.example.springbootbackend.model.Employee;
import com.example.springbootbackend.repository.EmployeeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import org.mockito.stubbing.OngoingStubbing;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepositoryMock;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee = new Employee("Klarees", "IRA", "klareesira@gmail.com");
    @Test
    public void givenEmployeeObject_returnEmployeeObject() throws Exception {
        when(employeeRepositoryMock.findByEmail(employee.getEmail())).thenReturn(Optional.empty());
        Employee savedEmployee = employeeService.addNewEmployee(employee);

        when(employeeRepositoryMock.save(employee)).thenReturn(employee);
        System.out.println(employeeRepositoryMock);
        System.out.println(employeeService);



        System.out.println(savedEmployee);

        assertThat(savedEmployee).isNotNull();
    }
}
