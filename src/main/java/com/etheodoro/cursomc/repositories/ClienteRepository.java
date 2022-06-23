package com.etheodoro.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etheodoro.cursomc.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

	
	
}
