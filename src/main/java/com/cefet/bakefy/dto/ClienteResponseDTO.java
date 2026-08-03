package com.cefet.bakefy.dto;

import com.cefet.bakefy.entity.Cliente;

import lombok.Getter;

@Getter
public class ClienteResponseDTO {

    private Integer idUsuario;
    private String nmUsuario;
    private String email;
    private String senha;

    public ClienteResponseDTO(Cliente cliente) {
        this.idUsuario = cliente.getIdUsuario();
        this.nmUsuario = cliente.getNmUsuario();
        this.email = cliente.getEmail();
        this.senha = cliente.getSenha();
    }
}