package com.etheodoro.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.etheodoro.cursomc.domain.ItemPedido;
import com.etheodoro.cursomc.domain.ItemPedidoPK;
import com.etheodoro.cursomc.domain.Pedido;
import com.etheodoro.cursomc.repositories.ItemPedidoRepository;
import com.etheodoro.cursomc.services.exceptions.DataIntegrityException;
import com.etheodoro.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ItemPedidoService {

	
	@Autowired
	private ItemPedidoRepository repository;
	
	public ItemPedido find(ItemPedidoPK id) {
		repository.findById(id).orElse(null);
		Optional<ItemPedido> optional = repository.findById(id);
		return optional.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + ItemPedido.class.getName())
		);
	}
	
	@Transactional
	public ItemPedido insert(ItemPedido obj) {
		obj.setId(obj.getId());
		obj = repository.save(obj);
		return obj;
	}	
	
	public ItemPedido update(ItemPedido obj) {
		ItemPedido newObj = find(obj.getId());
		return repository.save(newObj);
	}
	
	public void delete(ItemPedidoPK id) {
		ItemPedido obj = find(id);
		try {
			repository.deleteById(obj.getId());
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir porque existe pedido relacionado.");
		}
	}
	
	public void insertAll(Pedido obj) {
		repository.saveAll(obj.getItens());
	}
	
	public List<ItemPedido> findAll() {
		Optional<List<ItemPedido>> optional = Optional.of(repository.findAll());
		return optional.orElseThrow(() -> new ObjectNotFoundException(
				"Nenhuma Cliente encontradas" + ItemPedido.class.getName())
		);
	}
	
}
