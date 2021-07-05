package com.example.clip.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PayloadRequestDTO {

    @NotEmpty
    private BigDecimal amount;

    @NotEmpty
    private ClipUserDTO clipUser;

    @NotEmpty
    private CardDataDTO cardData;
}
