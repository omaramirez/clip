package com.example.clip.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CardDataDTO {
    @NotEmpty
    private String cardType;

    @NotEmpty
    @Pattern(regexp="^[0-9]*$", message="Amount field should digits")
    private String cardNumber;

    @NotEmpty
    @Pattern(regexp="^[0-9]*$", message="Amount field should digits")
    private String expMonth;

    @NotEmpty
    @Pattern(regexp="^[0-9]*$", message="Amount field should digits")
    private String expYear;
}
