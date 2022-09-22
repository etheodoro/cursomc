package com.etheodoro.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etheodoro.cursomc.domain.Pedido;
import com.etheodoro.cursomc.repositories.PedidoRepository;
import com.etheodoro.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;	
	
	public Pedido find(Integer id) {
		repo.findById(id).orElse(null);
		Optional<Pedido> optional = repo.findById(id);
		return optional.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName())
		);
	}
	
}
