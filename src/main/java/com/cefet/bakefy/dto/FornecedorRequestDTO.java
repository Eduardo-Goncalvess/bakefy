package com.cefet.bakefy.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FornecedorRequestDTO {

    @NotBlank(message = "O campo nome é obrigatório")
    private String nmFornecedor;

    private String email;

    private String telefone;

    @NotNull(message = "O campo idEmpresa é obrigatório")
    private Integer idEmpresa;
}