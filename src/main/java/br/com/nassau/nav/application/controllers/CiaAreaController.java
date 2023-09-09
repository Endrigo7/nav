package br.com.nassau.nav.application.controllers;

import br.com.nassau.nav.domain.entities.CiaArea;
import br.com.nassau.nav.domain.exceptions.CiaAreaNaoEncontradaException;
import br.com.nassau.nav.domain.services.CiaAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cia-area")
public class CiaAreaController {

    @Autowired
    private CiaAreaService ciaAreaService;

    @Deprecated
    @GetMapping("/{id}")
    public ResponseEntity<CiaArea> buscarPorId(@PathVariable String id){
        CiaArea ciaArea = ciaAreaService.buscarPorId(id);
        return ResponseEntity.ok().body(ciaArea);
    }

    @GetMapping("/buscar-nome/{nome}")
    public ResponseEntity<List<CiaArea>> buscarPorNome(@PathVariable String nome){
        List<CiaArea> ciasArea = ciaAreaService.buscarPorNome(nome);

        if(ciasArea.isEmpty()){
            throw new CiaAreaNaoEncontradaException();
        }

        return ResponseEntity.ok().body(ciasArea);
    }

    @GetMapping("/listar-todos")
    public ResponseEntity<List<CiaArea>> listarTodos(){
        List<CiaArea> ciasArea = ciaAreaService.listarTodos();
        return ResponseEntity.ok().body(ciasArea);
    }

    @PostMapping("/")
    public ResponseEntity<Void> salvar(@RequestBody CiaArea ciaArea) throws URISyntaxException {
        UUID id = ciaAreaService.salvar(ciaArea);
        URI uri = ServletUriComponentsBuilder //
                .fromCurrentContextPath() //
                .path("/" + id) //
                .buildAndExpand() //
                .toUri();

        return ResponseEntity.created(uri).build();
    }
}
