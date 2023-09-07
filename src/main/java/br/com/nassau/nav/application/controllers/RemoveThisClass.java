package br.com.nassau.nav.application.controllers;

import br.com.nassau.nav.infrastructure.entities.VooInfra;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestController
public class RemoveThisClass {

    @GetMapping("/azul/listar-todos")
    public ResponseEntity<List<VooInfra>> listarTodos(){

        VooInfra voo1 = VooInfra.builder()
                                    .numero("AZ-1234")
                                    .origem("REC")
                                    .destino("SPO")
                                    .dataHora(LocalDateTime.now())
                                    .valor(new BigDecimal(100))
                                .build();

        VooInfra voo2 = VooInfra.builder()
                .numero("AZ-2398")
                .origem("REC")
                .destino("SPO")
                .dataHora(LocalDateTime.now())
                .valor(new BigDecimal(150))
                .build();

        List<VooInfra> voos = Arrays.asList(voo1, voo2);

        return ResponseEntity.ok().body(voos);
    }
}
