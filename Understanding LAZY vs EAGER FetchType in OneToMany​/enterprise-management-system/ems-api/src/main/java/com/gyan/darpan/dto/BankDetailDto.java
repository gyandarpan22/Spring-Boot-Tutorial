package com.gyan.darpan.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankDetailDto {
    private String bankName;
    private String accountNumber;
    private String ifscCode;
}
