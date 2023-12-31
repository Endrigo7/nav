package br.com.nassau.nav.application.controllers;

import br.com.nassau.nav.domain.entities.BuscarVoo;
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

    @PostMapping("/listar-todos")
    private ResponseEntity<List<Voo>> listar(@RequestBody BuscarVoo buscarVoo){
        List<Voo> voos = vooService.listarTodosOsVoos(buscarVoo);
        return ResponseEntity.ok().body(voos);
    }
}
