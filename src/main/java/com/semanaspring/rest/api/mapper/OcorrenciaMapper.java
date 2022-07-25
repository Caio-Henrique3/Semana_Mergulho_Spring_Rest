package com.semanaspring.rest.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.semanaspring.rest.api.DTO.OcorrenciaDTO;
import com.semanaspring.rest.api.model.Ocorrencia;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class OcorrenciaMapper {

	private ModelMapper modelMapper;
	
	public OcorrenciaDTO toModel(Ocorrencia ocorrencia) {
		return modelMapper.map(ocorrencia, OcorrenciaDTO.class);
	}
	
	public List<OcorrenciaDTO> toCollectionModel(List<Ocorrencia> ocorrencias) {
		return ocorrencias.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}
	
}
