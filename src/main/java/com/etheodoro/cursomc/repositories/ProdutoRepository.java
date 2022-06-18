package com.etheodoro.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etheodoro.cursomc.domain.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

}
