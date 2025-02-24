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
public class Status1 {
    private int code;
    private String message;
    private Date timeStamp;

}
