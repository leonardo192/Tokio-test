package com.example.api.exceptionhandler;

import java.time.LocalDateTime;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.api.exception.EntityNotFoundException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<?> handlerEntityNotFound(EntityNotFoundException ex, WebRequest request){
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		String detail =  ex.getMessage();
		
		ProblemHandler problem = new ProblemHandler();
		problem.setStatus(status.value());
		problem.setType("https://api.com.br/entidade-nao-encontrada"); //fictício
		problem.setTitle("Entidade não encontrada");
		problem.setDetail(detail);
		problem.setDataHora(LocalDateTime.now());
		
		return handleExceptionInternal(ex, problem,new HttpHeaders(), status, request);
		
	}
	

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		if (body == null) {
			ProblemHandler problema = new ProblemHandler();

			problema.setStatus(status.value());
			problema.setDataHora(LocalDateTime.now());
			problema.setMensagem(status.getReasonPhrase());
			body = problema;
		} else if (body instanceof String) {
			ProblemHandler problema = new ProblemHandler();

			problema.setStatus(status.value());
			problema.setDataHora(LocalDateTime.now());
			problema.setMensagem((String) body);
			body = problema;
		}

		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleGeralException (Exception ex, WebRequest request){
		
		
		if(ex instanceof DataIntegrityViolationException) {
			HttpStatus status = HttpStatus.BAD_REQUEST;
			String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";
			
			ProblemHandler problem = new ProblemHandler();
			
			problem.setStatus(status.value());
			problem.setDataHora(LocalDateTime.now());
			problem.setTitle("Dados Inválidos");
			problem.setDetail(detail);
			return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
		}
		
		
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		String detail =  "Ocorreu um erro interno no sistema , tente novamente mais tarde. Caso o problema persista , consulte o fornecedor";
		
		ProblemHandler problem = new ProblemHandler();
		problem.setStatus(status.value());
		problem.setTitle("Erro de sistema");
		problem.setDetail(detail);
		problem.setDataHora(LocalDateTime.now());
		
		return handleExceptionInternal(ex, problem,new HttpHeaders(), status, request);
		
	}


}
