package com.etheodoro.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etheodoro.cursomc.domain.Categoria;
import com.etheodoro.cursomc.repositories.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;	
	
	public Categoria buscar(Integer id) {
		return repo.findById(id).orElse(null);
	}
	
}
