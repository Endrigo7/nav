package br.com.nassau.nav.services;

import br.com.nassau.nav.entities.Voo;
import br.com.nassau.nav.exceptions.VooNaoEncontradoException;
import br.com.nassau.nav.repositories.VooRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VooService {

    @Autowired
    private VooRepository vooRepository;

    public Voo buscarPorId(String id){
        return vooRepository
                .findById(id)
                .orElseThrow(VooNaoEncontradoException::new);
    }
}
