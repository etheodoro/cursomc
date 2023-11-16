package com.etheodoro.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etheodoro.cursomc.domain.Pagamento;
import com.etheodoro.cursomc.repositories.PagamentoRepository;

@Service
public class PagamentoService {
	
	@Autowired
	private PagamentoRepository repository;

	public void insert(Pagamento obj) {
		repository.save(obj);
	}
	
}
