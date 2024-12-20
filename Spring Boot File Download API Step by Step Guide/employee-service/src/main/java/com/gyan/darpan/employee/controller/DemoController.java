package com.gyan.darpan.employee.controller;

import com.gyan.darpan.employee.annotation.GlobalExceptionHandlerAnnotation1;
import com.gyan.darpan.employee.exception.EmployeeNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@GlobalExceptionHandlerAnnotation1
public class DemoController {

    @GetMapping(path = "hello", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> sayHello() {
        return new ResponseEntity<>("Hello", HttpStatus.OK);
    }

    @GetMapping(path = "demo/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> demo(@PathVariable int id) {
        if (id > 100 || id < 0) {
            throw new EmployeeNotFoundException(id);
        }

        return new ResponseEntity<>("Hello", HttpStatus.OK);
    }

    @GetMapping(path = "demo/headers", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> headersDemo(@RequestHeader(name = "header1") String header) {
        System.out.println("header1 : " + header);

        String response = "header1:" + header;

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "demo/headers1", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> headersDemo1(@RequestHeader HttpHeaders header) {
        System.out.println("header1 : " + header.getFirst("header1"));

        for (Map.Entry<String, List<String>> entry : header.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

        String response = "header1:" + header.getFirst("header1");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
