package br.com.nassau.nav.application.controllers;

import br.com.nassau.nav.infrastructure.entities.VooInfra;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
public class RemoveThisClass {

    @GetMapping("/azul/listar-todos")
    public ResponseEntity<List<VooInfra>> listarTodos(){

        System.out.println("Executou esse metodo simulando a AZUL");

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

    @GetMapping("/latam/listar-todos")
    public ResponseEntity<List<VooInfra>> listarTodosLatam(){

        System.out.println("Executou esse metodo simulando a LATAM");

        VooInfra voo1 = VooInfra.builder()
                .numero("LT-9548")
                .origem("REC")
                .destino("SPO")
                .dataHora(LocalDateTime.now())
                .valor(new BigDecimal(850))
                .build();


        List<VooInfra> voos = Collections.singletonList(voo1);

        return ResponseEntity.ok().body(voos);
    }

    public static void main(String[] args) {
        System.out.println(UUID.randomUUID());
    }
}
