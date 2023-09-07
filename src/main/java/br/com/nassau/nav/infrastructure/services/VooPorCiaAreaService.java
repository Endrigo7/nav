package br.com.nassau.nav.infrastructure.services;

import br.com.nassau.nav.domain.entities.CiaArea;
import br.com.nassau.nav.domain.entities.Voo;
import br.com.nassau.nav.infrastructure.entities.VooInfra;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class VooPorCiaAreaService {

    public List<Voo> getVoo(CiaArea ciaArea){
        RestTemplate restTemplate = new RestTemplate();

        try {
            System.out.println("consulta a cia: " + ciaArea.getNome() + " em: " + ciaArea.getEndpointListaVoos());

            ResponseEntity<VooInfra[]> listaVoos = restTemplate.getForEntity(ciaArea.getEndpointListaVoos(), VooInfra[].class);

            if (listaVoos.getBody() != null){
                return Arrays.stream(listaVoos.getBody())
                        .map(vooInfra -> Voo
                                .builder()
                                .ciaAerea(ciaArea)
                                .numero(vooInfra.getNumero())
                                .origem(vooInfra.getOrigem())
                                .destino(vooInfra.getDestino())
                                .dataHora(vooInfra.getDataHora())
                                .valorCiaArea(vooInfra.getValor())
                                .build())
                        .collect(Collectors.toList());
            }
        } catch (Exception e){
            System.out.println("erro ao consultar voo na cia " + ciaArea.getNome());
            System.out.println("erro:" + e.getMessage());
        }

        return Collections.emptyList();
    }
}
