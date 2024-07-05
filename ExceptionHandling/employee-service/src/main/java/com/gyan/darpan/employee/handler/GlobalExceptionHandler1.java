package com.gyan.darpan.employee.handler;

import com.gyan.darpan.employee.annotation.GlobalExceptionHandlerAnnotation1;
import com.gyan.darpan.employee.controller.DemoController;
import com.gyan.darpan.employee.exception.EmployeeNotFoundException;
import com.gyan.darpan.employee.response.Status1;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice(annotations = {GlobalExceptionHandlerAnnotation1.class})
public class GlobalExceptionHandler1 extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<Status1> handleEmployeeNotFoundException(EmployeeNotFoundException employeeNotFoundException) {

        Status1 status = Status1.builder()
                .code(employeeNotFoundException.getStatusCode())
                .message(employeeNotFoundException.getMessage())
                .timeStamp(new Date())
                .build();

        return new ResponseEntity<>(status, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Status1> handleException(Exception exception) {

        Status1 status = Status1.builder()
                .code(500)
                .message(exception.getMessage())
                .timeStamp(new Date())
                .build();

        return new ResponseEntity<>(status, HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
