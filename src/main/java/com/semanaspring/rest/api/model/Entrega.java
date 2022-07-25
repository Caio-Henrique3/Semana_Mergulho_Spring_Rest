package com.semanaspring.rest.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.semanaspring.rest.api.exception.NegocioExeception;
import com.semanaspring.rest.api.util.ValidationGroups;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Entrega {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private BigDecimal taxa;

	@JsonProperty(access = Access.READ_ONLY)
	private OffsetDateTime dataPedido;
	
	@JsonProperty(access = Access.READ_ONLY)
	private OffsetDateTime dataFinalizacao;

	@JsonProperty(access = Access.READ_ONLY)
	@Enumerated(EnumType.STRING) //Declaração para salvar no banco a String do 
	//enum referente, e não o seu número;
	private StatusEntrega status;
	
	@Valid
	@NotNull
	@Embedded //Serve para manter os dados da classe em questão (Destinatario), 
	//na tabela de onde esta classe for declarada (Cliente);
	private Destinatario destinatario;
	
	@Valid
	@ConvertGroup(from = Default.class, to = ValidationGroups.ClienteId.class)
	//@valid faz a validação para ver se as propriedades passadas do cliente na entrega estão
	//no objeto entrega. Porém, precisamos validar apenas se o id foi passado. Colocamos a anotação
	//@NotNull para garantir que o id não possa ser nulo quando passarmos um cliente, só que apenas
	//assim daria certo criar uma entrega, mas quebraria a inclusão de um novo cliente, porque ao criarmos
	//um novo cliente o id sempre vai ser nulo. Para isso então colocamos a anotação @ConvertGroup.
	//Contando com as alterações feitas na classe de cliente, ela transfere a validação do id para onde
	//colocarmos, sendo neste caso na interface ValidationGroups. Isso garante apenas que ele valide o id
	//e não o objeto cliente como um todo ao solicitar uma entrega.
	@NotNull
	@ManyToOne
	private Cliente cliente;
	
	@OneToMany(mappedBy = "entrega", cascade = CascadeType.ALL)
	private List<Ocorrencia> ocorrencias;

	public Ocorrencia adicionarOcorrencia(String descricao) {
		Ocorrencia ocorrencia = new Ocorrencia();
		ocorrencia.setDescricao(descricao);
		ocorrencia.setDataRegistro(OffsetDateTime.now());
		ocorrencia.setEntrega(this);
		
		this.getOcorrencias().add(ocorrencia);
		return ocorrencia;
	}

	public void finalizar() {
		if (!StatusEntrega.PENDENTE.equals(getStatus())) {
			throw new NegocioExeception("Entrega não pode ser finalizada!");
		}
		
		setStatus(StatusEntrega.FINALIZADA);
		setDataFinalizacao(OffsetDateTime.now());
	}
	
}
