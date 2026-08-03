package com.cefet.bakefy.dto;

import java.math.BigDecimal;

import com.cefet.bakefy.entity.Produto;

import lombok.Getter;

@Getter
public class ProdutoResponseDTO {

    private Integer idProduto;
    private String nmProduto;
    private BigDecimal preco;
    private String status;
    private Integer quantBuscas;
    private Integer idEmpresa;
    private String nmEmpresa;
    private Integer idFornecedor;
    private String nmFornecedor;
    private Integer idCategoria;
    private String nmCategoria;

    public ProdutoResponseDTO(Produto produto) {
        this.idProduto = produto.getIdProduto();
        this.nmProduto = produto.getNmProduto();
        this.preco = produto.getPreco();
        this.status = produto.getStatus();
        this.quantBuscas = produto.getQuantBuscas();
        this.idEmpresa = produto.getEmpresa().getIdUsuario();
        this.nmEmpresa = produto.getEmpresa().getNmUsuario();
        this.idFornecedor = produto.getFornecedor().getIdFornecedor();
        this.nmFornecedor = produto.getFornecedor().getNmFornecedor();
        this.idCategoria = produto.getCategoria().getIdCategoria();
        this.nmCategoria = produto.getCategoria().getNmCategoria();
    }
}
