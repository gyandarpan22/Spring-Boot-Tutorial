package com.gyan.darpan.employee.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Status {
    private int statusCode;
    private String message;
    private Date timeStamp;

}
