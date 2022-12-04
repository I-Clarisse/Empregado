package com.example.springbootbackend.service;

import com.example.springbootbackend.model.Employee;
import com.example.springbootbackend.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    // get employees service
    public List<Employee> getAll() {

        List<Employee> employees = employeeRepository.findAll();

        return employees;
    }

    //get employee service
    public Optional<Employee> getEmployee(Long employeeId){
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        if(employeeOptional.isPresent()){
            return employeeOptional;
        }
        throw new IllegalStateException("User with id "+employeeId+" not found");
    }

    // add new employee service
    public Employee addNewEmployee(Employee employee){
        Optional<Employee> employeeOptional = employeeRepository.findByEmail(employee.getEmail());
                if(employeeOptional.isPresent()){
                    throw new IllegalStateException("Employee with email: "+employee.getEmail() +" already exists");
                }
                return employeeRepository.save(employee);
    }

    //delete employee service
    public void deleteEmployee(Long employeeId){
        boolean exists = employeeRepository.existsById(employeeId);
        if(!exists) {
            throw new IllegalStateException("Employee with id " + employeeId + " does not exist");
        }
        employeeRepository.deleteById(employeeId);
    }

    //update employee service
//    @Transactional
    public Employee updateEmployee(Long employeeId, String firstName, String lastName, String email ){
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalStateException(
                        "Employee with id " + employeeId + "does not exist"
                ));
        if(firstName != null
                && firstName.length() > 0
                && !Objects.equals(employee.getFirstName(), firstName)){
            employee.setFirstName(firstName);
        }

        if(lastName != null
                && lastName.length() > 0
                && !Objects.equals(employee.getLastName(), lastName)){
            employee.setLastName(lastName);
        }

        if(email != null
                && email.length() > 0
                && !Objects.equals(employee.getEmail(), email)){
            Optional<Employee> employeeOptional = employeeRepository.findEmployeeByEmail(email);
            if(employeeOptional.isPresent()){
                throw new IllegalStateException("Email taken");
            }
            employee.setEmail(email);
        }
        return employee;
    }
}
