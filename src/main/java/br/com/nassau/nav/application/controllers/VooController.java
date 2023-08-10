package br.com.nassau.nav.application.controllers;

import br.com.nassau.nav.domain.entities.Voo;
import br.com.nassau.nav.domain.services.VooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/voo")
public class VooController {

    @Autowired
    private VooService vooService;

    @PostMapping("/listar")
    private ResponseEntity<List<Voo>> listar(){
        return null;
    }
}
