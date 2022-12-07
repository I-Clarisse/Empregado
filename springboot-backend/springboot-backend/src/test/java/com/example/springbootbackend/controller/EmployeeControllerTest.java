package com.example.springbootbackend.controller;

import com.example.springbootbackend.model.Employee;
import com.example.springbootbackend.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@RunWith(SpringRunner.class)
//@WebMvcTest(EmployeeController.class)
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @MockBean
    private EmployeeService employeeServiceMock;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAllSuccess() throws Exception {
        List<Employee> asList = Arrays.asList(new Employee ("Clar", "Lee", "leeclar@gmail.com" ),
                new Employee("Aderline", "Shughs", "aderlyshugs@gmail.com"));

        when(employeeServiceMock.getAll()).thenReturn(asList);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/api/employee/")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"firstName\":\"Clarisse\",\"lastName\":\"IRADUKUNDA\",\"email\":\"ciradukundaa@gmail.com\"}]"))
                .andReturn();
    }

    @Test
    public void givenEmployeeObject_returnSavedEmployee() throws Exception {

        // given - setup
        Employee employee = new Employee("Clarisse", "IRADUKUNDA", "ciradukunda@gmail.com");

        given(employeeServiceMock.addNewEmployee(any(Employee.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        // when - behavior we are going to test
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/api/employee/")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee));

        MvcResult result = (MvcResult) mockMvc
                .perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().json("[{\"firstName\":\"Clarisse\",\"lastName\":\"IRADUKUNDA\",\"email\":\"ciradukundaa@gmail.com\"}]"))
                .andReturn();
    }

    @Test
    public void givenListOfEmployees_returnAllEmployees() throws Exception{

        List <Employee> listOfEmployees = Arrays.asList(new Employee( "Clarisse", "IRADUKUNDA", "ciradukunda@gmail.com"),
                new Employee("Larissa", "GIKUNDIRO", "girissa@gmail.com"));

        when(employeeServiceMock.getAll()).thenReturn(listOfEmployees);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/api/employee  /")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result =mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"firstName\" :\"Klarees\", \"lastName\":\"IRA\", \"email\":\"klareesira@gmail.com\"},{\"firstName\" :\"Colombe\", \"lastName\":\"IGIHOZO\", \"email\":\"igihozocolombe@gmail.com\"}]"))
                .andReturn();
    }

    @Test
    public void givenEmployeeId_returnEmployeeObject() throws Exception{

        //given
        Long employeeId = 1L;
        Employee employee = new Employee("Clarisse", "IRADUKUNDA", "ciradukunda@gmail.com");

        given(employeeServiceMock.getEmployee(employeeId)).willReturn(Optional.of(employee));

        // action
        ResultActions response = mockMvc.perform(get("/api/employee/{id}", employeeId));

        // verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().json("[{\"firstName\":\"Clarisse\",\"lastName\":\"IRADUKUNDA\",\"email\":\"ciradukundaa@gmail.com\"}]"))
                .andReturn();
    }

    @Test
    public void givenInvalidEmployeeId_returnEmpty() throws Exception{

        Long employeeId = 1L;

        // given
        Employee employee = new Employee("Clarisse", "IRADUKUNDA", "ciradukundaa@gmail.com");

        given(employeeServiceMock.getEmployee(employeeId)).willReturn(Optional.empty());

        // action
        ResultActions response = mockMvc.perform(get("/api/employee/{id}",employeeId));

        // verify output
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void givenUpdatedEmployee_returnUpdatedEmployee() throws Exception{

        //given
        Long employeeId = 1L;

        Employee savedEmployee = new Employee("Clarisse", "IRADUKUNDA", "ciradukundaa@gmail.com");

        Employee updatedEmployee = new Employee("Klarees", "IRA", "klareesira@gmail.com");

        given(employeeServiceMock.getEmployee(employeeId)).willReturn(Optional.of(savedEmployee));
        given(employeeServiceMock.updateEmployee(employeeId,savedEmployee.getFirstName(), savedEmployee.getLastName(), savedEmployee.getEmail()))
                .willAnswer((invocation) -> invocation.getArgument(0));

        // when

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put("/api/employee/{id}", employeeId)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee));

        MvcResult result = (MvcResult) mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.firstName", is(updatedEmployee.getFirstName())))
                .andExpect((ResultMatcher) jsonPath("$.lastName", is(updatedEmployee.getLastName())))
                .andExpect((ResultMatcher) jsonPath("$.email", is(updatedEmployee.getEmail())));
    }
}
