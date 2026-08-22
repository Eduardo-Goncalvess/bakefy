package com.cefet.bakefy.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.cefet.bakefy.dto.ClienteRequestDTO;
import com.cefet.bakefy.dto.ClienteResponseDTO;
import com.cefet.bakefy.entity.Cliente;
import com.cefet.bakefy.entity.TipoUsuario;
import com.cefet.bakefy.repository.ClienteRepository;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> listar() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.stream().map(ClienteResponseDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public ClienteResponseDTO buscarPorId(Integer id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado. Id: " + id));

        return new ClienteResponseDTO(cliente);
    }

    @Transactional
    public ClienteResponseDTO inserir(ClienteRequestDTO dto) {

        if (clienteRepository.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe um cliente cadastrado com esse email.");
        }

        Cliente cliente = new Cliente();
        cliente.setNmUsuario(dto.getNmUsuario());
        cliente.setSenha(dto.getSenha());
        cliente.setEmail(dto.getEmail());
        cliente.setTipoUsuario(TipoUsuario.CLIENTE);

        return new ClienteResponseDTO(clienteRepository.save(cliente));
    }

    @Transactional
    public ClienteResponseDTO atualizar(Integer id, ClienteRequestDTO dto) {

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado. Id: " + id));

        if (clienteRepository.existsByEmailAndIdUsuarioNot(dto.getEmail(), id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe um cliente cadastrado com esse email.");
        }

        cliente.setNmUsuario(dto.getNmUsuario());
        cliente.setSenha(dto.getSenha());
        cliente.setEmail(dto.getEmail());

        return new ClienteResponseDTO(clienteRepository.save(cliente));
    }

    @Transactional
    public void excluir(Integer id) {

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado. Id: " + id));

        clienteRepository.delete(cliente);
    }

}
