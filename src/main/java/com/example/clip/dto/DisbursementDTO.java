package com.example.clip.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DisbursementDTO {

    private BigDecimal amount;
    private String clipUserName;
    private String date;
}
