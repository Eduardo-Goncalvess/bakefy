package com.cefet.bakefy.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProdutoRequestDTO {

    @NotBlank(message = "O campo nome é obrigatório")
    private String nmProduto;

    @NotNull(message = "O campo preco é obrigatório")
    private BigDecimal preco;

    @NotNull(message = "O campo idEmpresa é obrigatório")
    private Integer idEmpresa;

    @NotNull(message = "O campo idFornecedor é obrigatório")
    private Integer idFornecedor;

    @NotNull(message = "O campo idCategoria é obrigatório")
    private Integer idCategoria;
}
