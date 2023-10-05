package br.com.nassau.nav.infrastructure.services;

import br.com.nassau.nav.domain.entities.CiaAerea;
import br.com.nassau.nav.domain.entities.Voo;
import br.com.nassau.nav.infrastructure.entities.VooInfra;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class VooPorCiaAereaService {

    public List<Voo> getVoo(CiaAerea ciaAerea){
        RestTemplate restTemplate = new RestTemplate();

        try {
            System.out.println("consulta a cia: " + ciaAerea.getNome() + " em: " + ciaAerea.getEndpointListaVoos());

            ResponseEntity<VooInfra[]> listaVoos = restTemplate.getForEntity(ciaAerea.getEndpointListaVoos(), VooInfra[].class);

            if (listaVoos.getBody() != null){
                return Arrays.stream(listaVoos.getBody())
                        .map(vooInfra -> Voo
                                .builder()
                                .ciaAerea(ciaAerea)
                                .numero(vooInfra.getNumero())
                                .origem(vooInfra.getOrigem())
                                .destino(vooInfra.getDestino())
                                .dataHora(vooInfra.getDataHora())
                                .valorCiaAerea(vooInfra.getValor())
                                .build())
                        .collect(Collectors.toList());
            }
        } catch (Exception e){
            System.out.println("erro ao consultar voo na cia " + ciaAerea.getNome());
            System.out.println("erro:" + e.getMessage());
        }

        return Collections.emptyList();
    }
}
