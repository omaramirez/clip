package com.example.clip.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PayloadRequestDTO {

    @Pattern(regexp="^[1-9]\\d*(\\.\\d+)?$", message="Amount field should contain digits")
    private String amount;

    private ClipUserDTO clipUser;

    @Valid
    private CardDataDTO cardData;
}
