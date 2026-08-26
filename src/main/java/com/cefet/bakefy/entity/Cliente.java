package com.cefet.bakefy.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Data
@Entity(name = "TbCliente")
public class Cliente extends Usuario {

    @ManyToMany
    @JoinTable(
        name = "TbClienteProduto",
        joinColumns = @JoinColumn(name = "idCliente"),
        inverseJoinColumns = @JoinColumn(name = "idProduto"),
        uniqueConstraints = @UniqueConstraint(columnNames = {"idCliente", "idProduto"})
    )
    private Set<Produto> produtos = new HashSet<>();
}
