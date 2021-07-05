package com.example.clip.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CardDataDTO {
    @NotEmpty
    private String cardType;

    @NotEmpty
    private String cardNumber;

    @NotEmpty
    private String expMonth;

    @NotEmpty
    private String expYear;
}
