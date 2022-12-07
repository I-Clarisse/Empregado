package com.example.springbootbackend.controller;

import com.example.springbootbackend.model.Employee;
import com.example.springbootbackend.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping(path = "/api/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    //get all employees
    @GetMapping
    public List<Employee> getAll(){

        return employeeService.getAll();
    }

    //get single employee
    @GetMapping(path = "{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable("id") Long employeeId){
        return employeeService.getEmployee(employeeId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //create employee
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee registerNewEmployee(@RequestBody Employee employee) {

        return employeeService.addNewEmployee(employee);
    }

    //delete employee
    @DeleteMapping(path = "{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") Long employeeId){
        employeeService.deleteEmployee(employeeId);

        return new ResponseEntity<String>("Employee Deleted successfully!", HttpStatus.OK);
    }

    //Update employee
    @PutMapping(path = "{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") Long employeeId,
                               @RequestParam(required = false) String firstName,
                               @RequestParam(required = false) String lastName,
                               @RequestParam(required = false) String email) {
        Employee updatedEmployee = employeeService.updateEmployee(employeeId, firstName, lastName, email);

        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    }
}
