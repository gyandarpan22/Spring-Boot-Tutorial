package com.gyan.darpan.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gyan.darpan.client.RestClient;
import com.gyan.darpan.enums.Department;
import com.gyan.darpan.model.EmployeeListResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

        ResponseEntity<EmployeeListResponse> responseEntity =
                restClient.get(baseUrl, resourceUrl, EmployeeListResponse.class,queryParam);

        System.out.println("getEmployees::Response status Code : " + responseEntity.getStatusCode().toString());

        EmployeeListResponse employeeListResponse = responseEntity.getBody();

        System.out.println("getEmployees::Response Body " + objectMapper.writeValueAsString(employeeListResponse));
    }

}
