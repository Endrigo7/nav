package br.com.nassau.nav.application.controllers;

import br.com.nassau.nav.domain.entities.CiaAerea;
import br.com.nassau.nav.domain.exceptions.CiaAereaNaoEncontradaException;
import br.com.nassau.nav.domain.services.CiaAereaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cia-aerea")
public class CiaAereaController {

    @Autowired
    private CiaAereaService ciaAereaService;

    @Deprecated
    @GetMapping("/{id}")
    public ResponseEntity<CiaAerea> buscarPorId(@PathVariable String id){
        CiaAerea ciaAerea = ciaAereaService.buscarPorId(id);
        return ResponseEntity.ok().body(ciaAerea);
    }

    @GetMapping("/buscar-nome/{nome}")
    public ResponseEntity<List<CiaAerea>> buscarPorNome(@PathVariable String nome){
        List<CiaAerea> ciasAerea = ciaAereaService.buscarPorNome(nome);

        if(ciasAerea.isEmpty()){
            throw new CiaAereaNaoEncontradaException();
        }

        return ResponseEntity.ok().body(ciasAerea);
    }

    @GetMapping("/listar-todos")
    public ResponseEntity<List<CiaAerea>> listarTodos(){
        List<CiaAerea> ciasAerea = ciaAereaService.listarTodos();
        return ResponseEntity.ok().body(ciasAerea);
    }

    @PostMapping("/")
    public ResponseEntity<Void> salvar(@RequestBody CiaAerea ciaAerea) throws URISyntaxException {
        UUID id = ciaAereaService.salvar(ciaAerea);
        URI uri = ServletUriComponentsBuilder //
                .fromCurrentContextPath() //
                .path("/cia-aerea/" + id) //
                .buildAndExpand() //
                .toUri();

        return ResponseEntity.created(uri).build();
    }
}
