package com.cefet.bakefy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cefet.bakefy.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdUsuarioNot(String email, Integer idUsuario);

    Optional<Usuario> findByEmail(String email);
}
