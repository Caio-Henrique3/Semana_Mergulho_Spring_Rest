create table entrega (
	id bigint not null auto_increment,
	taxa decimal(10,2) not null,
	data_pedido datetime not null,
	data_finalizacao datetime,
	status varchar(20) not null,
	cliente_id bigint not null,
	
	destinatario_nome varchar(60) not null,
	destinatario_logradouro varchar(255) not null,
	destinatario_numero varchar(30) not null,
	destinatario_complemento varchar(60) not null,
	destinatario_bairro varchar(30) not null,
	
	primary key (id)
);

alter table entrega add constraint entreg_cliente
foreign key (cliente_id) references cliente (id);