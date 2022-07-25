package com.semanaspring.rest.api.exceptionhandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.semanaspring.rest.api.exception.EntidadeNaoEncontradaException;
import com.semanaspring.rest.api.exception.NegocioExeception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	//inicializar com o @AutoWires ou então pelo construtor;
	private MessageSource messageSource;

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<Error.Campo> campos = new ArrayList<>();
		Error error = new Error();

		for (ObjectError erro : ex.getBindingResult().getAllErrors()) {
			String nome = ((FieldError) erro).getField();
			String mensagem = messageSource.getMessage(erro, LocaleContextHolder.getLocale());
			//usamos messageSource.getMessage(erro, LocaleContextHolder.getLocale()); 
			//para trabalhar com a tradução para o local onde estará sendo executado o programa

			campos.add(new Error.Campo(nome, mensagem));
		}

		error.setStatus(status.value());
		error.setDataHora(LocalDateTime.now());
		error.setProblema("Um ou mais campos estão inválidos");
		error.setCampos(campos);

		return handleExceptionInternal(ex, error, headers, status, request);
	}

	@ExceptionHandler(NegocioExeception.class)
	public ResponseEntity<Object> handleNegocio(NegocioExeception ex, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Error error = new Error();

		error.setStatus(status.value());
		error.setDataHora(LocalDateTime.now());
		error.setProblema(ex.getMessage());

		return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<Object> handleNegocio(EntidadeNaoEncontradaException ex, WebRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		Error error = new Error();

		error.setStatus(status.value());
		error.setDataHora(LocalDateTime.now());
		error.setProblema(ex.getMessage());

		return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
	}

}
