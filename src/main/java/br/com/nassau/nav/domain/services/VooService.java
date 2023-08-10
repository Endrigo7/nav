package br.com.nassau.nav.domain.services;

import br.com.nassau.nav.domain.entities.Voo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VooService {

    @Autowired
    private CiaAreaService ciaAreaService;

    public List<Voo> listarTodosOsVoos(){
        return null;
    }
}
