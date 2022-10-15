package com.etheodoro.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.etheodoro.cursomc.domain.Cidade;
import com.etheodoro.cursomc.domain.Cliente;
import com.etheodoro.cursomc.domain.Endereco;
import com.etheodoro.cursomc.domain.enums.TipoCliente;
import com.etheodoro.cursomc.dto.ClienteDTO;
import com.etheodoro.cursomc.dto.ClienteNewDTO;
import com.etheodoro.cursomc.repositories.CidadeRepository;
import com.etheodoro.cursomc.repositories.ClienteRepository;
import com.etheodoro.cursomc.repositories.EnderecoRepository;
import com.etheodoro.cursomc.services.exceptions.DataIntegrityException;
import com.etheodoro.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	public Cliente find(Integer id) {
		repo.findById(id).orElse(null);
		Optional<Cliente> optional = repo.findById(id);
		return optional.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName())
		);
	}
	
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}	
	
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateDate(newObj, obj);
		return repo.save(newObj);
	}
	
	public void delete(Integer id) {
		Cliente obj = find(id);
		try {
			repo.deleteById(obj.getId());
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir porque a entidades relacionadas.");
		}
	}
	
	public List<Cliente> findAll() {
		Optional<List<Cliente>> optional = Optional.of(repo.findAll());
		return optional.orElseThrow(() -> new ObjectNotFoundException(
				"Nenhuma Cliente encontradas" + Cliente.class.getName())
		);
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Cliente fromDto(ClienteDTO objDto) {
		//TODO no lugar de cidade from dto  Cidade cid = new Cidade(...);
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
	}

	public Cliente fromDto(ClienteNewDTO objDto) {
		//TODO no lugar de cidade from dto  Cidade cid = new Cidade(...);
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()));
		Optional<Cidade> cid = cidadeRepository.findById(objDto.getCidadeId());
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid.get());
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		if (objDto.getTelefone2() != null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if (objDto.getTelefone3() != null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		return cli;
	}
	
	private void updateDate(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
}
