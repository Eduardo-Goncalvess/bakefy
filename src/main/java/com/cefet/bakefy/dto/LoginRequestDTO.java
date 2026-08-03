package com.cefet.bakefy.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequestDTO {

    @NotBlank(message = "O campo email é obrigatório")
    //@Email(message = "Email inválido")
    private String email;

    @NotBlank(message = "O campo senha é obrigatório")
    private String senha;
}
