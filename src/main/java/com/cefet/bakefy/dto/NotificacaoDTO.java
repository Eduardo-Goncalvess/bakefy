package com.cefet.bakefy.dto;

import lombok.Getter;

@Getter
public class NotificacaoDTO {

    private final Integer idProduto;
    private final String nmProduto;
    private final boolean disponivel;
    private final String mensagem;

    public NotificacaoDTO(Integer idProduto, String nmProduto, boolean disponivel, String mensagem) {
        this.idProduto = idProduto;
        this.nmProduto = nmProduto;
        this.disponivel = disponivel;
        this.mensagem = mensagem;
    }
}
