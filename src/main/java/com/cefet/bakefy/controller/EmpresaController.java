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

import com.cefet.bakefy.dto.EmpresaRequestDTO;
import com.cefet.bakefy.dto.EmpresaResponseDTO;
import com.cefet.bakefy.service.EmpresaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/empresas")
public class EmpresaController {

    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @GetMapping
    public ResponseEntity<List<EmpresaResponseDTO>> listar() {
        return ResponseEntity.ok(empresaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpresaResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(empresaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<EmpresaResponseDTO> inserir(@Valid @RequestBody EmpresaRequestDTO dto) {
        EmpresaResponseDTO empresa = empresaService.inserir(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(empresa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpresaResponseDTO> atualizar(@PathVariable Integer id, @Valid @RequestBody EmpresaRequestDTO dto) {
        return ResponseEntity.ok(empresaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        empresaService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    /* Login foi centralizado em UsuarioController -> POST /api/v1/usuarios/login */
}
