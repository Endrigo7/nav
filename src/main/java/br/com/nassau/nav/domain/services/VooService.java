package br.com.nassau.nav.domain.services;

import br.com.nassau.nav.domain.entities.BuscarVoo;
import br.com.nassau.nav.domain.entities.CiaArea;
import br.com.nassau.nav.domain.entities.Voo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class VooService {

    @Autowired
    private CiaAreaService ciaAreaService;

    /*
     * este é um metodo temporario até a chamada aos
     * outros microservices estarem prontas
     */

    private String builderCodigoVoo(CiaArea ciaArea){
        String codigoCiaArea = ciaArea.getNome().substring(0, 2);
        String numeroVoo = String.format("%04d", new Random().nextInt(9999));
        return codigoCiaArea + "-" + numeroVoo;
    }

    private Voo builderVoo(CiaArea ciaArea, BuscarVoo buscarVoo){
        BigDecimal valorVoo = new BigDecimal("100.10").setScale(2, RoundingMode.HALF_EVEN);
        BigDecimal valorComissao = new BigDecimal("1.2").setScale(2, RoundingMode.HALF_EVEN);

        return Voo.builder()
                .ciaAerea(ciaArea)
                .numero(builderCodigoVoo(ciaArea))
                .origem(buscarVoo.getOrigem())
                .destino(buscarVoo.getDestino())
                .valorCiaArea(valorVoo)
                .valorNav(valorVoo.multiply(valorComissao))
                .build();
    }

    public List<Voo> listarTodosOsVoos(BuscarVoo buscarVoo){
        return ciaAreaService
                .listarTodos()
                .stream()
                .map(ciaArea -> builderVoo(ciaArea, buscarVoo))
                .collect(Collectors.toList());
    }
}
