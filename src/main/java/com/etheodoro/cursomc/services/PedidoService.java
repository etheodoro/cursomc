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
import com.etheodoro.cursomc.repositories.PedidoRepository;
import com.etheodoro.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;	
	
	@Autowired
	private BoletoService boletoService;

	@Autowired
	private PagamentoService pagamentoService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ItemPedidoService itemPedidoService;
	
	@Autowired
	private ClienteService clienteService;
	
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
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto boleto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(boleto, obj.getInstante());
		}
		
		obj = repo.save(obj);
		pagamentoService.insert(obj.getPagamento());
		for ( ItemPedido i : obj.getItens() ) {
			i.setDesconto(0.0);
			i.setPedido(obj);
			i.setProduto(produtoService.find(i.getProduto().getId()));
			i.setPreco(i.getProduto().getPreco());
		}
		itemPedidoService.insertAll(obj);
		
		System.out.println(obj);		
		
		return obj;
	}
	
}
