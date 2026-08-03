package com.cefet.bakefy.dto;

import com.cefet.bakefy.entity.Categoria;

import lombok.Getter;

@Getter
public class CategoriaResponseDTO {

    private Integer idCategoria;
    private String nmCategoria;

    public CategoriaResponseDTO(Categoria categoria) {
        this.idCategoria = categoria.getIdCategoria();
        this.nmCategoria = categoria.getNmCategoria();
    }
}
