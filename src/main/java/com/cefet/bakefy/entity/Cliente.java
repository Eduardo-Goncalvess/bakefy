package com.cefet.bakefy.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Data
@Entity(name = "TbCliente")
public class Cliente extends Usuario {

    @ManyToMany
    @JoinTable(
        name = "TbClienteProduto",
        joinColumns = @JoinColumn(name = "idCliente"),
        inverseJoinColumns = @JoinColumn(name = "idProduto")
    )
    private List<Produto> produtos;
}
