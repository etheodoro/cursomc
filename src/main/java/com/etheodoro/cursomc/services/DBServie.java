package com.etheodoro.cursomc.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.etheodoro.cursomc.domain.Categoria;
import com.etheodoro.cursomc.domain.Cidade;
import com.etheodoro.cursomc.domain.Cliente;
import com.etheodoro.cursomc.domain.Endereco;
import com.etheodoro.cursomc.domain.Estado;
import com.etheodoro.cursomc.domain.ItemPedido;
import com.etheodoro.cursomc.domain.Pagamento;
import com.etheodoro.cursomc.domain.PagamentoComBoleto;
import com.etheodoro.cursomc.domain.PagamentoComCartao;
import com.etheodoro.cursomc.domain.Pedido;
import com.etheodoro.cursomc.domain.Produto;
import com.etheodoro.cursomc.domain.enums.EstadoPagamento;
import com.etheodoro.cursomc.domain.enums.Perfil;
import com.etheodoro.cursomc.domain.enums.TipoCliente;
import com.etheodoro.cursomc.repositories.CategoriaRepository;
import com.etheodoro.cursomc.repositories.CidadeRepository;
import com.etheodoro.cursomc.repositories.ClienteRepository;
import com.etheodoro.cursomc.repositories.EnderecoRepository;
import com.etheodoro.cursomc.repositories.EstadoRepository;
import com.etheodoro.cursomc.repositories.ItemPedidoRepository;
import com.etheodoro.cursomc.repositories.PagamentoRepository;
import com.etheodoro.cursomc.repositories.PedidoRepository;
import com.etheodoro.cursomc.repositories.ProdutoRepository;

@Service
public class DBServie {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public void instantiateTestDatabase() throws ParseException {
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Cama, Mesa e banho");
		Categoria cat4 = new Categoria(null, "Eletrônicos");
		Categoria cat5 = new Categoria(null, "Jardinagem");
		Categoria cat6 = new Categoria(null, "Decoração");
		Categoria cat7 = new Categoria(null, "Perfumaria");

		
		Produto p1 = new Produto(null, "Computador", 2000D);
		Produto p2 = new Produto(null, "Impressora", 800D);
		Produto p3 = new Produto(null, "Mouse", 80D);
		Produto p4 = new Produto(null, "Mesa de escritório", 300D);
		Produto p5 = new Produto(null, "Toalha", 50D);
		Produto p6 = new Produto(null, "Colcha", 200D);
		Produto p7 = new Produto(null, "TV true color", 1200D);
		Produto p8 = new Produto(null, "Roçadeira", 800D);
		Produto p9 = new Produto(null, "Abajour", 100D);
		Produto p10 = new Produto(null, "Pendente", 180D);
		Produto p11 = new Produto(null, "Shampoo", 90D);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2, p4));
		cat3.getProdutos().addAll(Arrays.asList(p5, p6));
		cat4.getProdutos().addAll(Arrays.asList(p1, p2, p3, p7));
		cat5.getProdutos().addAll(Arrays.asList(p8));
		cat6.getProdutos().addAll(Arrays.asList(p9, p10));
		cat7.getProdutos().addAll(Arrays.asList(p11));
		
		p1.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2, cat4));
		p3.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p4.getCategorias().addAll(Arrays.asList(cat2));
		p5.getCategorias().addAll(Arrays.asList(cat3));
		p6.getCategorias().addAll(Arrays.asList(cat3));
		p7.getCategorias().addAll(Arrays.asList(cat4));
		p8.getCategorias().addAll(Arrays.asList(cat5));
		p9.getCategorias().addAll(Arrays.asList(cat6));
		p10.getCategorias().addAll(Arrays.asList(cat6));
		p11.getCategorias().addAll(Arrays.asList(cat7));
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));		
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");

		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		//Cliente cli1 = new Cliente(null, "Maria Silva", "Maria@gmail.com", "01234567890", TipoCliente.PESSOA_FISICA);
		Cliente cli1 = new Cliente(null, "Maria Silva", passwordEncoder.encode("123456"), "evandrix@hotmail.com", "01234567890", TipoCliente.PESSOA_FISICA);
		Cliente cli2 = new Cliente(null, "Ana Costa ", passwordEncoder.encode("123456"), "evandrolt@gmail.com", "91204443033", TipoCliente.PESSOA_FISICA);
		cli2.addPerfil(Perfil.ADMIN);
		
		
		cli1.getTelefones().addAll(Arrays.asList("978787777","945454444"));
		cli2.getTelefones().addAll(Arrays.asList("978780000","945450000"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38220834", cli1, c2);
		Endereco e3 = new Endereco(null, "Rua Augusta", "100", "Apto 303", "Centro", "38220834", cli2, c2);		
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		cli2.getEnderecos().addAll(Arrays.asList(e3));
		
		clienteRepository.saveAll(Arrays.asList(cli1, cli2));
		enderecoRepository.saveAll(Arrays.asList(e1, e2, e3));
	
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);

		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);

		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);

		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
	
		ItemPedido ip1 = new ItemPedido(ped1, p1, 200.0, 1, 2000.0);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 00.0, 2, 80.0);
		ItemPedido ip3 = new ItemPedido(ped1, p2, 100.0, 1, 800.0);
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
	}

}
