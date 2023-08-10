package br.com.nassau.nav.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class Handler {

    @ExceptionHandler({VooNaoEncontradoException.class})
    public void handleNotFound(){

    }


}
