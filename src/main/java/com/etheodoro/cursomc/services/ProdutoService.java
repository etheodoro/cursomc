package com.etheodoro.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.etheodoro.cursomc.domain.Categoria;
import com.etheodoro.cursomc.domain.Produto;
import com.etheodoro.cursomc.repositories.CategoriaRepository;
import com.etheodoro.cursomc.repositories.ProdutoRepository;
import com.etheodoro.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepo;	

	@Autowired
	private CategoriaRepository categoriaRepo;	

	
	public Produto find(Integer id) {
		produtoRepo.findById(id).orElse(null);
		Optional<Produto> optional = produtoRepo.findById(id);
		return optional.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName())
		);
	}

	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = categoriaRepo.findAllById(ids);
		return produtoRepo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
		
	}
	
}
	
