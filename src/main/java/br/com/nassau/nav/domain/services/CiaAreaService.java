package br.com.nassau.nav.domain.services;

import br.com.nassau.nav.domain.entities.CiaArea;
import br.com.nassau.nav.domain.exceptions.CiaAreaNaoEncontradaException;
import br.com.nassau.nav.infrastructure.entities.CiaAreaJpa;
import br.com.nassau.nav.infrastructure.repositories.CiaAreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CiaAreaService {

    @Autowired
    private CiaAreaRepository ciaAreaRepository;

    private CiaArea toCiaArea(CiaAreaJpa ciaAreaJpa){
        return CiaArea.builder()
                .id(UUID.fromString(ciaAreaJpa.getId()))
                .nome(ciaAreaJpa.getNome())
                .endpointListaVoos(ciaAreaJpa.getEndpointListarVoos())
                .build();
    }

    public CiaArea buscarPorId(String id){
        CiaAreaJpa ciaAreaJpa = ciaAreaRepository
                                .findById(id)
                                .orElseThrow(CiaAreaNaoEncontradaException::new);

        return toCiaArea(ciaAreaJpa);
    }

    public List<CiaArea> listarTodos() {
        System.out.println(ciaAreaRepository.findAll());

        return ciaAreaRepository
                .findAll()
                .stream()
                .map(this::toCiaArea)
                .collect(Collectors.toList());
    }
}
