package com.etheodoro.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

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
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Cama, Mesa e banho");
		Categoria cat4 = new Categoria(null, "Eletrônicos");
		Categoria cat5 = new Categoria(null, "Jardinagem");
		Categoria cat6 = new Categoria(null, "Decoração");
		Categoria cat7 = new Categoria(null, "Perfumaria");

		
		Produto p1 = new Produto(null, "Computador", 2000D);
		Produto p2 = new Produto(null, "Impressoara", 800D);
		Produto p3 = new Produto(null, "Mouse", 80D);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));

		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepository.saveAll(Arrays.asList(cat1,cat2,cat3,cat4,cat5,cat6,cat7));		
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");

		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "Maria@gmail.com", "01234567890", TipoCliente.PESSOA_FISICA);
		
		cli1.getTelefones().addAll(Arrays.asList("978787777","945454444"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38220834", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
	
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
