package com.semanaspring.rest.api.service;

import java.time.OffsetDateTime;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.semanaspring.rest.api.exception.EntidadeNaoEncontradaException;
import com.semanaspring.rest.api.model.Cliente;
import com.semanaspring.rest.api.model.Entrega;
import com.semanaspring.rest.api.model.StatusEntrega;
import com.semanaspring.rest.api.repository.EntregaRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EntregaService {

	private EntregaRepository entregaRepository;
	private ClienteService clienteService;

	public Entrega buscarPorId(Long entregaId) {
		return entregaRepository.findById(entregaId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Entrega não encontrada!"));
	}
	
	public Entrega solicitarEntrega(Entrega entrega) {
		Cliente cliente = clienteService.buscarCliente(entrega.getCliente().getId());
		
		entrega.setCliente(cliente);
		entrega.setStatus(StatusEntrega.PENDENTE);
		entrega.setDataPedido(OffsetDateTime.now());
		
		return entregaRepository.save(entrega);
	}
	
	@Transactional
	public void finalizarEntraga(Long entregaId) {
		Entrega entrega = buscarPorId(entregaId);
		entrega.finalizar();
		entregaRepository.save(entrega);
	}
}
