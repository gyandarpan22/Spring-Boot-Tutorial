package com.gyan.darpan.employee.handler;

import com.gyan.darpan.employee.annotation.GlobalExceptionHandlerAnnotation;
import com.gyan.darpan.employee.exception.EmployeeNotFoundException;
import com.gyan.darpan.employee.response.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice(annotations = {GlobalExceptionHandlerAnnotation.class})
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<Status> handleEmployeeNotFoundException(EmployeeNotFoundException employeeNotFoundException) {

        Status status = Status.builder()
                .statusCode(employeeNotFoundException.getStatusCode())
                .message(employeeNotFoundException.getMessage())
                .timeStamp(new Date())
                .build();

        return new ResponseEntity<>(status, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Status> handleException(Exception exception) {

        Status status = Status.builder()
                .statusCode(500)
                .message(exception.getMessage())
                .timeStamp(new Date())
                .build();

        return new ResponseEntity<>(status, HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
