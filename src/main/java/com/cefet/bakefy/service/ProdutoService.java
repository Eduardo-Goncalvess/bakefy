package com.cefet.bakefy.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.cefet.bakefy.dto.ProdutoRequestDTO;
import com.cefet.bakefy.dto.ProdutoResponseDTO;
import com.cefet.bakefy.entity.Categoria;
import com.cefet.bakefy.entity.Empresa;
import com.cefet.bakefy.entity.Fornecedor;
import com.cefet.bakefy.entity.Produto;
import com.cefet.bakefy.repository.CategoriaRepository;
import com.cefet.bakefy.repository.EmpresaRepository;
import com.cefet.bakefy.repository.FornecedorRepository;
import com.cefet.bakefy.repository.ProdutoRepository;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final EmpresaRepository empresaRepository;
    private final FornecedorRepository fornecedorRepository;
    private final CategoriaRepository categoriaRepository;

    public ProdutoService(ProdutoRepository produtoRepository, EmpresaRepository empresaRepository,
            FornecedorRepository fornecedorRepository, CategoriaRepository categoriaRepository) {
        this.produtoRepository = produtoRepository;
        this.empresaRepository = empresaRepository;
        this.fornecedorRepository = fornecedorRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional(readOnly = true)
    public List<ProdutoResponseDTO> listar() {
        List<Produto> produtos = produtoRepository.findAll();
        return produtos.stream().map(ProdutoResponseDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public ProdutoResponseDTO buscarPorId(Integer id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado. Id: " + id));

        return new ProdutoResponseDTO(produto);
    }

    @Transactional
    public ProdutoResponseDTO inserir(ProdutoRequestDTO dto) {

        Empresa empresa = empresaRepository.findById(dto.getIdEmpresa())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não encontrada. Id: " + dto.getIdEmpresa()));

        Fornecedor fornecedor = fornecedorRepository.findById(dto.getIdFornecedor())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fornecedor não encontrado. Id: " + dto.getIdFornecedor()));

        Categoria categoria = categoriaRepository.findById(dto.getIdCategoria())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada. Id: " + dto.getIdCategoria()));

        Produto produto = new Produto();
        produto.setNmProduto(dto.getNmProduto());
        produto.setPreco(dto.getPreco());
        produto.setStatus("false");
        produto.setQuantBuscas(0);
        produto.setEmpresa(empresa);
        produto.setFornecedor(fornecedor);
        produto.setCategoria(categoria);

        return new ProdutoResponseDTO(produtoRepository.save(produto));
    }

    @Transactional
    public ProdutoResponseDTO atualizar(Integer id, ProdutoRequestDTO dto) {

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado. Id: " + id));

        Empresa empresa = empresaRepository.findById(dto.getIdEmpresa())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não encontrada. Id: " + dto.getIdEmpresa()));

        Fornecedor fornecedor = fornecedorRepository.findById(dto.getIdFornecedor())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fornecedor não encontrado. Id: " + dto.getIdFornecedor()));

        Categoria categoria = categoriaRepository.findById(dto.getIdCategoria())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada. Id: " + dto.getIdCategoria()));

        produto.setNmProduto(dto.getNmProduto());
        produto.setPreco(dto.getPreco());
        produto.setEmpresa(empresa);
        produto.setFornecedor(fornecedor);
        produto.setCategoria(categoria);

        return new ProdutoResponseDTO(produtoRepository.save(produto));
    }

    @Transactional
    public ProdutoResponseDTO alternarStatus(Integer id) {

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado. Id: " + id));

        boolean disponivelAtualmente = "true".equals(produto.getStatus());
        produto.setStatus(disponivelAtualmente ? "false" : "true");

        return new ProdutoResponseDTO(produtoRepository.save(produto));
    }

    @Transactional
    public void excluir(Integer id) {

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado. Id: " + id));

        produtoRepository.delete(produto);
    }
}
