package com.cefet.bakefy.dto;

import java.time.LocalDate;

import com.cefet.bakefy.entity.Empresa;

import lombok.Getter;

@Getter
public class EmpresaResponseDTO {

    private Integer idUsuario;
    private String nmUsuario;
    private String email;
    private String senha;
    private LocalDate dtCriacao;
    private String telefone;
    private String cidade;
    private String bairro;
    private String rua;
    private Integer num;

    public EmpresaResponseDTO(Empresa empresa) {
        this.idUsuario = empresa.getIdUsuario();
        this.nmUsuario = empresa.getNmUsuario();
        this.email = empresa.getEmail();
        this.senha = empresa.getSenha();
        this.dtCriacao = empresa.getDtCriacao();
        this.telefone = empresa.getTelefone();
        this.cidade = empresa.getCidade();
        this.bairro = empresa.getBairro();
        this.rua = empresa.getRua();
        this.num = empresa.getNum();
    }
}