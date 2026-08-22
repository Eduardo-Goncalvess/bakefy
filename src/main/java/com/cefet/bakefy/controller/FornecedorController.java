package com.cefet.bakefy.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cefet.bakefy.dto.FornecedorRequestDTO;
import com.cefet.bakefy.dto.FornecedorResponseDTO;
import com.cefet.bakefy.service.FornecedorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/fornecedores")
public class FornecedorController {

    private final FornecedorService fornecedorService;

    public FornecedorController(FornecedorService fornecedorService) {
        this.fornecedorService = fornecedorService;
    }

    @GetMapping
    public ResponseEntity<List<FornecedorResponseDTO>> listar() {
        return ResponseEntity.ok(fornecedorService.listar());
    }

    @GetMapping("/empresa/{idEmpresa}")
    public ResponseEntity<List<FornecedorResponseDTO>> listarPorEmpresa(@PathVariable Integer idEmpresa) {
        return ResponseEntity.ok(fornecedorService.listarPorEmpresa(idEmpresa));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FornecedorResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(fornecedorService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<FornecedorResponseDTO> inserir(@Valid @RequestBody FornecedorRequestDTO dto) {
        FornecedorResponseDTO fornecedor = fornecedorService.inserir(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(fornecedor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FornecedorResponseDTO> atualizar(@PathVariable Integer id, @Valid @RequestBody FornecedorRequestDTO dto) {
        return ResponseEntity.ok(fornecedorService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        fornecedorService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}