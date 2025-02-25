package com.gyan.darpan.employee.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailConfig {
    private String host;
    private int port;
    private String username;
    private String password;
}
