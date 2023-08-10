package br.com.nassau.nav.application.handlers;

import br.com.nassau.nav.domain.exceptions.VooNaoEncontradoException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class Handler {

    @ExceptionHandler({VooNaoEncontradoException.class})
    public void handleNotFound(){

    }


}
