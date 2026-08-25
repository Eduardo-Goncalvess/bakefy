package com.cefet.bakefy.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(name = "TbDispositivoCliente")
public class DispositivoCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDispositivo;

    @Column(length = 255, nullable = false, unique = true)
    private String tokenFcm;

    @ManyToOne
    @JoinColumn(name = "idCliente", nullable = false)
    private Cliente cliente;
}
