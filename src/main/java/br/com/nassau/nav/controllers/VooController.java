package br.com.nassau.nav.controllers;

import br.com.nassau.nav.entities.Voo;
import br.com.nassau.nav.services.VooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/voo")
public class VooController {

    @Autowired
    private VooService vooService;

    @GetMapping("/{id}")
    private ResponseEntity<Voo> buscarPorId(@PathVariable String id){
        Voo voo = vooService.buscarPorId(id);
        return ResponseEntity.ok(voo);
    }
}
