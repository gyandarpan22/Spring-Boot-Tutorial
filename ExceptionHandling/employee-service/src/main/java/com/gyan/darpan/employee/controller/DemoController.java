package com.gyan.darpan.employee.controller;

import com.gyan.darpan.employee.annotation.GlobalExceptionHandlerAnnotation1;
import com.gyan.darpan.employee.exception.EmployeeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
}
