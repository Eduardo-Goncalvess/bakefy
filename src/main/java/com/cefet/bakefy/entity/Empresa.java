package com.cefet.bakefy.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "TbEmpresa")
public class Empresa extends Usuario {

    @Column(nullable = false, updatable = false)
    private LocalDate dtCriacao;

    @Column(length = 11)
    private String telefone;

    @Column(length = 50)
    private String cidade;

    @Column(length = 50)
    private String bairro;

    @Column(length = 50)
    private String rua;

    private Integer num;

    @PrePersist
    public void prePersist() {
        this.dtCriacao = LocalDate.now();
    }
}
