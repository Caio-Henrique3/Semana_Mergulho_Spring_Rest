package com.semanaspring.rest.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.semanaspring.rest.api.DTO.EntregaDTO;
import com.semanaspring.rest.api.DTO.OcorrenciaDTO;
import com.semanaspring.rest.api.input.EntregaInput;
import com.semanaspring.rest.api.input.OcorrenciaInput;
import com.semanaspring.rest.api.mapper.EntregaMapper;
import com.semanaspring.rest.api.mapper.OcorrenciaMapper;
import com.semanaspring.rest.api.model.Entrega;
import com.semanaspring.rest.api.model.Ocorrencia;
import com.semanaspring.rest.api.repository.EntregaRepository;
import com.semanaspring.rest.api.service.EntregaService;
import com.semanaspring.rest.api.service.OcorrenciaService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/entregas")
public class EntregaController {

	private EntregaRepository entregaRepository;
	private EntregaService entregService;
	private OcorrenciaService ocorrenciaService;
	private EntregaMapper entregaMapper;
	private OcorrenciaMapper ocorrenciaMapper;
	
	@GetMapping
	public List<EntregaDTO> listar() {
		return entregaMapper.toCollectionModel(entregaRepository.findAll());
	}
	
	@GetMapping("/{entregaId}")
	public ResponseEntity<EntregaDTO> name(@PathVariable Long entregaId) {
		return entregaRepository.findById(entregaId)
				.map(entrega -> ResponseEntity.ok(entregaMapper.toModelMapper(entrega)))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EntregaDTO solicitaarEntrega(@Valid @RequestBody EntregaInput entrega) {
		Entrega novaEntrega = entregaMapper.toEntity(entrega);
		return entregaMapper.toModelMapper(entregService.solicitarEntrega(novaEntrega));
	}
	
	@PutMapping("/{entregaId}/finalizacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void finalizar(@PathVariable Long entregaId) {
		entregService.finalizarEntraga(entregaId);
	}
	
	@PostMapping("/{entregaId}/ocorrencias")
	@ResponseStatus(HttpStatus.CREATED)
	public OcorrenciaDTO registrar(@PathVariable Long entregaId,
			@Valid @RequestBody OcorrenciaInput ocorrenciaInput) {
		Ocorrencia ocorrenciaRegistrada =  ocorrenciaService.registrar(entregaId, ocorrenciaInput.getDescricao());
		
		return ocorrenciaMapper.toModel(ocorrenciaRegistrada);
	}
	
	@GetMapping("/{entregaId}/ocorrencias")
	@ResponseStatus(HttpStatus.CREATED)
	public List<OcorrenciaDTO> listarOcorrencia(@PathVariable Long entregaId) {
		Entrega entrega = entregService.buscarPorId(entregaId);
		
		return ocorrenciaMapper.toCollectionModel(entrega.getOcorrencias());
	}
	
}
