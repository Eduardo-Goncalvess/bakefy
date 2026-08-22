package com.cefet.bakefy.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cefet.bakefy.dto.LoginRequestDTO;
import com.cefet.bakefy.dto.LoginResponseDTO;
import com.cefet.bakefy.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/produtos")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        String token = usuarioService.autenticar(dto);
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

}
