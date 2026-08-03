package com.cefet.bakefy.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.cefet.bakefy.dto.FornecedorRequestDTO;
import com.cefet.bakefy.dto.FornecedorResponseDTO;
import com.cefet.bakefy.entity.Empresa;
import com.cefet.bakefy.entity.Fornecedor;
import com.cefet.bakefy.repository.EmpresaRepository;
import com.cefet.bakefy.repository.FornecedorRepository;

@Service
public class FornecedorService {

    private final FornecedorRepository fornecedorRepository;
    private final EmpresaRepository empresaRepository;

    public FornecedorService(FornecedorRepository fornecedorRepository, EmpresaRepository empresaRepository) {
        this.fornecedorRepository = fornecedorRepository;
        this.empresaRepository = empresaRepository;
    }

    @Transactional(readOnly = true)
    public List<FornecedorResponseDTO> listar() {
        List<Fornecedor> fornecedores = fornecedorRepository.findAll();
        return fornecedores.stream().map(FornecedorResponseDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public List<FornecedorResponseDTO> listarPorEmpresa(Integer idEmpresa) {
        List<Fornecedor> fornecedores = fornecedorRepository.findByEmpresaIdUsuario(idEmpresa);
        return fornecedores.stream().map(FornecedorResponseDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public FornecedorResponseDTO buscarPorId(Integer id) {
        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Fornecedor não encontrado. Id: " + id));

        return new FornecedorResponseDTO(fornecedor);
    }

    @Transactional
    public FornecedorResponseDTO inserir(FornecedorRequestDTO dto) {

        Empresa empresa = empresaRepository.findById(dto.getIdEmpresa())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Empresa não encontrada. Id: " + dto.getIdEmpresa()));

        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNmFornecedor(dto.getNmFornecedor());
        fornecedor.setEmail(dto.getEmail());
        fornecedor.setTelefone(dto.getTelefone());
        fornecedor.setEmpresa(empresa);

        return new FornecedorResponseDTO(fornecedorRepository.save(fornecedor));
    }

    @Transactional
    public FornecedorResponseDTO atualizar(Integer id, FornecedorRequestDTO dto) {

        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Fornecedor não encontrado. Id: " + id));

        Empresa empresa = empresaRepository.findById(dto.getIdEmpresa())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Empresa não encontrada. Id: " + dto.getIdEmpresa()));

        fornecedor.setNmFornecedor(dto.getNmFornecedor());
        fornecedor.setEmail(dto.getEmail());
        fornecedor.setTelefone(dto.getTelefone());
        fornecedor.setEmpresa(empresa);

        return new FornecedorResponseDTO(fornecedorRepository.save(fornecedor));
    }

    @Transactional
    public void excluir(Integer id) {

        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Fornecedor não encontrado. Id: " + id));

        fornecedorRepository.delete(fornecedor);
    }
}