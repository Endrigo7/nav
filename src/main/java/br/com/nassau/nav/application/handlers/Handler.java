package br.com.nassau.nav.application.handlers;

import br.com.nassau.nav.domain.exceptions.CiaAreaNaoEncontradaException;
import br.com.nassau.nav.domain.exceptions.VooNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class Handler {

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleAllExceptions(Exception e) {

        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage(), INTERNAL_SERVER_ERROR.value());

        return new ResponseEntity<>(exceptionResponse, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({VooNaoEncontradoException.class, CiaAreaNaoEncontradaException.class})
    public ResponseEntity<ExceptionResponse> handleNotFoundException(Exception e) {

        String mensagem =  (e instanceof VooNaoEncontradoException) ? "Voo não encontrado" : "Cia area não encontrada";

        ExceptionResponse exceptionResponse = new ExceptionResponse(mensagem, NOT_FOUND.value());

        return new ResponseEntity<>(exceptionResponse, NOT_FOUND);
    }
}
