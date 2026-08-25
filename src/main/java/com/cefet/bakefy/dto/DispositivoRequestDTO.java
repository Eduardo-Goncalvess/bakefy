package com.cefet.bakefy.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DispositivoRequestDTO {

    @NotBlank(message = "O campo tokenFcm é obrigatório")
    private String tokenFcm;
}
