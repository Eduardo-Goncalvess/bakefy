package com.cefet.bakefy.dto;

import com.cefet.bakefy.entity.Fornecedor;

import lombok.Getter;

@Getter
public class FornecedorResponseDTO {

    private Integer idFornecedor;
    private String nmFornecedor;
    private String email;
    private String telefone;
    private Integer idEmpresa;

    public FornecedorResponseDTO(Fornecedor fornecedor) {
        this.idFornecedor = fornecedor.getIdFornecedor();
        this.nmFornecedor = fornecedor.getNmFornecedor();
        this.email = fornecedor.getEmail();
        this.telefone = fornecedor.getTelefone();
        this.idEmpresa = fornecedor.getEmpresa().getIdUsuario();
    }
}
