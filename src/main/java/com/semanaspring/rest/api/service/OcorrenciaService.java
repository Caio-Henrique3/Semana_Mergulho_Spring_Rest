package com.semanaspring.rest.api.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.semanaspring.rest.api.model.Entrega;
import com.semanaspring.rest.api.model.Ocorrencia;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class OcorrenciaService {
	
	private EntregaService entregaService;
	
	@Transactional
	public Ocorrencia registrar(Long entregaId, String descricao) {
		Entrega entrega = entregaService.buscarPorId(entregaId);
	
		return entrega.adicionarOcorrencia(descricao);
	}
	
}
