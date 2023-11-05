package com.etheodoro.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.etheodoro.cursomc.domain.ItemPedido;
import com.etheodoro.cursomc.domain.PagamentoComBoleto;
import com.etheodoro.cursomc.domain.Pedido;
import com.etheodoro.cursomc.domain.enums.EstadoPagamento;
import com.etheodoro.cursomc.repositories.ItemPedidoRepository;
import com.etheodoro.cursomc.repositories.PagamentoRepository;
import com.etheodoro.cursomc.repositories.PedidoRepository;
import com.etheodoro.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;	
	
	@Autowired
	private BoletoService boletoService;

	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public Pedido find(Integer id) {
		repo.findById(id).orElse(null);
		Optional<Pedido> optional = repo.findById(id);
		return optional.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName())
		);
	}
	
	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto boleto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(boleto, obj.getInstante());
		}
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for ( ItemPedido i : obj.getItens() ) {
			i.setDesconto(0.0);
			i.setPreco(produtoService.find(i.getProduto().getId()).getPreco());
			i.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
		return obj;
	}
	
}
