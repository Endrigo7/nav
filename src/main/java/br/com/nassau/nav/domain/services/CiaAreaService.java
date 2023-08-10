package br.com.nassau.nav.domain.services;

import br.com.nassau.nav.domain.entities.CiaArea;
import br.com.nassau.nav.infrastructure.repositories.CiaAreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CiaAreaService {

    @Autowired
    private CiaAreaRepository ciaAreaRepository;

    public List<CiaArea> listarTodos() {
        return ciaAreaRepository
                .findAll()
                .stream()
                .map(ciaAreaJpa -> CiaArea.builder()
                                    .id(ciaAreaJpa.getId())
                                    .nome(ciaAreaJpa.getNome())
                                    .endpointListaVoos(ciaAreaJpa.getEndpointListarVoos())
                                    .build())
                .collect(Collectors.toList());
    }
}
