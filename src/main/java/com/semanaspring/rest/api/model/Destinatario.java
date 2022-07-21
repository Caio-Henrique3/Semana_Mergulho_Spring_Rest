package com.semanaspring.rest.api.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable //Anotação necessária para que ela possa fazer o papel de @Embedded na classe onde ela for declarada;
public class Destinatario {

	@NotBlank
	@Column(name = "destinatario_nome")
	private String nome;

	@NotBlank
	@Column(name = "destinatario_logradouro")
	private String logradouro;

	@NotBlank
	@Column(name = "destinatario_numero")
	private String numero;

	@NotBlank
	@Column(name = "destinatario_complemento")
	private String complemento;
	
	@NotBlank
	@Column(name = "destinatario_bairro")
	private String bairro;
	
}
