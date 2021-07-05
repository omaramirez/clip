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
public class PayloadResponseDTO {

    private BigDecimal amount;
    private ClipUserDTO clipUser;
    private String date;
    private CardDataDTO cardData;
}
