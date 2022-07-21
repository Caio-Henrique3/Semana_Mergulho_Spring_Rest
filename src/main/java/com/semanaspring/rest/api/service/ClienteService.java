package com.semanaspring.rest.api.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.semanaspring.rest.api.exception.NegocioExeception;
import com.semanaspring.rest.api.model.Cliente;
import com.semanaspring.rest.api.repository.ClienteRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ClienteService {

	private ClienteRepository clienteRepository;
	
	public Cliente buscarCliente(Long clienteId) {
		return clienteRepository.findById(clienteId)
				.orElseThrow(() -> new NegocioExeception("Cliente não encontrado!"));
	}
	
	@Transactional
	public Cliente salvar(Cliente cliente) {
		boolean emailUsado = clienteRepository.findByEmail(cliente.getEmail())
				.stream()
				.anyMatch(clienteExistente -> !clienteExistente.equals(cliente));
		
		if (emailUsado) {
			throw new NegocioExeception("E-mail já cadastrado em outro cliente!");
		}
		
		return clienteRepository.save(cliente);
	}
	
	@Transactional
	public void excluir(Long clienteId) {
		clienteRepository.deleteById(clienteId);
	}
	
}
