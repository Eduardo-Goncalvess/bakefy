package com.cefet.bakefy.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cefet.bakefy.dto.CategoriaResponseDTO;
import com.cefet.bakefy.repository.CategoriaRepository;

@RestController
@RequestMapping("/api/v1/categorias")
@CrossOrigin(origins = "*")
public class CategoriaController {

    private final CategoriaRepository categoriaRepository;

    public CategoriaController(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> listar() {
        List<CategoriaResponseDTO> categorias = categoriaRepository.findAll()
                .stream()
                .map(CategoriaResponseDTO::new)
                .toList();

        return ResponseEntity.ok(categorias);
    }
}
