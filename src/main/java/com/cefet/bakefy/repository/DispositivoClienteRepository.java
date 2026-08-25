package com.cefet.bakefy.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cefet.bakefy.entity.DispositivoCliente;

@Repository
public interface DispositivoClienteRepository extends JpaRepository<DispositivoCliente, Integer> {

    Optional<DispositivoCliente> findByTokenFcm(String tokenFcm);

    List<DispositivoCliente> findByCliente_IdUsuario(Integer idCliente);
}
