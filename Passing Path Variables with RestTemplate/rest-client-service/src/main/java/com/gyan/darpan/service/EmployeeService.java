package com.gyan.darpan.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gyan.darpan.client.RestClient;
import com.gyan.darpan.model.Employee;
import com.gyan.darpan.model.EmployeeListResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmployeeService {
    private final RestClient restClient;
    private final String baseUrl;
    private final ObjectMapper objectMapper;

    public EmployeeService(
            RestClient restClient,
            @Value("${employee.service.base-url}") String baseUrl
    ) {
        this.restClient = restClient;
        this.baseUrl = baseUrl;
        this.objectMapper = new ObjectMapper();
    }

    public void getEmployees() throws JsonProcessingException {
        String resourceUrl = "employee";

        Map<String, Object> queryParam = new HashMap<>();
        // queryParam.put("employeeName", "Sohan");
        // queryParam.put("department", Department.DEVELOPER);
        queryParam.put("pageNo", 2);

        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        ResponseEntity<EmployeeListResponse> responseEntity =
                //  restClient.get(baseUrl, resourceUrl, EmployeeListResponse.class, queryParam);
                restClient.execute(baseUrl, resourceUrl, EmployeeListResponse.class, queryParam, headers, HttpMethod.GET);

        System.out.println("getEmployees::Response status Code : " + responseEntity.getStatusCode().toString());

        EmployeeListResponse employeeListResponse = responseEntity.getBody();

        System.out.println("getEmployees::Response Body " + objectMapper.writeValueAsString(employeeListResponse));
    }

    public void getEmployee() throws JsonProcessingException {
        String resourceUrl = "employee/{employeeId}";

        Map<String, Object> pathVariables = new HashMap<>();
        pathVariables.put("employeeId", 2);

        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        ResponseEntity<Employee> responseEntity =
                restClient.execute(baseUrl, resourceUrl, Employee.class,
                        null, headers, HttpMethod.GET, pathVariables);

        System.out.println("getEmployees::Response status Code : " + responseEntity.getStatusCode());

        Employee employee = responseEntity.getBody();

        System.out.println("getEmployees::Response Body " + objectMapper.writeValueAsString(employee));
    }

    public void deleteEmployee() {
        String resourceUrl = "employee/{employeeId}";

        Map<String, Object> pathVariables = new HashMap<>();
        pathVariables.put("employeeId", 3);

        HttpHeaders headers = new HttpHeaders();

        ResponseEntity<String> responseEntity =
                restClient.execute(baseUrl, resourceUrl, String.class,
                        null, headers, HttpMethod.DELETE, pathVariables);

        System.out.println("getEmployees::Response status Code : " + responseEntity.getStatusCode());

        String body = responseEntity.getBody();

        System.out.println("getEmployees::Response Body " + body);
    }

}
