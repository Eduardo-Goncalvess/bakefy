package com.cefet.bakefy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cefet.bakefy.entity.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
}
