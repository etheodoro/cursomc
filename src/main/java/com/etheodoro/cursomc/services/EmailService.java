package com.etheodoro.cursomc.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.etheodoro.cursomc.domain.Pedido;

@Service
public interface EmailService {

	void sendOrderConfirmationEmail(Pedido pedido);
	
	void sendEmail(SimpleMailMessage message);
	
}
