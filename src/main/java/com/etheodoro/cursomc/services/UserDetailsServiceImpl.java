package com.etheodoro.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.etheodoro.cursomc.domain.Cliente;
import com.etheodoro.cursomc.repositories.ClienteRepository;
import com.etheodoro.cursomc.security.UserSecurity;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private ClienteRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Cliente cliente = repository.findByEmail(email);
		if (cliente == null ) {
			throw new UsernameNotFoundException(email);
		}
		
		UserSecurity userSecurity = new UserSecurity(
				cliente.getId()
				,cliente.getEmail()
				,cliente.getSenha()
				,cliente.getPerfis());
		
		return userSecurity;
	}

}
