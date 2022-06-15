package com.etheodoro.cursomc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.etheodoro.cursomc.domain.Categoria;

@Repository
public interface CategoriaRepositories extends JpaRepository<Categoria, Integer> {

	
	
}
