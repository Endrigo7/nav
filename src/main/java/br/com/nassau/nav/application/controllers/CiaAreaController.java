package br.com.nassau.nav.application.controllers;

import br.com.nassau.nav.domain.entities.CiaArea;
import br.com.nassau.nav.domain.services.CiaAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
        return ResponseEntity.ok().body(ciasArea);
    }

    @GetMapping("/listar-todos")
    public ResponseEntity<List<CiaArea>> listarTodos(){
        List<CiaArea> ciasArea = ciaAreaService.listarTodos();
        return ResponseEntity.ok().body(ciasArea);
    }
}
