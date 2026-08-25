package com.cefet.bakefy.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ClienteRequestDTO {

    @NotBlank(message = "O campo nome é obrigatório")
    private String nmUsuario;

    @NotBlank(message = "O campo senha é obrigatório")
    private String senha;

    @NotBlank(message = "O campo email é obrigatório")
    private String email;
}
