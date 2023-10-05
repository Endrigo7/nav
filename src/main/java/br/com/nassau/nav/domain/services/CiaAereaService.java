package br.com.nassau.nav.domain.services;

import br.com.nassau.nav.domain.entities.CiaAerea;
import br.com.nassau.nav.domain.exceptions.CiaAereaNaoEncontradaException;
import br.com.nassau.nav.infrastructure.entities.CiaAereaJpa;
import br.com.nassau.nav.infrastructure.repositories.CiaAereaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CiaAereaService {

    @Autowired
    private CiaAereaRepository ciaAereaRepository;

    private CiaAerea toCiaAerea(CiaAereaJpa ciaAereaJpa){
        return CiaAerea.builder()
                .id(UUID.fromString(ciaAereaJpa.getId()))
                .nome(ciaAereaJpa.getNome())
                .endpointListaVoos(ciaAereaJpa.getEndpointListarVoos())
                .build();
    }

    public CiaAerea buscarPorId(String id){
        CiaAereaJpa ciaAereaJpa = ciaAereaRepository
                                .findById(id)
                                .orElseThrow(CiaAereaNaoEncontradaException::new);

        return toCiaAerea(ciaAereaJpa);
    }

    public List<CiaAerea> buscarPorNome(String nome){
        return ciaAereaRepository
                .findByNome(nome.toUpperCase())
                .stream()
                .map(this::toCiaAerea)
                .collect(Collectors.toList());
    }

    public List<CiaAerea> listarTodos() {
        return ciaAereaRepository
                .findAll()
                .stream()
                .map(this::toCiaAerea)
                .collect(Collectors.toList());
    }

    public UUID salvar(CiaAerea ciaAerea){
        ciaAerea.setId(UUID.randomUUID());

        ciaAereaRepository.save( CiaAereaJpa.builder()
                                    .id(ciaAerea.getId().toString())
                                    .nome(ciaAerea.getNome())
                                    .endpointListarVoos(ciaAerea.getEndpointListaVoos())
                                    .build() );
        return ciaAerea.getId();
    }
}
