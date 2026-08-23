package com.cefet.bakefy.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.cefet.bakefy.dto.ClienteRequestDTO;
import com.cefet.bakefy.dto.ClienteResponseDTO;
import com.cefet.bakefy.dto.ProdutoResponseDTO;
import com.cefet.bakefy.entity.Cliente;
import com.cefet.bakefy.entity.Produto;
import com.cefet.bakefy.entity.TipoUsuario;
import com.cefet.bakefy.repository.ClienteRepository;
import com.cefet.bakefy.repository.ProdutoRepository;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    public ClienteService(ClienteRepository clienteRepository, ProdutoRepository produtoRepository) {
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
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

    @Transactional
    public void favoritar(Integer idCliente, Integer idProduto) {

        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado. Id: " + idCliente));

        Produto produto = produtoRepository.findById(idProduto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado. Id: " + idProduto));

        if (cliente.getProdutos() == null) {
            cliente.setProdutos(new ArrayList<>());
        }

        boolean jaFavoritado = cliente.getProdutos().stream()
                .anyMatch(p -> p.getIdProduto().equals(idProduto));

        if (!jaFavoritado) {
            cliente.getProdutos().add(produto);
            clienteRepository.save(cliente);
        }
    }

    @Transactional
    public void desfavoritar(Integer idCliente, Integer idProduto) {

        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado. Id: " + idCliente));

        if (cliente.getProdutos() != null) {
            cliente.getProdutos().removeIf(p -> p.getIdProduto().equals(idProduto));
            clienteRepository.save(cliente);
        }
    }

    @Transactional(readOnly = true)
    public List<ProdutoResponseDTO> listarFavoritos(Integer idCliente) {

        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado. Id: " + idCliente));

        List<Produto> produtos = cliente.getProdutos();

        if (produtos == null) {
            return List.of();
        }

        return produtos.stream().map(ProdutoResponseDTO::new).toList();
    }

}
