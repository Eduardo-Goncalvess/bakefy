package com.cefet.bakefy.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import com.cefet.bakefy.dto.EmpresaRequestDTO;
import com.cefet.bakefy.dto.EmpresaResponseDTO;
import com.cefet.bakefy.dto.LoginRequestDTO;
import com.cefet.bakefy.entity.Empresa;
import com.cefet.bakefy.entity.TipoUsuario;
import com.cefet.bakefy.repository.EmpresaRepository;

@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    @Transactional(readOnly = true)
    public List<EmpresaResponseDTO> listar() {
        List<Empresa> empresas = empresaRepository.findAll();
        return empresas.stream().map(EmpresaResponseDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public EmpresaResponseDTO buscarPorId(Integer id) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não encontrada. Id: " + id));

        return new EmpresaResponseDTO(empresa);
    }

    @Transactional
    public EmpresaResponseDTO inserir(EmpresaRequestDTO dto) {

        if (empresaRepository.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe uma empresa cadastrada com esse email.");
        }

        Empresa empresa = new Empresa();
        empresa.setNmUsuario(dto.getNmUsuario());
        empresa.setSenha(dto.getSenha());
        empresa.setEmail(dto.getEmail());
        empresa.setTelefone(dto.getTelefone());
        empresa.setCidade(dto.getCidade());
        empresa.setBairro(dto.getBairro());
        empresa.setRua(dto.getRua());
        empresa.setNum(dto.getNum());
        empresa.setTipoUsuario(TipoUsuario.EMPRESA);

        return new EmpresaResponseDTO(empresaRepository.save(empresa));
    }

    @Transactional
    public EmpresaResponseDTO atualizar(Integer id, EmpresaRequestDTO dto) {

        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não encontrada. Id: " + id));

        if (empresaRepository.existsByEmailAndIdUsuarioNot(dto.getEmail(), id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe uma empresa cadastrada com esse email.");
        }

        empresa.setNmUsuario(dto.getNmUsuario());
        empresa.setSenha(dto.getSenha());
        empresa.setEmail(dto.getEmail());
        empresa.setTelefone(dto.getTelefone());
        empresa.setCidade(dto.getCidade());
        empresa.setBairro(dto.getBairro());
        empresa.setRua(dto.getRua());
        empresa.setNum(dto.getNum());

        return new EmpresaResponseDTO(empresaRepository.save(empresa));
    }

    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(Integer id) {

        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não encontrada. Id: " + id));

        empresaRepository.delete(empresa);
    }

    @Transactional(readOnly = true)
    public EmpresaResponseDTO login(LoginRequestDTO dto) {

        Empresa empresa = empresaRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email ou senha inválidos."));

        if (!empresa.getSenha().equals(dto.getSenha())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email ou senha inválidos.");
        }

        return new EmpresaResponseDTO(empresa);
    }
}
